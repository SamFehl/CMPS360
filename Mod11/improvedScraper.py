import requests
from bs4 import BeautifulSoup
import json

def scrape_python_jobs(job_keyword, location_keyword, num_pages):
    base_url = "https://www.python.org/jobs/"
    jobs = []

    headers = {
        "User-Agent": "Mozilla/5.0 (Windows NT 10.0; Win64; x64) "
                      "AppleWebKit/537.36 (KHTML, like Gecko) "
                      "Chrome/120.0 Safari/537.36"
    }

    job_keyword = (job_keyword or "").strip().lower()
    location_keyword = (location_keyword or "").strip().lower()

    for page in range(num_pages):
        url = base_url if page == 0 else f"{base_url}?page={page + 1}"

        response = requests.get(url, headers=headers, timeout=30)

        # ✅ Python.org returns 404 when you go past the last page — stop cleanly
        if response.status_code == 404:
            print(f"Reached last page at page {page + 1} (404). Stopping.")
            break

        if response.status_code != 200:
            print(f"Request failed ({response.status_code}) on page {page + 1}")
            continue

        soup = BeautifulSoup(response.text, "html.parser")

        # Listings are <li> items in the jobs list
        job_listings = soup.select("ol.list-recent-jobs li") or soup.select("ol li")

        for job in job_listings:
            title_tag = job.select_one("h2 a")
            if not title_tag:
                continue

            title = title_tag.get_text(strip=True)
            job_url = "https://www.python.org" + title_tag.get("href", "")

            company_tag = job.select_one(".listing-company-name")
            company = company_tag.get_text(" ", strip=True) if company_tag else "N/A"

            location_tag = job.select_one(".listing-location")
            job_location = location_tag.get_text(" ", strip=True) if location_tag else "N/A"

            # Python.org usually doesn't show salary/summary like Indeed; keep placeholders
            summary = None
            salary = None

            # ✅ Looser filtering
            # Keyword: match in title OR company OR location (more forgiving)
            if job_keyword:
                haystack = f"{title} {company} {job_location}".lower()
                if job_keyword not in haystack:
                    continue

            # Location: allow partial match OR allow "remote" jobs when user enters a city
            if location_keyword:
                loc = job_location.lower()
                if location_keyword not in loc and "remote" not in loc:
                    continue

            jobs.append({
                "title": title,
                "company": company,
                "location": job_location,
                "summary": summary,
                "salary": salary,
                "url": job_url
            })

    return jobs


def save_jobs_to_json(jobs, filename="jobs.json"):
    with open(filename, "w", encoding="utf-8") as f:
        json.dump(jobs, f, indent=4, ensure_ascii=False)


if __name__ == "__main__":
    job_title = input("Enter job title to search for: ").strip()
    location = input("Enter job location: ").strip()
    num_pages = int(input("Enter number of pages to scrape: ").strip())

    jobs = scrape_python_jobs(job_title, location, num_pages)
    save_jobs_to_json(jobs, "jobs.json")
    print(f"Scraped {len(jobs)} job listings and saved to jobs.json")
