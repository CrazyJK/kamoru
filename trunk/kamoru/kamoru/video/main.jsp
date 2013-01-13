<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.*, kamoru.app.video.av.*, kamoru.frmwk.util.ServletUtils" %>
<%
request.setCharacterEncoding("UTF-8");
// parameter
String label 	  		= ServletUtils.getParameter(request, "label", "");
String opus 	  		= ServletUtils.getParameter(request, "opus", "");
String title 	  		= ServletUtils.getParameter(request, "title", "");
String actress 	  		= ServletUtils.getParameter(request, "actress", "");
String addCond    		= ServletUtils.getParameter(request, "addCond", "off");
String existVideo 		= ServletUtils.getParameter(request, "existVideo", "off");
String existSubtitles 	= ServletUtils.getParameter(request, "existSubtitles", "off");
String viewLabelDiv     = ServletUtils.getParameter(request, "viewLabelDiv", "block");
String viewActressDiv   = ServletUtils.getParameter(request, "viewActressDiv", "block");
addCond    		= "".equals(addCond)    ? "on" : addCond;
existVideo 		= "".equals(existVideo) ? "on" : existVideo;
existSubtitles  = "".equals(existSubtitles)  ? "on" : existSubtitles;

System.out.format("[%s] [%s] [%s] [%s] [%s] [%s] [%s] [%s] [%s]%n", 
		label, opus, title, actress, addCond, existVideo, existSubtitles, viewLabelDiv, viewActressDiv);

AVCollectionCtrl ctrl = new AVCollectionCtrl();
List<AVOpus> list = ctrl.getAV(label, opus, title, actress, 
		new Boolean("on".equals(addCond)).booleanValue(), 
		new Boolean("on".equals(existVideo)).booleanValue(), 
		new Boolean("on".equals(existSubtitles)).booleanValue());
Map<String, Integer> labelMap = ctrl.getLabels();
Map<String, Integer> actressMap = ctrl.getActress();

//Map<String, String> history = (Map<String, String>)session.getAttribute("randomHistory");
Map<String, String> history = null;
if(history == null) {
	history = new HashMap<String, String>();
	history.put("dummy", "dummy");
	session.setAttribute("randomHistory", history);
}

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
<script src="video.js" type="text/javascript"></script>
</head>
<body>
<div id="headerDiv">
	<div id="searchDiv" class="boxDiv">
	<form name="frm" method="post">
		<span class="searchGroupSpan">
		<label for="label">  Label	 </label><input type="text" name="label"   id="label"   value="<%=label %>"   class="schTxt">
		<label for="opus">   Opus  	 </label><input type="text" name="opus"    id="opus"    value="<%=opus %>"    class="schTxt">
		<label for="title">  Title 	 </label><input type="text" name="title"   id="title"   value="<%=title %>"   class="schTxt">
		<label for="actress">Actress </label><input type="text" name="actress" id="actress" value="<%=actress %>" class="schTxt">
		</span>
		<span class="searchGroupSpan">
		<label for="addCond">       Add cond  </label><input type="checkbox" name="addCond"    	   id="addCond"    	   <%="on".equals(addCond)    	  ? "checked" : "" %>>
		<label for="existVideo">    Video     </label><input type="checkbox" name="existVideo" 	   id="existVideo" 	   <%="on".equals(existVideo) 	  ? "checked" : "" %>>
		<label for="existSubtitles">Subtitles </label><input type="checkbox" name="existSubtitles" id="existSubtitles" <%="on".equals(existSubtitles) ? "checked" : "" %>>
		</span>
		<input type="button" value="Search" onclick="fnDetailSearch()">
		<input type="button" value="Random"   onclick="fnRandomPlay()">
		<input type="button" value="L"    onclick="fnLabelDivToggle()">
		<input type="button" value="A"  onclick="fnActressDivToggle()">
		<input type="hidden" name="viewLabelDiv"   id="viewLabelDiv"   value="<%=viewLabelDiv %>">
		<input type="hidden" name="viewActressDiv" id="viewActressDiv" value="<%=viewActressDiv %>">
	</form>
	</div>
	<div id="labelDiv" class="boxDiv" style="display:<%=viewLabelDiv %>">
	<% for(String key : labelMap.keySet()) { %>
		<span onclick="fnLabelSearch('<%=key %>')" class="labelSpanBtn"><%=key %>(<%=labelMap.get(key) %>)</span>
	<% } %>
	</div>
	<div id="actressDiv" class="boxDiv" style="display:<%=viewActressDiv %>">
	<% for(String key : actressMap.keySet()) { %>
		<span onclick="fnActressSearch('<%=key %>')" class="actressSpanBtn"><%=key %>(<%=actressMap.get(key) %>)</span>
	<% } %>
	</div>
