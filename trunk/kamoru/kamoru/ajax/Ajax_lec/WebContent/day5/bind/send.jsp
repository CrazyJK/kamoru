<%@ page language="java" contentType="text/plain; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core" %>
전송방법 : ${pageContext.request.method } 

<%

request.setCharacterEncoding("utf-8");
String msg1 = request.getParameter("msg1");
String msg2 = request.getParameter("msg2");

if ("GET".equals(request.getMethod())) {
  //전송방식이 GET이면 읽어들인 name의 값을 UTF8로 변환 한다.
  if ("GET".equals(request.getMethod())) {
    msg1 = new String(msg1.getBytes("8859_1"), "utf-8");
    msg2 = new String(msg2.getBytes("8859_1"), "utf-8");
  }
}

out.println("msg1 = " + msg1);
out.println("msg2 = " + msg2);
%>
