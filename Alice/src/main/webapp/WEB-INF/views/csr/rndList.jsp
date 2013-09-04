<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ page session="false" %>
<html>
<head>
	<title>RnD list</title>
</head>
<body>

<h3>Customer Service Request</h3>
<iframe src="${rndUrl}" id="ifrm_csd"></iframe>
</body>
</html>
