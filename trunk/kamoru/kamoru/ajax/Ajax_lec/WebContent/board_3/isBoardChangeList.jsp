<%@page import="com.multicampus.BoardCache"%>
<%@ page language="java" contentType="text/html; charset=EUC-KR" pageEncoding="EUC-KR"%>
<%
//�ɽ��� ���� �ʵ��� �ش��� �����Ѵ�.
response.setHeader("Pragma", "No-cache");
response.setHeader("Expires", "0");
response.setHeader("Cache-Control", "no-cache");

boolean result = false;

result = BoardCache.isEqualsBoardKey(request.getParameter("key"));

%>
[{isExist : <%= result%>}]
