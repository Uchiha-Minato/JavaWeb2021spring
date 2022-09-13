package util;

import domain.Project;

import java.util.ArrayList;

public class ProjectListFactory {
    private ArrayList<Project> projects = null;

    public ProjectListFactory(ArrayList<Project> projects) {
        this.projects = projects;
    }

    public boolean existed(String prjID) {
        for(int i = 0; i < projects.size(); i ++) {
            if(prjID.equals(projects.get(i).getPrjID()))
                return true;
        }
        return false;
    }

    public String getTitle(String prjID) {
        String title = "";
        for(int i = 0; i < projects.size(); i ++) {
            if(prjID.equals(projects.get(i).getPrjID()))
                title = projects.get(i).getPrjTitle();
        }
        return title;
    }

    public String getUserName(String prjID) {
        String userName = "";
        for(int i = 0; i < projects.size(); i ++) {
            if(prjID.equals(projects.get(i).getPrjID()))
                userName = projects.get(i).getUserName();
        }
        return userName;
    }

    public String getCreateDate(String prjID) {
        String createDate = "";
        for(int i = 0; i < projects.size(); i ++) {
            if(prjID.equals(projects.get(i).getPrjID()))
                createDate = projects.get(i).getPrjCreateDate();
        }
        return createDate;
    }

    public String getEndDate(String prjID) {
        String endDate = "";
        for(int i = 0; i < projects.size(); i ++) {
            if(prjID.equals(projects.get(i).getPrjID()))
                endDate = projects.get(i).getPrjEndDate();
        }
        return endDate;
    }

    public String getStatus(String prjID) {
        String status = "";
        for(int i = 0; i < projects.size(); i ++) {
            if(prjID.equals(projects.get(i).getPrjID()))
                status = projects.get(i).getPrjStatus();
        }
        return status;
    }

    public String getRemark(String prjID) {
        String remark = "";
        for(int i = 0; i < projects.size(); i ++) {
            if(prjID.equals(projects.get(i).getPrjID()))
                remark = projects.get(i).getPrjRemark();
        }
        return remark;
    }
}
