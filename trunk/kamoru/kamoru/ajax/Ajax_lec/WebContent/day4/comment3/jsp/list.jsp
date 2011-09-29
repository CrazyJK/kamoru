<?xml version="1.0" encoding="euc-kr" ?>

<%@ page language="java" contentType="text/xml; charset=EUC-KR"  pageEncoding="EUC-KR"%>
<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql" %>

<%
//케싱이 되지 않도록 해더를 설정한다.
response.setHeader("Pragma", "No-cache");
response.setHeader("Expires", "0");
response.setHeader("Cache-Control", "no-cache");
%>

<%--댓글 목록을 얻기 위한 SQL 구문을 작성한다. --%>
<sql:query var="rs" dataSource="jdbc/TestDB">
select id, name, content from tb_comment
</sql:query>

<%--댓글 목록을 XML 로 출력한다. --%>
<comments>
<c:forEach var="row" items="${rs.rows}">
	<comment id="${row.id}">
    	<name>${row.name}</name>
    	<content>${row.content}</content>
	</comment>
</c:forEach>
</comments>

