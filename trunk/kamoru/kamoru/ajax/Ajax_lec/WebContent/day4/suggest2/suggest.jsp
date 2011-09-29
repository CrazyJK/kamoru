<%@ page language="java" contentType="text/plain; charset=EUC-KR" pageEncoding="EUC-KR"%>
<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql" %>
<% 
  //��û���� ĳ���� ���� utf8�� ��ȯ�Ѵ�.
  request.setCharacterEncoding("utf-8");
%>

<%
//�ɽ��� ���� �ʵ��� �ش��� �����Ѵ�.
response.setHeader("Pragma", "No-cache");
response.setHeader("Expires", "0");
response.setHeader("Cache-Control", "no-cache");
%>

<%--��� ����� ��� ���� SQL ������ �ۼ��Ѵ�. --%>
<sql:query var="rs" dataSource="jdbc/MySQLDB">
  select contents from tb_suggest where keyword like ? order by rank
  <sql:param value="${param.keyword}%"/>
</sql:query>

<%--��� ����� XML �� ����Ѵ�. --%>
<c:forEach var="row" items="${rs.rows}" varStatus="status">
  <c:if test="${status.count != 1}">,</c:if>
  ${row.contents}
</c:forEach>
