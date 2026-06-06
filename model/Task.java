package model;

public class Task {
    private int id;
    private String title;
    private String description;
    private String status; // "Pending" or "Done"
    private String assignee;
    private String deadline;

    // Constructor with ID
    public Task(int id, String title, String description, String status, String assignee, String deadline) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.status = status;
        this.assignee = assignee;
        this.deadline = deadline;
    }

    // Constructor without ID (for creation)
    public Task(String title, String description, String status, String assignee, String deadline) {
        this.title = title;
        this.description = description;
        this.status = status;
        this.assignee = assignee;
        this.deadline = deadline;
    }

    public int getId() { return id; }
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    
    // Check: Returns true if the task is completed.
    public boolean isCompleted() { return "Done".equals(status); }
    
    public String getStatus() { return status; }
    // Update: Sets the status
    public void setStatus(String status) { this.status = status; }
    
    // For compatibility with older code temporarily:
    public void setCompleted(boolean completed) { this.status = completed ? "Done" : "Pending"; }
    
    public String getAssignee() { return assignee; }
    public String getDeadline() { return deadline; }
}