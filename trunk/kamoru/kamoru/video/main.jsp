<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.*, kamoru.app.video.av.*" %>
<%
request.setCharacterEncoding("UTF-8");
// parameter
String label = request.getParameter("label");
String opus = request.getParameter("opus");
String title = request.getParameter("title");
String actress = request.getParameter("actress");
String subtitles = request.getParameter("subtitles");

AVCollectionCtrl ctrl = new AVCollectionCtrl();
Map map = ctrl.getAVData();
List list = ctrl.getAV(map, label, opus, title, actress, new Boolean(subtitles).booleanValue());
//List list = ctrl.getAV(map);
Map labelMap = ctrl.getLabels(map);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>AV World</title>
<style type="text/css">
.schTxt {width:80px;}
</style>
<script type="text/javascript">
function fnLabelSearch(label) {
	document.getElementById("label").value = label;
	fnDetailSearch();
}
function fnDetailSearch() {
	/* 
	var label = document.getElementById("label").value;
	var opus = document.getElementById("opus").value;
	var title = document.getElementById("title").value;
	var actress = document.getElementById("actress").value;
	var subtitles = document.getElementById("subtitles").checked;
	location.href = "?label=" + label + "&opus=" + opus + "&title=" + title + "&actress=" + actress + "&subtitles=" + subtitles;
	 */
	var frm = document.forms["frm"];
	frm.submit();
}
function fnPlay(filepath) {
	document.getElementById("filepath").value = filepath;
	var frm = document.forms["playFrm"];
	frm.submit();
}
</script>
</head>
<body>
<form name="frm" method="post">
Label <input type="text" name="label" id="label" value="<%=label %>" class="schTxt">
Opus <input type="text" name="opus" value="<%=opus %>" class="schTxt">
Title <input type="text" name="title" value="<%=title %>" class="schTxt">
Actress <input type="text" name="actress" value="<%=actress %>" class="schTxt">
Subtitles <input type="checkbox" name="subtitles" <%="true".equals(subtitles) ? "checked" : "" %>>
<input type="button" value="Search" onclick="fnDetailSearch()">
<br>
<%
for(Object key : labelMap.keySet()) {
	Integer count = (Integer)labelMap.get(key);
%>
<a onclick="fnLabelSearch('<%=key %>')"><span style="display:inline;font-weight:bold; color:blue; cursor:pointer;"><%=key %>(<%=count %>)</span></a>
<%
}
%>
</form>
<br> Total <%=list.size() %>
<div style="width:100%; height:650px;overflow:auto">
<ol>
<%
for(Object o : list) {
	AVOpus av = (AVOpus)o;
%>	
	<li>
	<span style="color:blue;"><%=av.getLabel() %> - <%=av.getOpus() %> - <%=av.getActress() %> - <%=av.getTitle() %></span>
	<br/><span style="font-size:9pt;" onclick="fnPlay('<%=av.getAvFilename().replace('\\', '/') %>')"><%=av.getAvFilename() %></span>
	<br/><span style="font-size:9pt;"><%=av.getAvCover() %></span>
	<br/><span style="font-size:9pt;"><%=av.getAvSubtutles() %></span>
	<br/><span style="font-size:9pt;"><%=av.getAvOverview() %></span>
	</li>
<%
}
%>
</ol>
</div>

<form name="playFrm" target="ifrm" action="execProc.jsp" method="post">
<input type="text" name="filepath" id="filepath">
</form>
<iframe name="ifrm"></iframe>

</body>
</html>