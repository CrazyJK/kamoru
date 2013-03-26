<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.hs.frmwk.db.*, java.util.*, java.sql.*" %>
<%@ include file="../common.jsp" %>
<%
String id = request.getParameter("id");
HashMap data = new HashMap();
try
{
	ArrayList list = executeQuery(2102, id, requestURI);
	data = (HashMap)list.get(0);
}
catch(Exception e)
{
	e.printStackTrace();
}
%>
<div id="<%=id %>">
	<div class="toolbar">
		<h1><%=data.get("NAME") %></h1>
		<a class="back" href="#person">이전</a>
	</div>
    <ul class="rounded">
        <li><a href="./information/cmpnyView.jsp?id=<%=data.get("CMPNY_ID") %>"><%=data.get("CMPNY_NAME") %></a></li>
        <li><span style="color:white"><%=data.get("DEPT_NAME") + " " + data.get("POSITION") %></span></li>
        <li><a href="javascript:void(0);"><%=data.get("OPERATION") %></a></li>
        <li><a href="tel:<%=data.get("OFFICE_TEL") %>"><%=data.get("OFFICE_TEL") %></a></li>
        <% if(data.get("CELLULAR_PHONE") != null){ %>
        <li><a href="tel:<%=data.get("CELLULAR_PHONE") %>"><%=data.get("CELLULAR_PHONE") %></a></li><% } %>
        <% if(data.get("EMAIL") != null) {%>
        <li><a href="email:<%=data.get("EMAIL") %>"><%=data.get("EMAIL") %></a></li><% } %>
	</ul>
</div>