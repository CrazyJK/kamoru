<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<%
session.invalidate();
System.out.println("[" + new java.text.SimpleDateFormat("yyyy-MM-dd' 'hh:mm:ss").format(new java.util.Date()) + "] " + this.getClass().getName());
%>
