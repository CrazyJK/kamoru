<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"      uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn"     uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html>
<html>
<head>
<title>Detail view [${video.opus}] ${video.title}</title>
<script type="text/javascript">
$(document).ready(function() {
	$("body").css("background-image","url('<c:url value="/video/${video.opus}/cover" />')");
});
</script>
</head>
<body>
<c:set var="opus" value="${video.opus}"/>
<dl>
	<dt><span class="label">${video.title}</span></dt>
	<dd><input type="range" id="Rank-${video.opus}" name="points" min="<s:eval expression="@videoProp['minRank']"/>" max="<s:eval expression="@videoProp['maxRank']"/>" value="${video.rank}" onmouseup="fnRank('${video.opus}')"/></dd>
	<dd><span class="label" onclick="fnViewStudioDetail('${video.studio.name}')">${video.studio.name}</span></dd>
	<dd><span class="label">${video.opus}</span></dd>
	<dd><span class="label">Download : ${video.videoDate}</span></dd>
	<dd><span class="label">Release : ${video.releaseDate}</span></dd>
	<dd><span class="label">ETC info : ${video.etcInfo}</span></dd>
	<dd><span class="label" onclick="opener.fnPlay('${video.opus}')">VIDEO : ${video.videoFileListPath}</span></dd>
	<dd><span class="label">COVER : ${video.coverFilePath}</span></dd>
	<dd><span class="label">WEBP : ${video.coverWebpFilePath}</span></dd>
	<dd><span class="label" onclick="opener.fnEditSubtitles('${video.opus}')">SMI : ${video.subtitlesFileListPath}</span></dd>
	<dd><span class="label">INFO : ${video.infoFilePath}</span></dd>
	<dd><div  class="label">ETC : ${video.etcFileListPath}</div></dd>
	<dd><pre  class="label">${video.historyText}</pre></dd>
	<dd><pre  class="label" onclick="opener.fnEditOverview('${video.opus}')" >${video.overviewText}</pre></dd>
	<dd><c:forEach items="${video.actressList}" var="actress">
			<span class="label actressSpan" onclick="fnViewActressDetail('${actress.name}')">${actress.name}</span>
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
