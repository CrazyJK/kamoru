<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<%
String uid = request.getParameter("UID");
String pwd = request.getParameter("PWD");
int ret = -2;
String name = "";

//db���� uid/pwd Ȯ���� �Ѵ�.

if ("aaa".equals(uid) == true) {
	if ("111".equals(pwd) == true) {
//		request.getSession().setAttribute("username", "ȫ�浿");
		ret = 0;	//����
		name = "ȫ�浿";
	} else {
		ret = -1; //��й�ȣ �߸���  
	}
} else {
	ret = -2;//���̵� �������� �ʽ��ϴ�.
}
%>
<%=ret%>,<%=name%>
