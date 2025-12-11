using Microsoft.AspNetCore.Mvc;
using Microsoft.AspNetCore.Mvc.RazorPages;

namespace MyWebApp.Pages
{
    public class WeatherModel : PageModel
    {
        [BindProperty]
        public int? TemperatureF { get; set; }

        public string Advice { get; set; } = string.Empty;

        public void OnGet()
        {
            // Initial state
        }

        public void OnPost()
        {
            if (TemperatureF == null)
            {
                Advice = "Please enter a temperature.";
                return;
            }

            int temp = TemperatureF.Value;

            if (temp <= 32)
            {
                Advice = $"It's {temp}°F outside: It's freezing! Wear a heavy coat, hat, and gloves.";
            }
            else if (temp <= 60)
            {
                Advice = $"It's {temp}°F: A bit chilly. You might want a light jacket.";
            }
            else if (temp <= 80)
            {
                Advice = $"It's {temp}°F: Very comfortable weather. Perfect for outdoor activities.";
            }
            else
            {
                Advice = $"It's {temp}°F: It's hot! Stay hydrated and consider lighter clothing.";
            }
        }
    }
}
