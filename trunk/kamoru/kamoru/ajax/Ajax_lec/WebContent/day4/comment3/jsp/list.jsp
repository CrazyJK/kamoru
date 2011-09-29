<?xml version="1.0" encoding="euc-kr" ?>

<%@ page language="java" contentType="text/xml; charset=EUC-KR"  pageEncoding="EUC-KR"%>
<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql" %>

<%
//�ɽ��� ���� �ʵ��� �ش��� �����Ѵ�.
response.setHeader("Pragma", "No-cache");
response.setHeader("Expires", "0");
response.setHeader("Cache-Control", "no-cache");
%>

<%--��� ����� ��� ���� SQL ������ �ۼ��Ѵ�. --%>
<sql:query var="rs" dataSource="jdbc/TestDB">
select id, name, content from tb_comment
</sql:query>

<%--��� ����� XML �� ����Ѵ�. --%>
<comments>
<c:forEach var="row" items="${rs.rows}">
	<comment id="${row.id}">
    	<name>${row.name}</name>
    	<content>${row.content}</content>
	</comment>
</c:forEach>
</comments>

