<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"      uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn"     uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html>
<html>
<head>
<title><spring:message code="text.studio"/> <spring:message code="text.list"/></title>
</head>
<body>
<spring:message code="text.total"/> <spring:message code="text.studio"/> : ${fn:length(studioList)}
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
</body>
</html>
