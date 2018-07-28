<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
  <head>
    <base href="<%=basePath%>">
    <title>文章列表</title>
	<link href="css/common.css" type="text/css" rel="stylesheet">
	<style type="text/css">
		table{
			text-align: center;
			border-collapse: collapse;
		}
		.bgcolor{
		  	background-color: #F08080;
		}
	</style>
</head>
<body>
	<c:if test="${posts.size() == 0 }">
		<p>没有文章。</p>
	</c:if>
	<c:if test="${posts.size() != 0 }">
			<c:forEach items="${posts }" var="post">
				<article>
					<p>文章标题:${post.title }</p>
					<p>发布日期:${post.releaseDate }</p>
					<p>摘要:${post.summary }</p>
					<p>点击次数:${post.clickCount }</p>
					<p>评论次数:${post.replyCount }</p>
					<p>正文:${post.content }</p>
					<p>标签:${post.tags }</p>
					<p>文章类别id:${post.blogtypeid }</p>
				</article>
			</c:forEach>
	</c:if>
</body>
</html>