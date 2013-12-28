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
</style>
<script type="text/javascript">
$(document).ready(function(){
	$(window).bind("resize", resizeDivHeight);
	
	$("input[type=radio]").bind("click", function(){
		location.href= "?sort=" + $(this).val();
	}).css("display","none");

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
<s:message code="video.total"/> <s:message code="video.video"/> : ${fn:length(videoList)}

<input type="search" name="search" id="search" style="width:200px;" class="searchInput" placeHolder="<s:message code="video.search"/>" onkeyup="searchContent(this.value)"/>

<c:forEach items="${sorts}" var="s">
	<label class="item sort-item">
		<input type="radio" name="sort" value="${s}" ${s eq sort ? 'checked' : ''} style="display:none;">
		<span><s:message code="video.${s.desc}"/></span></label>
</c:forEach>

</div>

<div id="list_div" class="div-box" style="overflow:auto;">
	<table class="video-table" style="background-color:lightgray">
		<c:forEach items="${videoList}" var="video" varStatus="status">
		
		<tr <%-- class="${video.deletionCandidate ? 'deletionCandidate' : ''}" --%>>
			<td align="right">
				${status.count}</td>
			<td>
				<span class="label" onclick="fnViewStudioDetail('${video.studio.name}')">${video.studio.name}</span></td>
			<td onclick="fnViewVideoDetail('${video.opus}')">
				<span class="label">${video.opus}</span></td>
			<td>
				${video.title}</td>
			<td>
				<c:forEach items="${video.actressList}" var="actress">
					<span class="label" title="${actress.name}" onclick="fnViewActressDetail('${actress.name}')">${actress.name}</span>
				</c:forEach>
			</td>
			<td width="80px">
				${video.videoDate}</td>
			<td>
				<span class="label" title="<s:message code="video.playCount"/>">${video.playCount}</span></td>
			<td>
				<span class="label" title="<s:message code="video.rank"/>">${video.rank}</span></td>
			<td>
				<span class="label" title="<s:message code="video.Score"/>">${video.score}</span></td>
			<td width="45px" align="right">
				<fmt:formatNumber value="${video.length / ONE_GB}" pattern="#,##0.00G"/></td>
		</tr>
		
		</c:forEach>
	</table>
</div>

</body>
</html>
