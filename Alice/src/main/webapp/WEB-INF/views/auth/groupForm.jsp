<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"      uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn"     uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix='form'   uri='http://www.springframework.org/tags/form'%>
<!DOCTYPE html>
<html>
<head>
<title>Group FORM</title>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.8.3/jquery.min.js"></script>
</head>
<body>

<div class="container">
	<h2>Group</h2>
	<hr />	
	<div class="span-14 append-10 last">
	<form:form modelAttribute="authGroup">	
		<fieldset>
			<legend> Group </legend>
			<p>
				<form:label path="groupname">그룹이름:</form:label><br/>
				<form:input path="groupname" size="12" maxlength="12" />
				<form:errors cssClass="error" path="groupname" />
			</p>
			<p>
				<form:label path="parentid">상위그룹:</form:label><br/>
				<form:radiobutton path="parentid" label="없음" value=""/>
				<form:radiobuttons path="parentid" items="${authGroupList}" itemLabel="groupname" itemValue="groupid" />
			</p>
			<%-- <p>
				<label for="authGroup.groupid">그룹:</label><br/>
				<form:select path="authGroup.groupid" items="${authGroupList}" itemLabel="groupname" itemValue="groupid" />
			</p> --%>
			<p>
				<label for="roles">Role:</label><br/>
 				<form:checkboxes path="roles" items="${authRoleList}" itemLabel="rolename" itemValue="roleid" delimiter="<br/>"/>
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
