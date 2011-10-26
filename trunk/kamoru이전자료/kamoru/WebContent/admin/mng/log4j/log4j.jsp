<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="org.apache.log4j.*, org.apache.log4j.spi.*, java.util.*"%>
<%!
static Logger rootLogger = Logger.getRootLogger();

final String[] levels = new String[]{
		"TRACE","DEBUG","WARN","INFO","ERROR","FATAL"
};
%>
<%
String loggername = request.getParameter("loggername");
String loggerlevel   = request.getParameter("loggerlevel");

ArrayList loggerList = new ArrayList();
ArrayList loggerInfoList = new ArrayList();
Logger _logger = null;
String _loggername = null;
String _loggerlevel = null;
String _loggerappender = "";

loggerList.add(rootLogger);
LoggerRepository lr = rootLogger.getLoggerRepository();
for(Enumeration enumeration = lr.getCurrentLoggers();enumeration.hasMoreElements();){
	loggerList.add((Logger)enumeration.nextElement());
}

for(int i=0 ; i<loggerList.size() ; i++)
{
	_logger = (Logger)loggerList.get(i);

	_loggername = _logger.getName();
	_loggerappender = "";
	
	// set new Log level
	if(loggername != null && loggername.equals(_loggername) && loggerlevel != null && loggerlevel.trim().length() > 0){
		_logger.setLevel(Level.toLevel(loggerlevel));
	}
	_loggerlevel = _logger.getLevel() == null ? "" : _logger.getLevel().toString();
	

	for(Enumeration enumAppend = _logger.getAllAppenders(); enumAppend.hasMoreElements();){
		Appender appender = (Appender)enumAppend.nextElement();
		if(appender != null){
			_loggerappender += "[" + appender.getName() + "]";
		}
	}
	loggerInfoList.add(new String[]{	_loggername, _loggerlevel, _loggerappender});
}

%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>log4j control</title>
<link rel="stylesheet" type="text/css" href="/css/kamoru.css">
<link rel="stylesheet" type="text/css" href="/css/sitemng.css">
<script>
function changeLoggerLevel(loggerName){
	loggername 	= document.getElementById("loggername");	
	loggerlevel 	= document.getElementById("loggerlevel");	
	select_loggerLevel = document.getElementById(loggerName + "_loggerLevel");	

	loggername.value	= loggerName;
	loggerlevel.value 	= select_loggerLevel.value;
	document.forms[0].submit();
}
</script>
</head>
<body>
<form action="" method="get" name="log4jForm" id="log4jForm">
<input type="hidden" name="loggername" />
<input type="hidden" name="loggerlevel" />
</form>
	<h3>log4j control page</h3>
	<fieldset>
		<legend>Current Logger List &nbsp;<a href="log4j.jsp">refresh</a></legend>
		<table border="0">
		<thead>
			<tr>
				<th>Logger Name</th>
				<th>Current Level</th>
				<th>Change Level</th>
				<th>Current Appender</th>
			</tr>
		</thead>
		<tbody>
			<% for(int i=0 ; i<loggerInfoList.size() ; i++){ 
					String[] loggerInfo = (String[])loggerInfoList.get(i); %>
			<tr>
				<td><%= loggerInfo[0] %></td>
				<td><%= loggerInfo[1] %></td>
				<td>
					<select name="<%= loggerInfo[0] %>_loggerLevel" onchange="changeLoggerLevel('<%= loggerInfo[0] %>')">
						<optgroup label="to Level">
						<option value="">--</option>
						<% for(int j=0 ; j<levels.length ; j++){ %>
						<option value="<%= levels[j] %>" <%= loggerInfo[1].equals(levels[j]) ? "selected" : "" %>><%= levels[j] %></option>
						<% } %>
						</optgroup>
					</select>
				</td>
				<td><%= loggerInfo[2] %></td>
			</tr>
			<% } %>
		</tbody>
	</table>
	<p>For log4j.properties reloading, click <a href="http://kamoru/log4jinit?reload=true" target="log4jFrame">here</a></p>
	</fieldset>
<iframe class="ACTIONFRAME" name="log4jFrame" width="100%" height="100" frameborder="0"></iframe>
</body>
</html>