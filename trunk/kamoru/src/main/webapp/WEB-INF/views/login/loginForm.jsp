<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"      uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn"     uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix='form'   uri='http://www.springframework.org/tags/form'%>
<!DOCTYPE html>
<html>
<head>
<title><spring:message code="text.login"/></title>
<style type="text/css">
input[type=text],
input[type=password] {
	width:100%;
	height:30px;
	border:0 solid orange;
	border-radius: 5px;
}
input[type=submit] {
	min-width:46px;
	height:32px;
	border:1px solid #3079ed;
	background-color:#4d90fe;
	color:#fff;
	text-shadow:0 1px rgba(0,0,0,.1);
	border-radius: 3px;
}
input[type=submit]:hover {
	background-color:#4787ed;
}
div {
	margin:10px;
}
#sign-in {
	margin:20px 20px 20px;
	background-color:#f1f1f1; 
	border:1px solid #e5e5e5;
	padding:20px 25px 15px; 
	width:300px;
	float:right;
}
#sign-in-box {
	background-image: url('<spring:url value="/resources/favicon_kamoru.ico"/>');
	background-repeat: no-repeat;
	background-position: right top;
}
#lang-chooser {
	text-align:right;
}
</style>
<script type="text/javascript">
$(document).ready(function() {
	$("#identity").focus();
});
</script>
</head>
<body>
<div id="sign-in">
	<div id="sign-in-box">
		<h3><spring:message code="text.login"/></h3>
		<form name='f' action='<c:url value="/j_spring_security_check"/>' method='POST'>
			<div>
				<label for="identity"><spring:message code="text.identify"/></label>
		    	<input type='text' name='j_username' id="identity"/>
		    </div>
		    <div>
		    	<label for="passwd"><spring:message code="text.password"/></label>
		    	<input type='password' name='j_password' id="passwd"/>
		    </div>
		    <div>
		    	<input name="submit" type="submit" value="<spring:message code="text.login"/>"/>
			    <input id="remember_me" name="_spring_security_remember_me" type="checkbox">
		    	<label for="remember_me"><spring:message code="text.rememberme"/></label>
		    </div>
		    <div style="text-align:center">
		    	<span style="color:red">${msg}</span>
		    </div>
		</form>
	</div>
	<div id="lang-chooser">
		<form><spring:message code="text.language"/> 
			<select name="lang" onchange="document.forms[1].submit();">
				<option value="ko" ${locale eq "ko" ? "selected" : "" }><spring:message code="text.korean"/></option>
				<option value="en" ${locale eq "en" ? "selected" : "" }><spring:message code="text.english"/></option>
				<option value="ja" ${locale eq "ja" ? "selected" : "" }><spring:message code="text.japanese"/></option>
			</select>
		</form>
	</div>
</div>
<div>
	<img src="<spring:url value="/resources/img/subterraneans_by_joe_maccer-d6bnuip-reverse.jpg"/>">
	<h1></h1>
</div>
</body>
</html>