<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"      uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn"     uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix='form'   uri='http://www.springframework.org/tags/form'%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Login - kAmOrU</title>
<!--[if lt IE 9]>
<script src="http://html5shiv.googlecode.com/svn/trunk/html5.js"></script>
<![endif]-->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.8.3/jquery.min.js"></script>
<script type="text/javascript">
$(document).ready(function() {
	$("#identity").focus();
});
</script>
</head>
<body>
<h3>Login</h3>
<form name='f' action='<c:url value="/j_spring_security_check"/>' method='POST'>
 <table>
    <tr><td>Identity</td><td><input type='text' name='j_username' id="identity"/></td></tr>
    <tr><td>Password</td><td><input type='password' name='j_password'/></td></tr>
    <tr><td colspan='2'>
    	<input id="remember_me" name="_spring_security_remember_me" type="checkbox">
    	<label for="remember_me">Remember me</label>
    </td></tr>
    <tr><td colspan='2'><input name="submit" type="submit" value="Login"/><span style="color:red">${msg}</span></td></tr>
  </table>
</form>
</body>
</html>