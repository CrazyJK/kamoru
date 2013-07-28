<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"      uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn"     uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html>
<html>
<head>
<title><spring:message code="text.studio"/> <spring:message code="text.list"/></title>
<script type="text/javascript">
$(document).ready(function(){
	$(window).bind("resize", resizeDivHeight);
	
	resizeDivHeight();
});

function resizeDivHeight() {
	var windowHeight = $(window).height();
	var header = $("#header_div").outerHeight();
	var calculatedDivHeight = windowHeight - header - 20 * 2; 
	$("#list_div").outerHeight(calculatedDivHeight);	
}
</script>
</head>
<body>

<div id="header_div" class="div-box">
<spring:message code="text.total"/> <spring:message code="text.studio"/> : ${fn:length(studioList)}
</div>

<div id="list_div" class="div-box" style="overflow:auto;">
<table class="video-table" style="background-color:lightgray">
<c:forEach items="${studioList}" var="studio">
	<tr>
		<td onclick="fnViewStudioDetail('${studio.name}')">${studio.name}</td>
		<td>
			<c:forEach items="${studio.videoList}" var="video">
				<span class="label" title="${video.title}" onclick="fnViewVideoDetail('${video.opus}')">${video.opus}</span>
			</c:forEach>
		</td>
	</tr>
</c:forEach>
</table>
</div>
</body>
</html>
