<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.io.File, java.util.ArrayList" %>
<%
String path = "G:\\MySmartPhone\\tmp\\s2u2clockspack\\";
ArrayList folderlist = new ArrayList();

File rootfolder = new File(path);
File[] folders = rootfolder.listFiles();
for(int i=0; i<folders.length ; i++){
	if(folders[i].isDirectory()){
		String folder = folders[i].getName();
		folderlist.add(folder);
		System.out.println(folder);
	}
}
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>HS++ clock font view</title>
<link rel="stylesheet" type="text/css" href="/css/default.css">
</head>
<body>
<table border="1">
	<tr>
		<th>Folder</th>
		<th>Black</th>
		<th>White</th>
		<th>Folder</th>
		<th>Black</th>
		<th>White</th>
	</tr>
	<% for(int i=0 ; i<folderlist.size() ; i++){ String folder = (String)folderlist.get(i); %>
	<tr>
		<td><%= folder %></td>
		<td>
			<img src="/clock/<%= folder %>/Black/pm.png">
			<img src="/clock/<%= folder %>/Black/1.png"><img src="/clock/<%= folder %>/Black/2.png">
			<img src="/clock/<%= folder %>/Black/seperator.png">
			<img src="/clock/<%= folder %>/Black/3.png"><img src="/clock/<%= folder %>/Black/4.png">
		</td>
		<td bgcolor="black">
			<img src="/clock/<%= folder %>/White/pm.png">
			<img src="/clock/<%= folder %>/White/1.png"><img src="/clock/<%= folder %>/White/2.png">
			<img src="/clock/<%= folder %>/White/seperator.png">
			<img src="/clock/<%= folder %>/White/3.png"><img src="/clock/<%= folder %>/White/4.png">
		</td>
		<% folder = (String)folderlist.get(++i); %>
		<td><%= folder %></td>
		<td>
			<img src="/clock/<%= folder %>/Black/pm.png">
			<img src="/clock/<%= folder %>/Black/1.png"><img src="/clock/<%= folder %>/Black/2.png">
			<img src="/clock/<%= folder %>/Black/seperator.png">
			<img src="/clock/<%= folder %>/Black/3.png"><img src="/clock/<%= folder %>/Black/4.png">
		</td>
		<td bgcolor="black">
			<img src="/clock/<%= folder %>/White/pm.png">
			<img src="/clock/<%= folder %>/White/1.png"><img src="/clock/<%= folder %>/White/2.png">
			<img src="/clock/<%= folder %>/White/seperator.png">
			<img src="/clock/<%= folder %>/White/3.png"><img src="/clock/<%= folder %>/White/4.png">
		</td>
	</tr>
	<% } %>
</table>
</body>
</html>