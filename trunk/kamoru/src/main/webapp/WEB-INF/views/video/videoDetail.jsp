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
<script src="<c:url value="/resources/common.js" />"></script>
<script src="<c:url value="/resources/video/video-info-popup.js" />"></script>
<script type="text/javascript">
var context = '<spring:url value="/"/>';
</script>
</head>
<body>
<img src="<c:url value="/video/${video.opus}/cover" />" />
<%@ include file="/WEB-INF/views/video/videoInfo.inc" %>
<ul>
<li><span onclick="top.fnPlay('${video.opus}')">${video.videoFileListPath}</span>
<li><span>${video.coverFilePath}</span>
<li><span onclick="top.fnEditSubtitles('${video.opus}')" >${video.subtitlesFileListPath}</span>
<li><span>${video.etcFileListPath}</span>
<li><span title='${video.historyText}'>${video.historyFile}</span>
<li><span>${video.overviewFile}</span>
	<pre  onclick="opener.fnEditOverview('${video.opus}')"  >${video.overviewText}</pre>
</ul>
</body>
</html>
