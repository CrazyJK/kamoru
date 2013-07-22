<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html>
<html>
<head>
<title><sitemesh:write property='title'>Title goes here</sitemesh:write> - kAmOrU</title>
<meta charset="UTF-8" />
<link rel="shortcut icon" type="image/x-icon" href="<c:url value="/resources/video/video-favicon.ico" />">
<link rel="stylesheet" href="<c:url value="/resources/video/video-deco.css"   />" />
<link rel="stylesheet" href="<c:url value="/resources/video/video-main.css"   />" />
<link rel="stylesheet" href="<c:url value="/resources/video/video-search.css" />" />
<!--[if lt IE 9]><script src="http://html5shiv.googlecode.com/svn/trunk/html5.js"></script><![endif]-->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.8.3/jquery.min.js"></script>
<script src="<c:url value="/resources/common.js"     />" type="text/javascript"></script>
<script src="<c:url value="/resources/video/video.js"/>" type="text/javascript"></script>
<script type="text/javascript">
var context = '<c:url value="/"/>';
var locationPathname = window.location.pathname;

$(document).ready(function() {
	// Add class : elements in onclick attribute add class
	//$("*[onclick]").addClass("onclick");

	//set rank color
 	$('input[type="range"]').each(function() {
 		fnRankColor($(this));
 	});

	// Add listener : if labal click, empty input text value
	$("label").bind("click", function(){
		var id = $(this).attr("for");
		$("#" + id).val("");
	});

 	showNav();
});

/**
 * 현재 url비교하여 메뉴 선택 효과를 주고, 메뉴 이외의 창에서는 nav를 보이지 않게
 */
function showNav() {
	var found = false;
	$("nav#deco_nav ul li a").each(function() {
		if ($(this).attr("href") == locationPathname) {
			$(this).parent().addClass("menu-selected");
			found = true;
		}
		else {
			$(this).parent().addClass("menu");
		}
	});
	if(!found)
		$("#deco_nav").css("display", "none");
}
</script>

<sitemesh:write property="head" />

</head>
<body>
 
<nav id="deco_nav">
<ul>
	<li><a href="<c:url value="/video"/>"><spring:message code="text.main"/></a>
	<li><a href="<c:url value="/video/search"/>"><spring:message code="text.search"/></a>
	<li><a href="<c:url value="/video/history"/>"><spring:message code="text.history"/></a>
	<li><a href="<c:url value="/video/list"/>"><spring:message code="text.video"/></a>
	<li><a href="<c:url value="/video/actress"/>"><spring:message code="text.actress"/></a>
	<li><a href="<c:url value="/video/studio"/>"><spring:message code="text.studio"/></a>
	<li><a href="<c:url value="/"/>"><spring:message code="text.home"/></a>
</ul>
</nav>

<sitemesh:write property="body">Body goes here. Blah blah blah.</sitemesh:write>
	
<form name="actionFrm" target="ifrm" method="post"><input type="hidden" name="_method" id="hiddenHttpMethod"/></form>
<iframe id="actionIframe" name="ifrm" style="display:none; width:100%;"></iframe>
	
</body>
</html>