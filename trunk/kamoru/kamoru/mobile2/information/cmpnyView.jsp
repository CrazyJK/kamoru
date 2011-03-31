<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.hs.frmwk.db.*, java.util.*, java.sql.*" %>
<%@ include file="../common.jsp" %>
<%
	String id = request.getParameter("id");
	
	HashMap data = new HashMap();
	ArrayList plist = new ArrayList();
	try
	{
		ArrayList list = executeQuery(2202, id, requestURI);
		plist = executeQuery(2203, id, requestURI);
		if(list.size() == 1)
		{
			data = (HashMap)list.get(0);
		}
	}
	catch(Exception e)
	{
		e.printStackTrace();
	}
%>
<div id="<%=id %>">
	<div class="toolbar">
		<h1><%=data.get("CMPNY_NAME") %></h1>
		<a class="back" href="#cmpny">이전</a>
	</div>
    <ul class="rounded">
        <li><a href="javascript:void(0);"><%=data.get("CMPNY_NAME") %></a></li>
        <li><a href="javascript:void(0);"><%=nvl(data.get("ADDRESS")) + " " + nvl(data.get("ADVADDRESS")) %></a></li>
        <li><a href="javascript:void(0);"><%=nvl(data.get("HOMEPAGE")) %></a></li>
        <li><a href="javascript:void(0);">영업 <%=data.get("TBI_CHARGE") %></a></li>
        <li><textarea readonly="readonly"><%=nvl(data.get("ETC")) %></textarea></li>
	</ul>	
<%
	if(plist.size() > 0)
	{
%>
	<ul class="rounded">
<%
		for(int i=0; i<plist.size(); i++)
		{
			HashMap map = (HashMap)plist.get(i);
%>	
		<li class="arrow">
			<a href="./information/personView.jsp?id=<%=map.get("PERSON_ID") %>">
				<%=map.get("NAME") %>
			</a>
		</li>
<%
		}
%>
	</ul>
<%	
	}
%>	
</div>    