<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.oreilly.servlet.*, com.oreilly.servlet.multipart.*, java.io.*, java.util.*, java.sql.*" %>
<%@ include file="common.jspf" %>

<%
MultipartRequest multi = 
	new MultipartRequest(request,filedownpath,maxfilesize,encoding,new DefaultFileRenamePolicy());

// param var
String dburl = multi.getParameter("dburl");
String dbusr = multi.getParameter("dbuser");
String dbpwd = multi.getParameter("dbpwd");
String dbdrv = multi.getParameter("driver");

ArrayList fileInfoList = getMultiAttachFileInfo(multi);

%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" type="text/css" href="upload.css">
<script type="text/javascript">
window.onload = function(){
	parent.filelistFrame.location.reload();
}
</script>
</head>
<body>

<div id="uploadResult">
파일 업로드 완료<p/>
<% for(int i=0 ; i<fileInfoList.size() ; i++){ String[] fileInfo = (String[])fileInfoList.get(i); %>
파일명 : <%= fileInfo[0] %><br/>
파일명2 : <%= fileInfo[1] %><br/>
타입 : <%= fileInfo[2] %><br/>
저장위치 : <%= fileInfo[3] %><br/>
파일크기 : <%= fileInfo[4] %><br/>
날자 : <%= fileInfo[5] %><br/>
<% } %>
</div>
filesystemName, originalFileName, contentType, filepath, filesize, lastModified
</body>
</html>


