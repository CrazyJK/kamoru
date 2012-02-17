<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="html" uri="http://struts.apache.org/tags-html" %>
<%@ taglib prefix="bean" uri="http://struts.apache.org/tags-bean" %>
<%@ taglib prefix="logic" uri="http://struts.apache.org/tags-logic" %>
<%! static org.apache.commons.logging.Log logger = org.apache.commons.logging.LogFactory.getLog("JSP");%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title> <bean:message key="sample.title"/> </title>
</head>
<body>

<h3>Girl's Generation</h3>
<logic:iterate id="girl" name="sampleForm" property="sampleList" type="kamoru.app.sample.bean.GirlsGeneration">
	<li><bean:write name="girl" property="num"/> - <bean:write name="girl" property="name"/> - <bean:write name="girl" property="age"/> 
</logic:iterate>

<hr>

</body>
</html>
<%
logger.info("" + this.getClass().getName());
%>