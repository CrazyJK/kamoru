<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="org.apache.commons.lang.time.DateFormatUtils" %>
<%
String dateString = DateFormatUtils.format(new java.util.Date(), "yyyy-MM-dd");
%>
<!DOCTYPE html>
<html>
<head>
<title><sitemesh:write property='title'>Title goes here</sitemesh:write> - kAmOrU</title>
<link rel="stylesheet" href="/kamoru/css/deco.css" />
<!--[if lt IE 9]>
<script src="http://html5shiv.googlecode.com/svn/trunk/html5.js"></script>
<![endif]-->
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

	<section id="deco_section">
		<sitemesh:write property="body">Body goes here. Blah blah blah.</sitemesh:write>
	</section>
	
	<footer id="deco_footer">
		Copyright &copy; <time datetime="<%=dateString %>"><%=dateString %></time> 
		kAmOrU. All rights reserved.
	</footer>

</body>
</html>