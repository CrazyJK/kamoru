<?xml version="1.0" encoding="euc-kr" ?>
<%@ page language="java" contentType="text/xml; charset=EUC-KR" pageEncoding="EUC-KR"%>
<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql" %>
<%--�ѱ� ó���� ���� utf8 �� �����Ѵ� --%> 
<%request.setCharacterEncoding("utf-8");%>

<c:catch var="e">
  <%-- ����� ����Ѵ� --%>
  <sql:update var="update" dataSource="jdbc/MySQLDB">
    insert into tb_comment (name, content, make_date) values (?, ?, now())
    <%-- EL ������ �̿��Ͽ� ��û ���ڸ� �о�  ���� �����Ѵ�.--%>
    <sql:param value="${param.name}"/>
    <sql:param value="${param.content}"/>
  </sql:update> 
  
  <%--  ��ϵ� �ű� ���̵� ��´�  --%>
  <sql:query var="rs" dataSource="jdbc/MySQLDB">
    select id, make_date from tb_comment where id = LAST_INSERT_ID()
  </sql:query>
  
  <%--  �ű� ���̵� ����Ѵ�. --%>
  <c:forEach var="row" items="${rs.rows}">
      <c:set var="id" value="${row.id}" />
      <c:set var="make_date" value="${row.make_date}" />
  </c:forEach>
</c:catch>

<result>
  <c:choose>
    <%-- ���� �߻� --%>
    <c:when test="${e != null}">
      <code>error</code>
      <msg>${e}</msg>
    </c:when>
    <%-- �۾� ������  --%>
    <c:otherwise>
      <code>success</code>
      <id>${id}</id>
      <make_date>${make_date}</make_date>
    </c:otherwise>
  </c:choose> 
</result> 
