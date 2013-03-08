<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<dl>
	<dt onclick="fnViewStudioDetail('${studio.name}')">${studio.name}</dt>
	<dd>Homepage : ${studio.homepage}</dd>
	<dd>Company : ${studio.companyName}</dd>
	<dd>Actress(${fn:length(studio.actressList)}) :
		<c:forEach items="${studio.actressList}" var="actress">
			<span class="bgSpan" onclick="fnViewActressDetail('${actress.name}')">${actress.name}(${fn:length(actress.videoList)})</span>
		</c:forEach>
	</dd>
	<dd>Video(${fn:length(studio.videoList)}) : 
		<c:forEach items="${studio.videoList}" var="video">
			<span class="bgSpan" title="${video.title} ${video.actressList}" onclick="fnViewVideoDetail('${video.opus}')">${video.opus}</span>
		</c:forEach>
	</dd>
</dl>
