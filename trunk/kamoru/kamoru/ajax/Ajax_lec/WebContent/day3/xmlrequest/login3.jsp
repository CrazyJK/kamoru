<?xml version="1.0" encoding="euc-kr" ?>
<%@ page language="java" contentType="text/xml; charset=euc-kr" pageEncoding="EUC-KR"%>
<%
	String uid = "";
	String pwd = "";
  	uid = request.getParameter("UID");
  	pwd = request.getParameter("PWD");
  	System.out.println("uid = " + uid);
  	System.out.println("pwd = " + pwd);
  	
    out.println("<result>");
  	if ("aaa".equals(uid)) {
  		if ("bbb".equals(pwd)) {
  			out.print("<errorCode>1</errorCode><msg>홍길동</msg>");
  		} else{
  			//비밀번호가 잘못된 경우 
  			out.print("<errorCode>2</errorCode><msg>비밀번호가 잘못되었습니다.</msg>");
  		}
  	} else {
  		//아이디가 존재하지 않는 경우 
  		out.print("<errorCode>3</errorCode><msg>아이디가 존재하지 않습니다.</msg>");
  	}
    out.println("</result>");
%>

