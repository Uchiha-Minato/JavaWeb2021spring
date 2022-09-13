<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width,initial-scale=1">
    <title>注册</title>
    <link rel="stylesheet" href="css/bootstrap.css">
    <link rel="stylesheet" href="css/login.css">
    <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
    <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <script src="js/jquery-1.11.2.min.js"></script>
    <script src="js/bootstrap.min.js"></script>
    <script type="text/javascript">
        history.pushState(null, null, document.URL);
        window.addEventListener('popstate', function() {
            history.pushState(null, null, document.URL);
        });

        var success = 0;
        var nameRight = false;
        var pswRight = false;
        var psw2Right = false;
        var telRight = false;

        function checkName() {
            var name = form.userName.value;
            if(name.length < 1 || name.length > 10) {
                document.getElementById("error").style.visibility = "visible";
                document.getElementById("error").innerHTML = "用户名长度为1~10个字符";
                function error() {
                    document.getElementById("error").style.visibility = "hidden";
                    document.getElementById("error").innerHTML = null;
                }
                setTimeout(error, 1000);
                if(nameRight) {
                    if (success > 0) {
                        success--;
                    }
                    nameRight = false;
                }
            }
            else {
                if(!nameRight) {
                    if(success < 4) {
                        success++;
                    }
                    nameRight = true;
                }
            }
        }

        function checkPsw1() {
            var psw1 = form.password.value;
            var flagZM = false;
            var flagSZ = false;
            var flagQT = false;
            if(psw1.length < 6 || psw1.length > 10) {
                document.getElementById("error").style.visibility = "visible";
                document.getElementById("error").innerHTML = "密码长度为6~10个字符";
                function error() {
                    document.getElementById("error").style.visibility = "hidden";
                    document.getElementById("error").innerHTML = null;
                }
                setTimeout(error, 1000);
                if(pswRight) {
                    if(success > 0) {
                        success --;
                    }
                    pswRight = false;
                }
            }
            else {
                for(var i = 0; i < psw1.length; i ++) {
                    if((psw1.charAt(i) >= 'A' && psw1.charAt(i) <= 'Z') || (psw1.charAt(i) >= 'a' && psw1.charAt(i) <= 'z')) {
                        flagZM = true;
                    }
                    else if(psw1.charAt(i) >= '0' && psw1.charAt(i) <= '9') {
                        flagSZ = true;
                    }
                    else {
                        flagQT = true;
                    }
                }
                if(!flagZM || !flagSZ || flagQT) {
                    document.getElementById("error").style.visibility = "visible";
                    document.getElementById("error").innerHTML = "密码必须由数字和字母组成";
                    function error() {
                        document.getElementById("error").style.visibility = "hidden";
                        document.getElementById("error").innerHTML = null;
                    }
                    setTimeout(error, 1000);
                    if(pswRight) {
                        if(success > 0) {
                            success --;
                        }
                        pswRight = false;
                    }
                }
                else {
                    if(!pswRight) {
                        if (success < 4) {
                            success++;
                        }
                        pswRight = true;
                    }
                }
            }
        }

        function checkPsw2() {
            if(form.password.value != form.password2.value) {
                document.getElementById("error").style.visibility = "visible";
                document.getElementById("error").innerHTML = "两次密码不一致";
                function error() {
                    document.getElementById("error").style.visibility = "hidden";
                    document.getElementById("error").innerHTML = null;
                }
                setTimeout(error, 1000);
                if(psw2Right) {
                    if(success > 0) {
                        success --;
                    }
                    psw2Right = false;
                }
            }
            else {
                if(!psw2Right) {
                    if (success < 4) {
                        success++;
                    }
                    psw2Right = true;
                }
            }
        }

        function checkTel() {
            var tel = form.tel.value;
            var reg = /(13[0-9]|14[5|7]|15[0|1|2|3|4|5|6|7|8|9]|18[0|1|2|3|5|6|7|8|9])\d{8}/;
            if(tel.length != 11) {
                document.getElementById("error").style.visibility = "visible";
                document.getElementById("error").innerHTML = "电话长度为11位数字";
                function error() {
                    document.getElementById("error").style.visibility = "hidden";
                    document.getElementById("error").innerHTML = null;
                }
                setTimeout(error, 1000);
                if(telRight) {
                    if (success > 0) {
                        success--;
                    }
                    telRight = false;
                }
            }
            else {
                if(!reg.test(tel)) {
                    document.getElementById("error").style.visibility = "visible";
                    document.getElementById("error").innerHTML = "电话格式错误";
                    function error() {
                        document.getElementById("error").style.visibility = "hidden";
                        document.getElementById("error").innerHTML = null;
                    }
                    setTimeout(error, 1000);
                    if(telRight) {
                        if(success > 0) {
                            success --;
                        }
                        telRight = false;
                    }
                }
                else {
                    if(!telRight) {
                        if (success < 4) {
                            success++;
                        }
                        telRight = true;
                    }
                }
            }
        }

        function onRegister() {
            if(success >= 4) {
                document.getElementById("submit").type = "submit";
            }
            else {
                document.getElementById("error").style.visibility = "visible";
                document.getElementById("error").innerHTML = "注册失败，输入格式错误";
                function error() {
                    document.getElementById("error").style.visibility = "hidden";
                    document.getElementById("error").innerHTML = null;
                }
                setTimeout(error, 1000);
            }
        }

        function onExit() {
            window.location = ".";
        }
    </script>
    <style type="text/css">
        #login-form {
            height: 360px !important;
            transform: translate(0, -10%);
        }

        #error {
            z-index: 1;
            visibility: hidden;
            opacity: 0.75;
            position: absolute;
            left: 50%;
            top: 5%;
            transform: translate(-50%, 0);
            border-left: 2px inset red;
            border-top: 2px inset red;
            border-right: 2px outset red;
            border-bottom: 2px outset red;
            background: white;
            text-align: center;
            font-size: 25px;
            color: red;
            width: 400px;
            height: 50px;
        }
    </style>
</head>
<body>
    <div class="container-fluid">
        <div id="login-form">
            <h2 class="text-center">用户注册</h2>
            <form class="form-horizontal" name="form" action="UserServlet?type=register" method="post">
                <div class="input-group">
                    <div class="input-group-addon">
                        <span class="glyphicon glyphicon-user"></span>
                    </div>
                    <input type="text" class="form-control" id="userName" name="userName" placeholder="请输入用户名" onblur="checkName()" required="required" />
                </div>
                <div class="input-group">
                    <div class="input-group-addon">
                        <span class="glyphicon glyphicon-lock"></span>
                    </div>
                    <input type="password" class="form-control" id="password1" name="password" placeholder="请输入密码" onblur="checkPsw1()" required="required" />
                </div>
                <div class="input-group">
                    <div class="input-group-addon">
                        <span class="glyphicon glyphicon-lock"></span>
                    </div>
                    <input type="password" class="form-control" id="password2" name="password2" placeholder="请确认密码" onblur="checkPsw2()" required="required" />
                </div>
                <div class="input-group">
                    <div class="input-group-addon">
                        <span class="glyphicon glyphicon-phone"></span>
                    </div>
                    <input type="text" class="form-control" id="tel" name="tel" placeholder="请输入电话号码" onblur="checkTel()" required="required" />
                </div>
                <button class="btn btn-default btn-primary btn-block" type="button" onclick="onRegister()" id="submit">注册</button>
                <button class="btn btn-default btn-primary btn-block" type="button" onclick="onExit()">返回</button>
            </form>
        </div>
        <div id="error"></div>
    </div>
</body>
</html>