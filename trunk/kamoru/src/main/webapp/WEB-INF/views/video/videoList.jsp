<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"      uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn"     uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html>
<html>
<head>
<title><spring:message code="text.video"/> <spring:message code="text.list"/></title>
</head>
<body>
<spring:message code="text.total"/> <spring:message code="text.video"/> : ${fn:length(videoList)}
<table class="video-table" style="background-color:lightgray">
<c:forEach items="${videoList}" var="video">
	<tr>
		<td onclick="fnViewVideoDetail('${video.opus}')">${video.title}</td>
		<td><span class="label" onclick="fnViewStudioDetail('${video.studio.name}')">${video.studio.name}</span></td>
		<td>
			<c:forEach items="${video.actressList}" var="actress">
				<span class="label" title="${actress.name}" onclick="fnViewActressDetail('${actress.name}')">${actress.name}</span>
			</c:forEach>
		</td>
	</tr>
</c:forEach>
</table>
</body>
</html>
