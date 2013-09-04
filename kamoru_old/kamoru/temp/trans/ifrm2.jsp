<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
HttpSession m_Session = request.getSession();

m_Session.removeAttribute("param1");
m_Session.removeAttribute("param2");
m_Session.removeAttribute("param3");

String param1 = request.getParameter("param1");
String param2 = request.getParameter("param2");
String param3 = request.getParameter("param3");

m_Session.setAttribute("param1", param1);
m_Session.setAttribute("param2", param2);
m_Session.setAttribute("param3", param3);

//response.sendRedirect("ifrm3.jsp");

request.setAttribute("attr1", param1 + "-attr");
request.setAttribute("attr2", param2 + "-attr");
request.setAttribute("attr3", param3 + "-attr");

RequestDispatcher dispatcher = request.getRequestDispatcher("ifrm3.jsp");
dispatcher.forward(request, response);
%>    
<%-- 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script type="text/javascript">
function init(){
	location.href = "ifrm3.jsp";
}
</script>
</head>
<body onload="init()">
<h3>ifrm2.jsp</h3>
</body>
</html>
--%>