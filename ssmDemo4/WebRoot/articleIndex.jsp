<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>文章首页</title>
    
	<script src="${pageContext.request.contextPath}/js/jquery.min.js"></script>

</head>
<body>
    <script>
            var html = $.ajax({
                async: true,
                url: "${pageContext.request.contextPath}/article/list",
                type: "post",
                data: {'page':"1",'rows':'5'},
                dataType: "json",
                success: function (res) {
                	var articleDiv = document.createElement('div'),detail=""
                	for(var key in res['posts']){
                		,detail="";
                		detail = "<a target='_blank' href='${pageContext.request.contextPath}/article/detail?id="+
                		res['posts'][key]['id']+"'>"+res['posts'][key]['title']+"</a>"+
                		"<p>文章摘要:"+res['posts'][key]['summary']+"</p>";
                		articleDiv.innerHTML += detail;
                		document.body.appendChild(articleDiv);
                	}
               		
               		
					//console.log(res['posts']);
                    //console.log(res);
                },
                error: function (res) {
                	console.log("error");
                }
            })
    </script>
</body>
</html>
