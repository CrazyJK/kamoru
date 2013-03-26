<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.hs.frmwk.db.*, java.util.*" %>
<%@ include file="../common.jsp" %>
<%
	String sch = request.getParameter("sch");
	debug("search=" + sch);
	
	ArrayList person  = new ArrayList();
	ArrayList cmpny   = new ArrayList();
	ArrayList install = new ArrayList();
	if(sch != null && sch.trim().length() > 0)
	{
		try
		{
			person  = executeQuery(2101, sch, requestURI);
			cmpny   = executeQuery(2201, sch, requestURI);
			install = executeQuery(2301, sch, requestURI);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
%>
<form id="install" action="./information/ic.jsp" method="POST" class="form">
	<div class="toolbar">
		<h1>Information</h1>
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
	<h2>Person</h2>
	<ul class="rounded">
<% 
	if(person.size() > 0) 
	{ 
		for(int i=0; i<person.size(); i++)
		{ 
			HashMap data = (HashMap)person.get(i);
%>
		<li class="arrow">
			<a href="./information/personView.jsp?id=<%=data.get("PERSON_ID") %>">
				<%=data.get("NAME") %> - <%=data.get("CMPNY_NAME") %>
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
	<h2>Company</h2>
	<ul class="rounded">
<% 
	if(cmpny.size() > 0) 
	{ 
		for(int i=0; i<cmpny.size(); i++)
		{ 
			HashMap data = (HashMap)cmpny.get(i);
%>
		<li class="arrow">
			<a href="./information/cmpnyView.jsp?id=<%=data.get("CMPNY_ID") %>">
				<%=data.get("CMPNY_NAME") %>
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
	<h2>Install</h2>
	<ul class="rounded">
<% 
	if(install.size() > 0) 
	{ 
		for(int i=0; i<install.size(); i++)
		{ 
			HashMap data = (HashMap)install.get(i);
%>
		<li class="arrow">
			<a href="./information/installView.jsp?id=<%=data.get("INSTALL_ID") %>">
				<%=data.get("CMPNY_NAME") %> - <%=data.get("TITLE") %>
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
