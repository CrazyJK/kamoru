<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.hs.frmwk.db.*, java.util.*, java.sql.*" %>
<%@ include file="../common.jsp" %>
<%
	String id = request.getParameter("id");
	
//	com.hs.csd.ProductInfo pi = new com.hs.csd.ProductInfo();
	HashMap data = new HashMap();
	try
	{
		ArrayList list = executeQuery(2302, id, requestURI);
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
		<a class="back" href="#install">이전</a>
	</div>
    <ul class="rounded">
        <li><a href="javascript:void(0)"><%=data.get("TITLE") %></a></li>
        <li><a href="javascript:void(0)">지원담당자 : <%=data.get("INSTL_CHRGR") %></a></li>
        <li><a href="javascript:void(0)"><%--pi.getProductName(data.get("PRODUCT_ID").toString()) --%></a></li>
        <li><a href="javascript:void(0)"><%--pi.getVersion(data.get("VERSION_ID").toString()) --%></a></li>
        <li><a href="javascript:void(0)">설치일 : <%=data.get("INSTL_DATE").toString().substring(0, 10) %></a></li>
        <li><a href="javascript:void(0)"><%=data.get("INSTALL_TYPE") %></a></li>
        <li><textarea readonly="readonly"><%=nvl(data.get("USEDESC")) + "\n" + nvl(data.get("ETC")) %></textarea></li>
	</ul>	
</div>    