<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="EUC-KR"%>
<%
	String uid = "";
	String pwd = "";
  	uid = request.getParameter("UID");
  	pwd = request.getParameter("PWD");
  	System.out.println("uid = " + uid);
  	System.out.println("pwd = " + pwd);
  	
  	if ("aaa".equals(uid)) {
  		if ("bbb".equals(pwd)) {
  			out.print("[{status:1, msg:'홍길동'}]");
  		} else{
  			//비밀번호가 잘못된 경우 
  			out.print("[{status:2, msg:'비밀번호가 잘못되었습니다.'}]");
  		}
  	} else {
  		//아이디가 존재하지 않는 경우 
  		out.print("[{status:3, msg:'아이디가 존재하지 않습니다.'}]");
  	}
%>

