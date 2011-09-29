<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<%
String uid = request.getParameter("UID");
String pwd = request.getParameter("PWD");
int ret = -2;
String name = "";

//db에서 uid/pwd 확인을 한다.

if ("aaa".equals(uid) == true) {
	if ("111".equals(pwd) == true) {
//		request.getSession().setAttribute("username", "홍길동");
		ret = 0;	//성공
		name = "홍길동";
	} else {
		ret = -1; //비밀번호 잘못됨  
	}
} else {
	ret = -2;//아이디가 존재하지 않습니다.
}
%>
<%=ret%>,<%=name%>
