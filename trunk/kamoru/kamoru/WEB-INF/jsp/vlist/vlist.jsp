<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="html" uri="http://struts.apache.org/tags-html" %>
<%@ taglib prefix="bean" uri="http://struts.apache.org/tags-bean" %>
<%@ taglib prefix="logic" uri="http://struts.apache.org/tags-logic" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core"%>
<%! static org.apache.commons.logging.Log logger = org.apache.commons.logging.LogFactory.getLog("JSP");%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title> <bean:message key="vlist.title"/> </title>
</head>
<body>
<article>
	<header>
		<h1>File list</h1>
	</header>
	<section>
	<html:form action="/vlist">
		Path : <html:text property="pathName" style="width:300px;"/>
		<br/>
		확장자 : <html:text property="extension"/>
		구분자 : <html:text property="delimiter" style="width:50px;text-align:center;color:red;"/>
		검색어 : <html:text property="searchName" style="width:300px;"/>
		<html:submit property="searchBtn" value="검색"/>
		<html:hidden property="key"/>
		<html:hidden property="method"/>
		<a href="/kamoru/samefile.do">같은 크기의 파일 찾기</a>
		
		<logic:notEmpty name="vlistForm" property="vfileList">
			<hr>
			<logic:iterate id="vfile" name="vlistForm" property="vfileList" type="kamoru.app.vlist.bean.Vfile">
				<li><span style="color:red"><bean:write name="vfile" property="name"/></span> - <bean:write name="vfile" property="path"/> - <bean:write name="vfile" property="sizeConvert"/> 
				<br>
			</logic:iterate>
		</logic:notEmpty>
	</html:form>
	</section>
</article>
</body>
</html>
<% logger.info("" + this.getClass().getName()); %>