</div>
<div id="listDiv" class="boxDiv" style="background-image:url('image.jsp?opus=listImg')">
	<span id="totalCount">Total <%=list.size() %></span><span id="debug"></span>
	<ul>
	<% for(AVOpus av : list) { %>	
		<li id="<%=av.getOpus() %>">
			<div class="opusDiv">
				<%-- <table>
					<tr>
						<td  colspan="2"><span class="titleSpan"><%=av.getTitle() %></span></td>
					</tr>
					<tr valign="top">
						<td width="110px">
							<img src="image.jsp?opus=<%=av.getOpus() %>" height="120px" onclick="fnImageView('<%=av.getOpus() %>')"/>
						</td>
						<td>
							<dl>
								<dt><span class="labelSpan"><%=av.getLabel() %></span><span class="opusSpan"><%=av.getOpus() %></span>
								<% for(String actressName : av.getActressList()) { %>
								<span class="actressSpan" onclick="fnActressSearch('<%=actressName %>')"><%=actressName %></span>
								<% } %>
								</dt>
								<dd> 
									<span class="<%=av.existVideo()     ? "existFile" : "nonExistFile" %>" onclick="fnPlay('<%=av.getOpus() %>')" title="<%=av.getVideoPath() %>">Video</span>
									<span class="<%=av.existCover()     ? "existFile" : "nonExistFile" %>" title="<%=av.getCover()%>">Cover</span>
									<span class="<%=av.existSubtitles() ? "existFile" : "nonExistFile" %>">smi</span>
									<span class="<%=av.existOverview()  ? "existFile" : "nonExistFile" %>" title="<%=av.getOverviewTxt() %>" onclick="fnEditOverview('<%=av.getOpus() %>')">Overview</span>
								</dd>
							</dl>
						</td>
					</tr>
				</table> --%>
				<dl style="background-image:url('image.jsp?opus=<%=av.getOpus() %>'); background-size:300px 200px; height:200px;">
					<dt><span class="bgSpan" id="titleSpan"><%=av.getTitle() %></span></dt>
					<dd><span class="bgSpan" id="labelSpan"  onclick="fnLabelSearch('<%=av.getLabel() %>')"><%=av.getLabel() %></span></dd>
					<dd><span class="bgSpan" id="opusSpan"><%=av.getOpus() %></span></dd>
					<dd><% for(String actressName : av.getActressList()) { %>
						<span class="bgSpan" id="actressSpan" onclick="fnActressSearch('<%=actressName %>')"><%=actressName %></span>
						<% } %></dd>
					<dd><span class="bgSpan <%=av.existVideo()     ? "existFile" : "nonExistFile" %>" onclick="fnPlay('<%=av.getOpus() %>')" title="<%=av.getVideoPath() %>">Video</span></dd>
					<dd><span class="bgSpan <%=av.existCover()     ? "existFile" : "nonExistFile" %>" title="<%=av.getCover()%>" onclick="fnImageView('<%=av.getOpus() %>')">Cover</span></dd>
					<dd><span class="bgSpan <%=av.existSubtitles() ? "existFile" : "nonExistFile" %>">smi</span></dd>
					<dd><span class="bgSpan <%=av.existOverview()  ? "existFile" : "nonExistFile" %>" title="<%=av.getOverviewTxt() %>" onclick="fnEditOverview('<%=av.getOpus() %>')">Overview</span></dd>
				</dl>
			</div>
		</li>
	<% } %>
	</ul>
</div>

<form name="playFrm" target="ifrm" action="execProc.jsp" method="post">
	<input type="hidden" name="selectedOpus" id="selectedOpus">
</form>
<iframe name="ifrm" style="display:none; width:100%;"></iframe>

</body>
</html>