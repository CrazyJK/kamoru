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
  			out.print("<errorCode>1</errorCode><msg>ȫ�浿</msg>");
  		} else{
  			//��й�ȣ�� �߸��� ��� 
  			out.print("<errorCode>2</errorCode><msg>��й�ȣ�� �߸��Ǿ����ϴ�.</msg>");
  		}
  	} else {
  		//���̵� �������� �ʴ� ��� 
  		out.print("<errorCode>3</errorCode><msg>���̵� �������� �ʽ��ϴ�.</msg>");
  	}
    out.println("</result>");
%>

