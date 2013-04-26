<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ page session="false" %>

<spring:url value="/csr"       var="csr"/>

<html>
<head>
	<title>CSR Home</title>
</head>
<body>

<h1>Customer Service Request</h1>

<a href="${csr}/rqst">Rqst list</a>
</body>
</html>
