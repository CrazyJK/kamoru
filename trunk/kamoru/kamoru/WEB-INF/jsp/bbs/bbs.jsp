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
<title>BBS</title>
<style type="text/css">
#listTable {width:100%;border:1pt solid green;}
#listTable>tr {height:125px;}
#listTable>tr>th {text-align:center; font: 12pt bold; background-color:blue; color:#fff;}
#listTable>tr>td {text-align:center;}
</style>
</head>
<body>

<article>
	<header>bbs list</header>
	<section>
		<html:form action="/bbsCreateArticle">
			<html:text property="categoryid"/>
			<html:text property="schTitle"/>
			<html:text property="schWriter"/>
			<html:text property="schTag"/>
			<html:text property="pageNumbers"/>
			<html:text property="numberOfArticlesPerPage"/>

			<table id="listTable">
				<tr>
					<th>번호</th>
					<th>제목</th>
					<th>글쓴이</th>
					<th>날자</th>
					<th>추천</th>
				</tr>
				<logic:notEmpty name="bbsForm" property="articleList">
					<logic:iterate id="article" name="bbsForm" property="articleList" type="kamoru.app.bbs.bean.Article">
				<tr>
					<td><bean:write name="article" property="articleid"/></td>
					<td><bean:write name="article" property="title"/></td>
					<td><bean:write name="article" property="writer"/></td>
					<td><bean:write name="article" property="created"/></td>
					<td><bean:write name="article" property="likenum"/></td>
				</tr>					
					</logic:iterate>
				</logic:notEmpty>
				<logic:empty name="bbsForm" property="articleList">
				<tr>
					<td colspan="5">리스트가 없음</td>
				</tr>
				</logic:empty>
			</table>
			
			<html:submit>새글</html:submit>
			
		</html:form>
	</section>
</article>

</body>
</html>
<% logger.info("" + this.getClass().getName()); %>