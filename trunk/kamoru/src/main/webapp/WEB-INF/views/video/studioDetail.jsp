<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"      uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn"     uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html>
<html>
<head>
<title>${studio.name} Info</title>
</head>
<body>

<dl>
	<dt><span class="label">${studio.name}</span></dt>
	<dd><span class="label">Homepage : ${studio.homepage}</span></dd>
	<dd><span class="label">Company : ${studio.companyName}</span></dd>
	<dd><span class="label">Actress(${fn:length(studio.actressList)})</span>
		<c:forEach items="${studio.actressList}" var="actress">
			<span class="label" onclick="fnViewActressDetail('${actress.name}')">${actress.name}(${fn:length(actress.videoList)})</span>
		</c:forEach>
	</dd>
	<dd><span class="label">Video(${fn:length(studio.videoList)})</span></dd>
</dl>
<ul>
	<c:forEach items="${studio.videoList}" var="video">
		<%@ include file="/WEB-INF/views/video/videoInfo.inc" %>
	</c:forEach>
</ul>
</body>
</html>
