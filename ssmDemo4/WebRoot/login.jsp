<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>login</title>
  </head>
  
  <body>
    <form action="user/login" method="post">
    	用户名：<input  name="nameoremail" />
    	<br>
    	密码：<input  name="password" />
    	<br>
    	<input type="submit" value="登录" />
    </form> 
  </body>
</html>