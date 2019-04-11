<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>添加分类</title>

<style type="text/css">
<!--
body {
	margin-left: 3px;
	margin-top:  0px;
	margin-right: 3px;
	margin-bottom: 0px;
}
.STYLE1 {
	color: #e1e2e3;
	font-size: 12px;
}

.STYLE6 {
	color: #000000;
	font-size: 12;
}

.STYLE10 {
	color: #000000;
	font-size: 12px;
}

.STYLE19 {
	color: #344b50;
	font-size: 12px;
}
.STYLE21 {
	font-size: 12px;
	color: #3b6375;
}
.STYLE22 {
	font-size: 12px;
	color: #295568;
}
-->
</style>

	<script type="text/javascript">
        /*
   * 用户输完用户名之后去发ajax请
   * */
        function isCnameExists() {
            var usernameInput = document.getElementById("cname");
            // alert(username.value)
            //发送ajax异步请求
            // 1、创建XMLHttpRequest对象
            var  xhr= new XMLHttpRequest();

            //2、注册状态监控回调函数 : 什么时候调用？ 当收到服务器发回来的响应报文我们在这里去处理响应报文
            xhr.onreadystatechange = function() {
                // alert(xhr.readyState);

                if (xhr.readyState==4 && xhr.status==200) {
                    var resptext = xhr.responseText;
                    // alert(resptext)

                    var msgSpan = document.getElementById("message");
                    if (usernameInput.value == "") {
                        msgSpan.innerText = "";
                        return;
                    }
					if(resptext=="exist"){
						msgSpan.innerHTML= "类别名重复"
						msgSpan.style.color = 'red';
					}else{
						msgSpan.innerHTML= "类别名可用"
                        msgSpan.style.color = 'blue';
					}
                }

            }


            //3、建立与服务器的异步连接
            xhr.open("GET", "http://localhost/admin/CategoryServlet?op=isExist&cname="+usernameInput.value);


            // 4、发出异步请求
            xhr.send();


        }

	</script>


</head>
<body>
	
	 
		
			<form method="post" action="${pageContext.request.contextPath }/admin/CategoryServlet" >
					<input type="hidden" name="op" value="addCategory"/>
			
 	<table width="100%" border="0" align="center" cellpadding="0"
		cellspacing="0">
		<tr>
			<td height="30">
				<table width="100%" border="0" cellspacing="0" cellpadding="0">
					<tr>
						<td height="24" bgcolor="#353c44"><table width="100%"
								border="0" cellspacing="0" cellpadding="0">
								<tr>
									<td><table width="100%" border="0" cellspacing="0"
											cellpadding="0">
											<tr>
												<td width="6%" height="19" valign="bottom"><div
														align="center">
														<img src="images/tb.gif" width="14" height="14" />
													</div>
												</td>
												<td width="94%" valign="bottom"><span class="STYLE1">
														增加分类</span>
												</td>
											</tr>
										</table>
									</td>
									<td><div align="right">
											<span class="STYLE1"> 
												<!-- <input type="button" value="添加"/> -->  
												<!-- <input type="submit" value="删除" /> -->
												&nbsp;&nbsp;
											</span>
										</div>
									</td>
								</tr>
							</table>
						</td>
					</tr>
				</table>
			</td>
		</tr>
		<tr>
			<td>
			
			<table width="100%" border="0" cellpadding="0"
					cellspacing="1" bgcolor="#a8c7ce">
					<tr>
						<td width="4%" height="20" bgcolor="d3eaef" class="STYLE10"><div
								align="center">
								<input type="checkbox" name="checkbox" id="checkbox11" />
							</div>
						</td>
						<td width="10%" height="20" bgcolor="d3eaef" class="STYLE6"><div
								align="center">
								<span class="STYLE10"><span>分类类名：</span></span>
							</div>
						</td>
						<td width="80%" height="20" bgcolor="d3eaef" class="STYLE6">
						
						<div
								align="left">
 							 <span style="color:red">*</span> 
						
						<input type="text" name="cname" value="${cname }" id="cname" onblur="isCnameExists()" required="required" />
							<span id="message" class="STYLE6" ></span>
						 
							</div>
						</td>
						 
					</tr>	 
					 
				</table>
			</td>
		</tr>

		<tr>
			<td height="30">
				<table width="100%" border="0" cellspacing="0" cellpadding="0">
					<tr>
						<td width="33%"><div align="left">
								 
						</td>
						<td width="67%">
						   <div align="right">
						   <input type="submit" value="增加"/>
						   </div>
						</td>
					</tr>
				</table>
			</td>
		</tr>
	</table>
	</form>
	 
</body>
</html>