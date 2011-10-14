<%@ page language="java" contentType="text/plain; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.io.*, java.util.*" %>
<%
long startNanoTime = System.nanoTime();

String path   = request.getParameter("p");
String filter = request.getParameter("f");

StringBuilder sb = new StringBuilder();

try
{
	File dirF = new File(path);
	if(!dirF.exists() || !dirF.isDirectory())
	{
		sb.append(path + " is not a directory or no exists");
	}
	else
	{
		ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
		getFilelist(list, path, filter);

		if(list.size() == 0)
		{
			sb.append(path + " is empty");			
		}
		else
		{
			sb.append("[");
			for(int i=0; i<list.size(); i++)
			{
				HashMap<String, String> fileinfo = (HashMap<String, String>)list.get(i);
				sb.append("{");
				sb.append("NAME : \"").append(fileinfo.get("NAME")).append("\", ");
				sb.append("SIZE : \"").append(fileinfo.get("SIZE")).append("\", ");
				sb.append("PATH : \"").append(fileinfo.get("PATH")).append("\", ");
				sb.append("URI  : \"").append(fileinfo.get("URI") ).append("\", ");
				sb.append("MODIFIED : \"").append(fileinfo.get("MODIFIED")).append("\"");
			
				sb.append("}");
				if(i < list.size()-1)
				{
					sb.append(",");
				}
			}
			sb.append("]");
		}
	}
}
catch(Exception e)
{
	throw e;
}
out.println(sb.toString());
System.out.println("[" + request.getRequestURI() + "]  elapsed time : " + ((System.nanoTime() - startNanoTime) / 1000000) + "ms");

%>

<%!

void getFilelist(ArrayList<HashMap<String, String>> list, String dir, String filter) throws Exception
{
	String[] filterArr = filter.split(" ");
	
	
	ArrayList<String> dirList = new ArrayList<String>();
	File[] files = new File(dir).listFiles();
	for(File f: files)
	{
		if(f.isDirectory())
		{
			dirList.add(f.getAbsolutePath());
		}
		else
		{
			String name = f.getName().toLowerCase();
			for(int i=0; i<filterArr.length; i++)
			{
				if(name.endsWith(filterArr[i]))
				{
					list.add(getFileInfo(f));
				}
			}
		}
	}
	
	for(String path: dirList)
	{
		getFilelist(list, path, filter);
	}
}

HashMap<String, String> getFileInfo(File f) throws Exception
{
	String path = f.getParent().replace('\\','/');
	
	HashMap<String, String> map = new HashMap<String, String>();
	map.put("NAME", f.getName());
	map.put("SIZE", String.valueOf(f.length()));
	map.put("PATH", path);
	map.put("URI",  String.valueOf(f.toURI()));
	map.put("MODIFIED", String.valueOf(f.lastModified()));
	return map;
}
%>