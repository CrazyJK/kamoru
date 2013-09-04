<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"      uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn"     uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix='form'   uri='http://www.springframework.org/tags/form'%>
<spring:url value="/csr"       var="csr"/>
<!DOCTYPE html>
<html>
<head>
<title>Rqst</title>
</head>
<body>
<a href="${csr}/rqst">List</a>
<dl>
	<dt>${rqst.rqstid} - ${rqst.tbRqst.subject}</dt>
	<dd>${rqst.tbRqst.tbCmpnyinfo.cmpnyname }
		${rqst.tbRqst.version }
		${rqst.tbRqst.component}
		${rqst.tbRqst.boundary }
		${rqst.tbRqst.rqstername}
	</dd>
	<dd><textarea disabled>${rqst.tbRqst.content }</textarea></dd>
	<dd><textarea >${rqst.answer }</textarea></dd>
	<dd><textarea >${rqst.finalAnswer }</textarea></dd>
</dl>

</body>
</html>