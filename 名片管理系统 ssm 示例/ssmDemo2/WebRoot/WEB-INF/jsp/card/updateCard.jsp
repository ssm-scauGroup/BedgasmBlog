<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'updateCard.jsp' starting page</title>
    
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
    <form:form action="card/add?updateAct=update" method="post" modelAttribute="card" enctype="multipart/form-data">
		<table border=1 style="border-collapse: collapse">
			<caption>
				<font size=4 face=华文新魏>修改名片</font>
			</caption>
			<tr>
				<td>ID<font color="red">*</font></td>
				<td>
					<form:input path="id" readonly="true" cssStyle="border-width: 1pt; border-style: dashed; border-color: red"/>
				</td>
			</tr>
			<tr>
				<td>名称<font color="red">*</font></td>
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
				<td>地址</td>
				<td>
					<form:input path="address"/>
				</td>
			</tr>
			<tr>
				<td>Logo</td>
				<td>
					<input type="file" name="logoImage">
					<br>
					<!-- 从数据库取出的文件名 -->
					<c:if test="${card.logo != ''}">
						<img alt="" width="50" height="50"
						src="logos/${card.logo}"/>
					</c:if>	
				</td>
			</tr>
			
			<tr>
				<td align="center"><input type="submit" value="提交"/></td>
				<td align="left"><input type="reset" value="重置"/></td>
			</tr>
		</table>
	</form:form>
  </body>
</html>
