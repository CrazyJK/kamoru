<%@ page language="java" contentType="text/text; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<%
request.setCharacterEncoding("utf8");

String id = request.getParameter("id");
String text = request.getParameter("text");

if ("GET".equals(request.getMethod())) {
  text = new String(text.getBytes("8859_1"), "utf8");
}

System.out.println("id = " + id);
System.out.println("text = " + text);
///dbÀÛ¾÷ 

%>