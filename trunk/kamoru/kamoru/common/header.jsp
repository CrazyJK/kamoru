<%
System.out.println(
		"[" + new java.text.SimpleDateFormat("yyyy-MM-dd' 'HH:mm:ss").format(new java.util.Date()) + "] "
	+ request.getRequestURI()		
); 
%>