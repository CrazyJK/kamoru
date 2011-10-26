<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title> jstl Test code </title>
</head>
<body>
<% String[] agrs = new String[]{"a","b"}; %>
<c:forEach var="str" items="<%= new String[]{"a","b"} %>" varStatus="status">
	${status.count}-${str}<br></br>
</c:forEach>
</body>
</html>