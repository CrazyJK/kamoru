<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<%
String uid = request.getParameter("uid");
String pwd = request.getParameter("pwd");

if("aaa".equals(uid)){
	if("111".equals(pwd)){
		//����
		session.setAttribute("uName","ȫ�浿");
		response.sendRedirect("main.jsp");
	}else{
		out.print("<script type='text/javascript'>alert('��й�ȣ�� �߸��Ǿ����ϴ�.');history.back();</script>");
	}
	
}else{
	out.print("<script type='text/javascript'>alert('���̵� �������� �ʽ��ϴ�.');history.back();</script>");
}
%>