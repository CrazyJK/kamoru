<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*, java.net.*" %>
<%
String p = request.getParameter("p");
String p2 = URLDecoder.decode(p, "UTF-8");
String p3 = URLEncoder.encode(p, "UTF-8");
String p4 = URLDecoder.decode(p3, "UTF-8");
String p5 = new String(p.getBytes("8859_1"), "UTF-8");

System.out.format("p=%s, p2=%s, p3=%s, p4=%s, p5=%s %n", p, p2, p3, p4, p5);
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<!--[if lt IE 9]>
<script src="http://html5shiv.googlecode.com/svn/trunk/html5.js"></script>
<![endif]-->
</head>
<body>
<ol>
<li><%=p %>
<li><%=p2 %>
<li><%=p3 %>
<li><%=p4 %>
<li><%=p5 %>
</ol>
</body>
</html>
