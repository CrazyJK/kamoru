<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.*, kamoru.util.*" %>
<%@ page errorPage="/common/error.jsp"%>
<%@ include file="class.jspf" %>
<%
long startedTime = 0;
if (logger.isDebugEnabled()) startedTime = System.nanoTime();
%>
<% 
String bbsid 	= RequestUtil.getParameter(request, "bbsid");
if(bbsid == null || bbsid.trim().length() == 0) return;

String highlight 	= RequestUtil.getParameter(request,"highlight","",true,"UTF-8");//java.net.URLDecoder.decode(request.getParameter("highlight"));//
String ispopup		= RequestUtil.getParameter(request,"ispopup", "n");

setBBSHitCount(bbsid);

HashMap content = getBBS(bbsid);
if(content == null) return;

%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>게시판</title>
<link rel="stylesheet" type="text/css" href="/bbs/bbs.css">
<script type="text/javascript" src="/js/kamoru.js"></script>
<script type="text/javascript" src="/bbs/bbs.js"></script>
</head>
<body>
<fieldset>
	<legend class="CONTENTTITLE"><%= highlight(content.get("TITLE"), highlight) %>&nbsp;&nbsp;&nbsp;
	<% if(ispopup.equals("y")){ %>
		<button onclick="window.close()">Close</button>
	<% }else{ %>
		<button onclick="windowopen('/bbs/content.jsp?bbsid=<%= content.get("BBSID") %>&highlight=<%= highlight %>&ispopup=y','',800,600)">popup</button>
	<% } %>
	&nbsp;
	</legend>
	<div class="CONTENTTAG">
		<table border="0" width="100%">
			<tr>
				<td colspan="9" class="Bottom">
					<%= highlight(content.get("TAGNAMES"), highlight) %>
					<%= content.get("MODIFYDTIME") == null ? content.get("CREATDTIME") : content.get("MODIFYDTIME") %>
				</td>
				<td colspan="1" align="right" class="Bottom">
					<button onclick="modifyAction('EDIT',<%= content.get("BBSID") %>)">Edit</button>&nbsp;
					<button onclick="modifyAction('DELETE',<%= content.get("BBSID") %>)">Delete</button>
				</td>
			</tr>
		</table>
	</div>
	<div class="CONTENTBODY"><%= highlight(content.get("CONTENT"), highlight) %></div>
</fieldset>
</body>
</html>
<% if (logger.isDebugEnabled()) logger.debug("Elapsed time(ns) " +  (System.nanoTime() - startedTime)); %>
