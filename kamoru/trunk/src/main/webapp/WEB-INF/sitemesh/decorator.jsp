<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"        uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s"        uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ page import="org.apache.commons.lang3.time.DateFormatUtils" %>
<%@ page import="org.springframework.web.servlet.support.RequestContext" %>
<%
String dateString = DateFormatUtils.format(new java.util.Date(), "yyyy-MM-dd");
%>
<%
String lang = "ko";
try {
	lang = new RequestContext(request).getLocale().getLanguage();
} catch(Exception e) {}
%>

<!DOCTYPE html>
<html lang="<%=lang%>">
<head>
<link rel="shortcut icon" type="image/x-icon" href="<c:url value="/resources/favicon_kamoru.ico"/>">
<title><sitemesh:write property='title'>Title goes here</sitemesh:write> - kAmOrU</title>
<link rel="stylesheet" href="<c:url value="/resources/deco.css" />" />
<style type="text/css">
body {
	/* background-image:url('<c:url value="/resources/img/orgrimmar_horde_territory.jpg"/>'); */
	/* background-image:url('<c:url value="http://fc03.deviantart.net/fs71/i/2013/249/5/1/freckly_by_tanyashatseva-d6l9s2t.jpg"/>'); */
	background-repeat: repeat;
	background-position: center center;
}
</style>
<!--[if lt IE 9]>
<script src="http://html5shiv.googlecode.com/svn/trunk/html5.js"></script>
<![endif]-->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1/jquery.min.js"></script>
<script type="text/javascript">
$(document).ready(function(){
	$(window).bind("resize", resizeSectionHeight);
	resizeSectionHeight();
	showNav();
	var bgImgUrl = "<c:url value="/image/random"/>?t=" + new Date().getTime();
	$("body").css("background-image", "url(" + bgImgUrl + ")");
});
function resizeSectionHeight() {
	var windowHeight = $(window).height();
	var headerHeight = $("#deco_header").outerHeight();
	var navHeight    = $("#deco_nav").outerHeight();
	var footerHeight = $("#deco_footer").outerHeight();
	var resizeSectionHeight = windowHeight - headerHeight - navHeight - footerHeight -20; 
	$("#deco_section").height(resizeSectionHeight);
}
function showNav() {
	var found = false;
	$("nav#deco_nav ul li a").each(function() {
		if ($(this).attr("href") == window.location.pathname) {
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
<body id="deco_body">

	<header id="deco_header">
		<h1 id="deco_h1">
			<a href="<c:url value="/home"/>">kAmOrU&hellip;</a> <sitemesh:write property='title'/>
			<span style='float:right;font-size:12px;text-decoration:none; margin:10px 0 0;'>
				<s:message code="default.hello"/>&nbsp;
				<security:authentication property="principal.username" />
				<a href="mailto:<s:message code="default.mail.addr"/>" title="<s:message code="default.mail.reply"/>">
					<img alt="<s:message code="default.mail.addr"/>" src="<c:url value="/resources/tag_crazyjk_gmail.png"/>">
				</a>
			</span>
			
		</h1>
	</header>
 
	<nav id="deco_nav">
		<%@ include file="/WEB-INF/views/menu.inc" %>
	</nav>

	<section id="deco_section">
		<div id="deco_section_wapper">
		<sitemesh:write property="body">Body goes here. Blah blah blah.</sitemesh:write>
		</div>
	</section>
	
	<footer id="deco_footer">
		Copyright &copy; <time datetime="<%=dateString %>"><%=dateString %></time> 
		kAmOrU. All rights reserved.
	</footer>

</body>
</html>