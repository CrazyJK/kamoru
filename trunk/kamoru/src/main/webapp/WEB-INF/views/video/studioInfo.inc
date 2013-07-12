<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="fn"     uri="http://java.sun.com/jsp/jstl/functions" %>

<dl>
	<dt class="label" onclick="fnViewStudioDetail('${studio.name}')">${studio.name}</dt>
	<dd class="label">Homepage : ${studio.homepage}</dd>
	<dd class="label">Company : ${studio.companyName}</dd>
	<dd class="label">Actress(${fn:length(studio.actressList)}) :
		<c:forEach items="${studio.actressList}" var="actress">
			<span class="label" onclick="fnViewActressDetail('${actress.name}')">${actress.name}(${fn:length(actress.videoList)})</span>
		</c:forEach>
	</dd>
	<dd class="label">Video(${fn:length(studio.videoList)}) : 
		<c:forEach items="${studio.videoList}" var="video">
			<span class="label" title="${video.title} ${video.actressList}" onclick="fnViewVideoDetail('${video.opus}')">${video.opus}</span>
		</c:forEach>
	</dd>
</dl>
