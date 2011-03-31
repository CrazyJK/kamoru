<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.hs.frmwk.db.*, java.util.*" %>
<%@ include file="../common.jsp" %>
<%
	ArrayList list = new ArrayList();
	try
	{
		list = executeQuery(3104, userId, requestURI);
	}
	catch(Exception e)
	{
		e.printStackTrace();
	}
%>
<div id="runlist">
	<div class="toolbar">
		<h1>문의 진행</h1>
		<a class="back" href="#service">이전</a>
	</div>
<%
	if(list.size() > 0)
	{
%>	
    <ul class="rounded">
<%
		for(int i=0; i<list.size(); i++)
		{
			HashMap data = (HashMap)list.get(i);
%>    
        <li class="arrow">
        	<a href="./serviceflow/rqstcontentview.jsp?id=<%=data.get("RQST_ID") %>">
        		<%=data.get("CMPNY_NAME") %> - <%=data.get("SUBJECT") %>
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