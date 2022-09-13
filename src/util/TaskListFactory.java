package util;

import domain.Task;

import java.util.ArrayList;

public class TaskListFactory {
    private ArrayList<Task> tasks = null;

    public TaskListFactory(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }

    public boolean existed(String taskID) {
        for(int i = 0; i < tasks.size(); i ++) {
            if(taskID.equals(tasks.get(i).getTaskID()))
                return true;
        }
        return false;
    }

    public String getPrjID(String taskID) {
        String prjID = "";
        for(int i = 0; i < tasks.size(); i ++) {
            if(taskID.equals(tasks.get(i).getTaskID()))
                prjID = tasks.get(i).getPrjID();
        }
        return prjID;
    }

    public String getTitle(String taskID) {
        String title = "";
        for(int i = 0; i < tasks.size(); i ++) {
            if(taskID.equals(tasks.get(i).getTaskID()))
                title = tasks.get(i).getTaskTitle();
        }
        return title;
    }

    public String getCreateDate(String taskID) {
        String createDate = "";
        for(int i = 0; i < tasks.size(); i ++) {
            if(taskID.equals(tasks.get(i).getTaskID()))
                createDate = tasks.get(i).getTaskCreateDate();
        }
        return createDate;
    }

    public String getEndDate(String taskID) {
        String endDate = "";
        for(int i = 0; i < tasks.size(); i ++) {
            if(taskID.equals(tasks.get(i).getTaskID()))
                endDate = tasks.get(i).getTaskEndDate();
        }
        return endDate;
    }

    public int getImportance(String taskID) {
        int importance = 0;
        for(int i = 0; i < tasks.size(); i ++) {
            if(taskID.equals(tasks.get(i).getTaskID()))
                importance = tasks.get(i).getTaskImportance();
        }
        return importance;
    }

    public String getStatus(String taskID) {
        String status = "";
        for(int i = 0; i < tasks.size(); i ++) {
            if(taskID.equals(tasks.get(i).getTaskID()))
                status = tasks.get(i).getTaskStatus();
        }
        return status;
    }

    public String getRemark(String taskID) {
        String remark = "";
        for(int i = 0; i < tasks.size(); i ++) {
            if(taskID.equals(tasks.get(i).getTaskID()))
                remark = tasks.get(i).getTaskRemark();
        }
        return remark;
    }
}
