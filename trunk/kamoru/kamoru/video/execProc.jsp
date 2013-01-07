<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<%
request.setCharacterEncoding("UTF-8");
//parameter
String filepath = request.getParameter("filepath");
String command = "\"C:\\Program Files (x86)\\The KMPlayer\\KMPlayer.exe\" \"" + filepath.replace('/', '\\') + "\"";
System.out.println("command:" + command);
Runtime.getRuntime().exec(command);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
<title>Insert title here</title>
</head>
<body>
<%=filepath %>
</body>
</html>