package model;

import java.time.LocalDate;

public class Task {

    // Enum for task status
    public enum Status {
        TODO,
        IN_PROGRESS,
        COMPLETED
    }

    // Enum for task priority
    public enum Priority {
        LOW,
        MEDIUM,
        HIGH
    }

    private int taskId;
    private String title;
    private String description;
    private Status status;
    private Priority priority;
    private LocalDate deadline;
    private int assignedUserId;  // which user this task is assigned to
    private int projectId;       // which project this task belongs to

    // Constructor
    public Task(int taskId, String title, String description,
                Priority priority, LocalDate deadline,
                int assignedUserId, int projectId) {
        this.taskId = taskId;
        this.title = title;
        this.description = description;
        this.status = Status.TODO;       // default status
        this.priority = priority;
        this.deadline = deadline;
        this.assignedUserId = assignedUserId;
        this.projectId = projectId;
    }

    // Default constructor
    public Task() {}

    // Getters
    public int getTaskId()           { return taskId; }
    public String getTitle()         { return title; }
    public String getDescription()   { return description; }
    public Status getStatus()        { return status; }
    public Priority getPriority()    { return priority; }
    public LocalDate getDeadline()   { return deadline; }
    public int getAssignedUserId()   { return assignedUserId; }
    public int getProjectId()        { return projectId; }

    // Setters
    public void setTaskId(int taskId)               { this.taskId = taskId; }
    public void setTitle(String title)              { this.title = title; }
    public void setDescription(String description)  { this.description = description; }
    public void setStatus(Status status)            { this.status = status; }
    public void setPriority(Priority priority)      { this.priority = priority; }
    public void setDeadline(LocalDate deadline)     { this.deadline = deadline; }
    public void setAssignedUserId(int userId)       { this.assignedUserId = userId; }
    public void setProjectId(int projectId)         { this.projectId = projectId; }

    @Override
    public String toString() {
        return "Task [ID=" + taskId +
               ", Title=" + title +
               ", Status=" + status +
               ", Priority=" + priority +
               ", Deadline=" + deadline +
               ", AssignedTo=User#" + assignedUserId + "]";
    }
}
