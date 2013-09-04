<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.io.*, java.util.*" %>
<%
final String baseDir = "E:/av";
ArrayList<HashMap<String, String>> list = getFileList(baseDir);
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title> List file </title>
<style type="text/css">
.size {
	color:red;
}
.path {
	color:blue;
}
</style>
<script type="text/javascript"></script>
</head>
<body>
<header>
	<hgroup>
		<h1>File list</h1>
	</hgroup>
</header>

<section>
	<header><%=baseDir %> <%=list.size() %> files</header>
<ul> 
	<% for(int i=0; i<list.size(); i++){ HashMap<String, String> file = (HashMap<String, String>)list.get(i);%>
	<li><span class="path"><%=file.get("PATH") %></span>\<%=file.get("NAME") %> <span class="size"><%=file.get("SIZE") %></span> 
	<% } %>
</ul>
</section>
</body>
</html>

<%!
//ArrayList getFileInfoList


ArrayList<HashMap<String, String>> getFileList(String dir)throws Exception
{
	ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
	File[] files = new File(dir).listFiles();
	for(int i=0; i<files.length; i++)
	{
		if(files[i].isDirectory())
		{
			list.addAll(getFileList(files[i].getAbsolutePath()));
		}
		else
		{
			String name = files[i].getName().toLowerCase();
			if(name.endsWith("avi") || name.endsWith("wmv") || name.endsWith("mpg") || name.endsWith("mpeg") || name.endsWith("mkv"))
			{
				list.add(getFileInfo(files[i]));
			}
		}
	}
	return list;
}
HashMap<String, String> getFileInfo(File f)throws Exception
{
	HashMap<String, String> map = new HashMap<String, String>();
	map.put("NAME", f.getName());
	map.put("SIZE", String.valueOf(f.length()));
	map.put("PATH", f.getParent() );
	return map;
}
%>