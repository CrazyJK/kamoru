<%@ page language="java" contentType="text/plain; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.io.*, java.util.*, kamoru.util.video.*" %>
<%
long startNanoTime = System.nanoTime();

String path   	= request.getParameter("p");
String filter 	= request.getParameter("f");
String sort 	= request.getParameter("s");
String reverse 	= request.getParameter("r");

FileManager fm = new FileManager(path, filter);
try{
	fm.sort(Integer.parseInt(sort), Boolean.parseBoolean(reverse));	
}catch(Exception e){}
out.println(fm.jsonText());
%>
