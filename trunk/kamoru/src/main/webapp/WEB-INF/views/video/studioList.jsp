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
<script src="<c:url value="/resources/video/video.js" />"></script>
<script src="<c:url value="/resources/image-popup.js" />"></script>
<script type="text/javascript">
var context = '<spring:url value="/"/>';
</script>
</head>
<body>
Total actress : ${fn:length(studioList)}
<table class="video-table" style="background-color:lightgray">
<c:forEach items="${studioList}" var="studio">
	<tr>
		<td style="white-space:nowrap;" onclick="fnViewStudioDetail('${studio.name}')">${studio.name}</td>
		<td style="white-space:nowrap">
			<c:forEach items="${studio.videoList}" var="video">
				<span class="label" title="${video.title}" onclick="fnViewVideoDetail('${video.opus}')">${video.opus}</span>
			</c:forEach>
		</td>
	</tr>
</c:forEach>
</table>
</body>
</html>
