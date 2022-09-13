package controller;

import domain.User;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import util.UserFactory;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "UserServlet", urlPatterns = "/UserServlet")
public class UserServlet extends HttpServlet {
    private final UserFactory userFactory = new UserFactory();

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
            switch (type) {
                case "login" -> login(request, response);
                case "register" -> register(request, response);
                case "deleteUser" -> deleteUser(request, response);
                case "changePsw" -> changePsw(request, response);
            }
        }
        else {
            undo(request, response);
        }
    }

    private void login(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        PrintWriter pw = response.getWriter();
        String userName = request.getParameter("userName");
        String password = request.getParameter("password");
        if(userFactory.matched(userName, password)) {
            User user = userFactory.getUser(userName);
            request.getSession().setAttribute("user", user);
            RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/main.jsp");
            dispatcher.forward(request, response);
        }
        else {
            if(userFactory.existed(userName))
                pw.println("<script>if(confirm(\"密码错误！\") == true) { window.location = \".\"; }</script>");
            else
                pw.println("<script>if(confirm(\"用户不存在！\") == true) { window.location = \".\"; }</script>");
        }
        pw.println("<script>window.location = \".\";</script>");
    }

    private void register(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        PrintWriter pw = response.getWriter();
        User user = new User();
        user.setUserName(request.getParameter("userName"));
        user.setPassword(request.getParameter("password"));
        user.setTel(request.getParameter("tel"));
        if(!userFactory.existed(user.getUserName())) {
            if(userFactory.register(user))
                pw.println("<script>if(confirm(\"注册成功\") == true) { window.location = \"/register.jsp\"; }</script>");
            else
                pw.println("<script>if(confirm(\"注册失败，请重试\") == true) { window.location = \"/register.jsp\"; }</script>");
        }
        else
            pw.println("<script>if(confirm(\"用户已存在\") == true) { window.location = \"/register.jsp\"; }</script>");
    }

    private void deleteUser(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        PrintWriter pw = response.getWriter();
        String userName = request.getParameter("userName");
        if(userFactory.existed(userName)) {
            if(userFactory.deleteUser(userName))
                pw.println("<script>if(confirm(\"注销成功\") == true) { window.parent.location = \".\"; }</script>");
            else
                pw.println("<script>alert(\"请先把所有项目删除再注销\");</script>");
        }
        else
            pw.println("<script>if(confirm(\"用户不存在\") == true) { window.parent.location = \".\" }</script>");
    }

    private void changePsw(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        PrintWriter pw = response.getWriter();
        String userName = request.getParameter("userName");
        String password = request.getParameter("password");

        if(userFactory.existed(userName)) {
            if(userFactory.changePsw(userName, password))
                pw.println("<script>if(confirm(\"修改成功\") == true) { window.parent.location = \".\"; }</script>");
            else
                pw.println("<script>alert(\"修改失败，请重试\");</script>");
        }
        else
            pw.println("<script>if(confirm(\"用户不存在\") == true) { window.parent.location = \".\" }</script>");
    }

    private void undo(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        PrintWriter pw = response.getWriter();
        pw.println("<script>if(window.parent != null) { window.parent.location = \".\"; }" +
                "else { window.location = \".\"; }</script>");
    }
}
