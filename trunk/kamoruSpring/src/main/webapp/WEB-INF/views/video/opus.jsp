<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Detail view [${video.opus}] ${video.title} - AV Worlde</title>
<link rel="stylesheet" href="<c:url value="/resources/video.css" />" />
<style type="text/css">
.overviewTxt {width:100%; height:275px;}
</style>
<!--[if lt IE 9]>
<script src="http://html5shiv.googlecode.com/svn/trunk/html5.js"></script>
<![endif]-->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.8.3/jquery.min.js"></script>
<script src="<c:url value="/resources/video.js" />" type="text/javascript"></script>
</head>
<body>
	<img src="<c:url value="/video/${video.opus}/cover" />" />
<dl>
	<dt>${video.title}
		<c:forEach items="${video.actressList}" var="actress">
			<span class="actressSpan" onclick="opener.fnActressSearch('${actress}')">${actress}</span>
		</c:forEach></dt>
	<dd><span>${video.etcInfo}</span></dd>
	<dd><span onclick="opener.fnPlay('${video.opus}')"          >${video.videoFileListPath}</span></dd>
	<dd><span onclick="opener.fnImageView('${video.opus}')"     >${video.coverFilePath}</span></dd>
	<dd><span onclick="opener.fnEditSubtitles('${video.opus}')" >${video.subtitlesFileListPath}</span></dd>
	<dd><span>${video.etcFileListPath}</span></dd>
	<dd><span title='${video.historyText}'>${video.historyFile}</span></dd>
	<dd><span>${video.overviewFile}</span></dd>
	<dd><pre  onclick="opener.fnEditOverview('${video.opus}')"  >${video.overviewText}</pre></dd>
</dl>
</body>
</html>
