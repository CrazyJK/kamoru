<%@ page language="java" contentType="text/html; charset=EUC-KR"
	pageEncoding="EUC-KR"%>
<%String strRefreshTime = request.getParameter("refreshTime");
			if (strRefreshTime == null) {
				strRefreshTime = "0";
			}

			%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
<%
	if(!strRefreshTime.equals("0")) {
%>
<meta http-equiv="refresh" content="<%=strRefreshTime%>;url=MemoryInfo.jsp?refreshTime=<%=strRefreshTime%>">
<%
	}
%>
<title>Memory Check Page</title>
<script language="javaScript">
	function setTime(){
		if(!confirm("실행주기를 "+document.f.refreshTime.value+"sec로 변경 하시겠습니까?")) {
			document.f.refreshTime.value = _time;
		 	return;
		}
		document.f.submit();
	}
	
	function runGC(){
		var vUrl 	= "GarbageCollector.jsp";
		var vWidth 	= 400;
		var vHeight = 300;
		var vLeft 	= (window.screen.width- vWidth)/2;
		var vTop	= (window.screen.height - vHeight)/2;
		var vFeature = "width="+vWidth+", height="+vHeight+", top="+vTop+", left="+vLeft;
		vFeature = vFeature + "toolbar=1,location=0,directories=0,"+
					   "status=1,menubar=0,scrollbars=0,"+
					   "resizable=0,copyhistory=1";
		
		window.open(vUrl,"GC",vFeature);
	}
</script>
</head>
<body>
<b>메모리 사용 현황</b><br>
<hr>
<%= getMemoryInfo() %>
<hr>
Current refresh Time&nbsp;&nbsp;<%= strRefreshTime %>sec<br>
Current Time&nbsp;&nbsp;&nbsp;&nbsp;<%= new java.util.Date() %><br>
<form name="f" method="get" action="">주기 설정 
	<select name="refreshTime" onChange="setTime()">
<%
	for (int i = 0; i <= 120;) {
%>
		<option value="<%=i %>" <%= i == Integer.parseInt(strRefreshTime)? "selected" : ""%>>
		<%=i%>sec</option>
<%
		i = i + 10;
	}
%>
	</select>
</form>
<hr>
<input type="button" value="Garbage Collecting Start" onClick="runGC();"/>
<script language="javaScript">
	var _time = document.f.refreshTime.value;
</script>
</body>
</html>

<%!
public String getMemoryInfo(){
	StringBuffer sb = new StringBuffer();
	Runtime rt 	=  Runtime.getRuntime();
	long free 	= rt.freeMemory();
	long total 	= rt.totalMemory();
	long max	= 0;//rt.maxMemory();
	int availP	= 0;//rt.availableProcessors();
	long useRate = Math.round(((double)free / (double)total) * 100);
	String sUseRate = "<table width='95%' align='center' border='0' cellspacing=0 cellpadding=0 style='border:1 solid navy'>"
					+ "<tr><td width='"+useRate+"%' bgcolor='lightblue' align='center'>free : "+useRate+"%</td>"
					+ "<td bgcolor='gray'>&nbsp;</td></tr></table>";
	String hostAddr = "UnkonwnHost";
	String hostName = hostAddr;
	try{
		hostAddr = java.net.InetAddress.getLocalHost().getHostAddress();
		hostName = java.net.InetAddress.getLocalHost().getHostName();
	}catch(java.net.UnknownHostException uhe){
		hostAddr += " : "+uhe.toString();
	}
	
	sb.append("<table border='0' cellspacing='0' cellpadding='3' style='border:1 solod navy'>");
	sb.append("<tr><td>");
	sb.append("Host Address").append("</td><td align='right'>").append("<b>"+hostName+"["+hostAddr+"]").append("</b><br>");
	sb.append("</td></tr><tr><td>");
	sb.append("Free Memory").append("</td><td align='right'>").append("<b>"+free/1024/1024).append("</b>&nbsp;MB&nbsp;[").append(free/1024).append("&nbsp;KB]");
	sb.append("</td></tr><tr><td>");
	sb.append("Total memory").append("</td><td align='right'>").append("<b>"+total/1024/1024).append("</b>&nbsp;MB&nbsp;[").append(total/1024).append("&nbsp;KB]");
	sb.append("</td></tr><tr><td colspan='2'>");
	sb.append(sUseRate);
	sb.append("</td></tr><tr><td>");
	sb.append("Max Memory").append("</td><td align='right'>").append("<b>"+max/1024/1024).append("</b>&nbsp;MB&nbsp;[").append(max/1024).append("&nbsp;KB]");
	sb.append("</td></tr><tr><td>");
	sb.append("Available Process").append("</td><td align='right'>").append("<b>"+availP).append("</b><br>");
	sb.append("</td></tr><tr><td>");
	sb.append("</table>");
	
	//com.namjk.common.LogMaker.setClassName(getClass().getName());
	//com.namjk.common.LogMaker.print(sb.toString());
	return sb.toString();
}

%>