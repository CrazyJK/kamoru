<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<%
request.setCharacterEncoding("utf-8");
String uid = request.getParameter("uid");
String pwd = request.getParameter("pwd");
String uname = request.getParameter("pwd");
int code = -2;
String name = "";
if("aaa".equals(uid)){
	if("111".equals(pwd)){
		//����
		session.setAttribute("uName","ȫ�浿");
		
		code = 1;
		name = "ȫ�浿";
	}else{
		code = -1;
	}
}
System.out.println("[" + new java.text.SimpleDateFormat("yyyy-MM-dd' 'hh:mm:ss").format(new java.util.Date()) + "] " + this.getClass().getName()); 
%>{code:<%= code %>, name:"<%= name %>"}