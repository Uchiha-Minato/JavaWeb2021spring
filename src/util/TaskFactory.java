package util;

import dao.DBConnection;
import domain.Project;
import domain.Task;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class TaskFactory {
    private DBConnection dbConnection = null;

    public TaskFactory() {
        dbConnection = new DBConnection();
    }

    public void checkNotClosed() {
        dbConnection.checkNotClosed();
    }

    public Connection getConnection() {
        return dbConnection.getConn();
    }

    public ResultSet executeQuery(String userName) {
        ResultSet rs = null;
        checkNotClosed();
        try {
            PreparedStatement pstmt = getConnection().prepareStatement("SELECT * FROM taskJoin WHERE userName = ?");
            pstmt.setString(1, userName);
            rs = pstmt.executeQuery();
        } catch (Exception e) {
            System.out.println("数据查询失败..." + e);
        }
        return rs;
    }

    public boolean existed(String taskID) {
        checkNotClosed();
        try {
            PreparedStatement pstmt = getConnection().prepareStatement("SELECT * FROM task WHERE taskID = ?");
            pstmt.setString(1, taskID);
            ResultSet rs = pstmt.executeQuery();
            if(rs.next())
                return true;
        } catch(Exception e) {
            System.out.println("数据更新失败..." + e);
        }
        return false;
    }

    public boolean prjExisted(String prjID) {
        checkNotClosed();
        try {
            PreparedStatement pstmt = getConnection().prepareStatement("SELECT * FROM project WHERE prjID = ?");
            pstmt.setString(1, prjID);
            ResultSet rs = pstmt.executeQuery();
            if(rs.next())
                return true;
        } catch(Exception e) {
            System.out.println("数据查询失败..." + e);
        }
        return false;
    }

    public Task getTaskById(String taskID) {
        Task task = new Task();
        checkNotClosed();
        try {
            PreparedStatement pstmt = getConnection().prepareStatement("SELECT * FROM task WHERE taskID = ?");
            pstmt.setString(1, taskID);
            ResultSet rs = pstmt.executeQuery();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            if(rs.next()) {
                task.setPrjID(rs.getString("prjID"));
                task.setTaskID(rs.getString("taskID"));
                task.setTaskTitle(rs.getString("taskTitle"));
                task.setTaskCreateDate(sdf.format(rs.getTimestamp("taskCreateDate")));
                task.setTaskEndDate(sdf.format(rs.getTimestamp("taskEndDate")));
                task.setTaskImportance(rs.getInt("taskImportance"));
                task.setTaskStatus(rs.getString("taskStatus"));
                task.setTaskRemark(rs.getString("taskRemark"));
            }
        } catch(Exception e) {
            System.out.println("数据查询失败..." + e);
        }
        return task;
    }

    public ArrayList<Project> allProjects() {
        ArrayList<Project> projects = new ArrayList<>();
        checkNotClosed();
        try {
            PreparedStatement pstmt = getConnection().prepareStatement("SELECT * FROM project");
            ResultSet rs = pstmt.executeQuery();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            while(rs.next()){
                Project project = new Project();
                project.setPrjID(rs.getString("prjID"));
                project.setPrjTitle(rs.getString("prjTitle"));
                project.setUserName(rs.getString("userName"));
                project.setPrjCreateDate(sdf.format(rs.getTimestamp("prjCreateDate")));
                project.setPrjEndDate(sdf.format(rs.getTimestamp("prjEndDate")));
                project.setPrjStatus(rs.getString("prjStatus"));
                project.setPrjRemark(rs.getString("prjRemark"));
                projects.add(project);
            }
        } catch(Exception e) {
            System.out.println("数据查询失败..." + e);
        }
        return projects;
    }

    public ArrayList<Task> joinTasks(String userName) {
        ArrayList<Task> joinTasks = new ArrayList<>();
        try {
            ResultSet rsTaskJoin = executeQuery(userName);
            while(rsTaskJoin.next()) {
                String taskID = rsTaskJoin.getString("taskID");
                TaskListFactory taskListFactory = new TaskListFactory(joinTasks);
                if(!taskListFactory.existed(taskID)) {
                    Task task = getTaskById(taskID);
                    joinTasks.add(task);
                }
            }
        } catch(Exception e) {
            System.out.println("数据查询失败..." + e);
        }
        return joinTasks;
    }

    public boolean changeTaskStatus(String taskID, String taskStatus) {
        checkNotClosed();
        try {
            PreparedStatement pstmt = getConnection().prepareStatement("UPDATE task SET taskStatus = ? WHERE taskID = ?");
            pstmt.setString(1, taskStatus);
            pstmt.setString(2, taskID);
            int row = pstmt.executeUpdate();
            if(row > 0)
                return true;
        } catch(Exception e) {
            System.out.println("数据更新失败..." + e);
        }
        return false;
    }

    public boolean updatePrjStatus(String prjID) {
        checkNotClosed();
        try {
            PreparedStatement pstmtQuery = getConnection().prepareStatement("SELECT * FROM task WHERE prjID = ?");
            pstmtQuery.setString(1, prjID);
            ResultSet rs = pstmtQuery.executeQuery();
            int allImportance = 0;
            double allStatus = 0;
            boolean allCompleted = true;
            while(rs.next()) {
                allImportance += rs.getInt("taskImportance");
            }
            rs.beforeFirst();
            while(rs.next()) {
                int taskImportance = rs.getInt("taskImportance");
                String taskStatusString = rs.getString("taskStatus");
                if(!taskStatusString.equals("100%"))
                    allCompleted = false;
                int taskStatus = Integer.parseInt(taskStatusString.split("%")[0]);
                allStatus += taskStatus * taskImportance;
            }
            Integer temp = (int)(allStatus / allImportance);
            String updatedPrjStatus = "";
            if(allCompleted)
                updatedPrjStatus = "100%";
            else
                updatedPrjStatus = temp.toString();
            PreparedStatement pstmtUpdate = getConnection().prepareStatement("UPDATE project SET prjStatus = ? WHERE prjID = ?");
            pstmtUpdate.setString(1, updatedPrjStatus);
            pstmtUpdate.setString(2, prjID);
            int row = pstmtUpdate.executeUpdate();
            if(row > 0)
                return true;
        } catch(Exception e) {
            System.out.println("数据更新失败..." + e);
        }
        return false;
    }

    public String changeTaskStatusInfo(String taskID, String taskStatus, String prjID) {
        String toUser = "更新失败，请重试";

        if(changeTaskStatus(taskID, taskStatus) && updatePrjStatus(prjID))
            toUser = "更新成功";
        else {
            if(!prjExisted(prjID))
                toUser = "该项目不存在";
            else {
                if(!existed(taskID))
                    toUser = "该任务不存在";
            }
        }

        return toUser;
    }
}

