package ui;

import javax.swing.*;
import java.awt.*;
import service.AuthService;

public class LoginUI {

    // Display: Renders the full screen login interface.
    public void showLogin() {
        JFrame frame = new JFrame("CollabTask - Login");
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH); // Full screen
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(255, 248, 240));

        // Top Branding Bar
        JPanel navBar = new JPanel(new BorderLayout());
        navBar.setBackground(new Color(255, 118, 117)); // Coral red
        navBar.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));
        JLabel titleLabel = new JLabel("CollabTask", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 36));
        titleLabel.setForeground(Color.WHITE);
        navBar.add(titleLabel, BorderLayout.CENTER);

        // Center Login Panel wrapper for centering
        JPanel centerWrapper = new JPanel(new GridBagLayout());
        centerWrapper.setOpaque(false);

        // Actual Login Box
        JPanel loginBox = new JPanel(null);
        loginBox.setPreferredSize(new Dimension(450, 550));
        loginBox.setBackground(Color.WHITE);
        loginBox.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(0, 0, 0, 15), 1),
            BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));

        JLabel title = new JLabel("Welcome Back", SwingConstants.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 32));
        title.setForeground(new Color(45, 52, 54));
        title.setBounds(50, 40, 350, 40);

        JLabel subtitle = new JLabel("Please login to your account", SwingConstants.CENTER);
        subtitle.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        subtitle.setForeground(new Color(99, 110, 114));
        subtitle.setBounds(50, 90, 350, 20);

        JLabel emailLabel = new JLabel("Email");
        emailLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        emailLabel.setForeground(new Color(45, 52, 54));
        emailLabel.setBounds(50, 150, 350, 20);

        JTextField emailField = new JTextField();
        emailField.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        emailField.setBackground(new Color(250, 250, 250));
        emailField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(223, 230, 233), 2),
            BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        emailField.setBounds(50, 175, 350, 45);

        JLabel passwordLabel = new JLabel("Password");
        passwordLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        passwordLabel.setForeground(new Color(45, 52, 54));
        passwordLabel.setBounds(50, 240, 350, 20);

        JPasswordField passwordField = new JPasswordField();
        passwordField.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        passwordField.setBackground(new Color(250, 250, 250));
        passwordField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(223, 230, 233), 2),
            BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        passwordField.setBounds(50, 265, 350, 45);

        JButton loginBtn = new JButton("Log In");
        loginBtn.setFont(new Font("Segoe UI", Font.BOLD, 18));
        loginBtn.setBackground(new Color(9, 132, 227)); // Bright Blue
        loginBtn.setForeground(Color.WHITE);
        loginBtn.setFocusPainted(false);
        loginBtn.setBorder(BorderFactory.createEmptyBorder());
        loginBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        loginBtn.setBounds(50, 350, 350, 50);

        loginBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                loginBtn.setBackground(new Color(116, 185, 255));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                loginBtn.setBackground(new Color(9, 132, 227));
            }
        });

        loginBox.add(title);
        loginBox.add(subtitle);
        loginBox.add(emailLabel);
        loginBox.add(emailField);
        loginBox.add(passwordLabel);
        loginBox.add(passwordField);
        loginBox.add(loginBtn);

        JButton registerBtn = new JButton("Register New Account");
        registerBtn.setFont(new Font("Segoe UI", Font.BOLD, 16));
        registerBtn.setBackground(new Color(255, 255, 255));
        registerBtn.setForeground(new Color(9, 132, 227));
        registerBtn.setFocusPainted(false);
        registerBtn.setBorder(BorderFactory.createLineBorder(new Color(9, 132, 227), 2));
        registerBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        registerBtn.setBounds(50, 420, 350, 50);

        registerBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                registerBtn.setBackground(new Color(240, 248, 255));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                registerBtn.setBackground(new Color(255, 255, 255));
            }
        });

        registerBtn.addActionListener(e -> {
            frame.dispose();
            new RegisterUI().showRegister();
        });

        loginBox.add(registerBtn);

        centerWrapper.add(loginBox);

        mainPanel.add(navBar, BorderLayout.NORTH);
        mainPanel.add(centerWrapper, BorderLayout.CENTER);

        frame.add(mainPanel);
        frame.getRootPane().setDefaultButton(loginBtn);
        frame.setVisible(true);

        Runnable loginAction = () -> {
            String email = emailField.getText().trim();
            String password = new String(passwordField.getPassword()).trim();

            if (email.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Please fill all fields");
                return;
            }

            AuthService auth = new AuthService();
            boolean success = auth.login(email, password);

            if (success) {
                frame.dispose(); 
                new DashboardUI(email).showDashboard(); 
            } else {
                JOptionPane.showMessageDialog(frame, "Invalid Credentials");
            }
        };

        loginBtn.addActionListener(e -> loginAction.run());
        passwordField.addActionListener(e -> loginAction.run());
    }
}