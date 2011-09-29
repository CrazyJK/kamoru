<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<%
	request.getSession().invalidate();
	response.sendRedirect("login0.html");
%>