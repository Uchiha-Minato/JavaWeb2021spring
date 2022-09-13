<%@ page import="domain.User" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    User user = (User)session.getAttribute("user");
%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>协同工作平台</title>
    <link href="/main_files/font-awesome.min.css" rel="stylesheet" type="text/css">
    <link href="/main_files/miniui.css" rel="stylesheet" type="text/css">
    <link href="/main_files/common.css" rel="stylesheet" type="text/css">
    <link href="/main_files/skin.css" rel="stylesheet" type="text/css">
    <link href="/main_files/medium-mode.css" rel="stylesheet" type="text/css">
    <link href="/main_files/icons.css" rel="stylesheet" type="text/css">
    <link href="/main_files/jquery.mCustomScrollbar.css" rel="stylesheet" type="text/css">
    <link href="/main_files/menu.css" rel="stylesheet" type="text/css">
    <link href="/main_files/tabs.css" rel="stylesheet" type="text/css">
    <link href="/main_files/frame.css" rel="stylesheet" type="text/css">
    <link href="/main_files/index.css" rel="stylesheet" type="text/css">
    <script src="/main_files/boot.js.下载" type="text/javascript"></script>
    <script src="/main_files/jquery.min.js.下载" type="text/javascript"></script>
    <script src="/main_files/common.js.下载" type="text/javascript"></script>
    <script src="/main_files/jquery.mCustomScrollbar.concat.min.js.下载" type="text/javascript"></script>
    <script src="/main_files/menu.js.下载" type="text/javascript"></script>
    <script src="/main_files/menutip.js.下载" type="text/javascript"></script>
    <script type="text/javascript">
        function activeTab(item) {
            var tabs = mini.get("mainTabs");
            var tab = tabs.getTab(item.id);
            if(!tab) {
                tab = { name: item.id, title: item.text, url: item.url, iconCls: item.iconCls, showCloseButton: true };
                tab = tabs.addTab(tab);
            }
            tabs.activeTab(tab);
        }

        $(function () {
            var menu = new Menu("#mainMenu", {
                itemclick: function (item) {
                    if (!item.children) {
                        activeTab(item);
                    }
                }
            });

            $(".sidebar").mCustomScrollbar({ autoHideScrollbar: true });

            new MenuTip(menu);

            $.ajax({
                url: "data/menu.txt",
                success: function (text) {
                    var data = mini.decode(text);
                    menu.loadData(data);
                }
            })

            $("#toggle, .sidebar-toggle").click(function() {
                $('body').toggleClass('compact');
                mini.layout();
            });

            $(".dropdown-toggle").click(function(event) {
                $(this).parent().addClass("open");
                return false;
            });

            $(document).click(function(event) {
                $(".dropdown").removeClass("open");
            });
        });

        window.onload = function() {
            document.getElementById("content").src = "/welcome.jsp";
            document.getElementById("tab-title").innerText = "首页";
        };

        function onChangePsw() {
            document.getElementById("content").src = "/changePsw.jsp";
            document.getElementById("tab-title").innerText = "修改密码";
        }

        function onUser() {
            document.getElementById("content").src = "/user.jsp";
            document.getElementById("tab-title").innerText = "用户信息";
        }

        function onExit() {
            window.location = ".";
        }

        function onWelcome() {
            document.getElementById("content").src = "/welcome.jsp";
            document.getElementById("tab-title").innerText = "首页";
        }

        function onMyPrj() {
            document.getElementById("content").src = "/ProjectServlet?type=myPrjs&userName=<%=user.getUserName()%>";
            document.getElementById("tab-title").innerText = "管理员模式";
        }

        function onMyTask() {
            document.getElementById("content").src = "/TaskServlet?type=joinTasks&userName=<%=user.getUserName()%>";
            document.getElementById("tab-title").innerText = "普通模式";
        }
    </script>
