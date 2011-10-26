<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String parentURL = request.getParameter("parentURL");
	if(parentURL == null){
		parentURL = "memoryinfo.jsp";
	}
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Garbage Collector</title>
<style> <!-- url(images/feather.bmp) no-repeat center -->
	BODY{font-family:'굴림','Arial'; font-size:14px;}
</style>
<script language="javascript">
	function wClose(){
		opener.location.href="<%=parentURL%>"; 
		window.close();
	}
</script>
</head>
<body>
<center>
<b>Garbage Collection</b><br>
<hr>
<font color="blue" style="">Before</font><br>
<%
	Runtime rt 	= Runtime.getRuntime();
	long free 	= rt.freeMemory();
	long total 	= rt.totalMemory();
	long max	= rt.maxMemory();
	out.println("Free  = <b>"+(free/1024/1024) +"</b> MB ("+(free/1024) +" Kbytes)<br>");
	out.println("Total = <b>"+(total/1024/1024)+"</b> MB ("+(total/1024)+" Kbytes)<br>");
	out.println("Max   = <b>"+(max/1024/1024)  +"</b> MB ("+(max/1024)  +" Kbytes)<br>");
%>
<hr>
Garbage Collect.....
<%System.gc();%>
completed.
<hr>
<font color="blue" style="">After</font><br>
<%
	rt 		= Runtime.getRuntime();
	free 	= rt.freeMemory();
	total 	= rt.totalMemory();
	max		= rt.maxMemory();
	out.println("Free  = <b>"+(free/1024/1024) +"</b> MB ("+(free/1024) +" Kbytes)<br>");
	out.println("Total = <b>"+(total/1024/1024)+"</b> MB ("+(total/1024)+" Kbytes)<br>");
	out.println("Max   = <b>"+(max/1024/1024)  +"</b> MB ("+(max/1024)  +" Kbytes)<br>");
%>
<form action="">
<input type="hidden" name="parentURL" value="<%=parentURL%>"/>
<input type="submit" value="refresh"/>&nbsp;&nbsp;&nbsp;&nbsp;
<input type="button" value="EXIT" onClick="wClose();"/>
</form>
</center>
</body>
</html>