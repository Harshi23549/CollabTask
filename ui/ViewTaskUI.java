package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import service.TaskService;
import service.AuthService;
import model.Task;
import model.User;
import java.util.ArrayList;

public class ViewTaskUI {

    private String userEmail;

    // Initialize: Sets up view task UI with default guest email.
    public ViewTaskUI() {
        this.userEmail = "user@example.com";
    }

    // Initialize: Sets up view task UI with specific user email.
    public ViewTaskUI(String email) {
        this.userEmail = email;
    }

    // Display: Renders the full screen view tasks interface.
    public void showTasks() {
        
        AuthService auth = new AuthService();
        User currentUser = auth.getUser(userEmail);
        String role = currentUser != null ? currentUser.getRole() : "Member";

        JFrame frame = new JFrame(role.equals("Leader") ? "All Tasks" : "My Tasks");
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

        JLabel emailLabel = new JLabel(userEmail + " (" + role + ")");
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

        // Center wrapper to keep form sized nice
        JPanel centerWrapper = new JPanel(new GridBagLayout());
        centerWrapper.setOpaque(false);

        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.setPreferredSize(new Dimension(800, 600));
        contentPanel.setBackground(Color.WHITE);
        contentPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(0, 0, 0, 15), 1),
            BorderFactory.createEmptyBorder(40, 40, 40, 40)
        ));

        JLabel header = new JLabel("Task Repository 📚");
        header.setFont(new Font("Segoe UI", Font.BOLD, 36));
        header.setForeground(new Color(45, 52, 54));
        header.setBorder(BorderFactory.createEmptyBorder(0, 0, 25, 0));
        contentPanel.add(header, BorderLayout.NORTH);

        JPanel listPanel = new JPanel();
        listPanel.setLayout(new BoxLayout(listPanel, BoxLayout.Y_AXIS));
        listPanel.setBackground(new Color(250, 250, 250));

        TaskService service = new TaskService();
        ArrayList<Task> tasks = role.equals("Leader") ? service.getAllTasks() : service.getTasksForUser(userEmail);

        if (tasks == null || tasks.isEmpty()) {
            JLabel emptyLbl = new JLabel("🌟 No tasks found! You're all caught up.");
            emptyLbl.setFont(new Font("Segoe UI", Font.PLAIN, 20));
            emptyLbl.setForeground(new Color(45, 52, 54));
            emptyLbl.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
            listPanel.add(emptyLbl);
        } else {
            for (Task t : tasks) {
                JPanel taskRow = new JPanel(new BorderLayout(15, 0));
                taskRow.setBackground(new Color(250, 250, 250));
                taskRow.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(223, 230, 233)),
                    BorderFactory.createEmptyBorder(15, 20, 15, 20)
                ));
                taskRow.setMaximumSize(new Dimension(Integer.MAX_VALUE, 100));

                JCheckBox checkBox = new JCheckBox();
                checkBox.setSelected(t.isCompleted());
                checkBox.setBackground(new Color(250, 250, 250));
                checkBox.setCursor(new Cursor(Cursor.HAND_CURSOR));

                String taskContent = "<b>" + t.getTitle().toUpperCase() + "</b><br>" 
                                   + t.getDescription() + "<br>"
                                   + "<span style='color: #0984e3; font-size: 10px;'>" 
                                   + "Assignee: " + (t.getAssignee() != null ? t.getAssignee() : "None") 
                                   + " | Deadline: " + (t.getDeadline() != null ? t.getDeadline() : "None") 
                                   + "</span>";

                JLabel textLbl = new JLabel();
                textLbl.setFont(new Font("Segoe UI", Font.PLAIN, 16));
                textLbl.setForeground(new Color(45, 52, 54));

                Runnable updateLabel = () -> {
                    if (t.isCompleted()) {
                        textLbl.setText("<html><s style='color: #b2bec3;'>" + taskContent + "</s></html>");
                    } else {
                        textLbl.setText("<html>" + taskContent + "</html>");
                    }
                };
                updateLabel.run();

                checkBox.addActionListener(e -> {
                    t.setCompleted(checkBox.isSelected());
                    service.updateTaskStatus(t.getId(), t.getStatus());
                    updateLabel.run();
                });

                taskRow.add(checkBox, BorderLayout.WEST);
                taskRow.add(textLbl, BorderLayout.CENTER);

                if (role.equals("Leader")) {
                    JButton deleteBtn = new JButton("🗑");
                    deleteBtn.setFont(new Font("Segoe UI", Font.PLAIN, 20));
                    deleteBtn.setBackground(new Color(250, 250, 250));
                    deleteBtn.setForeground(new Color(214, 48, 49));
                    deleteBtn.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
                    deleteBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
                    deleteBtn.setFocusPainted(false);
                    deleteBtn.addActionListener(e -> {
                        int confirm = JOptionPane.showConfirmDialog(frame, "Are you sure you want to delete this task?", "Confirm Delete", JOptionPane.YES_NO_OPTION);
                        if (confirm == JOptionPane.YES_OPTION) {
                            service.deleteTask(t.getId());
                            listPanel.remove(taskRow);
                            listPanel.revalidate();
                            listPanel.repaint();
                        }
                    });
                    taskRow.add(deleteBtn, BorderLayout.EAST);
                }
                
                listPanel.add(taskRow);
            }
        }

        // Push elements to top
        listPanel.add(Box.createVerticalGlue());

        JScrollPane scrollPane = new JScrollPane(listPanel);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(223, 230, 233), 2));
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        contentPanel.add(scrollPane, BorderLayout.CENTER);

        centerWrapper.add(contentPanel);

        mainPanel.add(navBar, BorderLayout.NORTH);
        mainPanel.add(centerWrapper, BorderLayout.CENTER);

        frame.add(mainPanel);
        frame.setVisible(true);
    }
}