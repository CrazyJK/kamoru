<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"      uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn"     uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix='form'   uri='http://www.springframework.org/tags/form'%>
<!DOCTYPE html>
<html>
<head>
<title>User detail</title>
</head>
<body>
<dl>
	<dt>${authUser.username}</dt>
	<dd>ID : 					${authUser.userid}</dd>
	<dd>Group : 				${authUser.authGroup.groupname}</dd>
	<dd>RefKey : 				${authUser.refkey}</dd>
	<dd>accountlocked : 		${authUser.accountlocked}
		<a href="<spring:url value="/auth/user/${authUser.userid}/lock" />">Lock</a>
		<a href="<spring:url value="/auth/user/${authUser.userid}/unlock" />">Unlock</a>
	</dd>
	<dd>accountexpired : 		${authUser.accountexpired}</dd>
	<dd>accountexpireddate : 	${authUser.accountexpireddate}</dd>
	<dd>passwordexpired : 		${authUser.passwordexpired}</dd>
	<dd>passwordexpireddate : 	${authUser.passwordexpireddate}</dd>
	<dd>passwordfailcount : 	${authUser.passwordfailcount}</dd>
	<dd>lastlogindate : 		${authUser.lastlogindate}</dd>
 	<dd>Roles : 
 		<c:forEach items="${authUser.authRoleUserMaps}" var="authRoleUserMap">
			<a href="<spring:url value="/auth/role/${authRoleUserMap.authRole.roleid}"/>">${authRoleUserMap.authRole.rolename}</a>
		</c:forEach>
	</dd>
	<dd>
		<a href="<spring:url value="/auth/user"/>">List</a>
		<a href="<spring:url value="/auth/user/${authUser.userid}/edit"/>">EDIT</a>
		<a href="<spring:url value="/auth/user/${authUser.userid}/delete"/>">Delete</a>
	</dd>
</dl>
</body>
</html>
