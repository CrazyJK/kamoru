<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
request.setCharacterEncoding("UTF-8");
String method = request.getParameter("method");
String param = request.getParameter("param");
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>param receive 2</title>
<link rel="stylesheet" type="text/css" href="../mng.css">
</head>
<body topmargin="0" leftmargin="0">
<p>request.setCharacterEncoding("UTF-8");</p>
<p>request.getParameter =>[<%= param %>]</p>
<p>new String(param.getBytes("ISO-8859-1"),"UTF-8") =>[<%= new String(param.getBytes("ISO-8859-1"),"UTF-8") %>]</p>
</body>
</html>