<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.*, kamoru.app.video.av.*, kamoru.frmwk.util.ServletUtils" %>
<%
request.setCharacterEncoding("UTF-8");
// parameter
String label 	 = ServletUtils.getParameter(request, "label", "");
String opus 	 = ServletUtils.getParameter(request, "opus", "");
String title 	 = ServletUtils.getParameter(request, "title", "");
String actress 	 = ServletUtils.getParameter(request, "actress", "");
String subtitles = ServletUtils.getParameter(request, "subtitles", "");

AVCollectionCtrl ctrl = new AVCollectionCtrl();
List<AVOpus> list = ctrl.getAV(label, opus, title, actress, new Boolean("on".equals(subtitles)).booleanValue());
Map<String, Integer> labelMap = ctrl.getLabels();

session.setAttribute("avlist", list);
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>AV World</title>
<link rel="stylesheet" href="/kamoru/video/video.css" />
<!--[if lt IE 9]>
<script src="http://html5shiv.googlecode.com/svn/trunk/html5.js"></script>
<![endif]-->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.8.3/jquery.min.js"></script>
<script type="text/javascript">
$(document).ready(function(){
	$("label").bind("click", function(){
		var id = $(this).attr("for");
		$("#" + id).val("");
	});
});
function fnLabelSearch(label) {
	$("#label").val(label);
	fnDetailSearch();
}
function fnDetailSearch() {
	var frm = document.forms["frm"];
	frm.submit();
}
function fnPlay(selectedOpus) {
	$("#selectedOpus").val(selectedOpus);
	var frm = document.forms["playFrm"];
	frm.submit();
}
</script>
</head>
<body>
<div id="searchDiv" class="boxDiv">
	<form name="frm" method="post">
		<label for="label">    Label	 </label><input type="text"     name="label"     id="label"     value="<%=label %>"   class="schTxt">
		<label for="opus">     Opus  	 </label><input type="text"     name="opus"      id="opus"      value="<%=opus %>"    class="schTxt">
		<label for="title">    Title 	 </label><input type="text"     name="title"     id="title"     value="<%=title %>"   class="schTxt">
		<label for="actress">  Actress 	 </label><input type="text"     name="actress"   id="actress"   value="<%=actress %>" class="schTxt">
		<label for="subtitles">Subtitles </label><input type="checkbox" name="subtitles" id="subtitles" <%="on".equals(subtitles) ? "checked" : "" %>>
		<input type="button" value="Search" onclick="fnDetailSearch()">
	<hr/>
	<%
	for(Object key : labelMap.keySet()) {
		Integer count = (Integer)labelMap.get(key);
	%>
	<span onclick="fnLabelSearch('<%=key %>')" class="labelSpanBtn"><%=key %>(<%=count %>)</span>
	<%
	}
	%>
	</form>
</div>
<br/>
<div id="listDiv" class="boxDiv">
	<span>Total <%=list.size() %></span>
	<ol>
	<%
	for(Object o : list) {
		AVOpus av = (AVOpus)o;
	%>	
		<li><span class="titleSpan"><%=av.getTitle() %></span>
			<table style="height:100px">
				<tr valign="top">
					<td width="110px">
						<img src="image.jsp?opus=<%=av.getOpus() %>" height="120px"/>
					</td>
					<td>
						<dl>
							<dt>[<span class="labelSpan"><%=av.getLabel() %></span>][<span class="opusSpan"><%=av.getOpus() %></span>][<span class="actressSpan"><%=av.getActress() %></span>]
							</dt>
							<dd> 
								<span class="<%=av.existVideo()     ? "existFile" : "nonExistFile" %>" onclick="fnPlay('<%=av.getOpus() %>')" title="<%=av.getVideoPath() %>">Video</span>
								<span class="<%=av.existCover()     ? "existFile" : "nonExistFile" %>" title="<%=av.getCover()%>">Cover</span>
								<span class="<%=av.existSubtitles() ? "existFile" : "nonExistFile" %>">smi</span>
								<span class="<%=av.existOverview()  ? "existFile" : "nonExistFile" %>" title="<%=av.getOverviewTxt() %>">Overview</span>
							</dd>
							<dd class="overviewDD">
								<%=av.getOverviewTxt() %>
							</dd>
						</dl>
					
					</td>
			</table>
		</li>
	<%
	}
	%>
	</ol>
</div>

<form name="playFrm" target="ifrm" action="execProc.jsp" method="post">
	<input type="hidden" name="selectedOpus" id="selectedOpus">
</form>
<iframe name="ifrm" style="display:none; width:100%;"></iframe>

</body>
</html>