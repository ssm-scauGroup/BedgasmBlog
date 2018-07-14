<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<base href="<%=basePath%>">
<title>detail.jsp</title>
</head>
<body>
	<center>
		<table border=1 background="images/bb.jpg" style="border-collapse: collapse">
			<caption>
				<font size=4 face=华文新魏>名片详细信息</font>
			</caption>
			<tr>
				<td>ID</td>
				<td> ${card.id }</td>
			</tr>
			<tr>
				<td>姓名</td>
				<td>${card.name }</td>
			</tr>
			<tr>
				<td>电话</td>
				<td>${card.telephone }</td>
			</tr>
			<tr>
				<td>E-Mail</td>
				<td>${card.email }</td>
			</tr>
			<tr>
				<td>单位</td>
				<td>${card.company }</td>
			</tr>
			
			<tr>
				<td>地址</td>
				<td>${card.address }</td>
			</tr>
			
			<tr>
				<td>Logo</td>
				<td>
					<c:if test="${card.logo != '' }">
						<img alt="" width="250" height="250"
						src="logos/${card.logo}"/>
					</c:if>
				</td>
			</tr>
			
		</table>
	</center>
</body>
</html>