<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"      uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn"     uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix='form'   uri='http://www.springframework.org/tags/form'%>
<!DOCTYPE html>
<html>
<head>
<title>User FORM</title>
</head>
<body>

<div class="container">
	<h2>회원 가입</h2>
	<hr />	
	<div class="span-14 append-10 last">
	<form:form modelAttribute="authUser">	
		<fieldset>
			<legend> 회원 정보 </legend>
			<%-- <p>
				<form:label path="name">이름:</form:label><br/>
				<form:input path="name" size="20" maxlength="20" /> 
				<form:errors cssClass="error" path="name" />
			</p> --%>
			<%-- <form:hidden path="userid"/> --%>
			<p>
				<form:label path="username">사용자이름:</form:label><br/>
				<form:input path="username" size="12" maxlength="12" />
				<form:errors cssClass="error" path="username" />
			</p>
			<p>
				<form:label path="password">비밀번호:</form:label><br/>
				<form:password path="password" showPassword="true" size="12" maxlength="12" />
				<form:errors cssClass="error" path="password" />
			</p>
			<p>
				<label for="authGroup.groupid">그룹:</label><br/>
				<form:select path="authGroup.groupid" items="${authGroupList}" itemLabel="groupname" itemValue="groupid" />
			</p>
 			<p>
				<label for="roles">Role:</label><br/>
  				<form:checkboxes path="roles" items="${authRoleList}" itemLabel="rolename" itemValue="roleid" delimiter="<br/>"/>
			</p>
			<p>
				<form:label path="refkey">Ref Key:</form:label><br/>
				<form:input path="refkey" size="12" maxlength="12" />
				<form:errors cssClass="error" path="refkey" />
			</p>
			<p>
				<label for="accountlocked">Locked:</label><br/>
				<form:radiobutton path="accountlocked" value="T" label="Lock"/>
				<form:radiobutton path="accountlocked" value="F" label="UnLock"/>
			</p>
			<p>
				<input type="submit" value="  등록   " />
			</p>
		</fieldset>
	</form:form>
	</div>
</div>

</body>
</html>
