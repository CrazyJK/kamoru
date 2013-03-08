<dl>
	<dt onclick="fnViewVideoDetail('${video.opus}')">${video.title}</dt>
	<dd><span class="bgSpan" onclick="fnViewStudioDetail('${video.studio.name}')">${video.studio.name}</span></dd>
	<dd><c:forEach items="${video.actressList}" var="actress">
			<span class="actressSpan" onclick="fnViewActressDetail('${actress.name}')">${actress.name}</span>
		</c:forEach></dd>
	<dd><span>${video.etcInfo}</span></dd>
</dl>
