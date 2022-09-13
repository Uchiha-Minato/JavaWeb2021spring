package controller;

import domain.Project;
import domain.Task;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import util.ProjectListFactory;
import util.TaskFactory;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

@WebServlet(name = "TaskServlet", urlPatterns = "/TaskServlet")
public class TaskServlet extends HttpServlet {
    private TaskFactory taskFactory = new TaskFactory();

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
            if(type.equals("joinTasks")) {
                joinTasks(request, response);
            }
            else if(type.equals("seeTask")) {
                seeTask(request, response);
            }
            else if(type.equals("changeTaskStatus")) {
                changeTaskStatus(request, response);
            }
        }
        else {
            undo(request, response);
        }
    }

    private void undo(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        PrintWriter pw = response.getWriter();
        pw.println("<script>if(window.parent != null) { window.parent.location = \".\"; }" +
                "else { window.location = \".\"; }</script>");
    }

    private void joinTasks(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        PrintWriter pw = response.getWriter();
        String userName = request.getParameter("userName");
        ArrayList<Project> projects = taskFactory.allProjects();
        ProjectListFactory projectListFactory = new ProjectListFactory(projects);
        request.getSession().setAttribute("projectListFactory", projectListFactory);
        ArrayList<Task> joinTasks = taskFactory.joinTasks(userName);
        request.getSession().setAttribute("joinTasks", joinTasks);
        pw.println("<script>window.location = \"/myTask.jsp\";</script>");
    }

    private void seeTask(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        PrintWriter pw = response.getWriter();
        String taskID = request.getParameter("taskID");
        Task task = taskFactory.getTaskById(taskID);
        request.getSession().setAttribute("task", task);
        pw.println("<script>window.location = \"/myTask.jsp?joinTaskInfo=true\";</script>");
    }

    private void changeTaskStatus(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        PrintWriter pw = response.getWriter();
        String taskID = request.getParameter("taskID");
        String taskStatus = request.getParameter("taskStatus") + "%";
        String userName = request.getParameter("userName");
        String prjID = request.getParameter("prjID");
        pw.println("<script>if(confirm(\"" + taskFactory.changeTaskStatusInfo(taskID, taskStatus, prjID) + "\") == true)" +
                "{ window.location = \"/TaskServlet?type=joinTasks&userName=" + userName + "\" }</script>");
    }
}
