using System;
using System.Collections.Generic;

class TaskItem
{
    public int Id { get; set; }
    public string Description { get; set; }
    public bool IsCompleted { get; set; }

    public TaskItem(int id, string description)
    {
        Id = id;
        Description = description;
        IsCompleted = false;
    }
}

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
            Console.WriteLine("4. Mark a Task as Completed");
            Console.WriteLine("5. Exit");
            Console.Write("\nSelect an option (1-5): ");

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
                    MarkTaskCompleted();
                    break;
                case "5":
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
                Console.WriteLine($"{task.Id}. {(task.IsCompleted ? "[✔]" : "[ ]")} {task.Description}");
            }
        }

        Console.WriteLine("\nPress Enter to return to the menu...");
        Console.ReadLine();
    }

    static void MarkTaskCompleted()
    {
        Console.Clear();
        Console.WriteLine("=== Mark Task as Completed ===\n");

        if (tasks.Count == 0)
        {
            Console.WriteLine("There are no tasks to update.");
            Console.WriteLine("Press Enter to continue...");
            Console.ReadLine();
            return;
        }

        // Show existing tasks
        foreach (var task in tasks)
        {
            Console.WriteLine($"{task.Id}. {(task.IsCompleted ? "[✔]" : "[ ]")} {task.Description}");
        }

        Console.Write("\nEnter the ID of the task to mark as completed: ");
        string input = Console.ReadLine();

        if (!int.TryParse(input, out int id))
        {
            Console.WriteLine("\n❌ Invalid number!");
            Console.WriteLine("Press Enter to continue...");
            Console.ReadLine();
            return;
        }

        var taskToUpdate = tasks.Find(t => t.Id == id);

        if (taskToUpdate == null)
        {
            Console.WriteLine("\n❌ Task not found!");
        }
        else
        {
            taskToUpdate.IsCompleted = true;
            Console.WriteLine("\n✔ Task marked as completed!");
        }

        Console.WriteLine("Press Enter to continue...");
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

        // Show tasks here instead of calling ViewTasks()
        foreach (var t in tasks)
        {
            Console.WriteLine($"{t.Id}. {(t.IsCompleted ? "[✔]" : "[ ]")} {t.Description}");
        }

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
