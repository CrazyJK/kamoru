<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"      uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn"     uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Detail view [${video.opus}] ${video.title} - Video World</title>
<link rel="shortcut icon" type="image/x-icon" href="<c:url value="/resources/video/video-favicon.ico" />">
<link rel="stylesheet" href="<c:url value="/resources/video/video.css" />" />
<!--[if lt IE 9]>
<script src="http://html5shiv.googlecode.com/svn/trunk/html5.js"></script>
<![endif]-->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.8.3/jquery.min.js"></script>
<script type="text/javascript">
var context = '<spring:url value="/"/>';
var bgImageCount = 0;
$(document).ready(function() {
	// Add class : elements in onclick attribute add class
	$("*[onclick]").addClass("onclick");
	// set rank color
	fnRankColor($("#Rank-${video.opus}"));
});
</script>
<script src="<c:url value="/resources/video/video.js" />" type="text/javascript"></script>
<script src="<c:url value="/resources/common.js" />"></script>
<script src="<c:url value="/resources/image-popup.js" />"></script>
</head>
<body style="background-image:url('<c:url value="/video/${video.opus}/cover" />');">
<dl>
	<dt><span class="label">${video.title}</span></dt>
	<dd><input type="range" id="Rank-${video.opus}" name="points" min="-5" max="5" value="${video.rank}" onmouseup="fnRank('${video.opus}')"/></dd>
	<dd><span class="label" onclick="fnViewStudioDetail('${video.studio.name}')">${video.studio.name}</span></dd>
	<dd><span class="label">${video.opus}</span></dd>
	<dd><c:forEach items="${video.actressList}" var="actress">
			<span class="label actressSpan" onclick="fnViewActressDetail('${actress.name}')">${actress.name}</span>
			<ul>
			<c:forEach items="${actress.videoList}" var="video">
				<%@ include file="/WEB-INF/views/video/videoInfo.inc" %>
			</c:forEach>
			</ul>
		</c:forEach></dd>
	<dd><span class="label">Download DATE : ${video.videoDate}</span></dd>
	<dd><span class="label">Release Date : ${video.etcInfo}</span></dd>
	<dd><span class="label" onclick="opener.fnPlay('${video.opus}')">VIDEO : ${video.videoFileListPath}</span></dd>
	<dd><span class="label">COVER : ${video.coverFilePath}</span></dd>
	<dd><span class="label">WEBP : ${video.coverWebpFilePath}</span></dd>
	<dd><span class="label" onclick="opener.fnEditSubtitles('${video.opus}')">SMI : ${video.subtitlesFileListPath}</span></dd>
	<dd><span class="label">INFOFILE : ${video.infoFilePath}</span></dd>
	<dd><div  class="label">ETCFILE : ${video.etcFileListPath}</div></dd>
	<dd><pre  class="label">${video.historyText}</pre></dd>
	<dd><pre  class="label" onclick="opener.fnEditOverview('${video.opus}')" >${video.overviewText}</pre></dd>
</dl>
</body>
</html>
