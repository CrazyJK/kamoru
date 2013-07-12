<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="fn"     uri="http://java.sun.com/jsp/jstl/functions" %>

<dl>
	<dt><span class="label" onclick="fnViewActressDetail('${actress.name}')">${actress.name}</span></dt>
	<dd class="label">Birth : ${actress.birth}</dd>
	<dd class="label">Size : ${actress.bodySize}</dd>
	<dd class="label">Debut : ${actress.debut}</dd>
	<dd class="label">Studio(${fn:length(actress.studioList)}) :
		<c:forEach items="${actress.studioList}" var="studio">
			<span class="label" onclick="fnViewStudioDetail('${studio.name}')">${studio.name}(${fn:length(studio.videoList)})</span>
		</c:forEach>
	</dd>
	<dd class="label">Video(${fn:length(actress.videoList)}) :
		<c:forEach items="${actress.videoList}" var="video">
			<span class="label" title="${video.title}" onclick="fnViewVideoDetail('${video.opus}')">${video.opus}</span>
		</c:forEach>
	</dd>
</dl>
