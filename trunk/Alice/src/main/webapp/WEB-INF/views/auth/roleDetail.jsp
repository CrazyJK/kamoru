<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"      uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn"     uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix='form'   uri='http://www.springframework.org/tags/form'%>
<!DOCTYPE html>
<html>
<head>
<title>Role detail</title>
</head>
<body>

<dl>
	<dt>${role.rolename}</dt>
	<dd>roleid : ${role.roleid}</dd>
	<dd>enabled : ${role.enabled}</dd>
	<dd>users : 
		<c:forEach items="${role.authRoleUserMaps}" var="authRoleUserMap">
			<a href="<spring:url value="/auth/user/${authRoleUserMap.authUser.userid}"/>">${authRoleUserMap.authUser.username}</a>
		</c:forEach>
	</dd>
	<dd>groups : 
		<c:forEach items="${role.authRoleGroupMaps}" var="authRoleGroupMap">
			<a href="<spring:url value="/auth/group/${authRoleGroupMap.authGroup.groupid}"/>">${authRoleGroupMap.authGroup.groupname}</a>
		</c:forEach>
	</dd>

</dl>

</body>
</html>
