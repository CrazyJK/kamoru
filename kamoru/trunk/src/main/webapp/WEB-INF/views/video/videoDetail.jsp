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
<dl>
	<dt><span class="label-large">${video.title}</span><br/>
		<input type="range" id="Rank-${video.opus}" name="points" min="<s:eval expression="@videoProp['minRank']"/>" max="<s:eval expression="@videoProp['maxRank']"/>" value="${video.rank}" onmouseup="fnRank('${video.opus}')"/></dt>
	<dd><span class="label-large" onclick="fnViewStudioDetail('${video.studio.name}')">${video.studio.name}</span></dd>
	<dd><span class="label-large">${video.opus}</span></dd>
	<dd><span class="label-large">Download : ${video.videoDate}</span></dd>
	<dd><span class="label-large">Release : ${video.releaseDate}</span></dd>
	<dd><span class="label-large">ETC info : ${video.etcInfo}</span></dd>
	<dd><span class="label-large" onclick="opener.fnPlay('${video.opus}')">VIDEO : ${video.videoFileListPath}</span></dd>
	<dd><span class="label-large">COVER : ${video.coverFilePath}</span></dd>
	<dd><span class="label-large">WEBP : ${video.coverWebpFilePath}</span></dd>
	<dd><span class="label-large" onclick="opener.fnEditSubtitles('${video.opus}')">SMI : ${video.subtitlesFileListPath}</span></dd>
	<dd><span class="label-large">INFO : ${video.infoFilePath}</span></dd>
	<dd><div  class="label-large">ETC : ${video.etcFileListPath}</div></dd>
	<dd><pre  class="label-large">${video.historyText}</pre></dd>
	<dd><pre  class="label-large" onclick="opener.fnEditOverview('${video.opus}')" >${video.overviewText}</pre></dd>
	<dd><c:forEach items="${video.actressList}" var="actress">
			<span class="label-large actressSpan" onclick="fnViewActressDetail('${actress.name}')">${actress.name} (${fn:length(actress.videoList)})</span>
			<ul>
			<c:forEach items="${actress.videoList}" var="video">
				<c:choose>
				<c:when test="${video.opus != opus }">
				<%@ include file="/WEB-INF/views/video/videoInfo.inc" %>
				</c:when>
				</c:choose>
			</c:forEach>
			</ul>
		</c:forEach></dd>
</dl>
</body>
</html>
