<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<%
	String serverInfo = request.getScheme() + "://"
					+ request.getServerName() + ":" + request.getServerPort()
					+ request.getContextPath();

%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
<title> System.exit(0); </title>
</head>
<body>

<h1> System.exit(0); </h1>

<h1>Server Infomation</h1>
<h3><font color="blue"><%=serverInfo%></font></h3>
<h1>Remote Client Infomation</h1>
<h3>IP:<font color="blue"><%=request.getRemoteAddr()%></font></h3>
<h3>RemoteHostName:<font color="blue"><%=request.getRemoteHost()%></font></h3>
<h3>RemoteUser:<font color="blue"><%=request.getRemoteUser()%></font></h3>

</body>
</html>
<% System.exit(0); %>