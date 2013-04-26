<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"      uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn"     uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix='form'   uri='http://www.springframework.org/tags/form'%>
<!DOCTYPE html>
<html>
<head>
<title>Role list</title>
</head>
<body>
<ul>
	<c:forEach items="${authRoleList}" var="role">
	<li><dl>
			<dt>
			<a href="<spring:url value="/auth/role/${role.roleid}"/>">${role.rolename}</a>
			</dt>
			<dd>roleid : ${role.roleid}</dd>
			<dd>enabled : ${role.enabled}</dd>
			<%-- <dd>users : 
				<c:forEach items="${role.users}" var="user">
					<a href="<spring:url value="/auth/user/${user.userid}"/>">${user.username}</a>
				</c:forEach>
			</dd>
			<dd>groups : 
				<c:forEach items="${role.groups}" var="group">
					<a href="<spring:url value="/auth/group/${group.groupid}"/>">${group.groupname}</a>
				</c:forEach>
			</dd> --%>
	</dl>
	</c:forEach>
</ul>
</body>
</html>
