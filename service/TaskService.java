package service;

import model.Task;
import model.Task.Status;
import model.Task.Priority;
import validation.Validator;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class TaskService {

    private final List<Task> taskList = new ArrayList<>();
    private final AtomicInteger nextId = new AtomicInteger(1);

    public Task addTask(String title, String description,
                        Priority priority, LocalDate deadline,
                        int assignedUserId, int projectId) {

        String error = Validator.validateTask(title, description, deadline);
        if (error != null) {
            throw new IllegalArgumentException("Validation failed: " + error);
        }

        Task task = new Task(nextId.getAndIncrement(), title, description,
                             priority, deadline, assignedUserId, projectId);
        taskList.add(task);
        System.out.println("Task added successfully: " + task);
        return task;
    }

    public boolean updateTaskStatus(int taskId, Status newStatus) {
        for (Task task : taskList) {
            if (task.getTaskId() == taskId) {
                task.setStatus(newStatus);
                System.out.println("Task #" + taskId + " status updated to: " + newStatus);
                return true;
            }
        }
        throw new IllegalArgumentException("Task with ID " + taskId + " not found.");
    }

    public List<Task> getAllTasks() {
        return new ArrayList<>(taskList); // defensive copy
    }

    public List<Task> getTasksByUser(int userId) {
        List<Task> result = new ArrayList<>();
        for (Task task : taskList) {
            if (task.getAssignedUserId() == userId) {
                result.add(task);
            }
        }
        return result;
    }

    public List<Task> getTasksByProject(int projectId) {
        List<Task> result = new ArrayList<>();
        for (Task task : taskList) {
            if (task.getProjectId() == projectId) {
                result.add(task);
            }
        }
        return result;
    }

    public void printAllTasks() {
        if (taskList.isEmpty()) {
            System.out.println("No tasks found.");
            return;
        }
        System.out.println("\n===== ALL TASKS =====");
        for (Task task : taskList) {
            System.out.println(task);
        }
        System.out.println("=====================\n");
    }
}