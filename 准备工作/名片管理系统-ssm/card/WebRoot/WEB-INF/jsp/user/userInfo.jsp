<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
  <head>   
  	<base href="<%=basePath%>"> 
    <title>用户基本信息</title>
	<style type="text/css">
	table{
		text-align: center;
	}
	.textSize{
		width: 120px;
		height: 25px;
	}
	* {
		margin: 0px;
		padding: 0px;
	}
	body {
		font-family: Arial, Helvetica, sans-serif;
		font-size: 12px;
		margin: 10px 10px auto;
		background-image: url(images/bb.jpg);
	}
	</style>
  </head>
  <body>
	<table>
		<tr>
			<td colspan="2">用户基本信息</td>
		</tr>
		<tr>
			<td>姓名：</td>
			<td>${user.username }</td>
		</tr>
		<tr>
			<td>密码：</td>
			<td>${user.password }</td>
		</tr>
	</table>
  </body>
</html>
