package model;

import java.util.ArrayList;
import java.util.List;

public class Project {

    private int projectId;
    private String projectName;
    private String description;
    private List<Integer> memberUserIds;  // IDs of team members
    private List<Integer> taskIds;        // IDs of tasks in this project

    // Constructor
    public Project(int projectId, String projectName, String description) {
        this.projectId = projectId;
        this.projectName = projectName;
        this.description = description;
        this.memberUserIds = new ArrayList<>();
        this.taskIds = new ArrayList<>();
    }

    // Default constructor
    public Project() {
        this.memberUserIds = new ArrayList<>();
        this.taskIds = new ArrayList<>();
    }

    // Add a member to the project
    public void addMember(int userId) {
        if (!memberUserIds.contains(userId)) {
            memberUserIds.add(userId);
        }
    }

    // Remove a member from the project
    public void removeMember(int userId) {
        memberUserIds.remove(Integer.valueOf(userId));
    }

    // Add a task to the project
    public void addTask(int taskId) {
        if (!taskIds.contains(taskId)) {
            taskIds.add(taskId);
        }
    }

    // Remove a task from the project
    public void removeTask(int taskId) {
        taskIds.remove(Integer.valueOf(taskId));
    }

    // Getters
    public int getProjectId()              { return projectId; }
    public String getProjectName()         { return projectName; }
    public String getDescription()         { return description; }
    public List<Integer> getMemberUserIds(){ return memberUserIds; }
    public List<Integer> getTaskIds()      { return taskIds; }

    // Setters
    public void setProjectId(int projectId)          { this.projectId = projectId; }
    public void setProjectName(String projectName)   { this.projectName = projectName; }
    public void setDescription(String description)   { this.description = description; }

    @Override
    public String toString() {
        return "Project [ID=" + projectId +
               ", Name=" + projectName +
               ", Members=" + memberUserIds.size() +
               ", Tasks=" + taskIds.size() + "]";
    }
}
