<%@ page language="java" contentType="text/plain; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
// 캐쉬 방지
response.setHeader("Pragma","No-cache");
response.setHeader("Expires","0");
response.setHeader("Cache-Control","no-cache");

// 파라미터 받기 GET이냐, POST냐에 따라 다름
String reqMethod = request.getMethod();
String reqParam  = null;

// POST일 경우 인코딩 설정, XMLHttpRequest 의 POST 전송은 UTF-8
if ("POST".equals(reqMethod)) {
	request.setCharacterEncoding("utf-8");
}
reqParam = request.getParameter("param1");

// GET일 경우, 값을 8859_1에서 UTF-8로 재조합
if("GET".equals(request.getMethod())){
	//reqParam = new String(request.getParameter("param1").getBytes("8859_1"), "UTF-8");	
}
System.out.println("reqParam :" + reqParam);

%>
<%= request.getQueryString() %><br/>
METHOD : <%= reqMethod %><br/>
Param : <%= reqParam %>
 
<% System.out.println("[" + this.getClass().getName() + "] " + new java.util.Date()); %>
