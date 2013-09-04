<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<% 
	String contextPath = request.getContextPath();
%>
<%
String pagename = getClass().getName();

String strRefreshTime = request.getParameter("refreshTime");
if (strRefreshTime == null) {
	strRefreshTime = "0";
}

Runtime rt 	=  Runtime.getRuntime();
long free 	= rt.freeMemory();
long total 	= rt.totalMemory();
long max	= rt.maxMemory();
int availP	= rt.availableProcessors();
long useRate = Math.round(((double)free / (double)total) * 100);
String hostAddr = "UnkonwnHost";
String hostName = hostAddr;
try{
	hostAddr = java.net.InetAddress.getLocalHost().getHostAddress();
	hostName = java.net.InetAddress.getLocalHost().getHostName();
}catch(java.net.UnknownHostException uhe){
	hostAddr += " : "+uhe.toString();
}

%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<% if(!strRefreshTime.equals("0")) { %>
<meta http-equiv="refresh" content="<%=strRefreshTime%>;url=memoryinfo.jsp?refreshTime=<%=strRefreshTime%>">
<% } %>
<title>Memory Check Page</title>
<link rel="stylesheet" type="text/css" href="../mng.css">
<script language="javaScript">
function setTime(){
	if(!confirm("실행주기를 "+document.memory.refreshTime.value+"sec로 변경 하시겠습니까?")) {
		document.memory.refreshTime.value = _time;
	 	return;
	}
	document.memory.submit();
}

function runGC(){
	var vUrl 	= "garbagecollector.jsp";
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
		
function init(){
//			show_time();
}
</script>
</head>
<body onLoad="init()" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
<form name="memory" method="get" action="">
<fieldset>
<table border='0' width='100%' cellspacing='0' cellpadding='3' style='border:1 solod navy' class="text">
	<tr>			
		<td>Host Address</td>			
		<td align='right'><b><%= hostName+"["+hostAddr+"]" %></b></td>		
	</tr>		
	<tr>			
		<td>Free Memory</td>			
		<td align='right'><%= "<b>"+free/1024/1024+"</b>&nbsp;MB&nbsp;["+free/1024+"&nbsp;KB]" %></td>		
	</tr>		
	<tr>			
		<td>Total memory</td>			
		<td align='right'><%= "<b>"+total/1024/1024+"</b>&nbsp;MB&nbsp;["+total/1024+"&nbsp;KB]" %></td>		
	</tr>		
	<tr>			
		<td colspan='2'>
			<table width='100%' align='center' border='0' cellspacing=0 cellpadding=0 style='border:1 solid navy' class="text">
				<tr>
					<td width='<%= useRate %>%' bgcolor='lightblue' align='center' style="filter=progid:DXImageTransform.Microsoft.Gradient(GradientType=1, StartColorStr=#FFFFCC, EndColorStr=#ff9900)">free : <%= useRate %> %</td>
					<td>&nbsp;</td>
				</tr>
			</table>
		</td>		
	</tr>		
	<tr>			
		<td>Max Memory</td>			
		<td align='right'><%= "<b>"+max/1024/1024+"</b>&nbsp;MB&nbsp;["+max/1024+"&nbsp;KB]" %></td>		
	</tr>		
	<tr>			
		<td>Available_Process</td>			
		<td align='right'><b><%= availP %></b></td>		
	</tr>
	<tr>
		<td class="text">
			Current <a href="">refresh</a> Time&nbsp;&nbsp;
			<select name="refreshTime" onChange="setTime()" style="text-align:right;">
			<% for (int i = 0; i <= 120; i = i + 10) { %>
				<option value="<%=i %>" <%= i == Integer.parseInt(strRefreshTime)? "selected" : ""%> style="text-align:right;"><%=i%>s</option>
<% } %>
			</select>
		</td>
	</tr>
	<tr>
		<td>
			<a href="javaScript:runGC();"/>GarbageCollect</a>
		</td>
	</tr>
</table>
<script language="javaScript">
	var _time = document.memory.refreshTime.value;
</script>
</fieldset>
</form>
</body>
</html>

<%!
// 사용안함.
public String getMemoryInfo(){
	String html = "";
	Runtime rt 	= Runtime.getRuntime();
	long free 	= rt.freeMemory();
	long total 	= rt.totalMemory();
	long max	= rt.maxMemory();
	int availP	= rt.availableProcessors();
	long useRate = Math.round(((double)free / (double)total) * 100);
	String sUseRate = 
		"<table width='95%' align='center' border='0' cellspacing=0 cellpadding=0 style='border:1 solid navy' class=\"text\">" +
			"<tr>" + 
				"<td width='"+useRate+"%' bgcolor='lightblue' align='center' style=\"filter=progid:DXImageTransform.Microsoft.Gradient(GradientType=1, StartColorStr=#ff9900, EndColorStr=#FFFFCC)\">" +
					"free : "+useRate+"%</td>" +
				"<td bgcolor='gray' style=\"filter=progid:DXImageTransform.Microsoft.Gradient(GradientType=1, StartColorStr=#FFFFCC, EndColorStr=#ff9900)\" style='border-left:1px'>&nbsp;</td>" +
			"</tr>" +
		"</table>";
	String hostAddr = "UnkonwnHost";
	String hostName = hostAddr;
	try{
		hostAddr = java.net.InetAddress.getLocalHost().getHostAddress();
		hostName = java.net.InetAddress.getLocalHost().getHostName();
	}catch(java.net.UnknownHostException uhe){
		hostAddr += " : "+uhe.toString();
	}
	
	html = ""
	 + "	<table border='0' width='100%' cellspacing='0' cellpadding='3' style='border:1 solod navy' class=\"text\">"
	 + "		<tr>"
	 + "			<td>Host Address</td>"
	 + "			<td align='right'><b>"+hostName+"["+hostAddr+"]</b></td>"
	 + "		</tr>"
	 + "		<tr>"
	 + "			<td>Free Memory</td>"
	 + "			<td align='right'><b>"+free/1024/1024+"</b>&nbsp;MB&nbsp;["+free/1024+"&nbsp;KB]</td>"
	 + "		</tr>"
	 + "		<tr>"
	 + "			<td>Total memory</td>"
	 + "			<td align='right'><b>"+total/1024/1024+"</b>&nbsp;MB&nbsp;["+total/1024+"&nbsp;KB]</td>"
	 + "		</tr>"
	 + "		<tr>"
	 + "			<td colspan='2'>"+sUseRate+"</td>"
	 + "		</tr>"
	 + "		<tr>"
	 + "			<td>Max Memory</td>"
	 + "			<td align='right'><b>"+max/1024/1024+"</b>&nbsp;MB&nbsp;["+max/1024+"&nbsp;KB]</td>"
	 + "		</tr>"
	 + "		<tr>"
	 + "			<td>Available_Process</td>"
	 + "			<td align='right'><b>"+availP+"</b></td>"
	 + "		</tr>"
	 + "</table>";

	return html;
}
%>