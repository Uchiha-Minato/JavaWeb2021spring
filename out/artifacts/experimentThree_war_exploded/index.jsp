<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width,initial-scale=1">
    <title>登录</title>
    <link rel="stylesheet" href="css/bootstrap.css">
    <link rel="stylesheet" href="css/login.css">
    <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
    <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <script src="js/jquery-1.11.2.min.js"></script>
    <script src="js/bootstrap.min.js"></script>
    <script type="text/javascript">
        history.pushState(null, null, document.URL);
        window.addEventListener('popstate',
            function() {
                history.pushState(null, null, document.URL);
            });
    </script>
</head>
<body>
    <div class="container-fluid">
        <div id="login-form">
            <h2 class="text-center">用户登录</h2>
            <form class="form-horizontal" action="UserServlet" method="post">
                <div class="input-group">
                    <div class="input-group-addon">
                        <span class="glyphicon glyphicon-user"></span>
                    </div>
                    <input type="text" class="form-control" id="userName" name="userName" placeholder="请输入用户名" required="required" />
                </div>
                <div class="input-group">
                    <div class="input-group-addon">
                        <span class="glyphicon glyphicon-lock"></span>
                    </div>
                    <input type="password" class="form-control" id="password" name="password" placeholder="请输入密码" required="required" />
                </div>
                <div class="checkbox">
                    <label><input type="checkbox"> 记住我</label>
                    <a href="#" style="float: right;">忘记密码</a>
                </div>
                <button class="btn btn-default btn-primary btn-block">登录</button>
                <a href="/register.jsp">还没有注册？点击注册</a>
                <input type="hidden" name="type" value="login">
            </form>
        </div>
    </div>
</body>
</html>