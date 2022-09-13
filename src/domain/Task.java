package domain;

public class Task {
    private String prjID;
    private String taskID;
    private String taskTitle;
    private String taskCreateDate;
    private String taskEndDate;
    private int taskImportance;
    private String taskStatus;
    private String taskRemark;

    public String getPrjID() {
        return prjID;
    }

    public void setPrjID(String prjID) {
        this.prjID = prjID;
    }

    public String getTaskID() {
        return taskID;
    }

    public void setTaskID(String taskID) {
        this.taskID = taskID;
    }

    public String getTaskTitle() {
        return taskTitle;
    }

    public void setTaskTitle(String taskTitle) {
        this.taskTitle = taskTitle;
    }

    public String getTaskCreateDate() {
        return taskCreateDate;
    }

    public void setTaskCreateDate(String taskCreateDate) {
        this.taskCreateDate = taskCreateDate;
    }

    public String getTaskEndDate() {
        return taskEndDate;
    }

    public void setTaskEndDate(String taskEndDate) {
        this.taskEndDate = taskEndDate;
    }

    public int getTaskImportance() {
        return taskImportance;
    }

    public void setTaskImportance(int taskImportance) {
        this.taskImportance = taskImportance;
    }

    public String getTaskStatus() {
        return taskStatus;
    }

    public void setTaskStatus(String taskStatus) {
        this.taskStatus = taskStatus;
    }

    public String getTaskRemark() {
        return taskRemark;
    }

    public void setTaskRemark(String taskRemark) {
        this.taskRemark = taskRemark;
    }
}
