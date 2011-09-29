<?xml version="1.0" encoding="euc-kr" ?>
<%@ page language="java" contentType="text/xml; charset=EUC-KR" pageEncoding="EUC-KR"%>
<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql" %>

<c:catch var="e">
  <%-- 댓글을 삭제한다 --%>
  <sql:update dataSource="jdbc/TestDB" var="delete">
    delete from tb_comment WHERE id=?
    <%-- EL 구문을 이용하여 요청 인자를 읽어  값을 설정한다.--%>
    <sql:param value="${param.id}"/>
  </sql:update> 
</c:catch>

<result>
  <c:choose>
    <%-- 오류 발생 --%>
    <c:when test="${e != null}">
      <code>error</code>
    </c:when>
    <%-- 작업 성공시  --%>
    <c:otherwise>
      <code>success</code>
    </c:otherwise>
  </c:choose> 
</result> 
