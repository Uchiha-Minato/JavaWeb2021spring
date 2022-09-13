package util;

import dao.DBConnection;
import domain.Project;
import domain.Task;
import domain.TaskJoin;
import domain.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

public class ProjectFactory {
    private final DBConnection dbConnection;

    public ProjectFactory() {
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
            PreparedStatement pstmt = getConnection().prepareStatement("SELECT * FROM project WHERE userName = ?");
            pstmt.setString(1, userName);
            rs = pstmt.executeQuery();
        } catch (Exception e) {
            System.out.println("数据查询失败..." + e);
        }
        return rs;
    }

    public Project getPrjByID(String prjID) {
        Project project = new Project();
        checkNotClosed();
        try {
            PreparedStatement pstmt = getConnection().prepareStatement("SELECT * FROM project WHERE prjID = ?");
            pstmt.setString(1, prjID);
            ResultSet rs = pstmt.executeQuery();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            if(rs.next()) {
                project.setPrjID(rs.getString("prjID"));
                project.setPrjTitle(rs.getString("prjTitle"));
                project.setUserName(rs.getString("userName"));
                project.setPrjCreateDate(sdf.format(rs.getTimestamp("prjCreateDate")));
                project.setPrjEndDate(sdf.format(rs.getTimestamp("prjEndDate")));
                project.setPrjStatus(rs.getString("prjStatus"));
                project.setPrjRemark(rs.getString("prjRemark"));
            }
        } catch(Exception e) {
            System.out.println("数据查询失败..." + e);
        }
        return project;
    }

    public ArrayList<Project> getMyPrjs(String userName) {
        ArrayList<Project> projects = new ArrayList<>();
        try {
            ResultSet rs = executeQuery(userName);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            while(rs.next()) {
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

    public boolean existed(String prjID) {
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

    public boolean taskExisted(String taskID) {
        checkNotClosed();
        try {
            PreparedStatement pstmt = getConnection().prepareStatement("SELECT * FROM task WHERE taskID = ?");
            pstmt.setString(1, taskID);
            ResultSet rs = pstmt.executeQuery();
            if(rs.next())
                return true;
        } catch(Exception e) {
            System.out.println("数据查询失败..." + e);
        }
        return false;
    }

    public boolean taskJoinExisted(String taskID, String userName) {
        checkNotClosed();
        try {
            PreparedStatement pstmt = getConnection().prepareStatement("SELECT * FROM taskJoin WHERE taskID = ? AND userName = ?");
            pstmt.setString(1, taskID);
            pstmt.setString(2, userName);
            ResultSet rs = pstmt.executeQuery();
            if(rs.next())
                return true;
        } catch(Exception e) {
            System.out.println("数据查询失败..." + e);
        }
        return false;
    }

    public boolean createPrj(Project project) {
        checkNotClosed();
        project.setPrjID(getRandomPrjID());
        project.setPrjStatus("0%");
        try {
            PreparedStatement pstmt = getConnection().prepareStatement("INSERT INTO project VALUES(?,?,?,?,?,?,?)");
            pstmt.setString(1, project.getPrjID());
            pstmt.setString(2, project.getPrjTitle());
            pstmt.setString(3, project.getUserName());
            pstmt.setString(4, project.getPrjCreateDate());
            pstmt.setString(5, project.getPrjEndDate());
            pstmt.setString(6, project.getPrjStatus());
            pstmt.setString(7, project.getPrjRemark());
            int row = pstmt.executeUpdate();
            if(row > 0)
                return true;
        } catch(Exception e) {
            System.out.println("数据更新失败..." + e);
        }
        return false;
    }

    public String createPrjInfo(Project project) {
        String toUser = "新建项目失败，请重试";

        if(createPrj(project))
            toUser = "新建项目成功";
        else {
            if(existed(project.getPrjID()))
                toUser = "该项目已存在";
        }

        return toUser;
    }

    public String getRandomPrjID() {
        String prjID;
        Random random = new Random();
        do {
            int first = random.nextInt(10);
            int second = random.nextInt(10);
            int third = random.nextInt(10);
            prjID = "2021011" + first + second + third;
        } while(existed(prjID));
        return prjID;
    }

    public String getRandomTaskID() {
        String taskID;
        Random random = new Random();
        do {
            int first = random.nextInt(10);
            int second = random.nextInt(10);
            int third = random.nextInt(10);
            taskID = "2021111" + first + second + third;
        } while(taskExisted(taskID));
        return taskID;
    }

    public boolean checkPrjCreateDate(Project project) {
        try {
            ArrayList<Task> tasks = allTasks(project.getPrjID());
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date prjCreateDate = sdf.parse(project.getPrjCreateDate());
            for (Task task : tasks) {
                Date taskCreateDate = sdf.parse(task.getTaskCreateDate());
                if (prjCreateDate.after(taskCreateDate))
                    return false;
            }
        } catch(Exception e) {
            System.out.println("数据查询失败..." + e);
        }
        return true;
    }

    public boolean checkPrjEndDate(Project project) {
        try {
            ArrayList<Task> tasks = allTasks(project.getPrjID());
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date prjEndDate = sdf.parse(project.getPrjEndDate());
            for (Task task : tasks) {
                Date taskEndDate = sdf.parse(task.getTaskEndDate());
                if (prjEndDate.before(taskEndDate))
                    return false;
            }
        } catch(Exception e) {
            System.out.println("数据查询失败..." + e);
        }
        return true;
    }

    public boolean changePrj(Project project) {
        checkNotClosed();
        try {
            PreparedStatement pstmt = getConnection().prepareStatement("UPDATE project SET prjTitle = ?, prjCreateDate = ?, prjEndDate = ?, prjRemark = ? WHERE prjID = ?");
            pstmt.setString(1, project.getPrjTitle());
            pstmt.setString(2, project.getPrjCreateDate());
            pstmt.setString(3, project.getPrjEndDate());
            pstmt.setString(4, project.getPrjRemark());
            pstmt.setString(5, project.getPrjID());
            int row = pstmt.executeUpdate();
            if(row > 0)
                return true;
        } catch(Exception e) {
            System.out.println("数据更新失败..." + e);
        }
        return false;
    }

    public String changePrjInfo(Project project) {
        String toUser = "修改失败，请重试";

        if(checkPrjCreateDate(project)) {
            if(checkPrjEndDate(project)) {
                if(changePrj(project))
                    toUser = "修改成功";
                else {
                    if(!existed(project.getPrjID()))
                        toUser = "该项目不存在";
                }
            }
            else
                toUser = "项目结束时间不得早于全部任务结束时间";
        }
        else
            toUser = "项目开始时间不得晚于全部任务开始时间";

        return toUser;
    }

    public boolean deletePrj(String prjID) {
        checkNotClosed();
        try {
            PreparedStatement pstmt = getConnection().prepareStatement("DELETE FROM project WHERE prjID = ?");
            pstmt.setString(1, prjID);
            int row = pstmt.executeUpdate();
            if(row > 0)
                return true;
        } catch(Exception e) {
            System.out.println("数据更新失败..." + e);
        }
        return false;
    }

    public String deletePrjInfo(String prjID) {
        String toUser = "删除失败，请重试";

        if(deletePrj(prjID))
            toUser = "删除成功";
        else {
            if(!existed(prjID))
                toUser = "该项目不存在";
            else if(allTasks(prjID).size() > 0)
                toUser = "请先把所有任务删除再删除项目";
        }

        return toUser;
    }

    public boolean prjPublic(String prjID) {
        checkNotClosed();
        try {
            Project project = getPrjByID(prjID);
            PreparedStatement pstmt = getConnection().prepareStatement("UPDATE project SET prjCreateDate = ? WHERE prjID = ?");
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date now = new Date();//获得当前时间
            Date prjCreateDate = sdf.parse(project.getPrjCreateDate());
            if(now.before(prjCreateDate)) {
                pstmt.setString(1, sdf.format(now));
                pstmt.setString(2, prjID);
                int row = pstmt.executeUpdate();
                if(row > 0)
                    return true;
            }
        } catch(Exception e) {
            System.out.println("数据更新失败..." + e);
        }
        return false;
    }

    public String prjPublicInfo(String prjID) {
        String toUser = "发布失败，请重试";

        if(prjPublic(prjID))
            toUser = "发布成功";
        else {
            if(!existed(prjID))
                toUser = "该项目不存在";
        }

        return toUser;
    }

    public ArrayList<Task> allTasks(String prjID) {
        ArrayList<Task> tasks = new ArrayList<>();
        checkNotClosed();
        try {
            PreparedStatement pstmt = getConnection().prepareStatement("SELECT * FROM task WHERE prjID = ?");
            pstmt.setString(1, prjID);
            ResultSet rs = pstmt.executeQuery();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            while(rs.next()) {
                Task task = new Task();
                task.setPrjID(rs.getString("prjID"));
                task.setTaskID(rs.getString("taskID"));
                task.setTaskTitle(rs.getString("taskTitle"));
                task.setTaskCreateDate(sdf.format(rs.getTimestamp("taskCreateDate")));
                task.setTaskEndDate(sdf.format(rs.getTimestamp("taskEndDate")));
                task.setTaskImportance(rs.getInt("taskImportance"));
                task.setTaskStatus(rs.getString("taskStatus"));
                task.setTaskRemark(rs.getString("taskRemark"));
                tasks.add(task);
            }
        } catch(Exception e) {
            System.out.println("数据查询失败..." + e);
        }
        return tasks;
    }

    public ArrayList<TaskJoin> getTaskJoins(String taskID) {
        ArrayList<TaskJoin> taskJoins = new ArrayList<>();
        checkNotClosed();
        try {
            PreparedStatement pstmt = getConnection().prepareStatement("SELECT * FROM taskjoin WHERE taskID = ?");
            pstmt.setString(1, taskID);
            ResultSet rs = pstmt.executeQuery();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            while(rs.next()) {
                TaskJoin taskJoin = new TaskJoin();
                taskJoin.setPrjID(rs.getString("prjID"));
                taskJoin.setTaskID(rs.getString("taskID"));
                taskJoin.setUserName(rs.getString("userName"));
                taskJoin.setJoinDate(sdf.format(rs.getTimestamp("joinDate")));
                taskJoins.add(taskJoin);
            }
        } catch(Exception e) {
            System.out.println("数据查询失败..." + e);
        }
        return taskJoins;
    }

    public boolean deleteTask(String taskID) {
        checkNotClosed();
        try {
            PreparedStatement pstmt = getConnection().prepareStatement("DELETE FROM task WHERE taskID = ?");
            pstmt.setString(1, taskID);
            int row = pstmt.executeUpdate();
            if(row > 0)
                return true;
        } catch(Exception e) {
            System.out.println("数据更新失败..." + e);
        }
        return false;
    }

    public String deleteTaskInfo(String taskID) {
        String toUser = "删除失败，请重试";

        if(deleteTask(taskID))
            toUser = "删除成功";
        else {
            if(!taskExisted(taskID))
                toUser = "该任务不存在";
            else if(getTaskJoins(taskID).size() > 0)
                toUser = "请先把所有任务人员删除再删除任务";
        }

        return toUser;
    }

    public boolean createTask(Task task) {
        task.setTaskID(getRandomTaskID());
        task.setTaskStatus("0%");
        int taskImportance = task.getTaskImportance();
        checkNotClosed();
        try {
            PreparedStatement pstmt = getConnection().prepareStatement("INSERT INTO task VALUES(?,?,?,?,?,?,?,?)");
            pstmt.setString(1, task.getPrjID());
            pstmt.setString(2, task.getTaskID());
            pstmt.setString(3, task.getTaskTitle());
            pstmt.setString(4, task.getTaskCreateDate());
            pstmt.setString(5, task.getTaskEndDate());
            pstmt.setString(6, Integer.toString(taskImportance));
            pstmt.setString(7, task.getTaskStatus());
            pstmt.setString(8, task.getTaskRemark());
            int row = pstmt.executeUpdate();
            if(row > 0)
                return true;
        } catch(Exception e) {
            System.out.println("数据更新失败..." + e);
        }
        return false;
    }

    public boolean checkTaskCreateDate(Task task) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date taskCreateDate = sdf.parse(task.getTaskCreateDate());
            Date prjCreateDate = sdf.parse(getPrjByID(task.getPrjID()).getPrjCreateDate());
            if(taskCreateDate.before(prjCreateDate))
                return false;
        } catch(Exception e) {
            System.out.println("数据查询失败..." + e);
        }
        return true;
    }

    public boolean checkTaskEndDate(Task task) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date taskEndDate = sdf.parse(task.getTaskEndDate());
            Date prjEndDate = sdf.parse(getPrjByID(task.getPrjID()).getPrjEndDate());
            if(taskEndDate.after(prjEndDate))
                return false;
        } catch(Exception e) {
            System.out.println("数据查询失败..." + e);
        }
        return true;
    }

    public String createTaskInfo(Task task) {
        String toUser = "添加失败，请重试";

        if(checkTaskCreateDate(task)) {
            if(checkTaskEndDate(task)) {
                if(createTask(task))
                    toUser = "添加成功";
                else {
                    if(taskExisted(task.getTaskID()))
                        toUser = "该任务已存在";
                }
            }
            else
                toUser = "任务结束时间不得晚于项目结束时间";
        }
        else
            toUser = "任务开始时间不得早于项目开始时间";

        return toUser;
    }

    public boolean changeTask(Task task) {
        int taskImportance = task.getTaskImportance();
        checkNotClosed();
        try {
            PreparedStatement pstmt = getConnection().prepareStatement("UPDATE task SET taskTitle = ?, taskCreateDate = ?, taskEndDate = ?, taskImportance = ?, taskRemark = ? WHERE taskID = ?");
            pstmt.setString(1, task.getTaskTitle());
            pstmt.setString(2, task.getTaskCreateDate());
            pstmt.setString(3, task.getTaskEndDate());
            pstmt.setString(4, Integer.toString(taskImportance));
            pstmt.setString(5, task.getTaskRemark());
            pstmt.setString(6, task.getTaskID());
            int row = pstmt.executeUpdate();
            if(row > 0)
                return true;
        } catch(Exception e) {
            System.out.println("数据更新失败..." + e);
        }
        return false;
    }

    public String changeTaskInfo(Task task) {
        String toUser = "修改失败，请重试";

        if(checkTaskCreateDate(task)) {
            if(checkTaskEndDate(task)) {
                if(changeTask(task))
                    toUser = "修改成功";
                else {
                    if(!taskExisted(task.getTaskID()))
                        toUser = "该任务不存在";
                }
            }
            else
                toUser = "任务结束时间不得晚于项目结束时间";
        }
        else
            toUser = "任务开始时间不得早于项目开始时间";

        return toUser;
    }

    public Task getTaskByID(String taskID) {
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

    public ArrayList<User> allUsers() {
        ArrayList<User> users = new ArrayList<>();
        checkNotClosed();
        try {
            PreparedStatement pstmt = getConnection().prepareStatement("SELECT * FROM user");
            ResultSet rs = pstmt.executeQuery();
            while(rs.next()) {
                User user = new User();
                user.setUserName(rs.getString("userName"));
                user.setPassword(rs.getString("password"));
                user.setTel(rs.getString("tel"));
                users.add(user);
            }
        } catch(Exception e) {
            System.out.println("数据查询失败..." + e);
        }
        return users;
    }

    public boolean deleteTaskJoin(String taskID, String userName) {
        checkNotClosed();
        try {
            PreparedStatement pstmt = getConnection().prepareStatement("DELETE FROM taskjoin WHERE taskID = ? AND userName = ?");
            pstmt.setString(1, taskID);
            pstmt.setString(2, userName);
            int row = pstmt.executeUpdate();
            if(row > 0)
                return true;
        } catch(Exception e) {
            System.out.println("数据更新失败..." + e);
        }
        return false;
    }

    public String deleteTaskJoinInfo(String taskID, String userName) {
        String toUser = "移除失败，请重试";

        if(deleteTaskJoin(taskID, userName))
            toUser = "移除成功";
        else {
            if(!taskJoinExisted(taskID, userName))
                toUser = "用户未参与该任务";
        }

        return toUser;
    }

    public boolean addTaskJoin(TaskJoin taskJoin) {
        checkNotClosed();
        try {
            PreparedStatement pstmt = getConnection().prepareStatement("INSERT INTO taskJoin VALUES(?,?,?,?)");
            pstmt.setString(1, taskJoin.getPrjID());
            pstmt.setString(2, taskJoin.getTaskID());
            pstmt.setString(3, taskJoin.getUserName());
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date joinDate = new Date();
            pstmt.setString(4, sdf.format(joinDate));
            int row = pstmt.executeUpdate();
            if(row > 0)
                return true;
        } catch(Exception e) {
            System.out.println("数据更新失败..." + e);
        }
        return false;
    }

    public String addTaskJoinInfo(TaskJoin taskJoin) {
        String toUser = "添加失败，请重试";

        if(addTaskJoin(taskJoin))
            toUser = "添加成功";
        else {
            if(taskJoinExisted(taskJoin.getTaskID(), taskJoin.getUserName()))
                toUser = "用户已参与该任务";
        }

        return toUser;
    }

    public ArrayList<User> allJoinUsers(String taskID) {
        ArrayList<User> joinUsers = new ArrayList<>();
        checkNotClosed();
        try {
            PreparedStatement pstmt = getConnection().prepareStatement("SELECT u.userName, password, tel FROM taskJoin t JOIN user u ON t.userName = u.userName WHERE taskID = ?");
            pstmt.setString(1, taskID);
            ResultSet rs = pstmt.executeQuery();
            while(rs.next()) {
                User user = new User();
                user.setUserName(rs.getString("userName"));
                user.setPassword(rs.getString("password"));
                user.setTel(rs.getString("tel"));
                joinUsers.add(user);
            }
        } catch(Exception e) {
            System.out.println("数据查询失败..." + e);
        }

        return joinUsers;
    }

    public ArrayList<User> searchUsersByName(String userName) {
        ArrayList<User> searchUsers = new ArrayList<>();
        checkNotClosed();
        try {
            PreparedStatement pstmt = getConnection().prepareStatement("SELECT * FROM user WHERE userName like ?");
            pstmt.setString(1, "%" + userName + "%");
            ResultSet rs = pstmt.executeQuery();
            while(rs.next()) {
                User user = new User();
                user.setUserName(rs.getString("userName"));
                user.setPassword(rs.getString("password"));
                user.setTel(rs.getString("tel"));
                searchUsers.add(user);
            }
        } catch(Exception e) {
            System.out.println("数据查询失败..." + e);
        }
        return searchUsers;
    }
}
