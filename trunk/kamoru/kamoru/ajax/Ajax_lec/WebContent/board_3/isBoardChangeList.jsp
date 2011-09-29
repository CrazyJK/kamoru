<%@page import="com.multicampus.BoardCache"%>
<%@ page language="java" contentType="text/html; charset=EUC-KR" pageEncoding="EUC-KR"%>
<%
//케싱이 되지 않도록 해더를 설정한다.
response.setHeader("Pragma", "No-cache");
response.setHeader("Expires", "0");
response.setHeader("Cache-Control", "no-cache");

boolean result = false;

result = BoardCache.isEqualsBoardKey(request.getParameter("key"));

%>
[{isExist : <%= result%>}]
