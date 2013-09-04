<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%
System.out.println("ArrayList start" + new java.util.Date().toString());
ArrayList list = new ArrayList();
int count = 0;
for(int i=0 ; i<50000 ; i++){
	HashMap record = new HashMap();
	for(int j=0 ; j<6 ; j++){
		record.put("aaaaa"+j, "bbbbb");
	}

	list.add(record);
	if(count++ % 1000 == 0){
		System.out.println("next " + count + ":" + new java.util.Date().toString());
	}
}
System.out.println("ArrayList end" + new java.util.Date().toString());

%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>

</body>
</html>