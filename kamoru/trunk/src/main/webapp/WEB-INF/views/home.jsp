<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
#menu-list-div > section {
	display:inline-table;
	/* float:right; */
	/* background:#EEE; */
	border:1px #717074 solid;
	border-radius: 5px; /* future proofing */
	-moz-border-radius: 5px;
	-webkit-border-radius: 5px;
	margin:10px 5px 20px;
	padding:0 10px 5px;
	width:180px;
}

#menu-list-div section h4 {
	background:#717074;
	border-radius: 3px 3px 0 0; /* future proofing */
	-moz-border-radius: 3px 3px 0 0;
	color:#FFF;
	font-size:1.0em;
	line-height:1.0em;
	margin:0 -10px;
	padding:5px;
}

#menu-list-div section ul, #menu-list-div section li {
	margin:0 -5px;
	padding:0;
}

#menu-list-div section li {
	display:block;
	/* border-bottom:1px #FFF solid; */
	padding:3px 5px 3px 10px;
}

#menu-list-div section a {
	display:block;
	text-decoration:none;
}

#menu-list-div section a:hover {
	/* text-decoration:underline; */
}
</style>
</head>
<body>
<h1><s:message code="default.title"/></h1>
<h2><s:message code="default.hello"/>&nbsp;${auth.name}</h2>

<div id="menu-list-div">
	<h3><s:message code="default.app-list"/></h3>
	<%@ include file="/WEB-INF/views/menu.jspf" %>
</div>

<p>
	<security:authorize url="/home">
	<a href="<s:url value="/j_spring_security_logout"/>"><s:message code="default.logout"/></a>
	</security:authorize>
	<c:if test="${'anonymousUser' eq auth.principal}">
	<a href="<s:url value="/auth/login"/>"><s:message code="default.login"/></a>
	</c:if>
</p>

<P>
	<s:message code="default.server-time"/>&nbsp;${serverTime}
</P>

<%-- 
<div style="float:right;">
	<form><s:message code="default.language"/> 
		<select name="lang" onchange="document.forms[0].submit();">
			<option value="ko" <%="ko".equals(lang) ? "selected" : "" %>><s:message code="default.korean"/></option>
			<option value="en" <%="en".equals(lang) ? "selected" : "" %>><s:message code="default.english"/></option>
			<option value="ja" <%="ja".equals(lang) ? "selected" : "" %>><s:message code="default.japanese"/></option>
		</select>
	</form>
</div>
 --%>
</body>
</html>
