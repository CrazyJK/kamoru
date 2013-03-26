<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.hs.frmwk.db.*, java.util.*" %>
<%@ include file="../common.jsp" %>
<%
	String id = request.getParameter("id");
	ArrayList list = new ArrayList();
	try
	{
		list = executeQuery(3105, id, requestURI);
	}
	catch(Exception e)
	{
		e.printStackTrace();
	}
%>
<div id="rqstView-<%=id %>">
	<div class="toolbar">
		<h1>문의 내용</h1>
		<a class="back" href="#">이전</a>
	</div>
<%
	if(list.size() > 0)
	{
		HashMap data = (HashMap)list.get(0);
%>	
    <ul class="rounded">
    	<li>
    		<a href="javascript:void(0)">
	    		<%=data.get("CMPNY_NAME") %> <%=data.get("PRODUCT") %> <%=data.get("VERSION") %>
	    	</a>
	    </li>
    	<li><a href="javascript:void(0)"><%=data.get("SUBJECT") %></a></li>
    	<li>
	    	<table>
	    		<tr>
	    			<td style="color:white;">
	    				<%=nvl(data.get("CONTENT")).toString().replaceAll("\n", "<br/>") %>
	    			</td>
	    		</tr>
	    	</table>
    	</li>
    	<li>
	    	<table>
	    		<tr>
	    			<td style="color:white;">
	    				<%=nvl(data.get("ANSWER")).toString().replaceAll("\n", "<br/>") %>
	    			</td>
	    		</tr>
	    	</table>
    	</li>
	</ul>
<%
	}
%>		
</div>