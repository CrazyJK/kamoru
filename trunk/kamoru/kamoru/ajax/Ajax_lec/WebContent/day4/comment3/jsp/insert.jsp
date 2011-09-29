<?xml version="1.0" encoding="euc-kr" ?>
<%@ page language="java" contentType="text/xml; charset=EUC-KR" pageEncoding="EUC-KR"%>
<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql" %>
<%--한글 처리를 위해 utf8 로 변경한다 --%> 
<%request.setCharacterEncoding("utf-8");%>

<c:catch var="e">
  <%-- 댓글을 등록한다 --%>
  <sql:update var="update" dataSource="jdbc/TestDB">
    insert into tb_comment (name, content) values (?, ?)
    <%-- EL 구문을 이용하여 요청 인자를 읽어  값을 설정한다.--%>
    <sql:param value="${param.name}"/>
    <sql:param value="${param.content}"/>
  </sql:update> 
  
  <%--  등록된 신규 아이디를 얻는다  --%>
  <sql:query var="rs" dataSource="jdbc/TestDB">
  select LAST_INSERT_ID() as id
  </sql:query>
  
  <%--  신규 아이디를 출력한다. --%>
  <c:forEach var="row" items="${rs.rows}">
      <c:set var="id" value="${row.id}" />
  </c:forEach>
</c:catch>

<result>
  <c:choose>
    <%-- 오류 발생 --%>
    <c:when test="${e != null}">
      <code>error</code>
      <msg>${e}</msg>
    </c:when>
    <%-- 작업 성공시  --%>
    <c:otherwise>
      <code>success</code>
      <id>${id}</id>
    </c:otherwise>
  </c:choose> 
</result> 
