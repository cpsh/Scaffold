<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
    String path = request.getContextPath();
			String basePath = request.getScheme() + "://"
					+ request.getServerName() + ":" + request.getServerPort()
					+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<%=basePath%>">
<title>Restful API</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
<!-- 在 -servlet.xml中配置了springmvc对于静态资源的访问 -->
<script type="text/javascript" src="res/js/jquery-1.7.2.js"></script>
<script type="text/javascript">
	function ajax() {
		alert("ajax调用");
		$.ajax({
			type : 'post',
			//data: $('#form').serialize() + getSource() + license,
			url : "http://localhost:8080/hello/ajax",
			//dataType: 'json',
			success : function(data) {
				alert(data);
			}
		})
	}
	
	function sub(){
		var fm = document.getElementById("userInfo");
		fm.submit();
	}
</script>
</head>

<body>
	restful api page
	<br>
	<button onclick="ajax();">ajax</button>
	<br>
	<form id="userInfo" action="/springmvc/userinfo/2/update/" method="put">
		<input type="hidden" 　name="_method" 　value="put" />
		<input type="button" value="put提交" onclick="sub()">
	</form>
</body>
</html>
