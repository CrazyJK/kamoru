<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<% 
response.setHeader("Cache-Control","no-cache"); //HTTP 1.1
response.setHeader("Pragma","no-cache"); //HTTP 1.0
response.setDateHeader ("Expires", 0); //prevent caching at the proxy server

int[] ports = {40001, 41001, 42001, 43001, 44001, 45001, 46001, 47001,
			   40006, 41006, 42006, 43006, 44006, 45006, 46006, 47006,
			   9030,  9130,  9230,  9330,  9430,  9530,  9630,  9730,
			   6759,  6758,  6756,  6300,  6400,  6500,  6771,  7300,
			   1521};  
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Port Sacn</title>
</head>
<body>
<%
for(int i=0; i<ports.length; i++) {
	try {
		java.net.ServerSocket ss = new java.net.ServerSocket(ports[i]);
		ss.close();
	} catch(Exception e) {
		System.out.println(ports[i] + " port LISTEN<br/>");
		out.println(ports[i] + " port LISTEN<br/>");
	}
}
%>
</body>
</html>