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
  			out.print("[{status:1, msg:'ȫ�浿'}]");
  		} else{
  			//��й�ȣ�� �߸��� ��� 
  			out.print("[{status:2, msg:'��й�ȣ�� �߸��Ǿ����ϴ�.'}]");
  		}
  	} else {
  		//���̵� �������� �ʴ� ��� 
  		out.print("[{status:3, msg:'���̵� �������� �ʽ��ϴ�.'}]");
  	}
%>

