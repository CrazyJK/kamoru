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
<style type="text/css">
ul {
	list-style: none;
	padding: 0px;
 	display: block;
	clear: right;
}
ul > li {
	font-size: 11pt;
}
ol > li {
	font-size: 10pt;
}
.inputText {
	border-top:0px; border-left:0px; border-right:solid 1px #363; border-bottom:solid 1px #363;
}
.formNames {
	color:#363; font-weight:bold;
}
</style>
</head>
<body>
<article>
	<header>
		<h1>File list</h1>
	</header>
	<section>
	<html:form action="/vlist">
		<ul>
			<li><span class="formNames">Path</span>   
					<html:text property="pathName" style="width:300px;" styleClass="inputText"/>
			<li><span class="formNames">확장자</span> 
					<html:text property="extension" styleClass="inputText"/>
				<span class="formNames">구분자</span> 
					<html:text property="delimiter" style="width:50px;text-align:center;color:red;" styleClass="inputText"/>
			<li><span class="formNames">검색어</span> 
					<html:text property="searchName" style="width:300px;" styleClass="inputText"/>
			<li><span class="formNames">Mode</span> 
					<html:radio property="method" value="all">All</html:radio> 
					<html:radio property="method" value="sameSize">Same size</html:radio>
				<span class="formNames">Sort</span>	
					<html:radio property="sort" value="0">Name</html:radio> 
					<html:radio property="sort" value="1">Path</html:radio> 
					<html:radio property="sort" value="2">Size</html:radio> 
					<html:radio property="sort" value="3">Date</html:radio> 
					<html:checkbox property="reverse">Reverse</html:checkbox>
			<html:submit property="searchBtn" value="검색"/>
			<html:hidden property="key"/>
		</ul>	
		<hr>
		<logic:notEmpty name="vlistForm" property="vfileList">
			<ol>
				<logic:iterate id="vfile" name="vlistForm" property="vfileList" type="kamoru.app.vlist.bean.Vfile">
					<li><bean:write name="vfile" property="path"/>/<span style="color:red"><bean:write name="vfile" property="name"/></span> 
						<span style="color:blue">&nbsp;-&nbsp;</span> 
						<span title="<bean:write name="vfile" property="size"/>"><bean:write name="vfile" property="sizeConvert"/></span> 
						<span style="color:blue">&nbsp;-&nbsp;</span> 
						<bean:write name="vfile" property="lastModifiedDate"/>
				</logic:iterate>
			</ol>
		</logic:notEmpty>
		<logic:empty name="vlistForm" property="vfileList">
			<p style="text-align:center">No data</p>			
		</logic:empty>
	</html:form>
	</section>
</article>
</body>
</html>
<% logger.info("" + this.getClass().getName()); %>