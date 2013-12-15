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
	width:600px; border:0; font-size: 11px; 
}
.fullname:focus {
	background-color:yellow;
}
</style>
<script type="text/javascript">
$(document).ready(function(){
	$(window).bind("resize", resizeDivHeight);

	resizeDivHeight();
	
});

function resizeDivHeight() {
	var windowHeight = $(window).height();
	var header = $("#header_div").outerHeight();
	var calculatedDivHeight = windowHeight - header - 20 * 2; 
	$("#list_div").outerHeight(calculatedDivHeight);	
}
function checkboxTorrent(opus) {
	$("#checkbox-" + opus).click();
}
</script>
</head>
<body>
<div id="header_div" class="div-box">
<s:message code="video.total"/> <s:message code="video.video"/> : ${fn:length(videoList)}

<input type="search" name="search" id="search" style="width:200px;" class="searchInput" placeHolder="<s:message code="video.search"/>" onkeyup="searchContent(this.value)"/>

</div>

<div id="list_div" class="div-box" style="overflow:auto;">
	<table class="video-table" style="background-color:lightgray">
		<c:forEach items="${videoList}" var="video" varStatus="status">
		<tr>
			<td align="right">${status.count}</td>
			<td><span class="label" id="title-${video.opus}" onclick="fnViewVideoDetail('${video.opus}')">${video.opus}</span></td>
			<td><span class="label">
					<a onclick="checkboxTorrent('${video.opus}')" href="<s:eval expression="@prop['torrentURL']"/>${video.opus}" target="_blank" class="link">Get torrent</a></span>
				<input type="checkbox" id="checkbox-${video.opus}"/></td>
			<td><input type="text" value="${video.fullname}" class="fullname" readonly/></td>
		</tr>
		</c:forEach>
	</table>
</div>

</body>
</html>
