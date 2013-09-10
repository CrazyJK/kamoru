<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"      uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn"     uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html>
<html>
<head>
<title><spring:message code="text.video"/> <spring:message code="text.list"/></title>
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
</script>
</head>
<body>
<div id="header_div" class="div-box">
<spring:message code="text.total"/> <spring:message code="text.video"/> : ${fn:length(videoList)}

<input type="search" name="search" id="search" style="width:200px;" class="searchInput" placeHolder="Search" onkeyup="searchContent(this.value)"/>

</div>

<div id="list_div" class="div-box" style="overflow:auto;">
<table class="video-table" style="background-color:lightgray">
<c:forEach items="${videoList}" var="video" varStatus="status">
	<tr><td class="nowrap">${status.count}</td>
		<td class="nowrap" onclick="fnViewVideoDetail('${video.opus}')">${video.title}</td>
		<td class="nowrap"><span class="label" onclick="fnViewStudioDetail('${video.studio.name}')">${video.studio.name}</span></td>
		<td class="nowrap">
			<c:forEach items="${video.actressList}" var="actress">
				<span class="label" title="${actress.name}" onclick="fnViewActressDetail('${actress.name}')">${actress.name}</span>
			</c:forEach>
		</td>
	</tr>
</c:forEach>
</table>
</div>
</body>
</html>
