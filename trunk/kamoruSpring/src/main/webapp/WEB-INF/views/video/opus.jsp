<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Detail view [${video.opus}] - AV Worlde</title>
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
${video.title}
<br/>
<img src="<c:url value="/video/${video.opus}/cover" />" />
<br/>
Actress - 
<c:forEach items="${video.actressList}" var="actress">
	<span class="actressSpan" onclick="fnActressSearch('${actress}')">${actress}</span>
</c:forEach>
<br/>
<span class="" onclick="fnPlay('${video.opus}')"          >Video - ${video.videoPath}</span>
<br/>
<span class="" onclick="fnImageView('${video.opus}')"     >Cover - ${video.cover}</span>
<br/>
<span class="" onclick="fnEditSubtitles('${video.opus}')" >smi -${video.subtitles}</span>
<br/>
<span class="" onclick="fnEditOverview('${video.opus}')"  >Overview - ${video.overviewTxt}</span>

<form name="actionFrm" target="ifrm">
	<input type="hidden" name="opus" id="selectedOpus">
	<input type="hidden" name="mode" id="selectedMode">
</form>
<iframe name="ifrm" style="display:none; width:100%;"></iframe>

</body>
</html>
