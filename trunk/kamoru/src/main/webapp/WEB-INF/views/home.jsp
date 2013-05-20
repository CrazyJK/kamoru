<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page session="false" %>
<html>
<head>
	<title>kAmOrU Home</title>
</head>
<body>
<h1>
	<spring:message code="application.title"/> learning project using by SpringFramework
</h1>
<h2>
	<spring:message code="application.author"/>
</h2>
Application List
<ul>
	<li><a href="./video">Video Main</a>
	<li><a href="./video/list">Video list</a>
	<li><a href="./video/actress">Actress list</a>
	<li><a href="./video/studio">Studio List</a>
</ul>

<P>  The time on the server is ${serverTime}. </P>
</body>
</html>
