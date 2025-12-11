using System.Net.Http;
using System.Text.Json;
using Microsoft.AspNetCore.Mvc.RazorPages;
using Mod6.Models;

namespace Mod6.Pages
{
    public class StatsModel : PageModel
    {
        private readonly IHttpClientFactory _httpClientFactory;

        public SurveySummary? Summary { get; set; }
        public string RatingMessage { get; set; } = string.Empty;

        public StatsModel(IHttpClientFactory httpClientFactory)
        {
            _httpClientFactory = httpClientFactory;
        }

        public async Task OnGetAsync()
        {
            var client = _httpClientFactory.CreateClient("SurveyApi");
            try
            {
                var response = await client.GetAsync("api/surveys/summary");

                if (!response.IsSuccessStatusCode)
                {
                    Console.WriteLine("API summary call failed: " + response.StatusCode);
                    return;
                }

                var json = await response.Content.ReadAsStringAsync();
                Summary = JsonSerializer.Deserialize<SurveySummary>(json,
                    new JsonSerializerOptions { PropertyNameCaseInsensitive = true });

                if (Summary != null)
                {
                    if (Summary.AverageRating >= 4.0)
                    {
                        RatingMessage = "Overall student satisfaction is high.";
                    }
                    else if (Summary.AverageRating >= 3.0)
                    {
                        RatingMessage = "Student satisfaction is moderate. There may be room for improvement.";
                    }
                    else
                    {
                        RatingMessage = "Student satisfaction appears low. Course changes may be needed.";
                    }
                }
            }
            catch (Exception ex)
            {
                Console.WriteLine("Error calling API summary: " + ex.Message);
            }
        }
    }
}
