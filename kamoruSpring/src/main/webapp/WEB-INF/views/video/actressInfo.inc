<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<dl>
	<dt><span class="bgSpan" onclick="fnViewActressDetail('${actress.name}')">${actress.name}</span></dt>
	<dd>Birth : ${actress.birth}</dd>
	<dd>Size : ${actress.bodySize}</dd>
	<dd>Debut : ${actress.debut}</dd>
	<dd>Studio(${fn:length(actress.studioList)}) :
		<c:forEach items="${actress.studioList}" var="studio">
			<span class="bgSpan" onclick="fnViewStudioDetail('${studio.name}')">${studio.name}(${fn:length(studio.videoList)})</span>
		</c:forEach>
	</dd>
	<dd>Video(${fn:length(actress.videoList)}) :
		<c:forEach items="${actress.videoList}" var="video">
			<span class="bgSpan" title="${video.title}" onclick="fnViewVideoDetail('${video.opus}')">${video.opus}</span>
		</c:forEach>
	</dd>
</dl>
