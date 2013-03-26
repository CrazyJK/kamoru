<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.hs.frmwk.db.*, java.util.*" %>
<%@ include file="../common.jsp" %>
<%
	String pg = getParam(request, "p", "1");
debug("Page=" + pg);
	int contentCnt = 7;
	int p = Integer.parseInt(pg);
	int sp = (p - 1 ) * contentCnt + 1;
	int ep = (p - 1 ) * contentCnt + contentCnt;
	
	ArrayList list = new ArrayList();
	try
	{
		ArrayList param = new ArrayList();
		param.add(userId);
		param.add(String.valueOf(sp));
		param.add(String.valueOf(ep));
		list = executeQuery(3106, param, requestURI);
	}
	catch(Exception e)
	{
		e.printStackTrace();
	}
%>
<div id="donelist-<%=pg %>">
	<div class="toolbar">
		<h1>문의 완료</h1>
		<a class="back" href="#service">이전</a>
	</div>
	
<%
	if(list.size() > 0)
	{
		int totalCnt = 0;
		int currCnt = 0;
%>	
    <ul class="rounded">
<%
		for(int i=0; i<list.size(); i++)
		{
			HashMap data = (HashMap)list.get(i);
			totalCnt = Integer.parseInt(data.get("TOTAL").toString());
			currCnt  = Integer.parseInt(data.get("RNUM").toString());
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
	<ul class="individual">
<%
		if(p > 1)
		{
%>    
		<li><a href="#donelist-<%=p - 1 %>">◁ <%=p -1 %> Page</a></li>
<%
		}else{
%>		
		<li><a href="#donelist-0">First Page</a></li>
<%
		}
		if(totalCnt > currCnt)
		{
%>
		<li><a href="./serviceflow/rqstdonelist.jsp?p=<%=p + 1 %>"><%=p + 1 %> Page ▷</a></li>
<%
		}else{
%>
		<li><a href="#donelist-0">Last Page</a></li>
<%
		}
%>
	</ul>
	
<%
	}
%>		
</div>