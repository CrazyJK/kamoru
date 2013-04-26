<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"      uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn"     uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix='form'   uri='http://www.springframework.org/tags/form'%>
<!DOCTYPE html>
<html>
<head>
<title>User list</title>
</head>
<body>
<a href="<spring:url value="/auth/user/new"/>">NEW</a>
<ul>
	<c:forEach items="${userList}" var="authUser">
	<li><dl>
			<dt>
				<a href="<spring:url value="/auth/user/${authUser.userid}"/>">${authUser.username}</a>
			</dt>
			<dd>userid : 				${authUser.userid}</dd>
			<dd>group : 				${authUser.authGroup.groupname}</dd>
			<dd>RefKey : 				${authUser.refkey}</dd>
<%-- 			<dd>accountlocked : 		${authUser.accountlocked}</dd>
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
			</dd> --%>
		</dl>
	</c:forEach>
</ul>
</body>
</html>
