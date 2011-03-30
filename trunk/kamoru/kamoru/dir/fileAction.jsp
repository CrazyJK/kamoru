<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.net.*, java.io.*" %>
<%
String mode = request.getParameter("mode");
String uri = request.getParameter("uri");

if("PLAY".equals(mode))
{
	uri = uri.substring(6);
	String[] cmd = new String[]{"C:\\Program Files (x86)\\GRETECH\\GomPlayer\\GOM.exe", uri};
	System.out.println("[" + cmd[0] + "] - [" + cmd[1] + "]");
	Process process = new ProcessBuilder(cmd).start();
}
else if("DEL".equals(mode))
{
	uri = uri.substring(6);
	File f = new File(uri);
	System.out.println(uri + " delete " + f.delete());
	
}
	

//C:\Program Files (x86)\GRETECH\GomPlayer\GOM.exe file:/E:/av/SOE-559.avi
//C:\Program Files (x86)\GRETECH\GomPlayer\GOM.exe E:/av/SOE-559.avi
%>