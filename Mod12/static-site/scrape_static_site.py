import json
import re
from urllib.parse import urljoin

import requests
from bs4 import BeautifulSoup


BASE_URL = "http://localhost:8000/static-site/index.html"
OUTFILE = "scraped_jobs.json"


def text_or_none(el):
    return el.get_text(strip=True) if el else None


def list_texts(els):
    return [e.get_text(strip=True) for e in els]


def parse_job_page(job_url: str) -> dict:
    r = requests.get(job_url, timeout=10)
    r.raise_for_status()

    soup = BeautifulSoup(r.text, "lxml")
    job = soup.select_one("article.job")

    # --- 10+ distinct fields ---
    job_id = job.get("data-job-id") if job else None
    title = text_or_none(soup.select_one("[data-field='title']"))
    company = text_or_none(soup.select_one("[data-field='company']"))
    location = text_or_none(soup.select_one("[data-field='location']"))

    date_el = soup.select_one("[data-field='date_posted']")
    date_posted = date_el.get("datetime") if date_el else None

    job_type = text_or_none(soup.select_one("[data-field='job_type']"))
    remote = text_or_none(soup.select_one("[data-field='remote']"))
    salary = text_or_none(soup.select_one("[data-field='salary']"))
    experience_level = text_or_none(soup.select_one("[data-field='experience_level']"))

    contact_el = soup.select_one("[data-field='contact_email']")
    contact_email = None
    if contact_el:
        # mailto:jobs@x.com OR inner text
        href = contact_el.get("href", "")
        if href.startswith("mailto:"):
            contact_email = href.replace("mailto:", "").strip()
        else:
            contact_email = contact_el.get_text(strip=True)

    description = text_or_none(soup.select_one("[data-field='description']"))
    requirements = list_texts(soup.select("[data-field='requirements'] li"))
    tags = list_texts(soup.select("[data-field='tags'] li"))

    # Extra helpful fields (optional, still fine to store)
    source_url = job_url

    # Example: normalize salary a bit (optional)
    salary_min = None
    salary_max = None
    if salary:
        # pulls first two numbers like 70,000 and 85,000 if present
        nums = re.findall(r"[\d,]+", salary)
        if len(nums) >= 1:
            salary_min = int(nums[0].replace(",", ""))
        if len(nums) >= 2:
            salary_max = int(nums[1].replace(",", ""))

    return {
        "job_id": job_id,
        "title": title,
        "company": company,
        "location": location,
        "date_posted": date_posted,
        "job_type": job_type,
        "remote": remote,
        "salary": salary,
        "salary_min": salary_min,
        "salary_max": salary_max,
        "experience_level": experience_level,
        "contact_email": contact_email,
        "description": description,
        "requirements": requirements,
        "tags": tags,
        "source_url": source_url,
    }


def main():
    index = requests.get(BASE_URL, timeout=10)
    index.raise_for_status()

    soup = BeautifulSoup(index.text, "lxml")

    # Find job links from index
    links = soup.select("a.job-link")
    job_urls = [urljoin(BASE_URL, a.get("href")) for a in links if a.get("href")]

    results = []
    for url in job_urls:
        try:
            results.append(parse_job_page(url))
            print(f"Scraped: {url}")
        except Exception as e:
            print(f"Failed: {url} -> {e}")

    with open(OUTFILE, "w", encoding="utf-8") as f:
        json.dump(results, f, indent=2, ensure_ascii=False)

    print(f"\nSaved {len(results)} job records to {OUTFILE}")


if __name__ == "__main__":
    main()
