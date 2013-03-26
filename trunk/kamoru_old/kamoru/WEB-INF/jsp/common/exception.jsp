<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="html" uri="http://struts.apache.org/tags-html" %>
<%@ taglib prefix="bean" uri="http://struts.apache.org/tags-bean" %>
<%@ taglib prefix="logic" uri="http://struts.apache.org/tags-logic" %>
<!DOCTYPE html>
<html:html>
<head>
<meta charset="UTF-8">
<title><bean:message key="errors.system"/></title>
</head>
<body>
<div align="center" class="body">
	<h2><font color="red"><bean:message key="errors.system"/></font></h2>
	<html:messages id="error">
		<li><bean:write name="error"/>
	</html:messages>
	<hr>
	<blockquote style="background: wheat;">
	<%
		Exception ex = (Exception)request.getAttribute("errors.system");
		if(ex == null) out.println("ex == null");
		else out.println(ex.getMessage());
	%>
	</blockquote>
</div>
</body>
</html:html>