<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
String dscpt = request.getParameter("dscpt");
dscpt = new String(dscpt.getBytes("ISO-8859-1"), "UTF-8");
System.out.println(dscpt);
//String[] arr = dscpt.split(";");

%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>설명 보여주기</title>
<style type="text/css">
body {background-color:#fff}
body,form {margin:0px;}
body,td,div,input,select {font-family:굴림,Arial,Verdana; font-size:12px; color:#333;}
img {border:0;}
ul,li {list-style:none; margin:0px; padding:0px;}
dl,dd {margin:0px; padding:0px;}

a:link    {color:#333; text-decoration:none;} 
a:visited {color:#333; text-decoration:none;}
a:hover   {color:#333; text-decoration:underline;}
</style>
<script type="text/javascript">
</script>
</head>
<body>
<span style="font-weight:bold;">CS3팀.</span><br>
Site : <br/>
Task : <br/>
Address : <br/>
Task : <br/>
Start : <br/>
Arrival : <br/>
Dscpt : <br/>
</body>
</html>