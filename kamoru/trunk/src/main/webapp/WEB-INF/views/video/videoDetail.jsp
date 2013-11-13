<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"      uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn"     uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html>
<html>
<head>
<title>[${video.opus}] ${video.title}</title>
<script type="text/javascript">
$(document).ready(function() {
	$("body").css("background-image","url('<c:url value="/video/${video.opus}/cover" />')");
});
</script>
</head>
<body>
<c:set var="opus" value="${video.opus}"/>
<dl class="dl-detail">
	<dt><span class="label-large">${video.title}</span><br/>
		<input type="range" id="Rank-${video.opus}" name="points" min="<s:eval expression="@prop['minRank']"/>" max="<s:eval expression="@prop['maxRank']"/>" value="${video.rank}" onmouseup="fnRank('${video.opus}')"
			onchange="document.getElementById('Rank-${video.opus}-label').innerHTML = this.value;" />
		<em id="Rank-${video.opus}-label" class="rangeLabel">${video.rank}</em>
	</dt>
	<dd><span class="label-large" onclick="fnViewStudioDetail('${video.studio.name}')">${video.studio.name}</span></dd>
	<dd><span class="label-large">${video.opus}</span></dd>
	<dd><span class="label-large">Download : ${video.videoDate}</span></dd>
	<dd><span class="label-large">Release : ${video.releaseDate}</span></dd>
	<c:if test="${video.etcInfo ne ''}">
	<dd><span class="label-large">ETC info : ${video.etcInfo}</span></dd>
	</c:if>
	<dd><span class="label" onclick="opener.fnPlay('${video.opus}')">VIDEO : ${video.videoFileListPath}</span></dd>
	<dd><span class="label">COVER : ${video.coverFilePath}</span></dd>
	<dd><span class="label">WEBP : ${video.coverWebpFilePath}</span></dd>
	<c:if test="${video.subtitlesFileListPath ne ''}">
	<dd><span class="label" onclick="opener.fnEditSubtitles('${video.opus}')">SMI : ${video.subtitlesFileListPath}</span></dd>
	</c:if>
	<dd><span class="label">INFO : ${video.infoFilePath}</span></dd>
	<c:if test="${video.etcFileListPath ne ''}">
	<dd><div  class="label-large">ETC : ${video.etcFileListPath}</div></dd>
	</c:if>
	<c:if test="${video.historyText ne ''}">
	<dd><pre  class="label">${video.historyText}</pre></dd>
	</c:if>
	<c:if test="${video.overviewText ne ''}">
	<dd><pre  class="label-large" onclick="opener.fnEditOverview('${video.opus}')" >${video.overviewText}</pre></dd>
	</c:if>
	<dd>
		<c:forEach items="${video.actressList}" var="actress">
			<span class="label-large actressSpan" onclick="fnViewActressDetail('${actress.name}')">${actress.name} (${fn:length(actress.videoList)})</span>
			<div style="padding-left:60px;">
				<ul>
				<c:forEach items="${actress.videoList}" var="video">
					<c:choose>
						<c:when test="${video.opus != opus }">
						<%@ include file="/WEB-INF/views/video/videoInfo.inc" %>
						</c:when>
					</c:choose>
				</c:forEach>
				</ul>
			</div>
		</c:forEach>
	</dd>
</dl>
</body>
</html>
