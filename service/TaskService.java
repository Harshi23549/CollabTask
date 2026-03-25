package service;

import dao.TaskDAO;
import model.Task;
import java.util.ArrayList;

public class TaskService {

    TaskDAO dao = new TaskDAO();

    public void createTask(String title, String description) {
        Task task = new Task(title, description);
        dao.addTask(task);
    }

    public ArrayList<Task> getAllTasks() {
        return dao.getTasks();
    }
}