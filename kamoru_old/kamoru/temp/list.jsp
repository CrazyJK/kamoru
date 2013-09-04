<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="EUC-KR"%>
<%@ page import="java.io.*" %>
<%
String path = request.getRealPath("data");
File dir = new File(path);
File[] files = dir.listFiles();
%>    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Prestige list</title>
</head>
<body>
<table width="100%">
	<tr>
		<td width="100px">
			<ul>
				<% for(File f: files) { %>
				<li><a href="data/<%=f.getName() %>" target="ifrm"><%=f.getName() %></a></li>
				<% } %>
			</ul>
		</td>
	</tr>
</table>
</body>
</html>