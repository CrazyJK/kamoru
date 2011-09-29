<?xml version="1.0" encoding="euc-kr" ?>
<%@ page language="java" contentType="text/xml; charset=EUC-KR" pageEncoding="EUC-KR"%>
<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql" %>
<%--�ѱ� ó���� ���� utf8 �� �����Ѵ� --%> 
<%request.setCharacterEncoding("utf-8");%>

<c:catch var="e">
  <%-- ����� �����Ѵ�--%>
  <sql:update dataSource="jdbc/MySQLDB" var="update">
    UPDATE tb_comment SET name=?, content=? WHERE id=?
    <%-- EL ������ �̿��Ͽ� ��û ���ڸ� �о�  ���� �����Ѵ�.--%>
    <sql:param value="${param.name}"/>
    <sql:param value="${param.content}"/>
    <sql:param value="${param.id}"/>
  </sql:update> 
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
    </c:otherwise>
  </c:choose> 
</result> 
