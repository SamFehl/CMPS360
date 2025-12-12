import json
import os
import psycopg2
from psycopg2.extras import Json

JSON_FILE = "scraped_jobs.json"

# You can set these as environment variables, or edit defaults here:
PGHOST = os.getenv("PGHOST", "localhost")
PGPORT = os.getenv("PGPORT", "5432")
PGDATABASE = os.getenv("PGDATABASE", "postgres")
PGUSER = os.getenv("PGUSER", "postgres")
PGPASSWORD = os.getenv("PGPASSWORD", "@gL21384")  # change to your real password


INSERT_SQL = """
INSERT INTO scraped_jobs (
  job_id, title, company, location, date_posted, job_type, remote, salary,
  salary_min, salary_max, experience_level, contact_email, description,
  requirements, tags, source_url, raw_json
)
VALUES (
  %(job_id)s, %(title)s, %(company)s, %(location)s,
  %(date_posted)s::date, %(job_type)s, %(remote)s, %(salary)s,
  %(salary_min)s, %(salary_max)s, %(experience_level)s, %(contact_email)s,
  %(description)s,
  %(requirements)s::jsonb, %(tags)s::jsonb, %(source_url)s, %(raw_json)s::jsonb
)
ON CONFLICT (job_id) DO UPDATE SET
  title = EXCLUDED.title,
  company = EXCLUDED.company,
  location = EXCLUDED.location,
  date_posted = EXCLUDED.date_posted,
  job_type = EXCLUDED.job_type,
  remote = EXCLUDED.remote,
  salary = EXCLUDED.salary,
  salary_min = EXCLUDED.salary_min,
  salary_max = EXCLUDED.salary_max,
  experience_level = EXCLUDED.experience_level,
  contact_email = EXCLUDED.contact_email,
  description = EXCLUDED.description,
  requirements = EXCLUDED.requirements,
  tags = EXCLUDED.tags,
  source_url = EXCLUDED.source_url,
  raw_json = EXCLUDED.raw_json,
  scraped_at = NOW();
"""


def main():
    with open(JSON_FILE, "r", encoding="utf-8") as f:
        rows = json.load(f)

    conn = psycopg2.connect(
        host=PGHOST,
        port=PGPORT,
        dbname=PGDATABASE,
        user=PGUSER,
        password=PGPASSWORD,
    )
    conn.autocommit = False

    try:
        with conn.cursor() as cur:
            for r in rows:
                # Ensure JSON fields are JSON strings for jsonb casting
                r = dict(r)  # copy
                r["requirements"] = json.dumps(r.get("requirements", []))
                r["tags"] = json.dumps(r.get("tags", []))
                r["raw_json"] = json.dumps(r)

                cur.execute(INSERT_SQL, r)

        conn.commit()
        print(f"Inserted/updated {len(rows)} rows into scraped_jobs.")
    except Exception as e:
        conn.rollback()
        print("Load failed:", e)
        raise
    finally:
        conn.close()


if __name__ == "__main__":
    main()
