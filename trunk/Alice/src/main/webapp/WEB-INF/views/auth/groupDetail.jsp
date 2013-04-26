<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"      uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn"     uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix='form'   uri='http://www.springframework.org/tags/form'%>
<!DOCTYPE html>
<html>
<head>
<title>Group detail</title>
</head>
<body>
<dl>
	<dt>${authGroup.groupname}</dt>
	<dd>ID : ${authGroup.groupid}</dd>
	<dd>enabled : ${authGroup.enabled}</dd>
	<dd>ParentID: ${authGroup.parentid}</dd>
	<dd>Roles : 
 		<c:forEach items="${authGroup.authRoleGroupMaps}" var="authRoleGroupMap">
			<a href="<spring:url value="/auth/role/${authRoleGroupMap.authRole.roleid}"/>">${authRoleGroupMap.authRole.rolename}</a>&nbsp;
		</c:forEach>
	</dd>
	<dd>Users : 
 		<c:forEach items="${authGroup.authUsers}" var="authUser">
			<a href="<spring:url value="/auth/user/${authUser.userid}"/>">${authUser.username}</a>
		</c:forEach>
	</dd>
	<dd>
		<a href="<spring:url value="/auth/group"/>">List</a>
		<a href="<spring:url value="/auth/group/${authGroup.groupid}/edit"/>">EDIT</a>
		<a href="<spring:url value="/auth/group/${authGroup.groupid}/delete"/>">Delete</a>
	</dd>
</dl>
</body>
</html>
