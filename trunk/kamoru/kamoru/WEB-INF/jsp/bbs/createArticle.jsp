<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="html"  uri="http://struts.apache.org/tags-html" %>
<%@ taglib prefix="bean"  uri="http://struts.apache.org/tags-bean" %>
<%@ taglib prefix="logic" uri="http://struts.apache.org/tags-logic" %>
<%@ taglib prefix="c"     uri="http://java.sun.com/jstl/core" %>
<%! static org.apache.commons.logging.Log logger = org.apache.commons.logging.LogFactory.getLog("JSP");%>
<!DOCTYPE html>
<html>
<head> 
<meta charset="UTF-8">
<title>Article create - BBS</title>
<style type="text/css">
#listTable {width:100%;border:1pt solid green;}
#listTable>tr {height:125px;}
#listTable>tr>th {text-align:center; font: 12pt bold; background-color:blue; color:#fff;}
#listTable>tr>td {text-align:center;}
</style>
</head>
<body>

<article>
	<header>article create</header>
	<section>
		<html:form action="/bbsCreateArticleAction" enctype="multipart/form-data">  
			<html:text property="categoryid"/>
			<html:text property="schTitle"/>
			<html:text property="schWriter"/>
			<html:text property="schTag"/>
			<html:text property="pageNumbers"/>
			<html:text property="numberOfArticlesPerPage"/>

			<table id="listTable">
				<tr>
					<td>제목</td>
					<td><html:text property="newTitle"/></td>
				</tr>
				<tr>
					<td>태그</td>
					<td><html:text property="newTag"/></td>
				</tr>
				<tr>
					<td>내용</td>
					<td><html:textarea property="newBody"/></td>
				</tr>
				<tr>
					<td>파일</td>
					<td>
						<html:file name="bbsForm" property="newAttachFiles[0]"/><br/> 
						<html:file name="bbsForm" property="newAttachFiles[1]"/><br/>
						<html:file name="bbsForm" property="newAttachFiles[2]"/> 
					</td>
				</tr>
			</table>

			<html:submit>저장</html:submit>
			
			<html:link action="/bbs">목록으로</html:link>
			
		</html:form>
	</section>
</article>

</body>
</html>
<% logger.info("" + this.getClass().getName()); %>