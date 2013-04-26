<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"      uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn"     uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix='form'   uri='http://www.springframework.org/tags/form'%>
<!DOCTYPE html>
<html>
<head>
<title>Group list</title>
</head>
<body>
<a href="<spring:url value="/auth/group/new"/>">NEW</a>
<ul>
	<c:forEach items="${authGroupList}" var="group">
	<li><dl>
			<dt>
				<a href="<spring:url value="/auth/group/${group.groupid}"/>">${group.groupname}</a>
			</dt>
			<dd>groupid : ${group.groupid}</dd>
			<dd>parentid : ${group.parentid}</dd>
			<dd>enabled : ${group.enabled}</dd>
<%-- 			<dd>roles : 
				<c:forEach items="${group.authRoleGroupMaps}" var="authRoleGroupMap">
					<a href="<spring:url value="/auth/role/${authRoleGroupMap.authRole.roleid}"/>">${authRoleGroupMap.authRole.rolename}</a>
				</c:forEach>
			</dd>
			<dd>users : 
				<c:forEach items="${group.authUsers}" var="authUser">
					<a href="<spring:url value="/auth/user/${authUser.userid}"/>">${authUser.username}</a>
				</c:forEach>
			</dd> --%>
	</dl>
	</c:forEach>
</ul>
</body>
</html>
