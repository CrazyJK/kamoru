<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.io.*"%>
<%@ include file="common.jspf" %>
<%
String folder = filedownpath;

File[] files = new File(folder).listFiles();
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title> File list </title>
<link rel="stylesheet" type="text/css" href="upload.css">
<script type="text/javascript">
function jsDownload(dispfile, file, type){
	var downloadform = document.createElement("form");
		downloadform.method = "get";
//		downloadform.action = "<%= request.getContextPath() %>/common/download.jsp";
		downloadform.action = "<%= request.getContextPath() %>/servlet/streamwriter";
	var fileInput = document.createElement("input");
		fileInput.type  = "hidden";
		fileInput.name  = "file";
		fileInput.value =  file;
	var dispInput = document.createElement("input");
		dispInput.type  = "hidden";
		dispInput.name  = "dispfile";
		dispInput.value =  dispfile;
	var mimeInput = document.createElement("input");
		mimeInput.type  = "hidden";
		mimeInput.name  = "hmimetype";
		mimeInput.value =  type ? type : "";

	downloadform.appendChild(fileInput);
	downloadform.appendChild(dispInput);
	downloadform.appendChild(mimeInput);

	document.appendChild(downloadform);

	downloadform.submit();
}
</script>
</head>
<body>
<span>Folder : <%= folder %></span> 
<table id="filelistTable">
	<tr>
		<th class="filelistTH">이름</th>
		<th class="filelistTH">크기</th>
		<th class="filelistTH">날자</th>
	</tr>
<% for(File f : files){ String[] fileInfo = getFileInfo(f);%>
	<tr>
		<td class="filelistTD"><a href="javascript:jsDownload('<%= fileInfo[0] %>','<%= replaceAll(fileInfo[1] + fileInfo[0],"\\", "\\\\") %>')"><%= fileInfo[0] %></a></td>
		<td class="filelistTD" align="right"><%= fileInfo[2] %></td>
		<td class="filelistTD"><%= fileInfo[3] %></td>
	</tr>	
<% } %>
	
</table>

</body>
</html>