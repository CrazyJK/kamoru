<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<%@ page import="com.multicampus.*" %>    
<%
response.setHeader("Pragma","No-cache");
response.setHeader("Expires","0");
response.setHeader("Cache-Control","no-cache");

String board_key = request.getParameter("board_key");
String result = "-1";
//System.out.println("board_key=>"+board_key);
if(BoardCache.isEqualsBoardKey(board_key)){
	result = "1";	
}
//System.out.println("isReloadBoard.jsp result=" + result);
%>{code:<%= result %>}
