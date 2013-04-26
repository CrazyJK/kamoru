<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"      uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn"     uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix='form'   uri='http://www.springframework.org/tags/form'%>

<spring:url value="/"      	   var="alice"/>
<spring:url value="/auth"      var="auth"/>

<!DOCTYPE html>
<html>
<head>
<title>CSR</title>
</head>
<body>
<h1>권한 관리</h1>
<h2><a href="${auth}/user">User 관리</a></h2>
<h2><a href="${auth}/group">Group 관리</a></h2>
<h2><a href="${auth}/role">Role 관리</a></h2>

<h4><a href="${alice}">Home</a></h4>

</body>
</html>