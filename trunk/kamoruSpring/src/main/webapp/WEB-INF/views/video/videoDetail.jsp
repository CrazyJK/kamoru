<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"      uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn"     uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Detail view [${video.opus}] ${video.title} - AV Worlde</title>
<link rel="stylesheet" href="<c:url value="/resources/video.css" />" />
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
<span onclick="top.fnPlay('${video.opus}')">${video.videoFileListPath}</span>
<span>${video.coverFilePath}</span>
<span onclick="top.fnEditSubtitles('${video.opus}')" >${video.subtitlesFileListPath}</span>
<span>${video.etcFileListPath}</span>
<span title='${video.historyText}'>${video.historyFile}</span>
<span>${video.overviewFile}</span>
<pre  onclick="opener.fnEditOverview('${video.opus}')"  >${video.overviewText}</pre>

</body>
</html>
