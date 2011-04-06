<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.hs.frmwk.db.*, java.util.*" %>
<%@ include file="../common.jsp" %>
<%
	String sch = request.getParameter("sch");
	debug("search=" + sch);
	
	ArrayList list = new ArrayList();
	if(sch != null && sch.trim().length() > 0)
	{
		try
		{
			ArrayList param = new ArrayList();
			param.add(sch);
			param.add(sch);
			param.add(sch);
			list = executeQuery(4001, param, requestURI);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
%>
<form id="faqlist" action="./faq/faq.jsp" method="POST" class="form">
	<div class="toolbar">
		<h1>F A Q</h1>
		<a class="back" href="#home">이전</a>
        <a class="button submit" id="infoButton" href="#">검색</a>
	</div>
    <ul class="edit rounded">
		<li><input type="text" name="sch" placeholder="검색어를 입력하세요" id="some_name" value="<%=nvl(sch) %>"/></li>
	</ul>
	
<%
if(sch != null && sch.trim().length() > 0)
{
%>	
	<ul class="rounded">
<% 
	if(list.size() > 0) 
	{ 
		for(int i=0; i<list.size(); i++)
		{ 
			HashMap data = (HashMap)list.get(i);
%>
		<li class="arrow">
			<a href="./faq/faqView.jsp?id=<%=data.get("FAQ_ID") %>">
				<%=data.get("TITLE") %>
			</a>
		</li>
<% 
		} 
	} 
	else
	{
%>
		<li><a href="javascript:void(0);">결과가 없습니다</a></li>
<%
	}
%>	
	</ul>
<%
}
%>	
</form>
