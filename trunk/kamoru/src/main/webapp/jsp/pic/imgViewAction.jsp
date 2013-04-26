<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.io.File" %>
<%
	String cmd = request.getParameter("cmd");
	String a1 = request.getParameter("a1");
	
	System.out.println("cmd=" + cmd);
	System.out.println("a1=" + a1);
	
	String realpath = request.getSession().getServletContext().getRealPath(a1);

	File dir = new File(realpath);
	File[] f = dir.listFiles();
	
	System.out.println("realpath=" + realpath);
	System.out.println("dir=" + dir);
	System.out.println("f.langth=" + f.length);
	
	String outStr = "[";
	for(int i=0; i<f.length; i++){
		String name = f[i].getName();
		outStr += "\"" + name + "\"";
		if(i<f.length-1){
			outStr += ", ";
		}
	}
	outStr += "]";
	out.println("outStr=" + outStr);
%>