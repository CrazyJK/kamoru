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
<style type="text/css">
#menu-list-div > ul {
	list-style:url('<c:url value="/resources/magnify0.png"/>');
}
</style>
</head>
<body>
<h1><spring:message code="application.title"/></h1>
<h2><spring:message code="text.hello"/>&nbsp;<security:authentication property="principal.username" /></h2>

<div id="menu-list-div">
	<spring:message code="application.list"/>
	<%@ include file="/WEB-INF/views/menu.inc" %>
</div>

<p>
	<a href="<spring:url value="/j_spring_security_logout"/>"><spring:message code="text.logout"/></a>
</p>

<P>
	<spring:message code="server.time"/>&nbsp;${serverTime}
</P>

<div style="float:right;">
	<form><spring:message code="text.language"/> 
		<select name="lang" onchange="document.forms[0].submit();">
			<option value="ko" <%="ko".equals(lang) ? "selected" : "" %>><spring:message code="text.korean"/></option>
			<option value="en" <%="en".equals(lang) ? "selected" : "" %>><spring:message code="text.english"/></option>
			<option value="ja" <%="ja".equals(lang) ? "selected" : "" %>><spring:message code="text.japanese"/></option>
		</select>
	</form>
</div>

</body>
</html>
