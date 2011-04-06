<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.hs.frmwk.db.*, java.util.*" %>
<%@ include file="../common.jsp" %>
<% 
	String name = request.getParameter("name");
	debug("name=" + name);
	
	ArrayList list = new ArrayList();
	if(name != null && name.trim().length() > 0)
	{ 
		try
		{
			list = executeQuery(2201, name, requestURI);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
%>
<form id="cmpny" action="./information/cmpny.jsp" method="POST" class="form">
	<div class="toolbar">
		<h1>업체정보</h1>
		<a class="back" href="#ic">이전</a>
	</div>
    <ul class="edit rounded">
		<li><input type="text" name="name" placeholder="이름을 입력하세요" id="some_name" value="<%=name == null ? "" : name %>"/></li>
	</ul>
	<a style="margin:0 10px;color:rgba(0,0,0,.9)" href="#" class="submit whiteButton">Submit</a>
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
			<a href="./information/cmpnyView.jsp?id=<%=data.get("CMPNY_ID") %>">
				<%=data.get("CMPNY_NAME") %>
			</a>
		</li>
<% 		
		} 
%>
	</ul>
<% 
	} 
%>
</form>
