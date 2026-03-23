import java.time.LocalDate;
import model.Task.Priority;
import model.Task.Status;
import service.TaskService;
import validation.Validator;

/**
 * Main class to test and demonstrate Disha's module:
 *  - Model classes (User, Task, Project)
 *  - Validator (email, empty field, password, deadline)
 *  - TaskService (addTask, updateTaskStatus)
 */
public class Main {

    public static void main(String[] args) {

        System.out.println("========== CollabTask - Disha's Module Demo ==========\n");

        // ── 1. Validator Tests ──────────────────────────────────────────────

        System.out.println("--- Validation Tests ---");

        // Email validation
        System.out.println("Valid email (disha@gmail.com):   " + Validator.isValidEmail("disha@gmail.com"));
        System.out.println("Invalid email (dishaatgmail):    " + Validator.isValidEmail("dishaatgmail"));
        System.out.println("Empty email:                     " + Validator.isValidEmail(""));

        // Password validation
        System.out.println("Valid password (mypass1):        " + Validator.isValidPassword("mypass1"));
        System.out.println("Short password (abc):            " + Validator.isValidPassword("abc"));

        // Deadline validation
        System.out.println("Valid deadline (tomorrow):       " + Validator.isValidDeadline(LocalDate.now().plusDays(1)));
        System.out.println("Past deadline (yesterday):       " + Validator.isValidDeadline(LocalDate.now().minusDays(1)));

        // User registration validation
        System.out.println("\nUser Registration Validation:");
        String regError = Validator.validateUserRegistration("Disha Behl", "dishabehl18@gmail.com", "secure123", "member");
        System.out.println("Valid registration:   " + (regError == null ? "PASSED" : regError));

        String regError2 = Validator.validateUserRegistration("", "bademail", "123", "guest");
        System.out.println("Invalid registration: " + regError2);

        // ── 2. TaskService Tests ────────────────────────────────────────────

        System.out.println("\n--- Task Logic Tests ---");

        TaskService taskService = new TaskService();

        // Add valid tasks
        taskService.addTask("Design Database Schema",
                            "Create tables for User, Task, Project",
                            Priority.HIGH,
                            LocalDate.now().plusDays(5),
                            1, 1);  // assigned to user 1, project 1

        taskService.addTask("Implement Login Page",
                            "Build the login UI using JavaFX",
                            Priority.MEDIUM,
                            LocalDate.now().plusDays(10),
                            2, 1);  // assigned to user 2, project 1

        // Try adding a task with invalid data (past deadline)
        System.out.println("\nAttempting to add task with past deadline:");
        taskService.addTask("Old Task",
                            "This should fail",
                            Priority.LOW,
                            LocalDate.now().minusDays(2),
                            1, 1);

        // Try adding a task with empty title
        System.out.println("\nAttempting to add task with empty title:");
        taskService.addTask("",
                            "No title given",
                            Priority.LOW,
                            LocalDate.now().plusDays(3),
                            1, 1);

        // Print all tasks
        taskService.printAllTasks();

        // Update task status
        System.out.println("Updating Task #1 status to IN_PROGRESS:");
        taskService.updateTaskStatus(1, Status.IN_PROGRESS);

        System.out.println("Updating Task #2 status to COMPLETED:");
        taskService.updateTaskStatus(2, Status.COMPLETED);

        // Try updating a task that doesn't exist
        System.out.println("\nUpdating non-existent Task #99:");
        taskService.updateTaskStatus(99, Status.COMPLETED);

        // Print updated task list
        taskService.printAllTasks();

        // Get tasks by user
        System.out.println("Tasks for User #1:");
        taskService.getTasksByUser(1).forEach(System.out::println);

        System.out.println("\n========== Demo Complete ==========");
    }
}
