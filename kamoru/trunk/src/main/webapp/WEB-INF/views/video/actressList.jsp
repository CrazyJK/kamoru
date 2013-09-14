<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"      uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn"     uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html>
<html>
<head>
<title><s:message code="video.actress"/> <s:message code="video.list"/></title>
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
<s:message code="video.total"/> <s:message code="video.actress"/> : ${fn:length(actressList)}

<input type="search" name="search" id="search" style="width:200px;" class="searchInput" placeHolder="<s:message code="video.search"/>" onkeyup="searchContent(this.value)"/>

</div>

<div id="list_div" class="div-box" style="overflow:auto;">
<table class="video-table" style="background-color:lightgray">
<c:forEach items="${actressList}" var="actress" varStatus="status">
	<tr><td class="nowrap">${status.count}</td>
		<td class="nowrap" onclick="fnViewActressDetail('${actress.name}')">${actress.name}</td>
		<td class="nowrap">
			<c:forEach items="${actress.videoList}" var="video">
				<span class="label" title="${video.title}" onclick="fnViewVideoDetail('${video.opus}')">${video.opus}</span>
			</c:forEach>
		</td>
	</tr>
</c:forEach>
</table>
</div>
</body>
</html>
