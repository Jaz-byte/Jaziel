import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Project {
    String name;
    List<Task> tasks;

    Project(String name) {
        this.name = name;
        this.tasks = new ArrayList<>();
    }

    void addTask(String taskName, LocalDate deadline) {
        tasks.add(new Task(taskName, deadline));
        System.out.println("Task '" + taskName + "' added to '" + name + "'.");
    }

    void updateTask(String taskName, String newStatus) {
        for (Task task : tasks) {
            if (task.name.equals(taskName)) {
                task.status = newStatus.toLowerCase(); 
                
                System.out.println("Task '" + taskName + "' updated.");
                return;
            }
        }
        System.out.println("Task not found.");
    }

    LocalDate getUpcomingDeadline() {
        LocalDate upcomingDeadline = null;
        for (Task task : tasks) {
            if (upcomingDeadline == null || task.deadline.isBefore(upcomingDeadline)) {
                upcomingDeadline = task.deadline;
            }
        }
        return upcomingDeadline;
    }
}

class Task {
    String name;
    LocalDate deadline;
    String status;

    Task(String name, LocalDate deadline) {
        this.name = name;
        this.deadline = deadline;
        this.status = "incomplete";
    }
}

class ProjectManager {
    List<Project> projects;
    Scanner scanner;

    ProjectManager() {
        projects = new ArrayList<>();
        scanner = new Scanner(System.in);
    }

    void addTaskToExistingProject() {
        Project project = getProjectFromUser();
        if (project != null) {
            System.out.print("Enter task name: ");
            String taskName = scanner.nextLine();
            LocalDate deadline = getLocalDateFromUser();
            if (deadline != null) {
                project.addTask(taskName, deadline);
                System.out.println("Task '" + taskName + "' added to project '" + project.name + "'.");
            }
        }
    }

    void createProject() {
        System.out.print("Enter File name: ");
        String name = scanner.nextLine();
        Project project = new Project(name);
        projects.add(project);
        while (true) {
            System.out.print("Enter task name (or type 'done'): ");
            String taskName = scanner.nextLine();
            if (taskName.equalsIgnoreCase("done")) {
                break;

                
            }
            LocalDate deadline = getLocalDateFromUser();
            if (deadline != null) {
                project.addTask(taskName, deadline);
            }
        }
        System.out.println("Project '" + name + "' created.");
        
    }

    void updateTask() {
        Project project = getProjectFromUser();
        if (project != null) {
            System.out.print("Enter task name: ");
            String taskName = scanner.nextLine();
            System.out.print("Enter new status (Complete/Incomplete): ");
            String newStatus = scanner.nextLine();
            project.updateTask(taskName, newStatus);
        }
    }

    void viewProjectsOverview() {
        for (Project project : projects) {
            LocalDate upcomingDeadline = project.getUpcomingDeadline();
            System.out.println("File: " + project.name + ", Tasks: " + project.tasks.size() +
                    ", Upcoming Deadline: " + (upcomingDeadline != null ? upcomingDeadline.format(DateTimeFormatter.ISO_DATE) : "N/A"));
        }
    }

    void viewTaskList() {
        Project project = getProjectFromUser();
        if (project != null) {
            for (Task task : project.tasks) {
                System.out.println("Task: " + task.name + ", Status: " + task.status + ", Deadline: " + task.deadline.format(DateTimeFormatter.ISO_DATE));
            }
        }
    }

    void checkDeadlines() {
        LocalDate today = LocalDate.now();
        for (Project project : projects) {
            for (Task task : project.tasks) {
                if (task.status.equalsIgnoreCase("incomplete")) {
                    if (task.deadline.isBefore(today)) {
                        System.out.println("Alert: Task '" + task.name + "' in project '" + project.name + "' is overdue!");
                    } else if (task.deadline.isBefore(today.plusDays(3))) {
                        System.out.println("Alert: Task '" + task.name + "' in project '" + project.name + "' is due soon!");
                    
                    }
                }
            }
        }
    }
    

    Project getProjectFromUser() {
        System.out.print("Enter File name: ");
        String projectName = scanner.nextLine();
        for (Project project : projects) {
            if (project.name.equals(projectName)) {
                return project;
            }
        }
        System.out.println("Project not found.");
        return null;
    }

    LocalDate getLocalDateFromUser() {
        while (true) {
        System.out.print("Enter deadline (YYYY-MM-DD): ");
        String dateStr = scanner.nextLine();
        try {
            return LocalDate.parse(dateStr, DateTimeFormatter.ISO_DATE);
        } catch (DateTimeParseException e) {
            System.out.println("Invalid date format. Please use YYYY-MM-DD.");
            return null;
        }
    }
    }

    public static void main(String[] args) {
        ProjectManager manager = new ProjectManager();
        Scanner scanner = new Scanner(System.in);


        while (true) {
            String T = "";
            
        T = "╔══════════════════════════════════════════════════════╗\n" +
            "║              Project Management Tool                 ║\n" +
            "╠══════════════════════════════════════════════════════╣\n" +
            "║Menu:                                                 ║\n" +
            "║  1. Create project                                   ║\n" +
            "║  2. Add task to existing project                     ║\n" +
            "║  3. Update task                                      ║\n" +
            "║  4. View projects overview                           ║\n" +
            "║  5. View task list                                   ║\n" +
            "║  6. Check deadlines                                  ║\n" +
            "║  7. Exit                                             ║\n" +
            "╠══════════════════════════════════════════════════════╣";
            System.out.println(T);

            
            System.out.print  ("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    manager.createProject();
                    break;
                case 2:
                    manager.addTaskToExistingProject();
                    break;
                case 3:
                    manager.updateTask();
                    break;
                case 4:
                    manager.viewProjectsOverview();
                    break;
                case 5:
                    manager.viewTaskList();
                    break;
                case 6:
                    manager.checkDeadlines();
                    break;
                case 7:
                    System.out.println("Exiting...");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid choice.");
        }
      }
    } 
}                             