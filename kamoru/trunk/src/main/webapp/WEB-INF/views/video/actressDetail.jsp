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

<form action="<s:url value="/video/actress/${actress.name}"/>" method="post">
<input type="hidden" name="_method" id="hiddenHttpMethod" value="put"/>
<input type="hidden" name="name" value="${actress.name}"/>
<dl>
	<dt class="label-large center"><span>${actress.name}</span>
		<input class="actressInfo" type="text" name="localname" value="${actress.localName}" />
	</dt>
	<dd>
	<c:forEach items="${actress.webImage}" var="url">
		<img src="${url}" width="185px" onclick="popupImage('${url}')"/>
	</c:forEach>
	</dd>
	<dd><span class="label-title">Birth : <input class="actressInfo" type="text" name="birth" value="${actress.birth}" /> </span>
		<span class="label-title">Size :  <input class="actressInfo" type="text" name="bodySize" value="${actress.bodySize}" /></span>
		<span class="label-title">Height :<input class="actressInfo" type="text" name="height" value="${actress.height}" /></span>
		<span class="label-title">Debut : <input class="actressInfo" type="text" name="debut" value="${actress.debut}" /></span>
		<input class="button" type="submit" value="Save"/>	
	</dd>
	<dd><span class="label-title">Studio(${fn:length(actress.studioList)})</span>
		<c:forEach items="${actress.studioList}" var="studio">
			<span class="label" onclick="fnViewStudioDetail('${studio.name}')">${studio.name}(${fn:length(studio.videoList)})</span>
		</c:forEach>
	</dd>
	<dd><span class="label-title">Video(${fn:length(actress.videoList)})</span></dd>
</dl>
</form>

<ul>
	<c:forEach items="${actress.videoList}" var="video">
		<%@ include file="/WEB-INF/views/video/videoInfo.inc" %>
	</c:forEach>
</ul>

</body>
</html>