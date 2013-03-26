<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
String param1 = (String)session.getAttribute("param1");
String param2 = (String)session.getAttribute("param2");
String param3 = (String)session.getAttribute("param3");
System.out.format("param1:%s param2:%S param3:%s%n", param1, param2, param3);

String attr1 = (String)request.getAttribute("attr1");
String attr2 = (String)request.getAttribute("attr2");
String attr3 = (String)request.getAttribute("attr3");
System.out.format("attr1:%s attr2:%s attr3:%s%n", attr1, attr2, attr3);


%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script type="text/javascript">
function init(){
	parent.location = "next.jsp";
}
</script>
</head>
<body onload="init()">
<h4>ifrm3.jsp</h4>
param1 : <%=param1 %><br>
param2 : <%=param2 %><br>
param3 : <%=param3 %><br>
</body>
</html>