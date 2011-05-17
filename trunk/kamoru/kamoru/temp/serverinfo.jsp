<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Server info</title>
</head>
<body>

<header>Server Info</header>

<article>
<%=request.getScheme()%>://<%=request.getServerName() %>:<%=request.getServerPort() %><%=request.getContextPath() %><br/>

</article>

</body>
</html>