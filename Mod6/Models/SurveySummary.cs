namespace Mod6.Models
{
    public class SurveySummary
    {
        public int TotalSurveys { get; set; }
        public double AverageRating { get; set; }
        public string MostReviewedCourse { get; set; } = string.Empty;
    }
}
