import time
import json
import requests
from bs4 import BeautifulSoup

def scrape_indeed_jobs(job_title, location, num_pages=1):
    q = job_title.replace(" ", "+")
    l = location.replace(" ", "+")
    base_url = f"https://www.indeed.com/jobs?q={q}&l={l}"

    headers = {
        "User-Agent": "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120 Safari/537.36"
    }

    jobs = []

    for page in range(num_pages):
        url = f"{base_url}&start={page * 10}"
        resp = requests.get(url, headers=headers, timeout=30)

        if resp.status_code != 200:
            print(f"Request failed ({resp.status_code}) on page {page+1}")
            continue

        soup = BeautifulSoup(resp.text, "html.parser")

        # Newer Indeed pages often use 'a' tags with data-jk, or cards with 'job_seen_beacon'
        cards = soup.select("a[data-jk]") or soup.select(".job_seen_beacon")

        if not cards:
            print(f"No job cards found on page {page+1}. HTML structure may have changed.")
            # Optional: print a small snippet for debugging
            # print(soup.prettify()[:1000])
            continue

        for card in cards:
            # Try multiple common patterns
            title_el = card.select_one("h2 span") or card.select_one("[aria-label]")
            company_el = card.select_one("[data-testid='company-name']") or card.select_one(".companyName")
            loc_el = card.select_one("[data-testid='text-location']") or card.select_one(".companyLocation")
            summary_el = card.select_one(".job-snippet") or card.select_one("[data-testid='job-snippet']")
            salary_el = card.select_one(".salary-snippet") or card.select_one("[data-testid='attribute_snippet_testid']")

            title = title_el.get_text(strip=True) if title_el else None
            company = company_el.get_text(strip=True) if company_el else None
            loc_text = loc_el.get_text(strip=True) if loc_el else None
            summary = summary_el.get_text(" ", strip=True) if summary_el else None
            salary = salary_el.get_text(" ", strip=True) if salary_el else None

            # Some selectors may return huge blocks; keep only meaningful entries
            if title or company:
                jobs.append({
                    "title": title,
                    "company": company,
                    "location": loc_text,
                    "summary": summary,
                    "salary": salary
                })

        time.sleep(1)  # be polite / reduce blocking risk

    return jobs

def save_jobs_to_json(jobs, filename="jobs.json"):
    with open(filename, "w", encoding="utf-8") as f:
        json.dump(jobs, f, indent=4, ensure_ascii=False)

if __name__ == "__main__":
    job_title = input("Enter job title to search for: ").strip()
    location = input("Enter job location: ").strip()
    num_pages = int(input("Enter number of pages to scrape: ").strip())

    jobs = scrape_indeed_jobs(job_title, location, num_pages)
    save_jobs_to_json(jobs, "jobs.json")
    print(f"Scraped {len(jobs)} job listings and saved to jobs.json")
