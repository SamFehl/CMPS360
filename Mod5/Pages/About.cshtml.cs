using Microsoft.AspNetCore.Mvc.RazorPages;

namespace MyWebApp.Pages
{
    public class AboutModel : PageModel
    {
        public string Message { get; set; } = string.Empty;

        public void OnGet()
        {
            Message = "This application is a class project showcasing Razor Pages, navigation, and backend logic.";
        }
    }
}
