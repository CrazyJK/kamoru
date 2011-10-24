<%@ page language="java" contentType="text/plain; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
String clientIP = request.getRemoteAddr();
out.println(clientIP);
%>