<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.*, com.hs.frmwk.db.*, java.sql.*" %>
<%@ include file="method.jspf" %>
<% // parameter 받기
request.setCharacterEncoding("UTF-8");

String latitude 	= isnull(request.getParameter("latitude"), "37.51647829244714");
String longitude 	= isnull(request.getParameter("longitude"), "126.97448293879174");
String level		= isnull(request.getParameter("level"), "8");
String mode			= isnull(request.getParameter("mode"), "view");
String locationName		= request.getParameter("locationName");
String locationDesc		= request.getParameter("locationDesc");

String goSite		= request.getParameter("goSite");
String goTask		= request.getParameter("goTask");
String goTime		= request.getParameter("goTime");

String selectUserID		= request.getParameter("selectUserID");

out.println(" latitude > " + latitude);
out.println(", longitude> " + longitude);
out.println(", level> " + level);
out.println(", mode> " + mode);
out.println(", goSite> " + goSite);
out.println(", goTask> " + goTask);
out.println(", goTime> " + goTime);
out.println(", selectUserID> " + selectUserID);

%>
<% // mode에 따른 액션
if(mode.equalsIgnoreCase("saveLocation")){
	saveLocation(locationName, latitude, longitude, locationDesc);
}else if(mode.equalsIgnoreCase("savePlaceToGo")){
	savePlaceToGo(selectUserID, goSite, goTask, goTime, latitude, longitude);
}
%>
<html>
<head>
<title> MapAction </title>
<script>
function window_onload(){
	parent.location.href = "daum.jsp?latitude=" + <%= latitude %> + "&longitude=" + <%= longitude %> + "&level=" + <%= level %> + "&selectUserID=" + <%= selectUserID %>;
}
</script>
</head>
<body onload="window_onload()">
<!-- <form method="get" target="_parent">
<input type="hidden" name="latitude" value="<%= latitude %>"/>
<input type="hidden" name="longitude" value="<%= longitude %>"/>
<input type="hidden" name="level" value="<%= level %>"/>
<input type="hidden" name="selectUserID" value="<%= selectUserID %>"/>
</form> -->
</body>
</html>