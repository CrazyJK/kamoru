<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<!DOCTYPE html>
<html>
<head>
<title>Web Session List</title>
<style type="text/css">
.name {
	color:blue;
}
.value {
	color: teal;
}
</style>
</head>
<h1>Web session list</h1>
<body>
<ol>
<%
	Enumeration names = session.getAttributeNames();
	while (names.hasMoreElements()) {
		String name = (String)names.nextElement();
		Object value = session.getAttribute(name);
		out.println("<li><code class='name'>" + name + "</code> = <code class='value'>" + value + "</code>");
	}
%>
</ol>
</body>
</html>