<?xml version="1.0" encoding="euc-kr" ?>
<%@ page language="java" contentType="text/xml; charset=EUC-KR" pageEncoding="EUC-KR"%>
<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql" %>

<c:catch var="e">
  <%-- ����� �����Ѵ� --%>
  <sql:update dataSource="jdbc/TestDB" var="delete">
    delete from tb_comment WHERE id=?
    <%-- EL ������ �̿��Ͽ� ��û ���ڸ� �о�  ���� �����Ѵ�.--%>
    <sql:param value="${param.id}"/>
  </sql:update> 
</c:catch>

<result>
  <c:choose>
    <%-- ���� �߻� --%>
    <c:when test="${e != null}">
      <code>error</code>
    </c:when>
    <%-- �۾� ������  --%>
    <c:otherwise>
      <code>success</code>
    </c:otherwise>
  </c:choose> 
</result> 
