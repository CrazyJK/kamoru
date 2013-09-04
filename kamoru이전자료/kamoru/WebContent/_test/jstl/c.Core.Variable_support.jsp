<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>JSTL &gt; Core &gt; Variable support</title>
</head>
<body>
<a href="?param1=firstParam&param2=secondParam">First click!</a>

<hr/>

<c:set var="sessionValue1" scope="session" value="moo"/>
sessionValue1 = ${ foo }

<hr/>

<c:set var="param1" value="${param.param1}"/>
param1 = ${param1}

<hr/>

<c:set var="param2">
  <c:out value="${param.param2}" default="defaultParamValue"/>
</c:set>
param2 = ${param2}


</body>
</html>