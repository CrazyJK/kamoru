

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="EUC-KR"%>
<%
  String name = "";
  //���۹���� POST�̸� ���޵ǿ� ��� ������ ���� UTF8�� ��ȯ�Ѵ�.
  if ("POST".equals(request.getMethod())) {
    request.setCharacterEncoding("utf-8");
  }
  //���޵� name�� ���� ���� ��´�.  
  name = request.getParameter("name");
  //���۹���� GET�̸� �о���� name�� ���� UTF8�� ��ȯ �Ѵ�.
  if ("GET".equals(request.getMethod())) {
    name = new String(name.getBytes("8859_1"), "utf-8");
  }
  System.out.println("name=" + name);
%>
name=<%=name%>
ȣȣȣȣ
