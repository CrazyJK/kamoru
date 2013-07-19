<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"      uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn"     uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="s"      uri="http://www.springframework.org/tags" %>
<!DOCTYPE html>
<html>
<head>
<title>${actress.name}</title>
</head>
<body>

<div>
	<c:forEach items="${actress.webImage}" var="url">
		<img src="${url}" width="185px" onclick="popupImage('${url}')"/>
	</c:forEach>
</div>

<dl>
	<dt><span class="label">${actress.name}</span></dt>
	<dd><span class="label">
			Birth : ${actress.birth} 
			Size : ${actress.bodySize}
			Debut : ${actress.debut}</span></dd>
	<dd><span class="label">Studio(${fn:length(actress.studioList)})</span>
		<c:forEach items="${actress.studioList}" var="studio">
			<span class="label" onclick="fnViewStudioDetail('${studio.name}')">${studio.name}(${fn:length(studio.videoList)})</span>
		</c:forEach>
	</dd>
	<dd><span class="label">Video(${fn:length(actress.videoList)})</span></dd>
</dl>

<ul>
	<c:forEach items="${actress.videoList}" var="video">
		<%@ include file="/WEB-INF/views/video/videoInfo.inc" %>
	</c:forEach>
</ul>

</body>
</html>
