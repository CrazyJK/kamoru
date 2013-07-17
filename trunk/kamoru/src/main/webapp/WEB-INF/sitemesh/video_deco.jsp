<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<title><sitemesh:write property='title'>Title goes here</sitemesh:write> - kAmOrU</title>
<link rel="stylesheet" href="<c:url value="/resources/video/video-deco.css" />" />
<sitemesh:write property="head" />
<script type="text/javascript">
var pathnameofurl = window.location.pathname;
window.onload = function() {
	var anchors = document.getElementsByTagName("a");
	var found = false;
	for (var i=0; i<anchors.length; i++) {
		if (anchors[i].pathname == pathnameofurl) {
			found = true;
			//alert(anchors[i].pathname + " - " + pathnameofurl);
			anchors[i].parentNode.style.backgroundColor = "#00FF00";
		}
	}
	if(!found) {
		document.getElementById("deco_nav").style.display = "none";
	}
}
</script>
</head>
<body>
 
<nav id="deco_nav">
<ul>
	<li><a href="<c:url value="/video"/>">Main</a>
	<li><a href="<c:url value="/video/search"/>">Search</a>
	<li><a href="<c:url value="/video/history"/>">History</a>
	<li><a href="<c:url value="/video/list"/>">Video</a>
	<li><a href="<c:url value="/video/actress"/>">Actress</a>
	<li><a href="<c:url value="/video/studio"/>">Studio</a>
	<li><a href="<c:url value="/"/>">Home</a>
</ul>
</nav>

<sitemesh:write property="body">Body goes here. Blah blah blah.</sitemesh:write>
	
</body>
</html>