package service;

import dao.TaskDAO;
import model.Task;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;

public class TaskService {

    TaskDAO dao = new TaskDAO();

    // Create: Instantiates and saves a new task.
    public void createTask(String title, String description, String assignee, String deadline) {
        Task task = new Task(title, description, "Pending", assignee, deadline);
        dao.addTask(task);
    }

    // Fetch: Retrieves all tasks from the database layer.
    public ArrayList<Task> getAllTasks() {
        return dao.getTasks();
    }

    // Filter: Returns tasks assigned to a specific user.
    public ArrayList<Task> getTasksForUser(String email) {
        ArrayList<Task> allTasks = dao.getTasks();
        ArrayList<Task> userTasks = new ArrayList<>();
        for(Task t : allTasks) {
            if(t.getAssignee() != null && t.getAssignee().equals(email)) {
                userTasks.add(t);
            }
        }
        return userTasks;
    }

    public boolean updateTaskStatus(int taskId, String status) {
        return dao.updateTaskStatus(taskId, status);
    }

    public boolean deleteTask(int taskId) {
        return dao.deleteTask(taskId);
    }

    public void generateReport() {
        ArrayList<Task> allTasks = dao.getTasks();
        int total = allTasks.size();
        int completed = 0;
        int pending = 0;
        
        for (Task t : allTasks) {
            if ("Done".equals(t.getStatus())) {
                completed++;
            } else {
                pending++;
            }
        }
        
        try (PrintWriter writer = new PrintWriter(new FileWriter("report.txt"))) {
            writer.println("==================================");
            writer.println("      COLLABTASK REPORT           ");
            writer.println("==================================");
            writer.println("Total Tasks: " + total);
            writer.println("Completed Tasks: " + completed);
            writer.println("Pending Tasks: " + pending);
            writer.println("==================================");
            System.out.println("Report generated successfully at report.txt");
        } catch (Exception e) {
            System.err.println("Failed to generate report: " + e.getMessage());
        }
    }
}