<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.hs.frmwk.db.*, java.util.*" %>
<%@ include file="../common.jsp" %>
<%
	ArrayList list = new ArrayList();
	try
	{
		list = executeQuery(3103, userId, requestURI);
	}
	catch(Exception e)
	{
		e.printStackTrace();
	}
%>
<div id="todolist">
	<div class="toolbar">
		<h1>문의 대기</h1>
		<a class="back" href="#service">이전</a>
	</div>
    <ul class="rounded">
<%
	if(list.size() > 0)
	{
%>	
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
<%
	}
	else
	{
%>	
		<li>업무가 없습니다.</li>
<%
	}
%>	
	</ul>
</div>