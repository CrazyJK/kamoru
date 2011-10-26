<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.hs.frmwk.db.*, java.io.*" %>
<%
BWDBHelper helper = null;
int runCount = 0;
try
{
	helper = BWDBHelperFactory.getBWDBHelper();
	
	helper.setPreparedSQL("SELECT title, content, fn_gettagname(tags) AS tag FROM bbs WHERE state = 'N'");
	helper.executeQuery();
	while(helper.next()){
		String title   = helper.getRSString("title");
		String content = helper.getRSString("content");
		String tag     = helper.getRSString("tag");

		writeHTML(title, content, tag.trim());
		runCount++;
	}
}
catch(Exception e)
{
	e.printStackTrace();
}
finally
{
	if(helper != null) helper.close();
}
%>
<%!
final String filepath = "R:\\bbs\\";
public void writeHTML(String title, String content, String tag) throws IOException
{
	String titletag = "[" + tag.replaceAll(" ", "][") + "] ";

	String filename = title.trim().replaceAll("/", ",").replaceAll(":", " ");
	
	PrintWriter writer = new PrintWriter(new FileWriter(filepath + titletag + filename + ".html"), true);
	writer.println("<html>");
//	writer.println("<title>" + tag + "</title>");
	writer.println("<body>");
	writer.println(content);
	writer.println("<ul><li>" + tag + "</li></ul>");
	writer.println("</body>");
	writer.println("</html>");

	writer.flush();
	writer.close();
}
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>게시판 내보내기</title>
</head>
<body>
<%= runCount %>개의 게시물이 <%= filepath %>로 저장되었습니다.
</body>
</html>
