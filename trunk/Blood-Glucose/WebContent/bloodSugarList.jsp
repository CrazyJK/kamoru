<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page import="java.sql.*,java.util.*,  kamoru.bg.net.server.biz.*" %>
<%
	String strId = request.getParameter("id");

 	BloodGlucoseBiz biz = new BloodGlucoseBiz();
 	List <String> a = biz.getBloodSugarIdList();
 	List <String> b = biz.getBloodSugarList(strId);
 	
 	System.out.println(">>"+a.size());
 	System.out.println(">>"+b.size());
 	
 %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
<title>Insert title here</title>
</head>
<body>
<form >
<table width =800>
 <tr>
   <td>
   </td>
 </tr>
 <tr>
 	<td>
 	</td>
 </tr>
</table>
</form>
</body>
</html>