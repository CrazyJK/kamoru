<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<%
String uid = request.getParameter("UID");
String pwd = request.getParameter("PWD");

//db에서 uid/pwd 확인을 한다.

if ("aaa".equals(uid) == true) {
	if ("111".equals(pwd) == true) {
		request.getSession().setAttribute("username", "홍길동");
		response.sendRedirect("main.jsp");
	}
%>	
	<script type="text/javascript">
		alert("비밀번호가 잘못되었습니다");
		history.back(); 
	</script>	
<%	
} else {
%>
	<script type="text/javascript">
	alert("아이디가 존재하지 않습니다.");
	history.back(); 
	</script>
<%		
}
%>