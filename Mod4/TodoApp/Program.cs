using System;
using System.Collections.Generic;

class Program
{
    static List<TaskItem> tasks = new List<TaskItem>();
    static int taskCounter = 1;

    static void Main()
    {
        bool running = true;

        while (running)
        {
            Console.Clear();
            Console.WriteLine("====== TO-DO LIST APP ======");
            Console.WriteLine("1. Add a Task");
            Console.WriteLine("2. View Tasks");
            Console.WriteLine("3. Remove a Task");
            Console.WriteLine("4. Exit");
            Console.Write("\nSelect an option (1-4): ");

            string input = Console.ReadLine();

            switch (input)
            {
                case "1":
                    AddTask();
                    break;

                case "2":
                    ViewTasks();
                    break;

                case "3":
                    RemoveTask();
                    break;

                case "4":
                    running = false;
                    break;

                default:
                    Console.WriteLine("Invalid selection. Press Enter to continue...");
                    Console.ReadLine();
                    break;
            }
        }
    }

    static void AddTask()
    {
        Console.Clear();
        Console.WriteLine("=== Add New Task ===");
        Console.Write("Enter task description: ");
        string description = Console.ReadLine();

        if (string.IsNullOrWhiteSpace(description))
        {
            Console.WriteLine("\n❌ Task description cannot be empty!");
            Console.WriteLine("Press Enter to continue...");
            Console.ReadLine();
            return;
        }

        TaskItem newTask = new TaskItem(taskCounter++, description);
        tasks.Add(newTask);

        Console.WriteLine("\n✔ Task added successfully!");
        Console.WriteLine("Press Enter to continue...");
        Console.ReadLine();
    }

    static void ViewTasks()
    {
        Console.Clear();
        Console.WriteLine("=== Current Tasks ===\n");

        if (tasks.Count == 0)
        {
            Console.WriteLine("No tasks available.\n");
        }
        else
        {
            foreach (var task in tasks)
            {
                Console.WriteLine($"{task.Id}. {task.Description}  (Completed: {task.IsCompleted})");
            }
        }

        Console.WriteLine("\nPress Enter to return to the menu...");
        Console.ReadLine();
    }

    static void RemoveTask()
    {
        Console.Clear();
        Console.WriteLine("=== Remove Task ===\n");

        if (tasks.Count == 0)
        {
            Console.WriteLine("There are no tasks to remove.");
            Console.WriteLine("Press Enter to continue...");
            Console.ReadLine();
            return;
        }

        ViewTasks();
        Console.Write("\nEnter the ID of the task to remove: ");
        string input = Console.ReadLine();

        if (!int.TryParse(input, out int id))
        {
            Console.WriteLine("\n❌ Invalid number!");
            Console.WriteLine("Press Enter to continue...");
            Console.ReadLine();
            return;
        }

        var task = tasks.Find(t => t.Id == id);

        if (task == null)
        {
            Console.WriteLine("\n❌ Task not found!");
        }
        else
        {
            tasks.Remove(task);
            Console.WriteLine("\n✔ Task removed successfully!");
        }

        Console.WriteLine("Press Enter to continue...");
        Console.ReadLine();
    }
}
