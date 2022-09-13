<%@ page import="domain.User" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.lang.*" %>
<%@ page import="domain.Task" %>
<%@ page import="util.ProjectListFactory" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    User user = (User)session.getAttribute("user");
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>我的任务</title>
    <script type="text/javascript">
        window.onload = function() {
            document.getElementById("joinTaskInfo").style.visibility = "hidden";
            if(getQueryVariable("joinTaskInfo") == "true") {
                document.getElementById("joinTaskInfo").style.visibility = "visible";
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

        function onExit() {
            document.getElementById("joinTaskInfo").style.visibility = "hidden";
            document.getElementById("body").style.opacity = "1";
            document.getElementById("body").style.pointerEvents = "initial";
        }

        function changeTaskStatus(obj, i) {
            if(obj.value == "更新进度") {
                document.getElementsByName("range")[i].disabled = "";
                document.getElementsByName("textRange")[i].disabled = "";
                obj.value = "提交";

                for(var j = 0; j < document.getElementsByName("range").length; j ++) {
                    document.getElementsByName("range")[j].onclick = function() {
                        this.parentNode.children[1].value = this.value + "%";
                    };
                }

                for(var j = 0; j < document.getElementsByName("textRange").length; j ++) {
                    document.getElementsByName("textRange")[j].onkeyup = function() {
                        this.parentNode.children[0].value = this.value.split("%")[0];
                    }
                }
            }
            else if(obj.value == "提交") {
                document.getElementsByName("range")[i].disabled = "disabled";
                document.getElementsByName("textRange")[i].disabled = "disabled";
                obj.value= "更新进度";
                var range = document.getElementsByName("range")[i].value;
                var inputRange = document.createElement("input");
                inputRange.type = "hidden";
                inputRange.name = "taskStatus";
                inputRange.value = range;
                obj.parentNode.appendChild(inputRange);
                obj.type = "submit";
            }
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

        textarea {
            width: 99%;
            height: 150px;
            resize: none;
            font-size: 20px;
        }

        label {
            width: 100%;
        }

        #myTasks input {
            transform: translate(0, 50%);
            width: 90%;
        }

        input {
            text-align: center;
        }

        input[type="button"], input[type="submit"], input[type="radio"], button {
            cursor: pointer;
        }

        input[type="radio"] {
            width:auto!important;
            display:inherit;
            font-size: 16px;
            vertical-align: middle;
            margin-top: 0px;
            margin-bottom: 0px;
        }

        #myTasks {
            width: 1200px;
        }

        .rangeTd {
            width: 300px!important;
            height: 75px!important;
        }

        #joinTaskInfo {
            z-index: 2;
            position: absolute;
            left: 50%;
            top: 15%;
            transform: translate(-50%, 0);
            background-color: rgb(240, 240, 240);
            color: black;
            opacity: 0.9;
            border: 5px double #e0e0e0;
            width: 500px;
        }

        #joinTaskInfo textarea {
            height: 200px!important;
        }
    </style>
</head>
<body>
    <div id="body">
        <table id="myTasks">
            <tr>
                <th>项目编号</th>
                <th>项目标题</th>
                <th>项目负责人</th>
                <th>任务编号</th>
                <th>任务标题</th>
                <th>任务开始时间</th>
                <th>任务结束时间</th>
                <th>任务重要度</th>
                <th>任务进度</th>
                <th></th>
                <th></th>
                <th></th>
            </tr>
            <%
                try {
                    ProjectListFactory projectListFactory = (ProjectListFactory) session.getAttribute("projectListFactory");
                    ArrayList<Task> joinTasks = (ArrayList<Task>) session.getAttribute("joinTasks");
                    for(int i = 0; i < joinTasks.size(); i++) {
                        Task task = joinTasks.get(i);
                        out.println("<tr>");
                        out.println("<td>" + task.getPrjID() + "</td>");
                        out.println("<td>" + projectListFactory.getTitle(task.getPrjID()) + "</td>");
                        out.println("<td>" + projectListFactory.getUserName(task.getPrjID()) + "</td>");
                        out.println("<td>" + task.getTaskID() + "</td>");
                        out.println("<td>" + task.getTaskTitle() + "</td>");
                        out.println("<td>" + task.getTaskCreateDate() + "</td>");
                        out.println("<td>" + task.getTaskEndDate() + "</td>");
                        out.println("<td>" + task.getTaskImportance() + "</td>");
                        String status = task.getTaskStatus().split("%")[0];
                        int range = Integer.parseInt(status);
                        out.println("<td class=\"rangeTd\"><form><input type=\"range\" name=\"range\" value=\"" + range + "\" disabled>" +
                                "<input type=\"text\" name=\"textRange\" value=\"" + task.getTaskStatus() + "\" disabled></form></td>");
                        out.println("<td><form action=\"TaskServlet?type=seeTask\" method=\"post\">" +
                                "<input type=\"submit\" value=\"查看\">" +
                                "<input type=\"hidden\" name=\"taskID\" value=\"" + task.getTaskID() + "\"></form></td>");
                        out.println("<td><form action=\"TaskServlet?type=changeTaskStatus\" method=\"post\">" +
                                "<input type=\"button\" value=\"更新进度\" onclick=\"changeTaskStatus(this," + i + ")\">" +
                                "<input type=\"hidden\" name=\"taskID\" value=\"" + task.getTaskID() + "\">" +
                                "<input type=\"hidden\" name=\"userName\" value=\"" + user.getUserName() + "\">" +
                                "<input type=\"hidden\" name=\"prjID\" value=\"" + task.getPrjID() + "\"></form></td>");
                        out.println("</tr>");
                    }
                } catch(Exception e) {}
            %>
        </table>
    </div>
    <div id="joinTaskInfo">
        <%
            try {
                Task task = (Task) session.getAttribute("task");
                out.println("<p align=\"center\"><b style=\"font-size:20px\">" + task.getTaskTitle() + "</b></p>");
                out.println("<p><textarea disabled>任务描述：" + task.getTaskRemark() + "</textarea></p>");
            } catch(Exception e) {}
        %>
        <p align="center"><input type="button" value="返回" onclick="onExit()" style="width: 100px!important;"></p>
    </div>
</body>
</html>