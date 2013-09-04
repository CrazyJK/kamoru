<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.net.*, java.io.*" %>
<%
long startNanoTime = System.nanoTime();

request.setCharacterEncoding("UTF-8");

String mode 	= request.getParameter("mode");
String uri 		= request.getParameter("uri");
String player 	= request.getParameter("player");

System.out.format("mode=[%s], uri=[%s], player=[%s]%n", mode, uri, player);

if("PLAY".equals(mode))
{
	uri = uri.substring(6);
	String[] cmd = new String[]{player, uri};
	System.out.println("[" + cmd[0] + "] - [" + cmd[1] + "]");
	Process process = new ProcessBuilder(cmd).start();
}
else if("DEL".equals(mode))
{
	uri = uri.substring(6);
	File f = new File(uri);
	System.out.println("file exist " + f.exists() + " - " + f.getAbsolutePath());
	if(f.delete()) {
		System.out.println(uri + " delete ");
	}else{
		throw new Exception("Error:" + uri);
	}
}
	
System.out.println("[" + request.getRequestURI() + "] " + mode + " elapsed time : " + ((System.nanoTime() - startNanoTime) / 1000000) + "ms");
%>