<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page import="java.sql.*,java.util.*,  kamoru.bg.net.server.biz.*" %>
<%
	String strId = request.getParameter("id");

 	BloodGlucoseBiz biz = new BloodGlucoseBiz();
 	List <String> a = biz.getBloodSugarIdList();
 	List <String[]> b = biz.getBloodSugarList("06a9214036b8a1");
 	
 	System.out.println(">>"+a.size());
 	System.out.println(">>"+b.size());
 	
 // List -> String[]
    //String[] sArrays = b.toArray(new String[b.size()]);
     
   // for(String s : sArrays){
   //     System.out.println(s);
   // }
     
    // String[] -> List
   //ArrayList<String> mNewList = new ArrayList<String>(Arrays.asList(sArrays));
     
   // for(String s : mNewList){
    //    System.out.println(s);
   // }*/

 	
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
   <select>
   	<option value=''></option>
   	<option value='1'>11111111111</option>
   	<option value='2'>222222222222</option>
   </select>
   </td>
 </tr>
 <tr>
 	<td>
 		<table  border =1 cellspacing =0 >
 		<tr>
 			<td width ='200' align="center">측정일자</td>
 			<td width ='100' align="center">혈당값</td>
 		</tr>	
 		<tr>
 			<td align="center">2012-05-12 05:01:03</td>
 			<td align="center">112</td>
 		</table>
 	</td>
 </tr>
</table>
</form>
</body>
</html>