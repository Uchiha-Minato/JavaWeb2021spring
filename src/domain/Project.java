package domain;

public class Project {
    private String prjID;
    private String prjTitle;
    private String userName;
    private String prjCreateDate;
    private String prjEndDate;
    private String prjStatus;
    private String prjRemark;

    public String getPrjID() {
        return prjID;
    }

    public void setPrjID(String prjID) {
        this.prjID = prjID;
    }

    public String getPrjTitle() {
        return prjTitle;
    }

    public void setPrjTitle(String prjTitle) {
        this.prjTitle = prjTitle;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPrjCreateDate() {
        return prjCreateDate;
    }

    public void setPrjCreateDate(String prjCreateDate) {
        this.prjCreateDate = prjCreateDate;
    }

    public String getPrjEndDate() {
        return prjEndDate;
    }

    public void setPrjEndDate(String prjEndDate) {
        this.prjEndDate = prjEndDate;
    }

    public String getPrjStatus() {
        return prjStatus;
    }

    public void setPrjStatus(String prjStatus) {
        this.prjStatus = prjStatus;
    }

    public String getPrjRemark() {
        return prjRemark;
    }

    public void setPrjRemark(String prjRemark) {
        this.prjRemark = prjRemark;
    }
}
