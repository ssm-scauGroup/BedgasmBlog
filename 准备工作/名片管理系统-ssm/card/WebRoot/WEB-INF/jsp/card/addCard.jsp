<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%  
String path = request.getContextPath();
String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<base href="<%=basePath%>">
<title>addCard.jsp</title>
<link href="css/common.css" type="text/css" rel="stylesheet">
</head>    
<body>
	<form:form action="card/add" method="post" modelAttribute="card" enctype="multipart/form-data">
		<table border=1 style="border-collapse: collapse">
			<caption>
				<font size=4 face=华文新魏>添加名片</font>
			</caption>
			<tr>
				<td>姓名<font color="red">*</font></td>
				<td>
					<form:input path="name"/>
				</td>
			</tr>
			<tr>
				<td>电话<font color="red">*</font></td>
				<td>
					<form:input path="telephone"/>
				</td>
			</tr>
			<tr>
				<td>E-Mail</td>
				<td>
					<form:input path="email"/>
				</td>
			</tr>
			<tr>
				<td>单位</td>
				<td>
					<form:input path="company"/>
				</td>
			</tr>
			<tr>
				<td>职务</td>
				<td>
					<form:input path="post"/>
				</td>
			</tr>
			<tr>
				<td>地址</td>
				<td>
					<form:input path="address"/>
				</td>
			</tr>
			<tr>
				<td>logo</td>
				<td>
					<input type="file" name="logoImage"/>
				</td>
			</tr>
			<tr>
				<td align="center">
					<input type="submit" value="提交"/>
				</td>
				<td align="left">
					<input type="reset" value="重置"/>
				</td>
			</tr>
		</table>
	</form:form>
	${errorMessage }
</body>
</html>
