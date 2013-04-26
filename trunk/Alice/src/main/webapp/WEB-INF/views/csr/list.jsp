<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"      uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn"     uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix='form'   uri='http://www.springframework.org/tags/form'%>

<spring:url value="/csr"       var="csr"/>

<!DOCTYPE html>
<html>
<head>
<title>Rqst List</title>
</head>
<body>
<ul>
<c:forEach items="${rqstlist }" var="rqst">
<dl>
	<dt>
		<a href="${csr}/rqst/${rqst.rqstid}">
		${rqst.rqstid} - ${rqst.tbRqst.subject}</a>
	</dt>
	<dd>${rqst.tbRqst.tbCmpnyinfo.cmpnyname }
		${rqst.tbRqst.version }
		${rqst.tbRqst.component}
		${rqst.tbRqst.boundary }
		${rqst.tbRqst.rqstername}
	</dd>
</dl>
</c:forEach>
</ul>



</body>
</html>