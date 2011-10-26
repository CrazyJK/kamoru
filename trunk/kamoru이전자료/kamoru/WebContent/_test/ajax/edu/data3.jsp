<%@ page language="java" contentType="text/plain; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
String data = request.getParameter("data");

if(data != null && data.trim().length() > 0){
	 out.println("전달받은 데이터[" + data + "] 처리 완료");
}else{
	out.println("전달된 데이터가 없음");
}
%>