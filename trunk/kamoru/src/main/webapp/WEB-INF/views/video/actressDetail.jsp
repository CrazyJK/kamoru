<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"      uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn"     uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="s"      uri="http://www.springframework.org/tags" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>${actress.name}</title>
<link rel="shortcut icon" type="image/x-icon" href="<c:url value="/resources/video/video-favicon.ico" />">
<link rel="stylesheet" href="<c:url value="/resources/video/video.css" />" />
<!--[if lt IE 9]>
<script src="http://html5shiv.googlecode.com/svn/trunk/html5.js"></script>
<![endif]-->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.8.3/jquery.min.js"></script>
<script src="<c:url value="/resources/common.js" />"></script>
<script src="<c:url value="/resources/image-popup.js" />"></script>
<script type="text/javascript">
var context = '<s:url value="/"/>';
$(document).ready(function() {
	// Add class : elements in onclick attribute add class
	$("*[onclick]").addClass("onclick");
});
</script>
</head>
<body>
<div id="actressImage">
<c:forEach items="${actress.webImage}" var="url">
	<img src="${url}" width="185px" onclick="fnViewFullImage(this)"/>
</c:forEach>
</div>
<div>
	<%@ include file="/WEB-INF/views/video/actressInfo.inc" %>
</div>
<div>
	<ul>
	<c:forEach items="${actress.videoList}" var="video">
		<%@ include file="/WEB-INF/views/video/videoInfo.inc" %>
	</c:forEach>
	</ul>
</div>
</body>
</html>
