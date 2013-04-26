<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<spring:url value="/"      	   var="alice"/>
<spring:url value="/resources" var="resources"/>
<spring:url value="/blueprint" var="blueprint"/>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="shortcut icon" type="image/x-icon" href="${resources}/alice.ico">
<title>
	<sitemesh:write property='title'>Title goes here</sitemesh:write> - kAmOrU
</title>
<!--[if lt IE 8]><link rel="stylesheet" href="<spring:url value="/blueprint"/>/blueprint/ie.css" type="text/css" media="screen, projection"><![endif]-->
<!--[if lt IE 9]><script src="http://html5shiv.googlecode.com/svn/trunk/html5.js"></script><![endif]-->
<link rel="stylesheet" href="${blueprint}/screen.css" type="text/css" media="screen, projection">
<link rel="stylesheet" href="${blueprint}/print.css"  type="text/css" media="print">
<link rel="stylesheet" href="${resources}/sitemesh/deco.css" />

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
	var resizeSectionHeight = windowHeight - headerHeight - navHeight - footerHeight - 20; 
	$("#deco_section").height(resizeSectionHeight);
}
</script>
<sitemesh:write property="head" />
</head>
<body id="deco_body">

	<header id="deco_header">
		<h1 id="deco_header_h1">
			<a href="${auth}">Alice&hellip;</a> 
			<span class="deco_header_h1_left_iamge_span">
				<img alt="Alice logo" src="${resources}/sitemesh/Logo.jpg" height="33px">
			</span>
			<span class="deco_header_h1_right_span">
				<a href="${alice}j_spring_security_logout">Logout</a>
			</span>
			<span class="deco_header_h1_right_span">
				${currentUser.username}
			</span>
		</h1>
	</header>
 
	<nav id="deco_nav">
		<div id="deco_nav_div">
			<a href="${alice}auth" >Auth</a>
			<a href="${alice}csr" >CSR</a>
		</div>
	</nav>

	<section id="deco_section">
		<sitemesh:write property="body">Body goes here. Blah blah blah.</sitemesh:write>
	</section>
	
	<footer id="deco_footer">
		Copyright &copy; 2013 Alice. All rights reserved.
	</footer>

</body>
</html>