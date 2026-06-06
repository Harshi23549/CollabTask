package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;
import service.AuthService;
import service.TaskService;
import service.NotificationService;
import model.User;
import model.Task;
import java.util.List;

public class DashboardUI {

    private String userEmail;

    // Initialize: Sets up dashboard with default guest email.
    public DashboardUI() {
        this.userEmail = "user@example.com";
    }

    // Initialize: Sets up dashboard with specific user email.
    public DashboardUI(String email) {
        this.userEmail = email;
    }

    // Display: Renders the full screen dashboard interface.
    public void showDashboard() {
        JFrame frame = new JFrame("Dashboard");
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH); // Full screen
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        AuthService auth = new AuthService();
        User currentUser = auth.getUser(userEmail);
        String role = currentUser != null ? currentUser.getRole() : "Member";

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(255, 248, 240)); // Warm light background

        // Navbar
        JPanel navBar = new JPanel(new BorderLayout());
        navBar.setBackground(new Color(255, 118, 117)); // Bright warm coral red
        navBar.setBorder(BorderFactory.createEmptyBorder(15, 30, 15, 30));

        // Left side: User Pic + Email
        JPanel leftNav = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 0));
        leftNav.setOpaque(false);

        // Circular Avatar
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

        JLabel emailLabel = new JLabel(userEmail + " (" + role + ")");
        emailLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        emailLabel.setForeground(Color.WHITE);

        leftNav.add(avatar);
        leftNav.add(emailLabel);

        // Center: App Title
        JLabel titleLabel = new JLabel("CollabTask", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 32));
        titleLabel.setForeground(Color.WHITE);

        // Right side: Logout
        JPanel rightNav = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
        rightNav.setOpaque(false);
        JButton logoutBtn = new JButton("Logout");
        logoutBtn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        logoutBtn.setBackground(new Color(235, 98, 97)); // Solid slightly darker coral
        logoutBtn.setForeground(Color.WHITE);
        logoutBtn.setFocusPainted(false);
        logoutBtn.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        logoutBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        logoutBtn.setContentAreaFilled(false);
        logoutBtn.setOpaque(true);
        logoutBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                logoutBtn.setBackground(new Color(255, 138, 137)); // Lighter coral on hover
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                logoutBtn.setBackground(new Color(235, 98, 97));
            }
        });
        logoutBtn.addActionListener(e -> {
            frame.dispose();
            new LoginUI().showLogin();
        });
        rightNav.add(logoutBtn);

        navBar.add(leftNav, BorderLayout.WEST);
        navBar.add(titleLabel, BorderLayout.CENTER);
        navBar.add(rightNav, BorderLayout.EAST);

        // Center Content Area
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setOpaque(false);
        contentPanel.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));

        // Visual Progress Indicator
        TaskService taskService = new TaskService();
        ArrayList<Task> tasks = role.equals("Leader") ? taskService.getAllTasks() : taskService.getTasksForUser(userEmail);
        
        int total = tasks.size();
        int completed = 0;
        for (Task t : tasks) {
            if (t.isCompleted()) completed++;
        }
        int progress = total == 0 ? 0 : (int)((completed * 100.0) / total);

        JPanel progressPanel = new JPanel(new BorderLayout(10, 10));
        progressPanel.setOpaque(false);
        progressPanel.setMaximumSize(new Dimension(800, 120));
        
        JLabel progressTitle = new JLabel(role.equals("Leader") ? "Team Overall Progress" : "My Personal Progress");
        progressTitle.setFont(new Font("Segoe UI", Font.BOLD, 24));
        progressTitle.setForeground(new Color(45, 52, 54));
        
        JProgressBar progressBar = new JProgressBar(0, 100);
        progressBar.setValue(progress);
        progressBar.setStringPainted(true);
        progressBar.setString(progress + "% Complete (" + completed + "/" + total + " Tasks)");
        progressBar.setFont(new Font("Segoe UI", Font.BOLD, 18));
        progressBar.setPreferredSize(new Dimension(800, 50));
        progressBar.setForeground(new Color(116, 185, 255));
        progressBar.setBackground(new Color(223, 230, 233));
        progressBar.setBorderPainted(false);

        progressPanel.add(progressTitle, BorderLayout.NORTH);
        progressPanel.add(progressBar, BorderLayout.CENTER);
        progressPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Action Buttons
        JPanel buttonBox = new JPanel(new GridLayout(1, role.equals("Leader") ? 3 : 1, 50, 0));
        buttonBox.setOpaque(false);
        buttonBox.setMaximumSize(new Dimension(role.equals("Leader") ? 950 : 300, 250));

        if (role.equals("Leader")) {
            JButton addTaskBtn = createActionCard("Add Task", "📝", new Color(253, 203, 110)); 
            addTaskBtn.addActionListener(e -> {
                frame.dispose();
                new TaskUI(userEmail).showTaskUI();
            });
            buttonBox.add(addTaskBtn);
            
            JButton reportBtn = createActionCard("Generate Report", "📊", new Color(85, 239, 196)); 
            reportBtn.addActionListener(e -> {
                taskService.generateReport();
                JOptionPane.showMessageDialog(frame, "Report generated successfully as report.txt!");
            });
            buttonBox.add(reportBtn);
        }

        JButton viewTaskBtn = createActionCard(role.equals("Leader") ? "View All Tasks" : "My Tasks", "📚", new Color(116, 185, 255));
        viewTaskBtn.addActionListener(e -> {
            frame.dispose();
            new ViewTaskUI(userEmail).showTasks();
        });
        buttonBox.add(viewTaskBtn);

        contentPanel.add(progressPanel);
        contentPanel.add(Box.createVerticalStrut(50));
        contentPanel.add(buttonBox);

        mainPanel.add(navBar, BorderLayout.NORTH);
        mainPanel.add(contentPanel, BorderLayout.CENTER);

        frame.add(mainPanel);
        frame.setVisible(true);

        // Check Notifications
        NotificationService notifService = new NotificationService();
        List<Task> overdue = notifService.checkDeadlines(userEmail);
        if (!overdue.isEmpty()) {
            StringBuilder sb = new StringBuilder("⚠️ You have overdue tasks:\n\n");
            for (Task t : overdue) {
                sb.append("- ").append(t.getTitle()).append(" (Deadline: ").append(t.getDeadline()).append(")\n");
            }
            JOptionPane.showMessageDialog(frame, sb.toString(), "Deadline Alert", JOptionPane.WARNING_MESSAGE);
        }
    }

    // Generate: Creates a customized button card for actions.
    private JButton createActionCard(String text, String icon, Color bgColor) {
        JButton btn = new JButton("<html><center><font size='+4'>" + icon + "</font><br><br>" + text + "</center></html>");
        btn.setFont(new Font("Segoe UI", Font.BOLD, 26));
        btn.setBackground(bgColor);
        btn.setForeground(new Color(45, 52, 54)); 
        btn.setFocusPainted(false);
        btn.setPreferredSize(new Dimension(300, 250));
        btn.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(0, 0, 0, 20), 2),
            BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));

        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn.setBackground(bgColor.brighter());
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btn.setBackground(bgColor);
            }
        });

        return btn;
    }
}