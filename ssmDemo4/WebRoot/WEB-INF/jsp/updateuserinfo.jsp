<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'updateinfo.jsp' starting page</title>
    
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
  <form action="user/updateinfo" method="post"></form>
    <table>
    	<tr>
    	  <td>编号</td>
    	  <td>用户名</td>
    	  <td>昵称</td>
    	  <td>邮箱</td>
    	  <td>个性签名</td>
    	</tr>
    	  <tr>
    	  	<td><input type="text" name="id" value="${sessionScope.user.id}"></td>
    	  	<td><input type="text" name="username" value="${sessionScope.user.username}"></td>
    	  	<td><input type="text" name="nickname" value="${sessionScope.user.nickname}"></td>
    	  	<td><input type="text" name="email" value="${sessionScope.user.email}"></td>
    	  	<td><input type="text" name="signature" value="${sessionScope.user.signature}"></td>
    	  </tr>
    </table>
    <input type="submit" value="修改" />
  </body>
</html>
