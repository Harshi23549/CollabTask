package ui;

import javax.swing.*;
import service.AuthService;

public class LoginUI {

    public void showLogin() {

        JFrame frame = new JFrame("Login");

        JTextField email = new JTextField();
        JPasswordField password = new JPasswordField();
        JButton loginBtn = new JButton("Login");

        email.setBounds(50, 50, 200, 30);
        password.setBounds(50, 100, 200, 30);
        loginBtn.setBounds(80, 150, 100, 30);

        frame.add(email);
        frame.add(password);
        frame.add(loginBtn);

        frame.setSize(300, 250);
        frame.setLayout(null);
        frame.setVisible(true);

        loginBtn.addActionListener(e -> {

            AuthService auth = new AuthService();
            boolean success = auth.login(email.getText(), new String(password.getPassword()));

            if (success) {
                JOptionPane.showMessageDialog(frame, "Login Success");
                frame.dispose();
                new DashboardUI().showDashboard();
            } else {
                JOptionPane.showMessageDialog(frame, "Invalid Credentials");
            }
        });
    }
}