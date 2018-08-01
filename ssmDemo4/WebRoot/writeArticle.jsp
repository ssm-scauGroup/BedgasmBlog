<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>发布文章</title>
    <link href="${pageContext.request.contextPath}/css/bootstrap.min.css" rel="stylesheet" />
    <link href="${pageContext.request.contextPath}/static/editor.md-master/css/editormd.css" rel="stylesheet" />

	<script src="${pageContext.request.contextPath}/js/jquery.min.js"></script>

    <script src="${pageContext.request.contextPath}/static/editor.md-master/editormd.min.js"></script>	
	<script src="${pageContext.request.contextPath}/static/editor.md-master/lib/marked.min.js"></script>
	<script src="${pageContext.request.contextPath}/static/editor.md-master/lib/prettify.min.js"></script>
	<script src="${pageContext.request.contextPath}/static/editor.md-master/lib/raphael.min.js"></script>
	<script src="${pageContext.request.contextPath}/static/editor.md-master/lib/underscore.min.js"></script>
	<script src="${pageContext.request.contextPath}/static/editor.md-master/lib/sequence-diagram.min.js"></script>
	<script src="${pageContext.request.contextPath}/static/editor.md-master/lib/flowchart.min.js"></script>
	<script src="${pageContext.request.contextPath}/static/editor.md-master/lib/jquery.flowchart.min.js"></script>



</head>
<body>
	<span>博客标题：</span>
    <input type="text" id="title">
    <div class="editormd" id="blog-editormd">
        <textarea class="editormd-markdown-textarea" name="editormd-markdown-source" id="blog-editormd-markdown"></textarea>
        <!-- 第二个隐藏文本域，用来构造生成的HTML代码，方便表单POST提交，这里的name可以任意取，后台接受时以这个name键为准 -->
        <textarea class="editormd-html-textarea" name="editormd-html-source"></textarea>
    </div>
    
    <script type="text/javascript">
  $(function() {
      editormd("blog-editormd", {
          width   : "90%",
          height  : 640,
          syncScrolling : "single",
          path    : "${pageContext.request.contextPath}/static/editor.md-master/lib/",
          saveHTMLToTextarea : true
      });
  });
</script>
</body>
</html>
