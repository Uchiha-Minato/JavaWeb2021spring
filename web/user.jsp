<%@ page import="domain.User" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    User user = (User)session.getAttribute("user");
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>用户信息</title>
    <style>
        table {
            text-align: center;
            border: 3px double #e0e0e0;
            border-collapse: collapse;
        }

        tr:hover {
            background-color: #ddd;
        }

        tr {
            height: 40px;
        }

        th {
            background-color: #f0f0f0;
        }

        td {
            width: 150px;
        }
    </style>
    <script>
        function onSee(length) {
            if(document.getElementById("button").innerText == "查看密码") {
                document.getElementById("password").innerText = "<%=user.getPassword()%>";
                document.getElementById("button").innerText = "隐藏密码";
            }
            else {
                var s = "";
                for(var i = 0; i < length; i ++) {
                    s += "*";
                }
                document.getElementById("password").innerText = s;
                document.getElementById("button").innerText = "查看密码";
            }
        }
    </script>
</head>
<body>
    <div>
        <table>
            <tr>
                <th>用户名</th>
                <th>密&nbsp;&nbsp;&nbsp;码</th>
                <th>电&nbsp;&nbsp;&nbsp;话</th>
            </tr>
            <tr>
                <td><span><%=user.getUserName()%></span></td>
                <td><span id="password"><%
                    for(int i = 0; i < user.getPassword().length(); i ++)
                        out.print("*");
                %></span></td>
                <td><span><%=user.getTel()%></span></td>
            </tr>
            <tr>
                <td>
                    <button onclick="onSee(<%=user.getPassword().length()%>)" id="button" style="cursor: pointer">查看密码</button>
                </td>
                <td>
                    <form action="UserServlet?type=deleteUser" method="post">
                        <input type="submit" value="注销" style="cursor: pointer;transform: translate(0,34%)">
                        <input type="hidden" name="userName" value="<%=user.getUserName()%>">
                    </form>
                </td>
                <td></td>
            </tr>
        </table>
    </div>
</body>
</html>
