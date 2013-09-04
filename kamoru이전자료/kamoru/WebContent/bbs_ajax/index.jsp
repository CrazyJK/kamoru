<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.*, kamoru.util.StringUtil, kamoru.util.RequestUtil"%>
<%@ page errorPage="/common/error.jsp"%>
<%@ include file="class.jspf" %>
<%
long startedTime = 0;
if (logger.isDebugEnabled()) startedTime = System.nanoTime();

%>

<%
String selectTag 	= RequestUtil.getParameter(request, "selectTag", "");
String searchWord 	= RequestUtil.getParameter(request, "searchWord","");
String oper 		= RequestUtil.getParameter(request, "oper", 	 "AND");
String bbsid 		= RequestUtil.getParameter(request, "bbsid", 	 "");
String action 		= RequestUtil.getParameter(request, "action", 	 "");

ArrayList bbsList = null;
if(selectTag.trim().length() != 0 || searchWord.trim().length() != 0){
	bbsList = getBBSList(selectTag, searchWord, oper);
}

ArrayList tagList = getTagList();
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>게시판 - kAmOrU</title>
<link rel="stylesheet" type="text/css" href="./bbs.css">
<script type="text/javascript" src="../js/kamoru.js"></script>
<script type="text/javascript" src="./bbs.js"></script>
<script>
window.onload=function(){ 
	preTagselect('<%= selectTag %>');
}
</script>
</head>
<body>
<form id="searchForm" name="searchForm" method="get">
<input type="hidden" name="action" value="" />
<input type="hidden" id="selectTag" name="selectTag"/>
<input type="hidden" id="bbsid" name="bbsid" />

<fieldset>
	<legend> 태그 </legend>
	<div>
		<% for(int i=0; i<tagList.size() ; i++){ 
			HashMap tag = (HashMap)tagList.get(i); %>
		<span class="TAGNAME"
			  onclick="tagSelect(this, 'SUBMIT');" 
			  id="<%= tag.get("TAGID") %>" 
			  title="Count:<%= tag.get("STATUS") %>" 
			  style="font-size:<%= Integer.parseInt((String)tag.get("STATUS")) + 8 %>pt;">
			  <%= tag.get("TAGNAME") %></span>
		<% } %>
	</div>  
	<div>
		<input type="radio" name="oper" value="AND" id="AND" onclick="operSelect('SUBMIT')" <%= oper.equals("AND") ? "checked=\"checked\"" : "" %> /><label class="Pointer" for="AND">AND</label>
		<input type="radio" name="oper" value="OR" id="OR" onclick="operSelect('SUBMIT')" <%= oper.equals("OR") ? "checked=\"checked\"" : "" %> /><label class="Pointer" for="OR">OR</label>
		<input type="text" name="searchWord" class="INPUTTEXT" size="15" value="<%= searchWord %>"/>
		&nbsp;<button onclick="searchSubmit()">검색</button>
		&nbsp;<button onclick="writeTmpltOpen()">작성</button>
	</div> 
</fieldset>
<!-- include content.jsp start -->
<jsp:include page="content.jsp" flush="true">
	<jsp:param value="<%= bbsid %>" name="bbsid"/>
	<jsp:param value="<%= java.net.URLEncoder.encode(searchWord, "UTF-8") %>" name="highlight"/>
</jsp:include>
<!-- include content.jsp end -->

<table width="100%" border="0" cellspacing="0">
	<thead>
		<tr style="display:none;">
			<th>ID</th>
			<th>Tags</th>
			<th>Title</th>
			<th>Hits</th>
			<th style="display:none">Writer</th>
			<th style="display:none">Date</th>
		</tr>
	</thead>
	<tbody>
	<%
	if(bbsList != null)
	for(int i=0 ; i<bbsList.size() ; i++){
		HashMap content = (HashMap)bbsList.get(i);%>
	<tr class="LIST" onclick="viewContent('<%= content.get("BBSID") %>')" <%= bbsid.equals(content.get("BBSID")) ? "bgcolor=\"lightblue\"" : "" %> >
		<td class="LIST" ><%= content.get("BBSID") %></td>
		<td class="LIST" ><%= highlight(content.get("TAGNAMES"), searchWord) %></td>
		<td class="LIST" ><%= highlight(content.get("TITLE"), searchWord) %></td>
		<td class="LIST" align="right"><%= content.get("HIT") %></td>
		<td class="LIST" style="display:none"><%= content.get("CREATOR") %></td>
		<td class="LIST" style="display:none"><%= StringUtil.substring(content.get("CREATDTIME"), 2, 16, null) %></td>
	</tr>
	<% } %> 
	</tbody>
</table>

</form>  
</body>
</html>
<% if (logger.isDebugEnabled()) logger.debug("Elapsed time(ns) " +  (System.nanoTime() - startedTime)); %>
