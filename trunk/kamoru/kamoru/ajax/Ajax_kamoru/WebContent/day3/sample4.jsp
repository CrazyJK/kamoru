<%@ page language="java" contentType="text/plain; charset=UTF-8" pageEncoding="EUC-KR"%>
<%
String name    = "";
String age     = "";
String address = "";
  //���۹���� POST�̸� ���޵ǿ� ��� ������ ���� UTF8�� ��ȯ�Ѵ�.
  if ("POST".equals(request.getMethod())) {
    request.setCharacterEncoding("utf-8");
  }
  //���޵� name�� ���� ���� ��´�.  
  name    = request.getParameter("name");
  age     = request.getParameter("age");
  address = request.getParameter("address");
  //���۹���� GET�̸� �о���� name�� ���� UTF8�� ��ȯ �Ѵ�.
  if ("GET".equals(request.getMethod())) {
    name    = new String(name.getBytes("8859_1"), "utf-8");
    address = new String(name.getBytes("8859_1"), "utf-8");
  }
%>
Sample4.jsp�� ���ؼ� ����Ǵ� ����Դϴ�.
�ȳ��ϼ��� <%=name%> ȸ����!!!
�ȳ��ϼ��� <%=name%> ȸ����!!!
�ȳ��ϼ��� <%=name%> ȸ����!!!
�ȳ��ϼ��� <%=name%> ȸ����!!!
ȸ������ �Է��Ͻ� �ڷ�� 
name=<%=name%>
age=<%=age%>
address=<%=address%>
name=<%=name%>
age=<%=age%>
address=<%=address%>
�Դϴ� .
