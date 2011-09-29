<?xml version="1.0" encoding="euc-kr" ?>
<%@ page language="java" contentType="text/xml; charset=EUC-KR" pageEncoding="EUC-KR"%>
<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql" %>

<%@page import="com.multicampus.BoardCache"%>
<%
//�ɽ��� ���� �ʵ��� �ش��� �����Ѵ�.
response.setHeader("Pragma", "No-cache");
response.setHeader("Expires", "0");
response.setHeader("Cache-Control", "no-cache");
%>
<c:catch var="e">
  <%-- ����� �����Ѵ� --%>
  <sql:update dataSource="jdbc/MySQLDB" var="delete">
    delete from t_board WHERE board_id=?
    <%-- EL ������ �̿��Ͽ� ��û ���ڸ� �о�  ���� �����Ѵ�.--%>
    <sql:param value="${param.board_id}"/>
  </sql:update> 
</c:catch>

<% BoardCache.update(request.getParameter("board_id")); %>
