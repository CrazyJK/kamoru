

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="EUC-KR"%>
<%
  String name = "";
  //전송방식이 POST이면 전달되온 모든 인자의 값을 UTF8라 변환한다.
  if ("POST".equals(request.getMethod())) {
    request.setCharacterEncoding("utf-8");
  }
  //전달된 name의 인자 값을 얻는다.  
  name = request.getParameter("name");
  //전송방식이 GET이면 읽어들인 name의 값을 UTF8로 변환 한다.
  if ("GET".equals(request.getMethod())) {
    name = new String(name.getBytes("8859_1"), "utf-8");
  }
  System.out.println("name=" + name);
%>
name=<%=name%>
호호호호
