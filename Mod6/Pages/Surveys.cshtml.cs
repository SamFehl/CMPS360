using System.Net.Http;
using System.Text.Json;
using Microsoft.AspNetCore.Mvc.RazorPages;
using Mod6.Models;

namespace Mod6.Pages
{
    public class SurveysModel : PageModel
    {
        private readonly IHttpClientFactory _httpClientFactory;

        public List<Survey> Surveys { get; set; } = new();

        public SurveysModel(IHttpClientFactory httpClientFactory)
        {
            _httpClientFactory = httpClientFactory;
        }

        public async Task OnGetAsync()
        {
            var client = _httpClientFactory.CreateClient("SurveyApi");
            try
            {
                var response = await client.GetAsync("api/surveys");

                if (!response.IsSuccessStatusCode)
                {
                    Console.WriteLine("API surveys call failed: " + response.StatusCode);
                    return;
                }

                var json = await response.Content.ReadAsStringAsync();
                var data = JsonSerializer.Deserialize<List<Survey>>(json,
                    new JsonSerializerOptions { PropertyNameCaseInsensitive = true });

                if (data != null)
                {
                    Surveys = data;
                }
            }
            catch (Exception ex)
            {
                Console.WriteLine("Error calling API surveys: " + ex.Message);
            }
        }
    }
}
