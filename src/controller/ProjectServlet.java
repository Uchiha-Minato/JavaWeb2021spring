package controller;

import domain.Project;
import domain.Task;
import domain.TaskJoin;
import domain.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import util.ProjectFactory;
import util.UserListFactory;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

@WebServlet(name = "ProjectServlet", urlPatterns = "/ProjectServlet")
public class ProjectServlet extends HttpServlet {
    private ProjectFactory projectFactory = new ProjectFactory();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=utf-8");
        String type = request.getParameter("type");
        if(type != null) {
            if(type.equals("myPrjs")) {
                myPrjs(request, response);
            }
            else if(type.equals("createPrj")) {
                createPrj(request, response);
            }
            else if(type.equals("changePrj")) {
                changePrj(request, response);
            }
            else if(type.equals("deletePrj")) {
                deletePrj(request, response);
            }
            else if(type.equals("prjPublic")) {
                prjPublic(request, response);
            }
            else if(type.equals("seePrj")) {
                seePrj(request, response);
            }
            else if(type.equals("deleteTask")) {
                deleteTask(request, response);
            }
            else if(type.equals("createTask")) {
                createTask(request, response);
            }
            else if(type.equals("changeTask")) {
                changeTask(request, response);
            }
            else if(type.equals("seeTask")) {
                seeTask(request, response);
            }
            else if(type.equals("deleteTaskJoin")) {
                deleteTaskJoin(request, response);
            }
            else if(type.equals("addTaskJoin")) {
                addTaskJoin(request, response);
            }
            else if(type.equals("allUsers")) {
                allUsers(request, response);
            }
            else if(type.equals("searchUsers")) {
                searchUsers(request, response);
            }
        }
        else {
            undo(request, response);
        }
    }

    private void myPrjs(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        PrintWriter pw = response.getWriter();
        String userName = request.getParameter("userName");
        ArrayList<Project> myPrjs = projectFactory.getMyPrjs(userName);
        request.getSession().setAttribute("myPrjs", myPrjs);
        pw.println("<script>window.location = \"/myPrj.jsp\";</script>");
    }

    private void createPrj(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        PrintWriter pw = response.getWriter();
        Project project = new Project();
        project.setPrjTitle(request.getParameter("prjTitle"));
        project.setUserName(request.getParameter("userName"));
        project.setPrjCreateDate(request.getParameter("prjCreateDate") + " " + request.getParameter("prjCreateTime"));
        project.setPrjEndDate(request.getParameter("prjEndDate") + " " + request.getParameter("prjEndTime"));
        project.setPrjRemark(request.getParameter("prjRemark"));
        pw.println("<script>if(confirm(\"" + projectFactory.createPrjInfo(project) + "\") == true) {" +
                "window.location = \"/ProjectServlet?type=myPrjs&userName=" + project.getUserName() + "\"; }</script>");
    }

    private void changePrj(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        PrintWriter pw = response.getWriter();
        Project project = new Project();
        project.setPrjID(request.getParameter("prjID"));
        project.setPrjTitle(request.getParameter("prjTitle"));
        project.setUserName(request.getParameter("userName"));
        project.setPrjCreateDate(request.getParameter("prjCreateDate") + " " + request.getParameter("prjCreateTime"));
        project.setPrjEndDate(request.getParameter("prjEndDate") + " " + request.getParameter("prjEndTime"));
        project.setPrjRemark(request.getParameter("prjRemark"));
        pw.println("<script>if(confirm(\"" + projectFactory.changePrjInfo(project) + "\") == true) {" +
                "window.location = \"/ProjectServlet?type=myPrjs&userName=" + project.getUserName() + "\"; }</script>");
    }

    private void deletePrj(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        PrintWriter pw = response.getWriter();
        String prjID = request.getParameter("prjID");
        String userName = request.getParameter("userName");
        pw.println("<script>if(confirm(\"" + projectFactory.deletePrjInfo(prjID) + "\") == true) {" +
                "window.location = \"/ProjectServlet?type=myPrjs&userName=" + userName + "\"; }</script>");
    }

    private void prjPublic(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        PrintWriter pw = response.getWriter();
        String prjID = request.getParameter("prjID");
        String userName = request.getParameter("userName");
        pw.println("<script>if(confirm(\"" + projectFactory.prjPublicInfo(prjID) + "\") == true) {" +
                "window.location = \"/ProjectServlet?type=myPrjs&userName=" + userName + "\"; }</script>");
    }

    private void seePrj(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        PrintWriter pw = response.getWriter();
        String prjID = request.getParameter("prjID");
        Project project = projectFactory.getPrjByID(prjID);
        ArrayList<Task> tasks = projectFactory.allTasks(prjID);
        request.getSession().setAttribute("project", project);
        request.getSession().setAttribute("tasks", tasks);
        pw.println("<script>window.location = \"/myPrj.jsp?info=true\";</script>");
    }

    private void deleteTask(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        PrintWriter pw = response.getWriter();
        String prjID = request.getParameter("prjID");
        String taskID = request.getParameter("taskID");
        pw.println("<script>if(confirm(\"" + projectFactory.deleteTaskInfo(taskID) + "\") == true) {" +
                "window.location = \"/ProjectServlet?type=seePrj&prjID=" + prjID + "\"; }</script>");
    }

    private void createTask(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        PrintWriter pw = response.getWriter();
        Task task = new Task();
        task.setPrjID(request.getParameter("prjID"));
        task.setTaskTitle(request.getParameter("taskTitle"));
        task.setTaskCreateDate(request.getParameter("taskCreateDate") + " " + request.getParameter("taskCreateTime"));
        task.setTaskEndDate(request.getParameter("taskEndDate") + " " + request.getParameter("taskEndTime"));
        task.setTaskImportance(Integer.parseInt(request.getParameter("taskImportance")));
        task.setTaskRemark(request.getParameter("taskRemark"));
        pw.println("<script>if(confirm(\"" + projectFactory.createTaskInfo(task) + "\") == true) {" +
                "window.location = \"/ProjectServlet?type=seePrj&prjID=" + task.getPrjID() + "\"; }</script>");
    }

    private void changeTask(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        PrintWriter pw = response.getWriter();
        Task task = new Task();
        task.setPrjID(request.getParameter("prjID"));
        task.setTaskID(request.getParameter("taskID"));
        task.setTaskTitle(request.getParameter("taskTitle"));
        task.setTaskCreateDate(request.getParameter("taskCreateDate") + " " + request.getParameter("taskCreateTime"));
        task.setTaskEndDate(request.getParameter("taskEndDate") + " " + request.getParameter("taskEndTime"));
        task.setTaskImportance(Integer.parseInt(request.getParameter("taskImportance")));
        task.setTaskRemark(request.getParameter("taskRemark"));
        pw.println("<script>if(confirm(\"" + projectFactory.changeTaskInfo(task) + "\") == true) {" +
                "window.location = \"/ProjectServlet?type=seePrj&prjID=" + task.getPrjID() + "\"; }</script>");
    }

    private void seeTask(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        PrintWriter pw = response.getWriter();
        String taskID = request.getParameter("taskID");
        Task task = projectFactory.getTaskByID(taskID);
        request.getSession().setAttribute("task", task);
        ArrayList<TaskJoin> taskJoins = projectFactory.getTaskJoins(taskID);
        request.getSession().setAttribute("taskJoins", taskJoins);
        ArrayList<User> allUsers = projectFactory.allUsers();
        request.getSession().setAttribute("allUsers", allUsers);
        ArrayList<User> allJoinUsers = projectFactory.allJoinUsers(taskID);
        request.getSession().setAttribute("allJoinUsers", allJoinUsers);
        UserListFactory userListFactory = new UserListFactory(allUsers);
        request.getSession().setAttribute("userListFactory", userListFactory);
        pw.println("<script>window.location = \"/myPrj.jsp?taskInfo=true\";</script>");
    }

    private void deleteTaskJoin(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        PrintWriter pw = response.getWriter();
        String taskID = request.getParameter("taskID");
        String userName = request.getParameter("userName");
        pw.println("<script>if(confirm(\"" + projectFactory.deleteTaskJoinInfo(taskID, userName) + "\") == true) {" +
                "window.location = \"/ProjectServlet?type=seeTask&taskID=" + taskID + "\"; }</script>");
    }

    private void addTaskJoin(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        PrintWriter pw = response.getWriter();
        TaskJoin taskJoin = new TaskJoin();
        taskJoin.setPrjID(request.getParameter("prjID"));
        taskJoin.setTaskID(request.getParameter("taskID"));
        taskJoin.setUserName(request.getParameter("userName"));
        pw.println("<script>if(confirm(\"" + projectFactory.addTaskJoinInfo(taskJoin) + "\") == true) {" +
                "window.location = \"/ProjectServlet?type=seeTask&taskID=" + taskJoin.getTaskID() + "\"; }</script>");
    }

    private void allUsers(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        PrintWriter pw = response.getWriter();
        ArrayList<User> allUsers = projectFactory.allUsers();
        request.getSession().setAttribute("allUsers", allUsers);
        pw.println("<script>window.location = \"/myPrj.jsp?allUsers=true\";</script>");
    }

    private void searchUsers(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        PrintWriter pw = response.getWriter();
        String userName = request.getParameter("userName");
        ArrayList<User> searchUsers = projectFactory.searchUsersByName(userName);
        request.getSession().setAttribute("allUsers", searchUsers);
        pw.println("<script>window.location = \"/myPrj.jsp?allUsers=true\";</script>");
    }

    private void undo(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        PrintWriter pw = response.getWriter();
        pw.println("<script>if(window.parent != null) { window.parent.location = \".\"; }" +
                "else { window.location = \".\"; }</script>");
    }
}
