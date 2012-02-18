<%@ page import="kamoru.frmwk.util.DateUtils" %>
<%
String dateString = DateUtils.getDateString(DateUtils.DEFAULT_DATE_PATTERN);
%>
<!DOCTYPE html>
<html>
<head>
<title>kAmOrU - <sitemesh:write property='title'>Title goes here</sitemesh:write></title>
<!-- <link rel="stylesheet" href="css/kamoru.css" /> -->
<style type="text/css">
body#deco_body {background:url(/kamoru/img/green3.png); margin:0px auto; width:950px; font-family:맑은 고딕;}
body>header#deco_header {text-align:center;}
body>header>hgroup#deco_hgroup {}
body>header>hgroup>h1#deco_h1 {}
body>section#deco_section {text-align:center;background-color:white;}
body>footer#deco_footer {padding-top:20px; font: 10pt arial; text-align:center;}
</style>
<sitemesh:write property="head" />
</head>
<body id="deco_body">

	<header id="deco_header">
		<hgroup id="deco_hgroup">
			<h1 id="deco_h1">
				kAmOrU <img alt="kamoru.mail" src="/kamoru/img/kamoru_gmail.png">
			</h1>
		</hgroup>
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