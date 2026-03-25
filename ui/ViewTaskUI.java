package ui;

import javax.swing.*;
import service.TaskService;
import model.Task;
import java.util.ArrayList;

public class ViewTaskUI {

    public void showTasks() {

        JFrame frame = new JFrame("Tasks");

        JTextArea area = new JTextArea();

        TaskService service = new TaskService();
        ArrayList<Task> tasks = service.getAllTasks();

        StringBuilder data = new StringBuilder();

        for (Task t : tasks) {
            data.append(t.getTitle()).append(" - ").append(t.getDescription()).append("\n");
        }

        area.setText(data.toString());
        frame.add(new JScrollPane(area));

        frame.setSize(300, 300);
        frame.setVisible(true);
    }
}