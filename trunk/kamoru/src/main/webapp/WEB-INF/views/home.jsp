<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ page session="false" %>
<html>
<head>
	<title>Home</title>
</head>
<body>
<h1>
	<spring:message code="application.title"/> learning project using by SpringFramework
</h1>
<h2>
	Hello <security:authentication property="principal.username" />
</h2>
Application List
<ul>
	<li><a href="./video">Video Main</a>
	<li><a href="./video/search">Video search</a>
	<li><a href="./video/history">Video history</a>
	<li><a href="./video/list">Video list</a>
	<li><a href="./video/actress">Actress list</a>
	<li><a href="./video/studio">Studio List</a>
	<li><a href="./image">Image view</a>
	<li><a href="./requestMappingList">request mapping list</a>
	<li><a href="./jsp/util/sessionView.jsp">Web Session</a>
	<li><a href="./jsp/colors.jsp">Standard Colors</a>
	<li><a href="./jsp/util/threaddump.jsp">Thread dump</a>
	<li><a href="./jsp/pic/imgView.html">HTML5 Canvas Image view</a>
	
</ul>
<p><a href="<spring:url value="/j_spring_security_logout"/>">Logout!</a></p>
<P>  The time on the server is ${serverTime}. </P>
</body>
</html>
