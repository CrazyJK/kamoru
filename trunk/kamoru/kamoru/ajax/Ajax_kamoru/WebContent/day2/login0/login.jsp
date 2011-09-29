<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<%
String uid = request.getParameter("uid");
String pwd = request.getParameter("pwd");

if("aaa".equals(uid)){
	if("111".equals(pwd)){
		//성공
		session.setAttribute("uName","홍길동");
		response.sendRedirect("main.jsp");
	}else{
		out.print("<script type='text/javascript'>alert('비밀번호가 잘못되었습니다.');history.back();</script>");
	}
	
}else{
	out.print("<script type='text/javascript'>alert('아이디가 존재하지 않습니다.');history.back();</script>");
}
%>