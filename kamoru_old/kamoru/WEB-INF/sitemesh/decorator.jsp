<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="org.apache.commons.lang3.time.DateFormatUtils" %>
<%
String dateString = DateFormatUtils.format(new java.util.Date(), "yyyy-MM-dd");
%>
<!DOCTYPE html>
<html>
<head>
<link rel="shortcut icon" type="image/x-icon" href="favicon_kamoru.ico">
<title><sitemesh:write property='title'>Title goes here</sitemesh:write> - kAmOrU</title>
<link rel="stylesheet" href="/kamoru/css/deco.css" />
<!--[if lt IE 9]>
<script src="http://html5shiv.googlecode.com/svn/trunk/html5.js"></script>
<![endif]-->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.8.3/jquery.min.js"></script>
<script type="text/javascript">
$(document).ready(function(){
	$(window).bind("resize", resizeSectionHeight);
	resizeSectionHeight();
});
function resizeSectionHeight() {
	var windowHeight = $(window).height();
	var headerHeight = $("#deco_header").outerHeight();
	var navHeight    = $("#deco_nav").outerHeight();
	var footerHeight = $("#deco_footer").outerHeight();
	var resizeSectionHeight = windowHeight - headerHeight - navHeight - footerHeight - 16 - 20 - 20; 
	$("#deco_section").height(resizeSectionHeight);
}
</script>
<sitemesh:write property="head" />
</head>
<body id="deco_body">

	<header id="deco_header">
		<h1 id="deco_h1">
			<a href="/kamoru">kAmOrU&hellip;</a> 
			<span style='float:right;font-size:10px;text-decoration:none'>
			<img alt="kamoru.mail" src="/kamoru/img/kamoru_gmail.png">
			</span>
		</h1>
	</header>

	<nav id="deco_nav">
		<jsp:include page="/list.html"></jsp:include>
	</nav>

	<section id="deco_section">
		<sitemesh:write property="body">Body goes here. Blah blah blah.</sitemesh:write>
	</section>
	
	<footer id="deco_footer">
		Copyright &copy; <time datetime="<%=dateString %>"><%=dateString %></time> 
		kAmOrU. All rights reserved.
	</footer>

</body>
</html>