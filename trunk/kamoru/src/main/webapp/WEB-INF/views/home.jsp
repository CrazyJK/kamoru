<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ page import="org.springframework.web.servlet.support.RequestContext" %>
<%@ page import="java.util.Locale" %>

<%@ page session="false" %>
<%
Locale locale = new RequestContext(request).getLocale();
String lang = locale.getLanguage();
%>
<html>
<head>
<title><spring:message code="text.home"/></title>
</head>
<body>
<h1><spring:message code="application.title"/></h1>
<h2><spring:message code="text.hello"/>&nbsp;<security:authentication property="principal.username" /></h2>
<spring:message code="application.list"/>
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
<form><spring:message code="text.language"/> 
	<select name="lang" onchange="document.forms[0].submit();">
		<option value="ko" <%="ko".equals(lang) ? "selected" : "" %>>한국어</option>
		<option value="en" <%="en".equals(lang) ? "selected" : "" %>>English</option>
		<option value="jp" <%="jp".equals(lang) ? "selected" : "" %>>日本語</option>
	</select>
</form>
<p><a href="<spring:url value="/j_spring_security_logout"/>"><spring:message code="text.logout"/></a></p>
<P><spring:message code="server.time" arguments="${serverTime}"/></P>
</body>
</html>
