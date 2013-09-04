<dl style="background-image:url('<c:url value="/video/${video.opus}/cover" />'); background-size:300px 200px; height:200px;">
	<dt><span class="bgSpan" onclick="fnViewVideoDetail('${video.opus}')">${video.title}</span></dt>
	<dd><span class="bgSpan" onclick="fnViewStudioDetail('${video.studio.name}')">${video.studio.name}</span></dd>
	<dd><span class="bgSpan">${video.opus}</dd>
	<dd><c:forEach items="${video.actressList}" var="actress">
			<span class="bgSpan actressSpan" onclick="fnViewActressDetail('${actress.name}')">${actress.name}</span>
		</c:forEach></dd>
	<dd><span>${video.etcInfo}</span></dd>
</dl>
