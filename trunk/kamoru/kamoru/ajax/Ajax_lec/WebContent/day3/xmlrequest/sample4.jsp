<%@ page language="java" contentType="text/plain; charset=UTF-8" pageEncoding="EUC-KR"%>
<%
String name    = "";
String age     = "";
String address = "";
  //전송방식이 POST이면 전달되온 모든 인자의 값을 UTF8라 변환한다.
  if ("POST".equals(request.getMethod())) {
    request.setCharacterEncoding("utf-8");
  }
  //전달된 name의 인자 값을 얻는다.  
  name    = request.getParameter("name");
  age     = request.getParameter("age");
  address = request.getParameter("address");
  //전송방식이 GET이면 읽어들인 name의 값을 UTF8로 변환 한다.
  if ("GET".equals(request.getMethod())) {
    name    = new String(name.getBytes("8859_1"), "utf-8");
    address = new String(name.getBytes("8859_1"), "utf-8");
  }
%>
Sample4.jsp에 의해서 실행되는 결과입니다.
안녕하세요 <%=name%> 회원님!!!
안녕하세요 <%=name%> 회원님!!!
안녕하세요 <%=name%> 회원님!!!
안녕하세요 <%=name%> 회원님!!!
회원님이 입력하신 자료는 
name=<%=name%>
age=<%=age%>
address=<%=address%>
name=<%=name%>
age=<%=age%>
address=<%=address%>
입니다 .
