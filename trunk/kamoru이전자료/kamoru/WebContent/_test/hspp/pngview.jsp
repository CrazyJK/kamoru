<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.io.*" %>


<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title> img view</title>
</head>
<body bgcolor="gray">
<%
// String path = "G:\\MySmartPhone\\hs++theme\\oasis\\image";

String path = "G:\\MySmartPhone\\tmp\\evada\\Mono ICON";

File[] imgF = new File(path).listFiles();
for(int i=0 ; i<imgF.length ; i++){
	if(imgF[i].isFile())
%>		
	<img border="1" src="<%= imgF[i].getAbsolutePath() %>"/><!-- <%= imgF[i].getName() %> -->
<%
}
%>
</body>
</html>