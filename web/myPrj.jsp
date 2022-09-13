<%@ page import="domain.User" %>
<%@ page import="domain.Project" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.Date" %>
<%@ page import="domain.Task" %>
<%@ page import="domain.TaskJoin" %>
<%@ page import="util.UserListFactory" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    User user = (User)session.getAttribute("user");
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>我的项目</title>
    <script type="text/javascript">
        window.onload = function() {
            document.getElementById("create").style.visibility = "hidden";
            document.getElementById("change").style.visibility = "hidden";
            document.getElementById("information").style.visibility = "hidden";
            document.getElementById("createTask").style.visibility = "hidden";
            document.getElementById("changeTask").style.visibility = "hidden";
            document.getElementById("taskInfo").style.visibility = "hidden";
            document.getElementById("allUsers").style.visibility = "hidden";
            if(getQueryVariable("info") == "true") {
                document.getElementById("information").style.visibility = "visible";
                document.getElementById("body").style.opacity = "0.15";
                document.getElementById("body").style.pointerEvents = "none";
            }
            if(getQueryVariable("taskInfo") == "true") {
                document.getElementById("taskInfo").style.visibility = "visible";
                document.getElementById("information").style.visibility = "visible";
                document.getElementById("information").style.opacity = "0.15";
                document.getElementById("information").style.pointerEvents = "none";
                document.getElementById("body").style.opacity = "0.15";
                document.getElementById("body").style.pointerEvents = "none";
            }
            if(getQueryVariable("allUsers") == "true") {
                document.getElementById("allUsers").style.visibility = "visible";
                document.getElementById("taskInfo").style.visibility = "visible";
                document.getElementById("taskInfo").style.opacity = "0.15";
                document.getElementById("taskInfo").style.pointerEvents = "none";
                document.getElementById("information").style.visibility = "visible";
                document.getElementById("information").style.opacity = "0.15";
                document.getElementById("information").style.pointerEvents = "none";
                document.getElementById("body").style.opacity = "0.15";
                document.getElementById("body").style.pointerEvents = "none";
            }
        }

        function getQueryVariable(variable) {
            var query = window.location.search.substring(1);
            var vars = query.split("&");
            for (var i = 0; i < vars.length; i ++) {
                var pair = vars[i].split("=");
                if(pair[0] == variable) {
                    return pair[1];
                }
            }
            return(false);
        }

        function onCreate() {
            document.getElementById("create").style.visibility = "visible";
            document.getElementById("body").style.opacity = "0.15";
            document.getElementById("body").style.pointerEvents = "none";
        }

        function onExit() {
            document.getElementById("create").style.visibility = "hidden";
            document.getElementById("change").style.visibility = "hidden";
            document.getElementById("information").style.visibility = "hidden";
            document.getElementById("body").style.opacity = "1";
            document.getElementById("body").style.pointerEvents = "initial";
        }

        function onChange(obj) {
            var prjID = obj.parentNode.parentNode.parentNode.children[0].innerText;
            var prjTitle = obj.parentNode.parentNode.parentNode.children[1].innerText;
            var prjCreate = obj.parentNode.parentNode.parentNode.children[3].innerText;
            var prjCreateDate = prjCreate.split(" ")[0];
            var prjCreateTime = prjCreate.split(" ")[1];
            var prjEnd = obj.parentNode.parentNode.parentNode.children[4].innerText;
            var prjEndDate = prjEnd.split(" ")[0];
            var prjEndTime = prjEnd.split(" ")[1];
            var prjRemark = obj.parentNode.parentNode.parentNode.children[10].value;
            changeForm.prjTitle.value = prjTitle;
            changeForm.prjCreateDate.value = prjCreateDate;
            changeForm.prjCreateTime.value = prjCreateTime;
            changeForm.prjEndDate.value = prjEndDate;
            changeForm.prjEndTime.value = prjEndTime;
            changeForm.prjRemark.value = prjRemark;
            var inputPrjID = document.createElement("input");
            inputPrjID.type = "hidden";
            inputPrjID.name = "prjID";
            inputPrjID.value = prjID;
            document.getElementById("changeForm").appendChild(inputPrjID);
            document.getElementById("change").style.visibility = "visible";
            document.getElementById("body").style.opacity = "0.15";
            document.getElementById("body").style.pointerEvents = "none";
        }

        function onCreateTask(obj) {
            var prjID = obj.parentNode.parentNode.parentNode.children[1].value;
            var inputPrjID = document.createElement("input");
            inputPrjID.type = "hidden";
            inputPrjID.name = "prjID";
            inputPrjID.value = prjID;
            document.getElementById("createTaskForm").appendChild(inputPrjID);
            document.getElementById("createTask").style.visibility = "visible";
            document.getElementById("information").style.visibility = "visible";
            document.getElementById("information").style.opacity = "0.15";
            document.getElementById("information").style.pointerEvents = "none";
            document.getElementById("body").style.opacity = "0.15";
            document.getElementById("body").style.pointerEvents = "none";
        }

        function onTaskExit() {
            document.getElementById("createTask").style.visibility = "hidden";
            document.getElementById("changeTask").style.visibility = "hidden";
            document.getElementById("taskInfo").style.visibility = "hidden";
            document.getElementById("information").style.opacity = "1";
            document.getElementById("information").style.pointerEvents = "initial";
        }

        function onChangeTask(obj) {
            var prjID = obj.parentNode.parentNode.parentNode.parentNode.children[1].value;
            var taskID = obj.parentNode.parentNode.parentNode.children[0].innerText;
            var taskTitle = obj.parentNode.parentNode.parentNode.children[1].innerText;
            var taskCreate = obj.parentNode.parentNode.parentNode.children[2].innerText;
            var taskCreateDate = taskCreate.split(" ")[0];
            var taskCreateTime = taskCreate.split(" ")[1];
            var taskEnd = obj.parentNode.parentNode.parentNode.children[3].innerText;
            var taskEndDate = taskEnd.split(" ")[0];
            var taskEndTime = taskEnd.split(" ")[1];
            var taskImportance = obj.parentNode.parentNode.parentNode.children[4].innerText;
            var taskRemark = obj.parentNode.parentNode.parentNode.children[9].value;
            changeTaskForm.taskTitle.value = taskTitle;
            changeTaskForm.taskCreateDate.value = taskCreateDate;
            changeTaskForm.taskCreateTime.value = taskCreateTime;
            changeTaskForm.taskEndDate.value = taskEndDate;
            changeTaskForm.taskEndTime.value = taskEndTime;
            changeTaskForm.taskImportance.value = taskImportance;
            changeTaskForm.taskRemark.value = taskRemark;
            var inputPrjID = document.createElement("input");
            inputPrjID.type = "hidden";
            inputPrjID.name = "prjID";
            inputPrjID.value = prjID;
            document.getElementById("changeTaskForm").appendChild(inputPrjID);
            var inputTaskID = document.createElement("input");
            inputTaskID.type = "hidden";
            inputTaskID.name = "taskID";
            inputTaskID.value = taskID;
            document.getElementById("changeTaskForm").appendChild(inputTaskID);
            document.getElementById("changeTask").style.visibility = "visible";
            document.getElementById("information").style.visibility = "visible";
            document.getElementById("information").style.opacity = "0.15";
            document.getElementById("information").style.pointerEvents = "none";
            document.getElementById("body").style.opacity = "0.15";
            document.getElementById("body").style.pointerEvents = "none";
        }

        function onUserExit() {
            document.getElementById("allUsers").style.visibility = "hidden";
            document.getElementById("taskInfo").style.opacity = "1";
            document.getElementById("taskInfo").style.pointerEvents = "initial";
        }
    </script>
    <style type="text/css">
        #body table {
            text-align: center;
            border: 3px double #e0e0e0;
            border-collapse: collapse;
        }

        #body td {
            border: 1px solid #e0e0e0;
        }

        #body tr:nth-child(even) {
            background-color: #f0f0f0;
        }

        #body tr:hover {
            background-color: #ddd;
        }

        tr {
            height: 50px;
        }

        td {
            width: 200px;
            text-align: center;
        }

        #body {
            z-index: 1;
        }

        #create {
            z-index: 2;
            position: absolute;
            left: 50%;
            top: 10%;
            transform: translate(-50%, 0);
            background-color: rgb(240, 240, 240);
            opacity: 0.8;
            border: 5px double #e0e0e0;
        }

        textarea {
            width: 99%;
            height: 150px;
            resize: none;
            font-size: 20px;
        }

        #change {
            z-index: 2;
            position: absolute;
            left: 50%;
            top: 10%;
            transform: translate(-50%, 0);
            background-color: rgb(240, 240, 240);
            opacity: 0.8;
            border: 5px double #e0e0e0;
        }

        label {
            width: 100%;
        }

        #create td input, #change td input, #createTask td input, #changeTask td input {
            width: 100%;
        }

        #myPrjs input {
            transform: translate(0, 50%);
            width: 90%;
        }

        input {
            text-align: center;
        }

        input[type="button"], input[type="submit"], input[type="radio"], button {
            cursor: pointer;
        }

        #information table {
            text-align: center;
            border: 3px double #e0e0e0;
            border-collapse: collapse;
            width: 1000px;
        }

        #information td {
            border: 1px solid #e0e0e0;
        }

        #information tr:nth-child(even) {
            background-color: #f0f0f0;
        }

        #information tr:hover {
            background-color: #ddd;
        }

        #information input {
            transform: translate(0, 50%);
            width: 90%;
        }

        #information {
            z-index: 2;
            position: absolute;
            left: 50%;
            top: 5%;
            transform: translate(-50%, 0);
            background-color: rgb(240, 240, 240);
            width: 1000px;
        }

        #createTask {
            z-index: 3;
            position: absolute;
            left: 50%;
            top: 10%;
            transform: translate(-50%, 0);
            background-color: rgb(240, 240, 240);
            opacity: 0.8;
            border: 5px double #e0e0e0;
        }

        #changeTask {
            z-index: 3;
            position: absolute;
            left: 50%;
            top: 10%;
            transform: translate(-50%, 0);
            background-color: rgb(240, 240, 240);
            opacity: 0.8;
            border: 5px double #e0e0e0;
        }

        input[type="radio"] {
            width:auto!important;
            display:inherit;
            font-size: 16px;
            vertical-align: middle;
            margin-top: 0px;
            margin-bottom: 0px;
        }

        #taskInfo {
            z-index: 3;
            position: absolute;
            left: 50%;
            top: 5%;
            transform: translate(-50%, 0);
            background-color: rgb(240, 240, 240);
            width: 800px;
        }

        #taskInfo table {
            text-align: center;
            border: 3px double #e0e0e0;
            border-collapse: collapse;
            width: 800px;
        }

        #taskInfo td {
            border: 1px solid #e0e0e0;
        }

        #taskInfo tr:nth-child(even) {
            background-color: #f0f0f0;
        }

        #taskInfo tr:hover {
            background-color: #ddd;
        }

        #taskInfo input {
            transform: translate(0, 50%);
            width: 90%;
        }

        #allUsers table {
            text-align: center;
            border: 3px double #e0e0e0;
            border-collapse: collapse;
            width: 500px;
        }

        #allUsers th, #allUsers td {
            border: 1px solid #ddd;
        }

        #allUsers tr:nth-child(even) {
            background-color: #f0f0f0;
        }

        #allUsers tr:hover {
            background-color: #ddd;
        }

        #allUsers {
            z-index: 4;
            position: absolute;
            left: 50%;
            top: 15%;
            transform: translate(-50%, 0);
            background-color: rgb(240, 240, 240);
            width: 500px;
        }

        .allUsers-body input {
            transform: translate(0, 50%);
            width: 90%;
        }

        .input-search {
            text-align: center;
            width: 100%;
            height: 28px;
        }
    </style>
