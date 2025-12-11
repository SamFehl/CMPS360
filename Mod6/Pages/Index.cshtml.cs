using System.Net.Http;
using System.Text.Json;
using Microsoft.AspNetCore.Mvc.RazorPages;
using Mod6.Models;

namespace Mod6.Pages
{
    public class IndexModel : PageModel
    {
        private readonly IHttpClientFactory _httpClientFactory;

        public SurveySummary? Summary { get; set; }

        public IndexModel(IHttpClientFactory httpClientFactory)
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
            }
            catch (Exception ex)
            {
                Console.WriteLine("Error calling API summary: " + ex.Message);
            }
        }
    }
}
