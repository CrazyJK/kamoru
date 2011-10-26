<%@ page language="java" contentType="text/plain; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.oreilly.servlet.*, com.oreilly.servlet.multipart.*, java.io.*, java.util.*, java.sql.*" %>
<%@ include file="common.jspf" %>
<%
String folder = request.getParameter("folder");

MultipartRequest multi = null;
try{
	multi = new MultipartRequest(request,filedownpath,maxfilesize,encoding,new DefaultFileRenamePolicy());
	File   f = multi.getFile("Filedata");
	String name = f.getName();
	String path = f.getPath().replaceAll(name, "");
	String size = getKB(f.length());
	String date = new java.text.SimpleDateFormat("yyyy-MM-dd' 'HH:mm:ss").format(new java.util.Date(f.lastModified()));
%>{ ErrorMsg : "",
	FileName : "<%= name %>",
	FilePath : "<%= path %>",
	FileSize : "<%= size %>",
	FileDate : "<%= date %>"}<%
}catch(IOException ioe){
	ioe.printStackTrace();
	System.out.println(ioe.getMessage());
%>{ErrorMsg : "<%= ioe.getMessage() %>"}<%	
}
%>
