<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	String[] colors = {"aqua", "black", "blue", "fuchsia", "gray", "green", "lime", "maroon", "navy", "olive", "orange", "purple", "red", "silver", "teal", "white", "yellow"};
	String rgba = request.getParameter("rgba");
%>
<!DOCTYPE html>
<html>
<head>
<title>Standard colors</title>
<style type="text/css">
li {
	display:inline-block;
	margin: 10px;
}
div {
	width:150px; height:150px;
	text-shadow: 1px 1px 1px white;
	border-radius: 10px;
}
</style>
</head>
<body>
<h2>If you see custom color, input parameter by ?rgba(123, 123, 123, 0.5)</h2>
<ul>
<%	for (String color : colors) { %>
	<li><div style="background-color:<%=color %>;"><%=color %></div>
<%	} %>
	<li><div style="background-color:rgba(<%=rgba %>);">rgba(<%=rgba %>)</div>
</ul>
</body>
</html>