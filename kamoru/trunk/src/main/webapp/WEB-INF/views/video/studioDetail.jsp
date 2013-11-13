<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn"  uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="s" 	uri="http://www.springframework.org/tags" %>
<!DOCTYPE html>
<html>
<head>
<title>${studio.name}</title>
</head>
<body>

<form action="<s:url value="/video/studio/${studio.name}"/>" method="post">
<input type="hidden" name="_method" id="hiddenHttpMethod" value="put"/>
<dl class="dl-detail">
	<dt class="label-large center">
		<span>${studio.name}</span>
	</dt>
	<dd>
		<span class="label-title">Homepage : <input class="studioInfo" type="text" name="homepage" value="${studio.homepage}" /></span>
		<span class="label-title">Company : <input class="studioInfo" type="text" name="companyname" value="${studio.companyName}" /></span>
		<input class="button" type="submit" value="Save"/>	
	</dd>
	<dd>
		<span class="label-title">Actress(${fn:length(studio.actressList)})</span>
	</dd>
	<dd>
		<div style="padding-left:60px;">
		<c:forEach items="${studio.actressList}" var="actress">
			<span class="label" onclick="fnViewActressDetail('${actress.name}')">${actress.name}(${fn:length(actress.videoList)})</span>
		</c:forEach>
		</div>
	</dd>
	<dd><span class="label-title">Video(${fn:length(studio.videoList)})</span></dd>
</dl>
</form>

<div style="padding-left:60px;">
	<ul>
		<c:forEach items="${studio.videoList}" var="video">
			<%@ include file="/WEB-INF/views/video/videoInfo.inc" %>
		</c:forEach>
	</ul>
</div>

</body>
</html>
