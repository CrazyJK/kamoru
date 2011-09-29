<%@ page language="java" contentType="text/html; charset=EUC-KR"  pageEncoding="EUC-KR"%>
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
<table border="1">
<c:forEach var="row" items="${rs.rows}">
	<tr>
    	<td>${row.id}</td> 
    	<td>${row.name}</td>
    	<td>${row.content}</td>
	</tr>
</c:forEach>
</table>
