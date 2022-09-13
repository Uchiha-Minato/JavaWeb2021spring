package domain;

public class TaskJoin {
    private String prjID;
    private String taskID;
    private String userName;
    private String joinDate;

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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getJoinDate() {
        return joinDate;
    }

    public void setJoinDate(String joinDate) {
        this.joinDate = joinDate;
    }
}
