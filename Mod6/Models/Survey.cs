namespace Mod6.Models
{
    public class Survey
    {
        public int Id { get; set; }
        public string CourseCode { get; set; } = string.Empty;
        public string Instructor { get; set; } = string.Empty;
        public double Rating { get; set; }
        public string Comments { get; set; } = string.Empty;
        public DateTime CreatedAt { get; set; }
    }
}
