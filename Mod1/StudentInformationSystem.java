import java.util.Scanner;

public class StudentInformationSystem {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Welcome to the Student Information System!\n");

        //Name
        System.out.print("Enter your name: ");
        String name = scanner.nextLine();

        //Age
        int age;
        while (true) {
            System.out.print("Enter your age: ");
            age = scanner.nextInt();

            if (age > 0) {
                break;
            } else {
                System.out.println("Invalid age. Please enter a positive integer.");
            }
        }

        //GPA
        double gpa;
        while (true) {
            System.out.print("Enter your GPA (0.0 - 4.0): ");
            gpa = scanner.nextDouble();

            if (gpa >= 0.0 && gpa <= 4.0) {
                break;
            } else {
                System.out.println("Invalid GPA. Please enter a value between 0.0 and 4.0.");
            }
        }

        //Credits
        System.out.print("Enter the number of completed credits: ");
        int completedCredits = scanner.nextInt();

        //Subject Hours
        double[] subjectHours = new double[5];
        double totalWeeklyHours = 0.0;

        for (int i = 0; i < subjectHours.length; i++) {
            System.out.print("Enter the hours you study per week for subject " + (i + 1) + ": ");
            subjectHours[i] = scanner.nextDouble();

            totalWeeklyHours += subjectHours[i];
        }

        //Calcs
        //Avg hours per day
        double avgStudyHoursPerDay = totalWeeklyHours / 7.0;

        //hours per semester
        double totalStudyHoursPerSemester = totalWeeklyHours * 16;

        //remaining credits
        final int TOTAL_CREDITS_NEEDED = 120;
        int remainingCredits = TOTAL_CREDITS_NEEDED - completedCredits;
        if (remainingCredits < 0) {
            remainingCredits = 0;
        }

        //print
        System.out.println("\n--- Student Summary ---");
        System.out.println("Name: " + name);
        System.out.println("Age: " + age);
        System.out.printf("GPA: %.2f%n", gpa);
        System.out.println("Completed Credits: " + completedCredits);
        System.out.println("Remaining Credits: " + remainingCredits);

        System.out.println();
        System.out.printf("Average Study Hours Per Day: %.2f%n", avgStudyHoursPerDay);
        System.out.printf("Total Study Hours Per Semester: %.0f%n", totalStudyHoursPerSemester);

        scanner.close();
    }
}
