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
.clazz {
	color: navy;
}
</style>
</head>
<body>
<ol>
<%
	Enumeration names = session.getAttributeNames();
	while (names.hasMoreElements()) {
		String name = (String)names.nextElement();
		Object value = session.getAttribute(name);
		String clazz = value.getClass().getName();
		out.println(
				"<li><code class='name'>" + name + "</code>&nbsp;"
				  + "<code class='value'>" + value + "</code>&nbsp;"
				  + "<code class='clazz'>" + clazz + "</code>");
	}
%>
</ol>
</body>
</html>