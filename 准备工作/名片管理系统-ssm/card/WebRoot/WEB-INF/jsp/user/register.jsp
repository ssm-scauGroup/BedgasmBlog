<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<base href="<%=basePath%>">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<style type="text/css">
	.textSize{
		width: 100pt;
		height: 15pt
	}
</style>
<title>注册画面</title>
<script type="text/javascript">
	//输入姓名后，调用该方法，判断用户名是否可用
	function nameIsNull(){
		var name = document.getElementById("username").value;
		if(name == ""){
			alert("请输入姓名！");
			document.getElementById("username").focus();
			return false;
		}
		document.getElementById("flag").value="0";
		document.registForm.submit();
		return true;
	}
	//注册时检查输入项
	function allIsNull(){
		var name = document.getElementById("username").value;
		var pwd = document.getElementById("password").value;
		var repwd = document.getElementById("repassword").value;
		if(name == ""){
			alert("请输入姓名！");
			document.getElementById("username").focus();
			return false;
		}
		if(pwd == ""){
			alert("请输入密码！");
			document.getElementById("password").focus();
			return false;
		}
		if(repwd == ""){
			alert("请输入确认密码！");
			document.getElementById("repassword").focus();
			return false;
		}
		if(pwd != repwd){
			alert("2次密码不一致，请重新输入！");
			document.getElementById("password").value = "";
			document.getElementById("repassword").value = "";
			document.getElementById("password").focus();
			return false;
		}
		document.getElementById("flag").value = "1";
		document.registForm.submit();
		return true;
	}
</script>
</head>
<body>
	<form:form action="user/register" method="post" modelAttribute="user" name="registForm" >
		<form:hidden path="flag" id="flag"/>
		<table 
		border=1 
		bgcolor="lightblue" 
		align="center">
			<tr>
				<td>姓名：</td>
				<td>
					<form:input id="username" path="username" cssClass="textSize" onblur="nameIsNull()"/>
					<c:if test="${isExit=='false' }">
						<font color=red size=5>×</font>
					</c:if>
					<c:if test="${isExit=='true' }">
						<font color=red size=5>√</font>
					</c:if>
				</td>
			</tr>
			
			<tr>
				<td>密码：</td>
				<td>
					<form:password id="password" path="password" cssClass="textSize" maxlength="20"/>
				</td>
			</tr>
			
			<tr>
				<td>确认密码：</td>
				<td>
					<form:password id="repassword" path="repassword" cssClass="textSize" maxlength="20"/>
				</td>
			</tr>
			
			<tr>
				<td colspan="2" align="center"><input type="button" value="注册" onclick="allIsNull()"/></td>
			</tr>
		</table>
	</form:form>
</body>
</html>