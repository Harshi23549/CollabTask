package dao;

import java.util.ArrayList;
import model.Task;

public class TaskDAO {

    private static ArrayList<Task> tasks = new ArrayList<>();

    public void addTask(Task task) {
        tasks.add(task);
        System.out.println("Task Stored: " + task.getTitle());
    }

    public ArrayList<Task> getTasks() {
        return tasks;
    }
}