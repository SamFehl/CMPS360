using Microsoft.AspNetCore.Mvc;
using Microsoft.AspNetCore.Mvc.RazorPages;

namespace MyWebApp.Pages
{
    public class GradesModel : PageModel
    {
        [BindProperty]
        public int? Score { get; set; }

        public string ResultMessage { get; set; } = string.Empty;

        public void OnGet()
        {
            // Default view with no score yet
        }

        public void OnPost()
        {
            if (Score == null)
            {
                ResultMessage = "Please enter a grade between 0 and 100.";
                return;
            }

            if (Score < 0 || Score > 100)
            {
                ResultMessage = "Invalid grade. Please enter a value between 0 and 100.";
            }
            else if (Score >= 90)
            {
                ResultMessage = $"Score {Score}: Excellent! You received an A.";
            }
            else if (Score >= 80)
            {
                ResultMessage = $"Score {Score}: Great job! You received a B.";
            }
            else if (Score >= 70)
            {
                ResultMessage = $"Score {Score}: You passed with a C.";
            }
            else
            {
                ResultMessage = $"Score {Score}: Unfortunately, this is below passing. Consider reviewing the material.";
            }
        }
    }
}
