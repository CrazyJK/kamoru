<%
//response.setCharacterEncoding("EUC-KR");
//response.setContentType("text/html; charset=EUC-KR");

String name = request.getParameter("name");
System.out.println("1" + name);
out.println("1" + name);

String name2 = new String(name.getBytes("iso-8859-1"), "EUC-KR");
System.out.println("2" + name2);
out.println("2" + name2);

String name3 = new String(name.getBytes("iso-8859-1"), "UTF-8");
System.out.println("3" + name3);
out.println("3" + name3);

String name4 = java.net.URLDecoder.decode(name,"EUC-KR"); 
System.out.println("4" + name4);
out.println("4" + name4);

String name5 = java.net.URLDecoder.decode(name,"EUC-KR"); 
System.out.println("5" + name5);
out.println("5" + name5);



%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>Insert title here</title>
</head>
<body>

</body>
</html>