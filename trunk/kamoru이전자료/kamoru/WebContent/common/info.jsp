<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	String serverInfo = request.getScheme() + "://"
					+ request.getServerName() + ":" + request.getServerPort()
					+ request.getContextPath();
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>-[<%=serverInfo%>]</title>
<style>
body {font-family : Arial; width:600px;}
table {border:1px solid blue;}
th {text-align:left;}
</style>
</head>
<body>
<h1>Server Infomation</h1>

<h3><font color="blue"><%=serverInfo%></font></h3>

<table>
	<tr>
		<th colspan="2">Remote Client Infomation</th>
	</tr>
	<tr>
		<td>IP</td>
		<td><%=request.getRemoteAddr()%></td>
	</tr>
	<tr>
		<td>RemoteHostName</td>
		<td><%=request.getRemoteHost()%></td>
	</tr>
	<tr>
		<td>RemoteUser</td>
		<td><%=request.getRemoteUser()%></td>
	</tr>
</table>

<table>
	<tr>
		<th colspan="2">Request Headers</th>
	</tr>
	<%
	java.util.Enumeration eeee = request.getHeaderNames();
	while(eeee.hasMoreElements()){
		String headerName = (String)eeee.nextElement();
	%>
	<tr>
		<td><%= headerName %></td>
		<td><%= request.getHeader(headerName) %></td>
	</tr>
	<%
	}
	%>
</table>
<h2>Context List of this server</h2>

</body>
</html>
