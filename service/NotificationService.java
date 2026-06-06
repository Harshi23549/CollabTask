package service;

import model.Task;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

public class NotificationService {
    
    public List<Task> checkDeadlines(String userEmail) {
        TaskService taskService = new TaskService();
        ArrayList<Task> tasks = taskService.getTasksForUser(userEmail);
        List<Task> overdueTasks = new ArrayList<>();
        LocalDate today = LocalDate.now();

        for (Task t : tasks) {
            if (!"Done".equals(t.getStatus()) && t.getDeadline() != null && !t.getDeadline().isEmpty()) {
                try {
                    LocalDate deadline = LocalDate.parse(t.getDeadline());
                    if (deadline.isBefore(today)) {
                        overdueTasks.add(t);
                    }
                } catch (DateTimeParseException e) {
                    // Ignore improperly formatted dates
                }
            }
        }
        return overdueTasks;
    }
}
