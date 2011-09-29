<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<%
String id=request.getParameter("id");
String age=request.getParameter("age");

id.substring(10,10);

out.println("id=" + id + "<br/>");
out.println("age=" + age + "<br/>");
%>