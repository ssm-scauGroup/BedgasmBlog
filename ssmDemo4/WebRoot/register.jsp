<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>login</title>
  </head>
  
  <body>
    <form action="user/register" method="post">
	    <input id="username" class="form-control" type="text" 
	    placeholder="用户名(4-16位)" name="username">
	    <span class="error error1"></span>
	
	    <input id="email" class="form-control" type="text" 
	    placeholder="邮箱" name="email">
	     <span class="error error2"></span>
	
	    <input id="pwd" class="form-control" type="password" 
	    placeholder="密码(8-30位)" name="pwd">
	     <span class="error error3"></span>
	
	    <input id="password_confirmation" class="form-control" 
	    type="password" placeholder="确认密码" name="password_confirmation">
	     <span class="error error4"></span>
	
	    <input class="btn btn-default btn-register" type="submit" 
	    value="注 册" name="commit">
    </form> 
  </body>
</html>