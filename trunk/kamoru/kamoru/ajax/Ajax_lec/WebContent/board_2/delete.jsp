<?xml version="1.0" encoding="euc-kr" ?>
<%@ page language="java" contentType="text/xml; charset=EUC-KR" pageEncoding="EUC-KR"%>
<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql" %>

<c:catch var="e">
  <%-- ����� �����Ѵ� --%>
  <sql:update dataSource="jdbc/MySQLDB" var="delete">
    delete from t_board WHERE board_id=?
    <%-- EL ������ �̿��Ͽ� ��û ���ڸ� �о�  ���� �����Ѵ�.--%>
    <sql:param value="${param.board_id}"/>
  </sql:update> 
</c:catch>

