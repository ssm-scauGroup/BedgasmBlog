<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
  <head>
  	<base href="<%=basePath%>">
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"> 
    <title>My JSP 'index.jsp' starting page</title>
  </head>
  <body>
    <p>没注册的用户，请<a href="register">注册</a>！</p>
    
    <p>已注册的用户，去<a href="login">登录</a>！</p>
  </body>
</html>
