<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn"  uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s"   uri="http://www.springframework.org/tags" %>
<!DOCTYPE html>
<html lang="${locale}">
<head>
<title><s:message code="video.manager"/></title>
<style type="text/css">
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
	$("#contentDiv").outerHeight(calculatedDivHeight);	
}
</script>
</head>
<body>

<div id="header_div" class="div-box">
	<s:message code="video.manager"/>
</div>

<div id="contentDiv" class="div-box" style="overflow:auto; text-align:left;">

<ul>
	<li><a href="<c:url value="/video/manager/move"/>" target="ifrm"><s:message code="video.mng.move"/></a>
	<li><a href="<c:url value="/video/manager/rank"/>" target="ifrm"><s:message code="video.mng.rank"/></a>
	<li><a href="<c:url value="/video/manager/score"/>" target="ifrm"><s:message code="video.mng.score"/></a>
</ul>








</div>

</body>
</html>