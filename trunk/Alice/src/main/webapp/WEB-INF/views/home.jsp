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
	Hello <security:authentication property="principal.username" />!
</h1>

<ul>
	<li>principal : <security:authentication property="principal" />
	<li>details : <security:authentication property="details"/>  
	<li>credentials : <security:authentication property="credentials"/>  
	<li>authorities : <security:authentication property="authorities"/>  
</ul>

<security:authorize url="/admin/**">
	<spring:url value="/admin" var="admin_url"/>
	<strong>Only Admin page</strong> <a href="${admin_url}">GO!</a>
</security:authorize>

<P>  The time on the server is ${serverTime}. </P>

<a href="/alice/j_spring_security_logout">Logout!</a>
</body>
</html>
