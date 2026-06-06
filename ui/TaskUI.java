package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import service.TaskService;
import service.AuthService;
import model.User;

public class TaskUI {

    private String userEmail;

    // Initialize: Sets up task UI with default guest email.
    public TaskUI() {
        this.userEmail = "user@example.com";
    }

    // Initialize: Sets up task UI with specific user email.
    public TaskUI(String email) {
        this.userEmail = email;
    }

    // Display: Renders the full screen add task interface.
    public void showTaskUI() {

        AuthService auth = new AuthService();
        User currentUser = auth.getUser(userEmail);
        String role = currentUser != null ? currentUser.getRole() : "Member";
        
        // Members shouldn't be here in the new logic, but if they somehow bypass:
        if (!role.equals("Leader")) {
            new DashboardUI(userEmail).showDashboard();
            return;
        }

        JFrame frame = new JFrame("Add Task");
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH); // Full screen
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(255, 248, 240));

        // Navbar
        JPanel navBar = new JPanel(new BorderLayout());
        navBar.setBackground(new Color(255, 118, 117)); // Bright warm coral red
        navBar.setBorder(BorderFactory.createEmptyBorder(15, 30, 15, 30));

        // Left side: Back Button + Avatar + Email
        JPanel leftNav = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 0));
        leftNav.setOpaque(false);

        JButton backBtn = new JButton("<html><b>&larr;</b> Back</html>");
        backBtn.setFont(new Font("Segoe UI", Font.BOLD, 16));
        backBtn.setBackground(new Color(235, 98, 97)); // Solid slightly darker coral
        backBtn.setForeground(Color.WHITE);
        backBtn.setFocusPainted(false);
        backBtn.setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15));
        backBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        backBtn.setContentAreaFilled(false);
        backBtn.setOpaque(true);
        backBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                backBtn.setBackground(new Color(255, 138, 137)); // Lighter coral on hover
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                backBtn.setBackground(new Color(235, 98, 97));
            }
        });
        backBtn.addActionListener(e -> {
            frame.dispose();
            new DashboardUI(userEmail).showDashboard();
        });

        JPanel avatar = new JPanel() {
            @Override
            // Paint: Custom rendering for the circular user avatar.
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(Color.WHITE);
                g2.fill(new Ellipse2D.Double(0, 0, 40, 40));
                g2.setColor(new Color(255, 118, 117));
                g2.setFont(new Font("Segoe UI", Font.BOLD, 20));
                String initial = userEmail.isEmpty() ? "U" : userEmail.substring(0, 1).toUpperCase();
                FontMetrics fm = g2.getFontMetrics();
                int x = (40 - fm.stringWidth(initial)) / 2;
                int y = (fm.getAscent() + (40 - (fm.getAscent() + fm.getDescent())) / 2);
                g2.drawString(initial, x, y);
                g2.dispose();
            }
        };
        avatar.setPreferredSize(new Dimension(40, 40));
        avatar.setOpaque(false);

        JLabel emailLabel = new JLabel(userEmail);
        emailLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        emailLabel.setForeground(Color.WHITE);

        leftNav.add(backBtn);
        leftNav.add(avatar);
        leftNav.add(emailLabel);

        // Center: App Title
        JLabel titleLabel = new JLabel("CollabTask", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 32));
        titleLabel.setForeground(Color.WHITE);

        navBar.add(leftNav, BorderLayout.WEST);
        navBar.add(titleLabel, BorderLayout.CENTER);

        // Center Form Area
        JPanel centerWrapper = new JPanel(new GridBagLayout());
        centerWrapper.setOpaque(false);

        JPanel panel = new JPanel(null);
        panel.setPreferredSize(new Dimension(500, 550));
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(0, 0, 0, 15), 1),
            BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));

        JLabel header = new JLabel("Create New Task 📝");
        header.setFont(new Font("Segoe UI", Font.BOLD, 28));
        header.setForeground(new Color(45, 52, 54));
        header.setBounds(40, 30, 400, 40);

        JLabel titleLbl = new JLabel("Task Title");
        titleLbl.setFont(new Font("Segoe UI", Font.BOLD, 14));
        titleLbl.setForeground(new Color(99, 110, 114));
        titleLbl.setBounds(40, 90, 400, 20);

        JTextField titleField = new JTextField();
        titleField.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        titleField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(223, 230, 233), 2),
            BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        titleField.setBounds(40, 115, 420, 45);

        JLabel descLbl = new JLabel("Description");
        descLbl.setFont(new Font("Segoe UI", Font.BOLD, 14));
        descLbl.setForeground(new Color(99, 110, 114));
        descLbl.setBounds(40, 175, 400, 20);

        JTextField descField = new JTextField();
        descField.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        descField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(223, 230, 233), 2),
            BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        descField.setBounds(40, 200, 420, 45);

        JLabel assignLbl = new JLabel("Assigned To");
        assignLbl.setFont(new Font("Segoe UI", Font.BOLD, 14));
        assignLbl.setForeground(new Color(99, 110, 114));
        assignLbl.setBounds(40, 260, 400, 20);

        dao.UserDAO userDAO = new dao.UserDAO();
        java.util.ArrayList<User> users = userDAO.getAllUsers();
        java.util.Vector<String> userEmails = new java.util.Vector<>();
        for (User u : users) {
            userEmails.add(u.getEmail());
        }

        JComboBox<String> assignCombo = new JComboBox<>(userEmails);
        assignCombo.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        assignCombo.setBackground(Color.WHITE);
        assignCombo.setBounds(40, 285, 420, 45);

        JLabel deadLbl = new JLabel("Deadline (e.g. YYYY-MM-DD)");
        deadLbl.setFont(new Font("Segoe UI", Font.BOLD, 14));
        deadLbl.setForeground(new Color(99, 110, 114));
        deadLbl.setBounds(40, 345, 400, 20);

        JTextField deadField = new JTextField();
        deadField.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        deadField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(223, 230, 233), 2),
            BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        deadField.setBounds(40, 370, 420, 45);

        JButton addBtn = new JButton("Save Task");
        addBtn.setFont(new Font("Segoe UI", Font.BOLD, 18));
        addBtn.setBackground(new Color(253, 203, 110)); // Matching yellow/orange 
        addBtn.setForeground(new Color(45, 52, 54));
        addBtn.setFocusPainted(false);
        addBtn.setBorder(BorderFactory.createEmptyBorder());
        addBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        addBtn.setBounds(40, 440, 420, 60);

        addBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                addBtn.setBackground(new Color(255, 234, 167));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                addBtn.setBackground(new Color(253, 203, 110));
            }
        });

        panel.add(header);
        panel.add(titleLbl);
        panel.add(titleField);
        panel.add(descLbl);
        panel.add(descField);
        panel.add(assignLbl);
        panel.add(assignCombo);
        panel.add(deadLbl);
        panel.add(deadField);
        panel.add(addBtn);

        centerWrapper.add(panel);

        mainPanel.add(navBar, BorderLayout.NORTH);
        mainPanel.add(centerWrapper, BorderLayout.CENTER);

        frame.add(mainPanel);
        frame.setVisible(true);

        addBtn.addActionListener(e -> {
            if (titleField.getText().trim().isEmpty() || descField.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Please enter title and description");
                return;
            }
            if (deadField.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Please enter a deadline");
                return;
            }
            TaskService service = new TaskService();
            service.createTask(
                titleField.getText().trim(), 
                descField.getText().trim(),
                (String) assignCombo.getSelectedItem(),
                deadField.getText().trim()
            );
            JOptionPane.showMessageDialog(frame, "Task Added Successfully! ✅");
            titleField.setText("");
            descField.setText("");
            deadField.setText("");
        });
    }
}