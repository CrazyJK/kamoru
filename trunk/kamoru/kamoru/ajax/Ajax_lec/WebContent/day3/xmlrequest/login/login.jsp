<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<%
String uid = request.getParameter("UID");
String pwd = request.getParameter("PWD");

//db���� uid/pwd Ȯ���� �Ѵ�.

if ("aaa".equals(uid) == true) {
	if ("111".equals(pwd) == true) {
		request.getSession().setAttribute("username", "ȫ�浿");
		response.sendRedirect("main.jsp");
	}
%>	
	<script type="text/javascript">
		alert("��й�ȣ�� �߸��Ǿ����ϴ�");
		history.back(); 
	</script>	
<%	
} else {
%>
	<script type="text/javascript">
	alert("���̵� �������� �ʽ��ϴ�.");
	history.back(); 
	</script>
<%		
}
%>