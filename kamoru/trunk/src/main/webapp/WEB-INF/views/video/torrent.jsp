<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn"  uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" 	uri="http://www.springframework.org/tags" %>
<c:set var="ONE_GB" value="${1024*1024*1024}"/>
<!DOCTYPE html>
<html>
<head>
<title><s:message code="video.video"/> <s:message code="video.list"/></title>
<style type="text/css">
.selected {
	color: blue;
}
.fullname {
	width:500px; border:0; font-size: 11px; 
}
.fullname:focus {
	background-color:yellow;
}
.mark {
	background-color:orange;
	background: url('<c:url value="/resources/yes_check.png"/>');
	background-size: 30px 25px;
	background-repeat: no-repeat;
}
</style>
<script type="text/javascript">
$(document).ready(function(){});

/**
 * 비디오 확인을 기억하기 위해 css class를 변경한다.
 */
function fnMarkChoice(opus) {
	$("#check-" + opus).addClass("mark");
	//$("#check-" + opus).hide();
	//$("#check-" + opus + " > *").attr("disabled",true);
}
</script>
</head>
<body>

<div id="header_div" class="div-box">
	<s:message code="video.total"/> <s:message code="video.video"/> : ${fn:length(videoList)}
	<input type="search" name="search" id="search" style="width:200px;" 
		class="searchInput" placeHolder="<s:message code="video.search"/>" 
		onkeyup="searchContent(this.value)"/>
</div>

<div id="content_div" class="div-box" style="overflow:auto;">
	<table class="video-table" style="background-color:lightgray">
		<c:forEach items="${videoList}" var="video" varStatus="status">
		<tr id="check-${video.opus}">
			<td align="right">${status.count}</td>
			<td><span class="label">
					<a onclick="fnMarkChoice('${video.opus}')" 
						href="<s:eval expression="@prop['video.torrent.url']"/>${video.opus}" 
						target="_blank" class="link">Get torrent</a></span></td>
			<td><input type="text" value="${video.fullname}" class="fullname" readonly/></td>
			<td><span class="label" id="title-${video.opus}" 
					onclick="fnViewVideoDetail('${video.opus}')">${video.opus}</span></td>
			<td>
				<c:forEach items="${video.videoCandidates}" var="candidate">
				<form method="post" target="ifrm" action="<c:url value="/video/${video.opus}/confirmCandidate"/>">
					<input type="submit" value="${candidate.name}" onclick="fnMarkChoice('${video.opus}')"/>
					<input type="hidden" name="path" value="${candidate.absolutePath}"/>
				</form>
				</c:forEach>
			</td>
		</tr>
		</c:forEach>
	</table>
</div>

</body>
</html>
