<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
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
<title><s:message code="default.home"/></title>
<style type="text/css">
#menu-list-div > ul {
	list-style:url('<c:url value="/resources/magnify0.png"/>');
}
</style>
</head>
<body>
<h1><s:message code="default.title"/></h1>
<h2><s:message code="default.hello"/>&nbsp;<security:authentication property="principal.username" /></h2>

<div id="menu-list-div">
	<s:message code="default.app-list"/>
	<%@ include file="/WEB-INF/views/menu.inc" %>
</div>

<p>
	<a href="<s:url value="/j_spring_security_logout"/>"><s:message code="default.logout"/></a>
</p>

<P>
	<s:message code="default.server-time"/>&nbsp;${serverTime}
</P>

<div style="float:right;">
	<form><s:message code="default.language"/> 
		<select name="lang" onchange="document.forms[0].submit();">
			<option value="ko" <%="ko".equals(lang) ? "selected" : "" %>><s:message code="default.korean"/></option>
			<option value="en" <%="en".equals(lang) ? "selected" : "" %>><s:message code="default.english"/></option>
			<option value="ja" <%="ja".equals(lang) ? "selected" : "" %>><s:message code="default.japanese"/></option>
		</select>
	</form>
</div>

</body>
</html>
