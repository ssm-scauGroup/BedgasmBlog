 <%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'userinfo.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

  </head>
  
  <body>
  <p>${param.message}</p>
        <table border=1>
    	<tr>
    	  <td>编号</td>
    	  <td>用户名</td>
    	  <td>昵称</td>
    	  <td>邮箱</td>
    	  <td>个性签名</td>
    	  <td>头像</td>
    	</tr>
    	  <tr>
    	  	<td>${sessionScope.user.id}</td>
    	  	<td>${sessionScope.user.username}</td>
    	  	<td>${sessionScope.user.nickname}</td>
    	  	<td>${sessionScope.user.email}</td>
    	  	<td>${sessionScope.user.signature}</td>
    	  	<td><img src="${pageContext.request.contextPath}${sessionScope.user.profile}"></td>
    	  </tr>
    </table>
    
    <a href="user/preupdateinfo">修改信息</a>
    <a href="user/prechangepw">修改密碼</a>
  </body>
</html>
