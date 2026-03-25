package ui;

import javax.swing.*;
import service.TaskService;

public class TaskUI {

    public void showTaskUI() {

        JFrame frame = new JFrame("Add Task");

        JTextField title = new JTextField();
        JTextField desc = new JTextField();
        JButton add = new JButton("Add");

        title.setBounds(50, 40, 200, 30);
        desc.setBounds(50, 90, 200, 30);
        add.setBounds(80, 140, 100, 30);

        frame.add(title);
        frame.add(desc);
        frame.add(add);

        frame.setSize(300, 250);
        frame.setLayout(null);
        frame.setVisible(true);

        add.addActionListener(e -> {

            TaskService service = new TaskService();
            service.createTask(title.getText(), desc.getText());

            JOptionPane.showMessageDialog(frame, "Task Added");
        });
    }
}