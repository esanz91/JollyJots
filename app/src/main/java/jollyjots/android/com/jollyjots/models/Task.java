package jollyjots.android.com.jollyjots.models;

public class Task {

    private String taskTitle;
    private String priority;

    public Task() {
        //init
    }

    public Task(String taskTitle) {
        this.taskTitle = taskTitle;
    }

    public Task(String taskTitle, String priority) {
        this.taskTitle = taskTitle;
        this.priority = priority;
    }

    public void setTaskTitle(String taskTitle) {
        this.taskTitle = taskTitle;
    }

    public String getTaskTitle() {
        return this.taskTitle;
    }
}
