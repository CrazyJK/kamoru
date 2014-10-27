<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<!DOCTYPE html>
<html>
<head>
<title>Web Session List</title>
</head>
<body>
<ol class="code-view">
<%
	Enumeration names = session.getAttributeNames();
	while (names.hasMoreElements()) {
		String name = (String)names.nextElement();
		Object value = session.getAttribute(name);
		String clazz = value.getClass().getName();
		out.println(
				"<li><code class='code-name'>" + name + "</code>&nbsp;"
				  + "<code class='code-value'>" + value + "</code>&nbsp;"
				  + "<code class='code-clazz'>" + clazz + "</code>");
	}
%>
</ol>
</body>
</html>