</head>
<body>
    <div id="body">
        <table id="myPrjs">
            <tr>
                <th>项目编号</th>
                <th>项目标题</th>
                <th>项目负责人</th>
                <th>项目开始时间</th>
                <th>项目结束时间</th>
                <th>项目进度</th>
                <th><button onclick="onCreate()">新建项目</button></th>
                <th></th>
                <th></th>
                <th></th>
            </tr>
            <%
                ArrayList<Project> myPrjs = (ArrayList<Project>)session.getAttribute("myPrjs");
                for(int i = 0; i < myPrjs.size(); i ++) {
                    out.println("<tr>");
                    out.println("<td>" + myPrjs.get(i).getPrjID() + "</td>");
                    out.println("<td>" + myPrjs.get(i).getPrjTitle() + "</td>");
                    out.println("<td>" + myPrjs.get(i).getUserName() + "</td>");
                    out.println("<td>" + myPrjs.get(i).getPrjCreateDate() + "</td>");
                    out.println("<td>" + myPrjs.get(i).getPrjEndDate() + "</td>");
                    String status = myPrjs.get(i).getPrjStatus().split("%")[0];
                    int range = Integer.parseInt(status);
                    out.println("<td><form><input type=\"range\" value=\"" + range + "\" disabled>" + status + "%</form></td>");
                    out.println("<td><form action=\"ProjectServlet?type=seePrj\" method=\"post\">" +
                            "<input type=\"submit\" value=\"查看\">" +
                            "<input type=\"hidden\" name=\"prjID\" value=\"" + myPrjs.get(i).getPrjID() + "\"></form></td>");

                    String prjCreateDate = myPrjs.get(i).getPrjCreateDate();
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    Date now = new Date();
                    Date start = sdf.parse(prjCreateDate);
                    if(now.before(start))
                        out.println("<td><form action=\"ProjectServlet?type=prjPublic\" method=\"post\">" +
                                "<input type=\"submit\" value=\"立即发布\">" +
                                "<input type=\"hidden\" name=\"prjID\" value=\"" + myPrjs.get(i).getPrjID() + "\">" +
                                "<input type=\"hidden\" name=\"userName\" value=\"" + user.getUserName() + "\"></form></td>");
                    else
                        out.println("<td><form><input type=\"button\" value=\"已发布\" onclick=\"javascript:alert('已发布')\"></form></td>");

                    out.println("<td><form><input type=\"button\" value=\"修改\" onclick=\"onChange(this)\"></form></td>");

                    out.println("<td><form action=\"ProjectServlet?type=deletePrj\" method=\"post\">" +
                            "<input type=\"submit\" value=\"删除\">" +
                            "<input type=\"hidden\" name=\"prjID\" value=\"" + myPrjs.get(i).getPrjID() + "\">" +
                            "<input type=\"hidden\" name=\"userName\" value=\"" + user.getUserName() + "\"></form></td>");
                    out.println("<input type=\"hidden\" value=\"" + myPrjs.get(i).getPrjRemark() + "\">");
                    out.println("</tr>");
                }
            %>
        </table>
    </div>
    <div id="create">
        <form action="ProjectServlet?type=createPrj" method="post" name="createForm" id="createForm">
            <table>
                <caption><p><b style="font-size: 20px">新建项目</b></p></caption>
                <tr>
                    <td><label>项目标题：</label></td>
                    <td colspan="2"><input type="text" name="prjTitle" required></td>
                </tr>
                <tr>
                    <td><label>项目开始时间：</label></td>
                    <td><input type="date" name="prjCreateDate" required></td>
                    <td><input type="time" step="1" name="prjCreateTime" required></td>
                </tr>
                <tr>
                    <td><label>项目结束时间：</label></td>
                    <td><input type="date" name="prjEndDate" required></td>
                    <td><input type="time" step="1" name="prjEndTime" required></td>
                </tr>
                <tr>
                    <td><label>项目描述：</label></td>
                    <td colspan="2"><textarea rows="5" cols="20" form="createForm" name="prjRemark" required></textarea></td>
                </tr>
            </table><br>
            <div align="center">
                <input type="submit" value="提交">
                <input type="button" onclick="onExit()" value="取消">
            </div>
            <input type="hidden" name="userName" value="<%=user.getUserName()%>">
        </form>
    </div>
    <div id="change">
        <form action="ProjectServlet?type=changePrj" method="post" name="changeForm" id="changeForm">
            <table>
                <caption><p><b style="font-size: 20px">修改项目</b></p></caption>
                <tr>
                    <td><label>项目标题：</label></td>
                    <td colspan="2"><input type="text" name="prjTitle" required></td>
                </tr>
                <tr>
                    <td><label>项目开始时间：</label></td>
                    <td><input type="date" name="prjCreateDate" required></td>
                    <td><input type="time" step="1" name="prjCreateTime" required></td>
                </tr>
                <tr>
                    <td><label>项目结束时间：</label></td>
                    <td><input type="date" name="prjEndDate" required></td>
                    <td><input type="time" step="1" name="prjEndTime" required></td>
                </tr>
                <tr>
                    <td><label>项目描述：</label></td>
                    <td colspan="2"><textarea rows="5" cols="20" form="changeForm" name="prjRemark" required></textarea></td>
                </tr>
            </table><br>
            <div align="center">
                <input type="submit" value="提交">
                <input type="button" onclick="onExit()" value="取消">
            </div>
            <input type="hidden" name="userName" value="<%=user.getUserName()%>">
        </form>
    </div>
    <div id="information">
        <table>
            <tr>
                <th>任务编号</th>
                <th>任务标题</th>
                <th>任务开始时间</th>
                <th>任务结束时间</th>
                <th>任务重要度</th>
                <th>任务进度</th>
                <th><button onclick="onCreateTask(this)">添加任务</button></th>
                <th><button onclick="onExit()">返回</button></th>
                <th></th>
            </tr>
            <%
                try {
                    ArrayList<Task> tasks = (ArrayList<Task>)session.getAttribute("tasks");
                    Project project = (Project)session.getAttribute("project");
                    out.println("<p align=\"center\"><b style=\"font-size:20px\">" + project.getPrjTitle() + "</b></p>");
                    out.println("<p><textarea disabled>项目描述：" + project.getPrjRemark() + "</textarea></p>");
                    out.println("<input type=\"hidden\" value=\"" + project.getPrjID() + "\">");
                    if(tasks != null && project != null) {
                        for(int i = 0; i < tasks.size(); i ++) {
                            Task task = tasks.get(i);
                            out.println("<tr>");
                            out.println("<td>" + task.getTaskID() + "</td>");
                            out.println("<td>" + task.getTaskTitle() + "</td>");
                            out.println("<td>" + task.getTaskCreateDate() + "</td>");
                            out.println("<td>" + task.getTaskEndDate() + "</td>");
                            out.println("<td>" + task.getTaskImportance() + "</td>");
                            String status = task.getTaskStatus().split("%")[0];
                            int range = Integer.parseInt(status);
                            out.println("<td><form><input type=\"range\" value=\"" + range + "\" disabled>" + status + "%</form></td>");
                            out.println("<td><form action=\"ProjectServlet?type=seeTask\" method=\"post\">" +
                                    "<input type=\"submit\" value=\"查看\">" +
                                    "<input type=\"hidden\" name=\"taskID\" value=\"" + task.getTaskID() + "\"></form></td>");
                            out.println("<td><form><input type=\"button\" value=\"修改\" onclick=\"onChangeTask(this)\"></form></td>");
                            out.println("<td><form action=\"ProjectServlet?type=deleteTask\" method=\"post\">" +
                                    "<input type=\"submit\" value=\"删除\">" +
                                    "<input type=\"hidden\" name=\"taskID\" value=\"" + task.getTaskID() + "\">" +
                                    "<input type=\"hidden\" name=\"prjID\" value=\"" + task.getPrjID() + "\"></form></td>");
                            out.println("<input type=\"hidden\" value=\"" + tasks.get(i).getTaskRemark() + "\">");
                            out.println("</tr>");
                        }
                    }
                } catch(Exception e) {}
            %>
        </table>
    </div>
    <div id="createTask">
        <form action="ProjectServlet?type=createTask" method="post" name="createTaskForm" id="createTaskForm">
            <table>
                <caption><p><b style="font-size: 20px">添加任务</b></p></caption>
                <tr>
                    <td><label>任务标题：</label></td>
                    <td colspan="2"><input type="text" name="taskTitle" required></td>
                </tr>
                <tr>
                    <td><label>任务开始时间：</label></td>
                    <td><input type="date" name="taskCreateDate" required></td>
                    <td><input type="time" step="1" name="taskCreateTime" required></td>
                </tr>
                <tr>
                    <td><label>任务结束时间：</label></td>
                    <td><input type="date" name="taskEndDate" required></td>
                    <td><input type="time" step="1" name="taskEndTime" required></td>
                </tr>
                <tr>
                    <td><label>任务描述：</label></td>
                    <td colspan="2"><textarea rows="5" cols="20" form="createTaskForm" name="taskRemark" required></textarea></td>
                </tr>
                <tr>
                    <td><label>任务重要度：</label></td>
                    <td colspan="2" style="text-align: left!important">
                        <label style="cursor: pointer"><input type="radio" name="taskImportance" value="1" checked>1</label><br>
                        <label style="cursor: pointer"><input type="radio" name="taskImportance" value="2">2</label><br>
                        <label style="cursor: pointer"><input type="radio" name="taskImportance" value="3">3</label>
                    </td>
                </tr>
            </table><br>
            <div align="center">
                <input type="submit" value="提交">
                <input type="button" onclick="onTaskExit()" value="取消">
            </div>
        </form>
    </div>
    <div id="changeTask">
        <form action="ProjectServlet?type=changeTask" method="post" name="changeTaskForm" id="changeTaskForm">
            <table>
                <caption><p><b style="font-size: 20px">修改任务</b></p></caption>
                <tr>
                    <td><label>任务标题：</label></td>
                    <td colspan="2"><input type="text" name="taskTitle" required></td>
                </tr>
                <tr>
                    <td><label>任务开始时间：</label></td>
                    <td><input type="date" name="taskCreateDate" required></td>
                    <td><input type="time" step="1" name="taskCreateTime" required></td>
                </tr>
                <tr>
                    <td><label>任务结束时间：</label></td>
                    <td><input type="date" name="taskEndDate" required></td>
                    <td><input type="time" step="1" name="taskEndTime" required></td>
                </tr>
                <tr>
                    <td><label>任务描述：</label></td>
                    <td colspan="2"><textarea rows="5" cols="20" form="changeTaskForm" name="taskRemark" required></textarea></td>
                </tr>
                <tr>
                    <td><label>任务重要度：</label></td>
                    <td colspan="2" style="text-align: left!important;">
                        <label style="cursor: pointer"><input type="radio" name="taskImportance" value="1">1</label><br>
                        <label style="cursor: pointer"><input type="radio" name="taskImportance" value="2">2</label><br>
                        <label style="cursor: pointer"><input type="radio" name="taskImportance" value="3">3</label>
                    </td>
                </tr>
            </table><br>
            <div align="center">
                <input type="submit" value="提交">
                <input type="button" onclick="onTaskExit()" value="取消">
            </div>
        </form>
    </div>
    <div id="taskInfo">
        <table>
            <tr>
                <th>用户名</th>
                <th>电话号码</th>
                <th>参与时间</th>
                <th><form action="ProjectServlet?type=allUsers" method="post"><input type="submit" value="添加成员"></form></th>
                <th><form><input type="button" value="返回" onclick="onTaskExit()"></form></th>
            </tr>
            <%
                try {
                    ArrayList<TaskJoin> taskJoins = (ArrayList<TaskJoin>)session.getAttribute("taskJoins");
                    UserListFactory userListFactory = (UserListFactory)session.getAttribute("userListFactory");
                    Task task = (Task)session.getAttribute("task");
                    out.println("<p align=\"center\"><b style=\"font-size:20px\">" + task.getTaskTitle() + "</b></p>");
                    out.println("<p><textarea disabled>项目描述：" + task.getTaskRemark() + "</textarea></p>");
                    if(taskJoins != null && userListFactory != null && task != null) {
                        for(int i = 0; i < taskJoins.size(); i ++) {
                            TaskJoin taskJoin = taskJoins.get(i);
                            out.println("<tr>");
                            out.println("<td>" + taskJoin.getUserName() + "</td>");
                            out.println("<td>" + userListFactory.getTel(taskJoin.getUserName()) + "</td>");
                            out.println("<td>" + taskJoin.getJoinDate() + "</td>");
                            out.println("<td colspan=\"2\"><form action=\"ProjectServlet?type=deleteTaskJoin\" method=\"post\">" +
                                    "<input type=\"submit\" value=\"移除\">" +
                                    "<input type=\"hidden\" name=\"taskID\" value=\"" + taskJoin.getTaskID() + "\">" +
                                    "<input type=\"hidden\" name=\"userName\" value=\"" + taskJoin.getUserName() + "\"></form></td>");
                        }
                    }
                } catch(Exception e) {}
            %>
        </table>
    </div>
    <div id="allUsers">
        <table>
            <tr>
                <form action="ProjectServlet?type=searchUsers" method="post">
                    <td colspan="2">
                        <input type="text" name="userName" placeholder="请输入用户名" onfocus="this.placeholder = ''" onblur="this.placeholder = '请输入用户名'"
                               autocomplete="off" class="input-search">
                    </td>
                    <td><input type="submit" value="搜索" class="input-search"></td>
                    <td><input type="button" value="返回" class="input-search" onclick="onUserExit()"></td>
                </form>
            </tr>
            <tr>
                <th>用户名</th>
                <th>电话号码</th>
                <th style="border: none!important"></th>
                <th style="border: none!important"></th>
            </tr>
            <%
                try {
                    ArrayList<User> allUsers = (ArrayList<User>)session.getAttribute("allUsers");
                    ArrayList<User> allJoinUsers = (ArrayList<User>)session.getAttribute("allJoinUsers");
                    Task task = (Task) session.getAttribute("task");
                    Project project = (Project) session.getAttribute("project");
                    if(allUsers != null && allJoinUsers != null && task != null && project != null) {
                        UserListFactory joinListFactory = new UserListFactory(allJoinUsers);
                        for (int i = 0; i < allUsers.size(); i++) {
                            out.println("<tr class=\"allUsers-body\">");
                            out.println("<td>" + allUsers.get(i).getUserName() + "</td>");
                            out.println("<td>" + allUsers.get(i).getTel() + "</td>");
                            if(joinListFactory.existed(allUsers.get(i).getUserName())) {
                                out.println("<td colspan=\"2\"><form><input type=\"button\" value=\"已添加\" onclick=\"javascript:alert('用户已参与该任务')\"></form></td>");
                            }
                            else
                                out.println("<td colspan=\"2\"><form action=\"ProjectServlet?type=addTaskJoin\" method=\"post\">" +
                                        "<input type=\"submit\" value=\"添加\">" +
                                        "<input type=\"hidden\" name=\"prjID\" value=\"" + project.getPrjID() + "\">" +
                                        "<input type=\"hidden\" name=\"taskID\" value=\"" + task.getTaskID() + "\">" +
                                        "<input type=\"hidden\" name=\"userName\" value=\"" + allUsers.get(i).getUserName() + "\"></form></td>");
                            out.println("</tr>");
                        }
                    }
                } catch(Exception e) {}
            %>
        </table>
    </div>
</body>
</html>
