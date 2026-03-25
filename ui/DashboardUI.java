package ui;

import javax.swing.*;

public class DashboardUI {

    public void showDashboard() {

        JFrame frame = new JFrame("Dashboard");

        JButton addTask = new JButton("Add Task");
        JButton viewTask = new JButton("View Tasks");
        JButton logout = new JButton("Logout");

        addTask.setBounds(80, 40, 150, 30);
        viewTask.setBounds(80, 90, 150, 30);
        logout.setBounds(80, 140, 150, 30);

        frame.add(addTask);
        frame.add(viewTask);
        frame.add(logout);

        frame.setSize(300, 250);
        frame.setLayout(null);
        frame.setVisible(true);

        addTask.addActionListener(e -> new TaskUI().showTaskUI());

        viewTask.addActionListener(e -> new ViewTaskUI().showTasks());

        logout.addActionListener(e -> {
            frame.dispose();
            new LoginUI().showLogin();
        });
    }
}