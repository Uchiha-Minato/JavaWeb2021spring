<%@ page import="domain.User" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    User user = (User)session.getAttribute("user");
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width,initial-scale=1">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="keywords" content="HTML,CSS,XML,JavaScript">
    <title>修改密码</title>
    <style type="text/css">
        body {
            font: 14px/28px "微软雅黑";
        }

        .contact *:focus {
            outline: none;
        }

        .contact {
            width: 700px;
            height: auto;
            background: #f6f6f6;
            margin: 40px auto;
            padding: 10px;
        }

        .contact ul {
            width: 650px;
            margin: 0 auto;
        }

        .contact ul li {
            border-bottom: 1px solid #dfdfdf;
            list-style: none;
            padding: 12px;
        }

        .contact ul li label {
            width: 120px;
            display: inline-block;
            float: left;
        }

        .contact ul li input[type=text], .contact ul li input[type=password] {
            width: 220px;
            height: 25px;
            border: 1px solid #aaa;
            padding: 3px 8px;
            border-radius: 5px;
        }

        .contact ul li input:focus {
            border-color: #c00;
        }

        .contact ul li input[type=text] {
            transition: padding .25s;
            -o-transition: padding .25s;
            -moz-transition: padding .25s;
            -webkit-transition: padding .25s;
        }

        .contact ul li input[type=password] {
            transition: padding .25s;
            -o-transition: padding .25s;
            -moz-transition: padding .25s;
            -webkit-transition: padding .25s;
        }

        .contact ul li input:focus {
            padding-right: 70px;
        }

        .btn {
            position: relative;
            left: 275px;
        }

        .tips {
            color: rgba(0, 0, 0, 0.5);
            padding-left: 10px;
        }

        .tips_true, .tips_false {
            padding-left: 10px;
        }

        .tips_true {
            color: green;
        }

        .tips_false {
            color: #ff0000;
        }
    </style>
    <script>
        var success = 0;
        var pswRight = false;
        var psw2Right = false;

        function checkPsw1() {
            document.getElementById("password").style.width = "220px";
            var psw1 = form.password.value;
            var flagZM = false;
            var flagSZ = false;
            var flagQT = false;
            if(psw1.length < 6 || psw1.length > 10) {
                password1Error.innerHTML = '<font class="tips_false">长度为6~10个字符</font>';
                if(pswRight) {
                    if(success > 0) {
                        success --;
                    }
                    pswRight = false;
                }
            }
            else {
                for(var i = 0; i < psw1.length; i ++) {
                    if((psw1.charAt(i) >= 'A' && psw1.charAt(i) <= 'Z')
                        || (psw1.charAt(i) >= 'a' && psw1.charAt(i) <= 'z')) {
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
                    password1Error.innerHTML = '<font class="tips_false">密码必须由数字和字母组成</font>';
                    if(pswRight) {
                        if(success > 0) {
                            success --;
                        }
                        pswRight = false;
                    }
                }
                else {
                    password1Error.innerHTML = '<font class="tips_true">输入正确</font>';
                    if(!pswRight) {
                        if (success < 2) {
                            success++;
                        }
                        pswRight = true;
                    }
                }
            }
        }

        function checkPsw2() {
            document.getElementById("password2").style.width = "220px";
            if(form.password.value != form.password2.value) {
                password2Error.innerHTML = '<font class="tips_false">密码不一致</font>';
                if(psw2Right) {
                    if(success > 0) {
                        success --;
                    }
                    psw2Right = false;
                }
            }
            else {
                password2Error.innerHTML = '<font class="tips_true">输入正确</font>';
                if(!psw2Right) {
                    if(success < 2) {
                        success++;
                    }
                    psw2Right = true;
                }
            }
        }

        function onChangePsw() {
            if(success >= 2) {
                document.getElementById("submit").type = "submit";
            }
            else {
                alert("修改失败，输入格式错误");
            }
        }
    </script>
</head>
<body>
    <div class="contact">
        <form name="form" action="UserServlet?type=changePsw" method="post">
            <ul>
                <li>
                    <label>新密码：</label>
                    <input type="password" id="password" name="password" placeholder="请输入密码" onBlur="checkPsw1()"
                           onclick="javascript:this.style.width = '250px';" required />
                    <span class="tips" id="password1Error">密码必须由数字和字母组成</span>
                </li>
                <li>
                    <label>确认密码：</label>
                    <input type="password" id="password2" name="password2" placeholder="请确认密码" onBlur="checkPsw2()"
                           onclick="javascript:this.style.width = '250px';" required />
                    <span class="tips" id="password2Error">两次密码需要相同</span>
                </li>
            </ul>
            <b class="btn">
                <input type="button" value="提交" id="submit" onclick="onChangePsw()" style="cursor: pointer" />
                <input type="reset" value="重置" style="cursor: pointer" />
            </b>
            <input type="hidden" name="userName" value="<%=user.getUserName()%>">
        </form>
    </div>
    <div style="text-align: center;"></div>
</body>
</html>
