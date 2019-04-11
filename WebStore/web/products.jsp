<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%--<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">--%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Shoes Store - Products</title>
<meta name="keywords" content="shoes store, products, free template, ecommerce, online shopping, website templates, CSS, HTML" />
<meta name="description" content="Shoes Store, Products, free shopping template provided " />
<link href="templatemo_style.css" rel="stylesheet" type="text/css" />

<link rel="stylesheet" type="text/css" href="css/ddsmoothmenu.css" />

<script type="text/javascript" src="js/jquery.min.js"></script>
<script type="text/javascript" src="js/ddsmoothmenu.js">



</script>

<script type="text/javascript">

ddsmoothmenu.init({
	mainmenuid: "top_nav", //menu DIV id
	orientation: 'h', //Horizontal or vertical menu: Set to "h" or "v"
	classname: 'ddsmoothmenu', //class added to menu's outer DIV
	//customtheme: ["#1c5a80", "#18374a"],
	contentsource: "markup" //"markup" or ["container_id", "path_to_menu_file"]
})

</script>

</head>

<body>
<c:if test="${empty categories }">
		<jsp:forward page="/MainServlet"></jsp:forward>
</c:if>
<div id="templatemo_body_wrapper">
<div id="templatemo_wrapper">
	
	<div id="templatemo_header">
    	<div id="site_title"><h1><a href="http://localhost/${pageContext.request.contextPath }">Online Shoes Store</a></h1></div>
        <div id="header_right">
        	<p>
        	<c:if test="${!empty user }">
	        	<a href="${pageContext.request.contextPath }/user/personal.jsp">我的个人中心</a> |
	        </c:if>
	        <a href="${pageContext.request.contextPath }/CartServlet?op=findCart">购物车</a> | 
	        <c:if test="${empty user }">
	        	<a href="${pageContext.request.contextPath }/user/login.jsp">登录</a> |
	        	<a href="${pageContext.request.contextPath }/user/regist.jsp">注册</a></p>
	        </c:if>
	        <c:if test="${!empty user }">
	        	${user.nickname }
	        	<a href="${pageContext.request.contextPath }/UserServlet?op=logout">退出</a></p>
	        </c:if> 
		</div>
        <div class="cleaner"></div>
    </div> <!-- END of templatemo_header -->
    
    <div id="templatemo_menubar">
    	<div id="top_nav" class="ddsmoothmenu">
            <ul>
                <li><a href="${pageContext.request.contextPath }/MainServlet" class="selected">主页</a></li>
            </ul>
            <br style="clear: left" />
        </div> <!-- end of ddsmoothmenu -->
        <div id="templatemo_search">
            <form action="${pageContext.request.contextPath }/ProductServlet" method="get">
              <input type="hidden" name="op" value="findProByName"/>
              <input type="text" value="${pname }" name="pname" id="keyword" title="keyword" onfocus="clearText(this)" onblur="clearText(this)" class="txt_field" />
              <input type="submit" name="Search" value=" " alt="Search" id="searchbutton" title="Search" class="sub_btn"  />
            </form>
        </div>
    </div> <!-- END of templatemo_menubar -->
    
    <div id="templatemo_main">
    	<div id="sidebar" class="float_l">
        	<div class="sidebar_box"><span class="bottom"></span>
            	<h3>品牌</h3>   
                <div class="content"> 
                	<ul class="sidebar_list">
                    	<c:forEach items="${categories }" var="category" varStatus="vs">
                			<c:if test="${vs.index !=0}">
                				<c:if test="${vs.index != fn:length(categories)-1 }">
                					<li>
                						<a href="${pageContext.request.contextPath }/ProductServlet?op=byCid&cid=${category.cid}">${category.cname}</a>
                					</li>
                				</c:if>
                			</c:if>
                			<c:if test="${vs.index==0 }">
                				<li class="first">
                					<a href="${pageContext.request.contextPath }/ProductServlet?op=byCid&cid=${category.cid}">${category.cname}</a>
                				</li>
                			</c:if>
                			<c:if test="${vs.index == fn:length(categories)-1 }">
                				<li class="last">
                					<a href="${pageContext.request.contextPath }/ProductServlet?op=byCid&cid=${category.cid}">${category.cname}</a>
                				</li>
                			</c:if>
                		</c:forEach>
                    </ul>
                </div>
            </div>
        </div>
        <div id="content" class="float_r">
        	
        	<span><h3  style="width:600px;heigth:40px;background: #EEEEEE; padding: 10px"> Products </h3></span>
        	
        	<c:forEach items="${requestScope.products }" var="product" varStatus="vs">
        		<div class="${vs.index % 3 != 2?'product_box':'product_box no_margin_right' }">
	            	<a href="${pageContext.request.contextPath }/ProductServlet?op=findProductById&pid=${product.pid}">
	            		<img src="${pageContext.request.contextPath}/image/${product.imgurl}" width="200" height="150" alt="" title="${product.pname }"/>
	            	</a>
	                <p>${product.pname }</p>
	                <p class="product_price">￥ ${product.estoreprice }</p>
	                <c:if test="${empty user }">
	                	<a href="javascript:login()" class="addtocart"></a>
	                </c:if>
	                <c:if test="${!empty user }">
	                	<a href="javascript:addCart(${product.pid },${user.uid})" class="addtocart"></a>
	                </c:if>
	                <a href="${pageContext.request.contextPath }/ProductServlet?op=findProductById&pid=${product.pid}" class="detail"></a>
            	</div>
            	<c:if test="${vs.index % 3 == 2}">
            		<div class="cleaner"></div>
            	</c:if>
        	</c:forEach>
        <%--<div class="cleaner"></div>--%>
		</div>

		<div class="cleaner"></div>
		<table width="100%" border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td width="33%"><div align="left">
								<span class="STYLE22">&nbsp;&nbsp;&nbsp;&nbsp;共有<strong>
									${page.totalRecordsNum }</strong> 条记录，当前第<strong>${page.currentPageNum }</strong> 页，共 <strong>${page.totalPageNum }</strong> 页</span>
				</div>
				</td>
				<td width="67%">
					<table width="312" border="0" align="right" cellpadding="0" cellspacing="0">
						<tr>
							<td width="49">
								<div align="center">
											<span class="STYLE22">
											<a href="${pageContext.request.contextPath }/ProductServlet?op=byCid&num=1&pid=${pid}&pname=${pname}&cid=${cid}&maxprice=${maxprice}&minprice=${minprice}">首页</a>
											</span>
								</div>
							</td>
							<td width="49">
								<div align="center">
											<span class="STYLE22">
											<a href="${pageContext.request.contextPath }/ProductServlet?op=byCid&num=${page.prevPageNum}&pid=${pid}&pname=${pname}&cid=${cid}&maxprice=${maxprice}&minprice=${minprice}">上一页</a>
											</span>
								</div>
							</td>
							<td width="49"><span class="STYLE22">
									    <div align="center">
											<span class="STYLE22">
											<a href="${pageContext.request.contextPath }/ProductServlet?op=byCid&num=${page.nextPageNum}&pid=${pid}&pname=${pname}&cid=${cid}&maxprice=${maxprice}&minprice=${minprice}">下一页</a>
											</span>
										</div>
							</td>
							<td width="49">
								<div align="center">
									<span class="STYLE22"><a href="${pageContext.request.contextPath }/ProductServlet?op=byCid&num=${page.totalPageNum}&pid=${pid}&pname=${pname}&cid=${cid}&maxprice=${maxprice}&minprice=${minprice}">尾页</a></span>
								</div>
							</td>
							<td width="37" class="STYLE22"><div align="center">转到</div>
							</td>
							<td width="22">
								<div align="center">
									<input type="text" name="num" id="num" value="${page.currentPageNum }" style="width:20px; height:12px; font-size:12px; border:solid 1px #7aaebd;" />
								</div>
							</td>
							<td width="22" class="STYLE22"><div align="center">页</div>
							</td>
							<td width="35">
								<div align="center">
									<span class="STYLE22"><a style="cursor:pointer;" onclick="jump()">跳转</a></span>
								</div>
							</td>
						</tr>
					</table>
				</td>
			</tr>
		</table>

	</div> <!-- END of templatemo_main -->
    
    <div id="templatemo_footer">
    	Copyright (c) 2016 <a href="#">Web商城</a> | <a href="#">Web工作室</a>
    </div> <!-- END of templatemo_footer -->
    
</div> <!-- END of templatemo_wrapper -->
</div> <!-- END of templatemo_body_wrapper -->
	<script type="text/javascript">
		function login(){
			alert("请先登录");
			window.location.href="${pageContext.request.contextPath}/user/login.jsp";
		}
		function addCart(pid,uid){
			window.location.href="${pageContext.request.contextPath}/CartServlet?op=addCart&pid="+pid+"&uid="+uid;
		}
        function jump() {

            var num = document.getElementById("num").value;
            if (!/^[1-9][0-9]*$/.test(num)) {
                alert("请输入正确的页码");
                return;
            }

            if (num > ${page.totalPageNum}) {
                alert("页码超出范围");
                return;
            }

            window.location.href = "${pageContext.request.contextPath }/ProductServlet?op=byCid&num="
                + num +"&pid=${pid}&pname=${pname}&cid=${cid}&maxprice=${maxprice}&minprice=${minprice}";

        }
	</script>
</body>
</html>