</head>
<body style="zoom: 1; visibility: visible;">
    <div class="navbar">
        <div class="navbar-header">
            <div class="navbar-brand">协同工作平台</div>
            <div class="navbar-brand navbar-brand-compact">协</div>
        </div>
        <ul class="nav navbar-nav">
            <li><a id="toggle"><span><img src="/main_files/bar-top.png"></span></a></li>
            <li><a><span>您好，<%=user.getUserName()%>，欢迎使用协同工作平台！</span></a></li>
        </ul>
        <ul class="nav navbar-nav navbar-right">
            <li><a onclick="onChangePsw()"><i class="fa fa-pencil-square-o"></i> 修改密码</a></li>
            <li class="dropdown">
                <a class="dropdown-toggle userinfo"><img class="user-img" src="/main_files/user.jpg"> 个人资料</a>
                <ul class="dropdown-menu pull-right">
                    <li><a onclick="onUser()" style="cursor: pointer">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;用户信息</a></li>
                    <li><a onclick="onExit()" style="cursor: pointer">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;退出登录</a></li>
                </ul>
            </li>
        </ul>
    </div>
    <div class="container">
        <div class="sidebar mCustomScrollbar _mCS_1 mCS-autoHide mCS_no_scrollbar">
            <div id="mCSB_1" class="mCustomScrollBox mCS-light mCSB_vertical mCSB_inside" style="max-height: 703px;" tabindex="0">
                <div id="mCSB_1_container" class="mCSB_container mCS_y_hidden mCS_no_scrollbar_y" style="position:relative; top:0; left:0;" dir="ltr">
                    <div class="sidebar-toggle"><img src="/main_files/bar.png" class="fa fa-fw fa-dedent"></div>
                    <div>
                        <ul class="menu">
                            <li class="has-children">
                                <a class="menu-title" onclick="onWelcome()">
                                    <span><img src="/main_files/index.png" style="transform: translate(5%,25%);"></span>
                                    <span class="menu-text">首页</span>
                                    <span class="menu-arrow fa"></span>
                                </a>
                            </li>
                            <li class="has-children">
                                <a class="menu-title" onclick="onMyPrj()">
                                    <span><img src="/main_files/myQues.png" style="transform: translate(5%,25%);"></span>
                                    <span class="menu-text">管理员模式</span>
                                    <span class="menu-arrow fa"></span>
                                </a>
                            </li>
                            <li class="has-children">
                                <a class="menu-title" onclick="onMyTask()">
                                    <span><img src="/main_files/create.png" style="transform: translate(5%,25%);"></span>
                                    <span class="menu-text">普通模式</span>
                                    <span class="menu-arrow fa"></span>
                                </a>
                            </li>
                        </ul>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div class="main">
        <div class="mini-tabs main-tabs mini-tabs-position-top" style="border-width: 0px; height: 100%; padding: 0px;">
            <table class="mini-tabs-table" cellspacing="0" cellpadding="0">
                <tbody>
                <tr style="width:100%;">
                    <td></td>
                    <td style="text-align:left;vertical-align:top;width:100%;">
                        <div class="mini-tabs-scrollCt" style="padding-right: 0px; width: 1500px;">
                            <div class="mini-tabs-headers mini-tabs-header-top" style="width: auto; left: 0px; margin-left: 0px;">
                                <table class="mini-tabs-header" cellspacing="0" cellpadding="0">
                                    <tbody>
                                    <tr>
                                        <td class="mini-tabs-space mini-tabs-firstSpace"><div></div></td>
                                        <td class="mini-tab mini-corner-all mini-tab-active" style="">
                                            <span class="mini-tab-text" id="tab-title" style="width: 75px"></span>
                                        </td>
                                        <td class="mini-tabs-space mini-tabs-lastSpace" style="width: 100%;"><div></div></td>
                                    </tr>
                                    </tbody>
                                </table>
                            </div>
                        </div>
                        <div class="mini-tabs-bodys mini-tabs-body-top" style="width: 1299px; height: 656px;">
                            <div class="mini-tabs-body">
                                <iframe id="content" style="border: 0px;width: 1299px;height: 650px"></iframe>
                            </div>
                        </div>
                    </td>
                    <td></td>
                </tr>
                </tbody>
            </table>
        </div>
    </div>
    <div class="tooltip right menutip in" style="display: none;">
        <div class="tooltip-arrow"></div>
        <div class="tooltip-inner"></div>
    </div>
</body>
</html>