<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"      uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn"     uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="shortcut icon" type="image/x-icon" href="<c:url value="/resources/video/video-favicon.ico" />">
<title>Studio List</title>
<link rel="stylesheet" href="<c:url value="/resources/video/video.css" />" />
<!--[if lt IE 9]>
<script src="http://html5shiv.googlecode.com/svn/trunk/html5.js"></script>
<![endif]-->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.8.3/jquery.min.js"></script>
<script src="<c:url value="/resources/common.js" />"></script>
<script src="<c:url value="/resources/video/video-info-popup.js" />"></script>
<script type="text/javascript">
var context = '<spring:url value="/"/>';
</script>
</head>
<body>
<ul>
<c:forEach items="${studioList}" var="studio">
	<li id="<c:out value="${studio.name}"/>">
		<div class="opusBoxDiv" style="width:99%;">                   
			<%@ include file="/WEB-INF/views/video/studioInfo.inc" %>
		</div>
	</li>
</c:forEach>
</ul>
</body>
</html>
