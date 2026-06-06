package ui;

import javax.swing.*;
import java.awt.*;
import service.AuthService;

public class RegisterUI {

    public void showRegister() {
        JFrame frame = new JFrame("CollabTask - Register");
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

        // Actual Register Box
        JPanel registerBox = new JPanel(null);
        registerBox.setPreferredSize(new Dimension(500, 650));
        registerBox.setBackground(Color.WHITE);
        registerBox.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(0, 0, 0, 15), 1),
            BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));

        JLabel title = new JLabel("Create Account", SwingConstants.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 32));
        title.setForeground(new Color(45, 52, 54));
        title.setBounds(50, 30, 400, 40);

        // Name
        JLabel nameLabel = new JLabel("Full Name");
        nameLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        nameLabel.setBounds(50, 90, 400, 20);
        JTextField nameField = new JTextField();
        nameField.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        nameField.setBounds(50, 115, 400, 45);

        // Email
        JLabel emailLabel = new JLabel("Email");
        emailLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        emailLabel.setBounds(50, 175, 400, 20);
        JTextField emailField = new JTextField();
        emailField.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        emailField.setBounds(50, 200, 400, 45);

        // Password
        JLabel passwordLabel = new JLabel("Password");
        passwordLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        passwordLabel.setBounds(50, 260, 400, 20);
        JPasswordField passwordField = new JPasswordField();
        passwordField.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        passwordField.setBounds(50, 285, 400, 45);

        // Role
        JLabel roleLabel = new JLabel("Role");
        roleLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        roleLabel.setBounds(50, 345, 400, 20);
        JComboBox<String> roleCombo = new JComboBox<>(new String[]{"Member", "Leader"});
        roleCombo.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        roleCombo.setBounds(50, 370, 400, 45);

        JButton registerBtn = new JButton("Register");
        registerBtn.setFont(new Font("Segoe UI", Font.BOLD, 18));
        registerBtn.setBackground(new Color(9, 132, 227)); 
        registerBtn.setForeground(Color.WHITE);
        registerBtn.setFocusPainted(false);
        registerBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        registerBtn.setBounds(50, 450, 400, 50);

        JButton backBtn = new JButton("Back to Login");
        backBtn.setFont(new Font("Segoe UI", Font.BOLD, 16));
        backBtn.setBackground(Color.WHITE);
        backBtn.setForeground(new Color(9, 132, 227));
        backBtn.setFocusPainted(false);
        backBtn.setBorder(BorderFactory.createLineBorder(new Color(9, 132, 227), 2));
        backBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        backBtn.setBounds(50, 520, 400, 50);

        backBtn.addActionListener(e -> {
            frame.dispose();
            new LoginUI().showLogin();
        });

        registerBtn.addActionListener(e -> {
            String name = nameField.getText().trim();
            String email = emailField.getText().trim();
            String pass = new String(passwordField.getPassword()).trim();
            String role = (String) roleCombo.getSelectedItem();

            if (name.isEmpty() || email.isEmpty() || pass.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Please fill all fields");
                return;
            }

            AuthService auth = new AuthService();
            boolean success = auth.register(name, email, pass, role);
            if (success) {
                JOptionPane.showMessageDialog(frame, "Registration Successful! You can now log in.");
                frame.dispose();
                new LoginUI().showLogin();
            } else {
                JOptionPane.showMessageDialog(frame, "Registration Failed! Email might already be taken.");
            }
        });

        registerBox.add(title);
        registerBox.add(nameLabel);
        registerBox.add(nameField);
        registerBox.add(emailLabel);
        registerBox.add(emailField);
        registerBox.add(passwordLabel);
        registerBox.add(passwordField);
        registerBox.add(roleLabel);
        registerBox.add(roleCombo);
        registerBox.add(registerBtn);
        registerBox.add(backBtn);

        centerWrapper.add(registerBox);

        mainPanel.add(navBar, BorderLayout.NORTH);
        mainPanel.add(centerWrapper, BorderLayout.CENTER);

        frame.add(mainPanel);
        frame.getRootPane().setDefaultButton(registerBtn);
        frame.setVisible(true);
    }
}
