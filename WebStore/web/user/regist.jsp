<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%--<!DOCTYPE html>--%>
<html>
	<head>
		<meta charset="utf-8">
		<link href="${pageContext.request.contextPath}/user/css/style.css" rel="stylesheet" type="text/css">
		<meta name="viewport" content="width=device-width, initial-scale=1">
		<!--webfonts-->
		<!--//webfonts-->

		<script type="text/javascript">
            function isUsernameExists() {
                var pid = document.getElementsByName("username")[0];
                var message = document.getElementById("message");
                if (pid.value == "") {
                    message.innerText = "";
                    return;
                }
                var request = new XMLHttpRequest();
                request.onreadystatechange = function () {
                    if (request.readyState == 4 && request.status == 200) {
                        var responseText = request.responseText;
                        if (responseText == "exist") {
                            message.innerText = "用户名重复";
                            message.style.color = 'red';
                        } else {
                            message.innerText = "用户名可用";
                            message.style.color = 'blue';
                        }
                    }
                };
                var url = "http://localhost/admin/UserServlet?op=isExist&username=" + pid.value;
                request.open("GET",url, true);
                request.send(null);
            }
		</script>
	</head>

	<body>
		<div class="main" align="center">
			<div class="header">
				<h1>创建一个免费的新帐户！</h1>
			</div>
			<p></p>
			<form method="post" action="${pageContext.request.contextPath }/UserServlet">
				<input type="hidden" name="op" value="regist" />
				<ul class="left-form">
					<li>
						<span id="message" class="STYLE6" ></span><br>
						<input type="text" name="username" id="username" placeholder="用户名"  value="${msg.username}" onblur="isUsernameExists()"/>

						<a href="#" class="icon ticker"> </a>
						<div class="clear"> </div>
					</li>
					<li>
						${msg.error.nickname }<br/>
						<input type="text" name="nickname" placeholder="昵称" value="${msg.nickname}" required="required"/>
						<a href="#" class="icon ticker"> </a>
						<div class="clear"> </div>
					</li>
					<li>
						${msg.error.email }<br/>
						<input type="text" name="email" placeholder="邮箱" value="${msg.email}" required="required"/>
						<a href="#" class="icon ticker"> </a>
						<div class="clear"> </div>
					</li>
					<li>
						${msg.error.password }<br/>
						<input type="password" name="password" placeholder="密码" value="${msg.password}" required="required"/>
						<a href="#" class="icon into"> </a>
						<div class="clear"> </div>
					</li>
					<li>
						${msg.error.birthday }<br/>
						<input type="text" placeholder="出生日期" name="birthday" value="${msg.birthday}" size="15" />
						<div class="clear"> </div>
					</li>
					<li>
						<input type="button" onclick="window.location.href='${pageContext.request.contextPath}/index.jsp'" value="返回">
						<input type="submit" value="创建账户">
						<input type="button" onclick="window.location.href='${pageContext.request.contextPath}/user/login.jsp'" value="登陆">
						<div class="clear"> </div>
					</li>
			</ul>

			<div class="clear"> </div>

			</form>

		</div>
		<!-----start-copyright---->

		<!-----//end-copyright---->

	</body>

</html>