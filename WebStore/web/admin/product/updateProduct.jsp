<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title></title>
		
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
	</head>
	<body>
	<%-- <form method="post" action="${pageContext.request.contextPath }/ProductServlet" enctype="multipart/form-data"> --%>
	<form method="post" enctype="multipart/form-data" action="${pageContext.request.contextPath }/admin/ProductServlet?op=addProduct&rawpid=${product.pid}">

		<input type="hidden" name="rawimgurl" value="${product.imgurl }">

		
		 
		
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
														修改商品</span>
												</td>
											</tr>
										</table>
									</td>
									<td><div align="right">
											<span class="STYLE1"> 
												 
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
			    <table width="100%" border="0" cellpadding="0" cellspacing="1" bgcolor="#a8c7ce">
					<tr>
						<td width="4%" height="20" bgcolor="d3eaef" class="STYLE10"><div
								align="center">
								<input type="checkbox" name="checkbox" id="checkbox11" />
							</div>
						</td>
						<td width="10%" height="20" bgcolor="d3eaef" class="STYLE6"><div
								align="center">
								<span class="STYLE10">品牌</span>
							</div>
						</td>
						<td width="80%" height="20" bgcolor="d3eaef" class="STYLE6"><div
								align="left">
 							<select name="cid" id="st" onchange="change()">
								<c:forEach items="${categories}" var="category">
									<option value="${category.cid}" <c:if test="${category.cid == product.cid}">selected</c:if>>${category.cname}</option>
								</c:forEach>	
							</select>
						 
						 
							</div>
						</td>
						 
					</tr>
				 
				 <tr>
						<td width="4%" height="20" bgcolor="d3eaef" class="STYLE10">
						   <div
								align="center">
								<input type="checkbox" name="checkbox" id="checkbox11" />
							</div>
						</td>
						<td width="10%" height="20" bgcolor="d3eaef" class="STYLE6">
						    <div
								align="center">
								<span class="STYLE10">商品号</span>
							</div>
						</td>
						<td width="80%" height="20" bgcolor="d3eaef" class="STYLE6">
						   <div
								align="left">

						   <input type="text " name="pid"  value="${product.pid}" readonly="readonly"/>

							</div>
						</td>
						 
					</tr>
					
					 <tr>
						<td width="4%" height="20" bgcolor="d3eaef" class="STYLE10"><div
								align="center">
								<input type="checkbox" name="checkbox" id="checkbox11" />
							</div>
						</td>
						<td width="10%" height="20" bgcolor="d3eaef" class="STYLE6"><div
								align="center">
								<span class="STYLE10">总库存</span>
							</div>
						</td>
						<td width="80%" height="20" bgcolor="d3eaef" class="STYLE6"><div
								align="left">
 							 
						<input type="text "  name="pnum" value="${product.pnum }"/>
						 
							</div>
						</td>
						 
					</tr>
					
				 <tr>
						<td width="4%" height="20" bgcolor="d3eaef" class="STYLE10">
						   <div
								align="center">
								<input type="checkbox" name="checkbox" id="checkbox11" />
							</div>
						</td>
						<td width="10%" height="20" bgcolor="d3eaef" class="STYLE6">
						    <div
								align="center">
								<span class="STYLE10">商品名称</span>
							</div>
						</td>
						<td width="80%" height="20" bgcolor="d3eaef" class="STYLE6">
						   <div
								align="left">
 							 
						   <input type="text " name="pname" width="200"  value="${product.pname}"/>
						 
							</div>
						</td>
						 
					</tr>		
			
								
								
					
					 <tr>
						<td width="4%" height="20" bgcolor="d3eaef" class="STYLE10"><div
								align="center">
								<input type="checkbox" name="checkbox" id="checkbox11" />
							</div>
						</td>
						<td width="10%" height="20" bgcolor="d3eaef" class="STYLE6"><div
								align="center">
								<span class="STYLE10">商城价:￥</span>
							</div>
						</td>
						<td width="80%" height="20" bgcolor="d3eaef" class="STYLE6"><div
								align="left">
 							 
						<input type="text " name="estoreprice" value="${product.estoreprice}"/><br>
						 
							</div>
						</td>
						 
					</tr>
					
					
					 <tr>
						<td width="4%" height="20" bgcolor="d3eaef" class="STYLE10"><div
								align="center">
								<input type="checkbox" name="checkbox" id="checkbox11" />
							</div>
						</td>
						<td width="10%" height="20" bgcolor="d3eaef" class="STYLE6"><div
								align="center">
								<span class="STYLE10">市场价:￥</span>
							</div>
						</td>
						<td width="80%" height="20" bgcolor="d3eaef" class="STYLE6"><div
								align="left">
 							 
						<input type="text " name="markprice" value="${product.markprice }"/><br>
						 
							</div>
						</td>
						 
					</tr>			
					
					 <tr>
						<td width="4%" height="20" bgcolor="d3eaef" class="STYLE10"><div
								align="center">
								<input type="checkbox" name="checkbox" id="checkbox11" />
							</div>
						</td>
						<td width="10%" height="20" bgcolor="d3eaef" class="STYLE6"><div
								align="center">
								<span class="STYLE10">图片</span>
							</div>
						</td>
						<td width="80%" height="20" bgcolor="d3eaef" class="STYLE6"><div
								align="left">
 							 
 							<%-- <input type="file" name="imgurl"  value="${product.imgurl}"/><br> --%>						 
							
							<img width="200px" height="180px" src="${pageContext.request.contextPath}/image/${product.imgurl}"/>
								<input type="file" name="imgurl" value="${product.imgurl}"/><br>
						</div>
						</td>
						 
					</tr>			
					
					 <tr>
						<td width="4%" height="20" bgcolor="d3eaef" class="STYLE10"><div
								align="center">
								<input type="checkbox" name="checkbox" id="checkbox11" />
							</div>
						</td>
						<td width="10%" height="20" bgcolor="d3eaef" class="STYLE6"><div
								align="center">
								<span class="STYLE10">商品描述</span>
							</div>
						</td>
						<td width="80%" height="20" bgcolor="d3eaef" class="STYLE6"><div
								align="left">
 							 
						     <textarea name="desc" cols="80" rows="5" >${product.desc}</textarea>
						 
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
						   <input type="button" onclick="history.go(-1);" value="返回">
						   <input type="submit" value="修改"/>
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


