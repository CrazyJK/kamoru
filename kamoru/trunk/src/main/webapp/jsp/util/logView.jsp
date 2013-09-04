<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.io.*" %>
<%@ page import="java.util.*" %>
<%@ page import="org.apache.commons.io.*" %>
<%@ page import="org.apache.commons.lang3.*" %>

<%
String logpath = request.getParameter("logpath");
String delimeter = request.getParameter("delimeter");
String search = request.getParameter("search");
search = search == null ? "" : search.trim();
String[] searchArray = trimArray(StringUtils.splitByWholeSeparator(search, ","));

String msg = "";
List<String[]> lines = new ArrayList<String[]>();
int tdCount = 0;
if (logpath != null) {
	File logfile = new File(logpath);
	if (logfile.exists() && logfile.isFile()) {
		lines = readLines(logfile, delimeter, searchArray);
		for (String[] line : lines) {
			tdCount = line.length > tdCount ? line.length : tdCount;
		}
	}
	else {
		msg = logpath + " invalid";
	}
}
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8" />
<title>Log4j View</title>
<style type="text/css">
.log-td {
	font-size: 9pt;
	white-space:nowrap;
	border-bottom: 1px solid lightblue;
	font-family: "나눔고딕코딩";
}
#log-div tr:hover {
	background-color: lightblue;
}
.divBox {
	border: 1px solid blue;
	border-radius: 10px;
	margin: 3px;
}
#form-div {
	padding:2px;
}
#log-div {
	overflow: auto;
	height: 600px;
}
em {
	color: red;
	font-style: normal;
}
</style>
</head>
<body>
<div id="form-div" class="divBox">
	<form>
		<label for="logpath">Log path</label><input type="text" id="logpath" name="logpath" size="50" value="E:\tools\springsource-e4\sts-3.2.0.RELEASE\crazy.root.log"/>
		<label for="delimeter">Delimeter</label><input type="text" id="delimeter" name="delimeter" size="2" value="] ["/>
		<label for="search">Keyword</label><input type="text" id="search" name="search" value="<%=search %>"/>
		<input type="submit">
	</form>
</div>
<%=ArrayUtils.toString(searchArray) %>
<div id="log-div" class="divBox">
	<table>
		<%	for (String[] line : lines) { 
			String colspan = line.length > 1 ? "" : "colspan=" +tdCount; 
		%>
		<tr>
			<%	for (String str : line) { %>
			<td class="log-td" <%=colspan %>><%=str %></td>
			<%	} %>
		</tr>		
		<%	} %>
	</table>
</div>
</body>
</html>
<%!
List<String[]> readLines(File file, String delimeter, String[] search) throws Exception {
	List<String[]> lineArrayList = new ArrayList<String[]>(); 
	
	List<String> lineList = FileUtils.readLines(file);
	for (String line : lineList) {
		if (search.length == 0 || containsAny(line, search)) {
			line = StringUtils.replaceEach(line, 
					search,
					wrapString(search, "<em>", "</em>"));
			lineArrayList.add(StringUtils.splitByWholeSeparator(line, delimeter));
		}
	}

	return lineArrayList;
}
String[] wrapString(String[] strArr, String str1, String str2) {
	String[] retArr = Arrays.copyOf(strArr, strArr.length);
	for (int i=0; i < strArr.length; i++) {
		retArr[i] = str1 + strArr[i] + str2;
	}
	return retArr;
}
boolean containsAny(String str, String[] searchArr) {
	for (String search : searchArr) {
		if (StringUtils.contains(str, search))
			return true;
	}
	return false;
}
String[] trimArray(String[] strArr) {
	for (int i=0; i < strArr.length; i++) {
		strArr[i] = strArr[i].trim();
	}
	return strArr;
}
%>