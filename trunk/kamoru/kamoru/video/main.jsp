<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.*, kamoru.app.video.av.*, kamoru.frmwk.util.ServletUtils" %>
<%
	request.setCharacterEncoding("UTF-8");
// parameter
String studio 	  		= ServletUtils.getParameter(request, "studio", "");
String opus 	  		= ServletUtils.getParameter(request, "opus", "");
String title 	  		= ServletUtils.getParameter(request, "title", "");
String actress 	  		= ServletUtils.getParameter(request, "actress", "");
String addCond    		= ServletUtils.getParameter(request, "addCond", "off");
String existVideo 		= ServletUtils.getParameter(request, "existVideo", "off");
String existSubtitles 	= ServletUtils.getParameter(request, "existSubtitles", "off");
String viewStudioDiv    = ServletUtils.getParameter(request, "viewStudioDiv", "block");
String viewActressDiv   = ServletUtils.getParameter(request, "viewActressDiv", "block");
String listViewType		= ServletUtils.getParameter(request, "listViewType", "box"); //card, box, sbox or list
String sortMethod		= ServletUtils.getParameter(request, "sortMethod", "O");
String sortReverse		= ServletUtils.getParameter(request, "sortReverse", "off");
String useCacheData		= ServletUtils.getParameter(request, "useCacheData", "off");
//System.out.format("studio[%s] opus[%s] title[%s] actress[%s] addCond[%s] existVideo[%s] existSubtitles[%s] viewStudioDiv[%s] viewActressDiv[%s] listViewType[%s] sortMethod[%s] sortReverse[%s] useCacheData[%s]%n", 
//		studio, opus, title, actress, addCond, existVideo, existSubtitles, viewStudioDiv, viewActressDiv, listViewType, sortMethod, sortReverse, useCacheData);

AVCollectionCtrl ctrl = new AVCollectionCtrl();

List<AVOpus> list = ctrl.getAV(studio, opus, title, actress, 
		"on".equals(addCond), 
		"on".equals(existVideo), 
		"on".equals(existSubtitles),
		sortMethod,
		"on".equals(sortReverse),
		"on".equals(useCacheData));
Map<String, Integer> studioMap = ctrl.getStudios();
Map<String, Integer> actressMap = ctrl.getActress();

Map<String, String> history = (Map<String, String>)session.getAttribute("randomHistory");
//Map<String, String> history = null;
if(history == null) {
	history = new HashMap<String, String>();
	history.put("dummy", "dummy");
}
System.out.println(history.toString());

session.setAttribute("AVCollectionCtrl", ctrl);
session.setAttribute("avlist", list);
session.setAttribute("randomHistory", history);
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
	<form name="frm" method="get">
		<span class="searchGroupSpan">
			<label for="studio"> Studio	 </label><input type="text" name="studio"  id="studio"  value="<%=studio %>"  class="schTxt">
			<label for="opus">   Opus  	 </label><input type="text" name="opus"    id="opus"    value="<%=opus %>"    class="schTxt">
			<label for="title">  Title 	 </label><input type="text" name="title"   id="title"   value="<%=title %>"   class="schTxt">
			<label for="actress">Actress </label><input type="text" name="actress" id="actress" value="<%=actress %>" class="schTxt">
			<span class="checkbox" id="checkbox-addCond"        title="Add additional conditions">Add</span><input type="hidden" name="addCond"    	   id="addCond"    	   value="<%=addCond %>">
			<span class="checkbox" id="checkbox-existVideo"     title="exist Video?">V</span>			    <input type="hidden" name="existVideo" 	   id="existVideo" 	   value="<%=existVideo %>">
			<span class="checkbox" id="checkbox-existSubtitles" title="exist Subtitles?">S</span>			<input type="hidden" name="existSubtitles" id="existSubtitles" value="<%=existSubtitles %>">
			<span class="separatorSpan">|</span>
			<span class="button" onclick="fnDetailSearch()">Search</span>
			<span class="separatorSpan">|</span>
			<span class="button" onclick="fnRandomPlay()">Random</span>
		</span>
		<span class="viewGroupSpan">
			<span class="radio" id="radio-listViewType-card"  title="show card type list">C</span> 
			<span class="radio" id="radio-listViewType-box"   title="show box type list">B</span> 
			<span class="radio" id="radio-listViewType-sbox"  title="show small box type list">SB</span>
			<span class="radio" id="radio-listViewType-table" title="show table type list">T</span>			<input type="hidden" name="listViewType" id="listViewType" value="<%=listViewType %>">
			<span class="separatorSpan">|</span>
			<span class="radio" id="radio-sortMethod-S" title="sort by studio">S</span>
			<span class="radio" id="radio-sortMethod-O" title="sort by opus">O</span>
			<span class="radio" id="radio-sortMethod-T" title="sort by title">T</span>
			<span class="radio" id="radio-sortMethod-A" title="sort by actress">A</span>
			<span class="radio" id="radio-sortMethod-L" title="sort by lastmodified">M</span>				<input type="hidden" name="sortMethod"  id="sortMethod"  value="<%=sortMethod %>">
			<span class="checkbox" id="checkbox-sortReverse" title="reverse sort">R</span>					<input type="hidden" name="sortReverse" id="sortReverse" value="<%=sortReverse %>">
			<span class="separatorSpan">|</span>
			<span class="checkbox" id="checkbox-viewStudioDiv"  title="view Studio panel"  onclick="fnStudioDivToggle()">S</span>	<input type="hidden" name="viewStudioDiv"  id="viewStudioDiv"  value="<%=viewStudioDiv %>"> 
			<span class="checkbox" id="checkbox-viewActressDiv" title="view Actress panel" onclick="fnActressDivToggle()">A</span>	<input type="hidden" name="viewActressDiv" id="viewActressDiv" value="<%=viewActressDiv %>">
			<span class="separatorSpan">|</span>
			<span class="checkbox" id="checkbox-useCacheData" title="use cache data">C</span>				<input type="hidden" name="useCacheData" id="useCacheData" value="<%=useCacheData %>">
		</span>
	</form>
	</div>
	<div id="studioDiv" class="boxDiv" style="display:<%="on".equals(viewStudioDiv) ? "" : "none" %>">
	<% for(String key : studioMap.keySet()) { %>
		<span onclick="fnStudioSearch('<%=key %>')" class="studioSpanBtn"><%=key %>(<%=studioMap.get(key) %>)</span>
	<% } %>
	</div>
	<div id="actressDiv" class="boxDiv" style="display:<%="on".equals(viewActressDiv) ? "" : "none" %>">
	<% for(String key : actressMap.keySet()) { %>
		<span onclick="fnActressSearch('<%=key %>')" class="actressSpanBtn"><%=key %>(<%=actressMap.get(key) %>)</span>
	<% } %>
	</div>
</div>
<div id="contentDiv" class="boxDiv" style="background-image:url('image.jsp?opus=<%=ctrl.listImageName %>')">
	<span id="totalCount">Total <%=list.size() %></span><span id="debug"></span><span id="bgimg" onclick="fnImageView('<%=ctrl.listImageName %>');">BG</span>
	<% if("card".equals(listViewType)) { %>
	<ul>
		<% for(AVOpus av : list) { %>	
		<li id="<%=av.getOpus() %>" class="boxLI">
			<div class="opusBoxDiv">
				<table>
					<tr>
						<td  colspan="2"><span class="titleSpan"><%=av.getTitle() %></span></td>
					</tr>
					<tr valign="top">
						<td width="110px">
							<img src="image.jsp?opus=<%=av.getOpus() %>" height="120px" onclick="fnImageView('<%=av.getOpus() %>')"/>
						</td>
						<td>
							<dl>
								<dt><span class="studioSpan"><%=av.getStudio() %></span>&nbsp;<span class="opusSpan"><%=av.getOpus() %></span></dt>
								<dt>
								<% for(String actressName : av.getActressList()) { %>
								<span class="actressSpan" onclick="fnActressSearch('<%=actressName %>')"><%=actressName %></span>
								<% } %>
								</dt>
								<dd> 
									<span class="<%=av.existVideo()     ? "existFile" : "nonExistFile" %>" onclick="fnPlay('<%=av.getOpus() %>')"          title="<%=av.getVideoPath() %>">Video</span>
									<span class="<%=av.existCover()     ? "existFile" : "nonExistFile" %>" onclick="fnImageView('<%=av.getOpus() %>')"     title="<%=av.getCover()%>">Cover</span>
									<span class="<%=av.existSubtitles() ? "existFile" : "nonExistFile" %>" onclick="fnEditSubtitles('<%=av.getOpus() %>')" title="<%=av.getSubtitles() %>">smi</span>
									<span class="<%=av.existOverview()  ? "existFile" : "nonExistFile" %>" onclick="fnEditOverview('<%=av.getOpus() %>')"  title="<%=av.getOverviewTxt() %>">Overview</span>
								</dd>
								<dd>
									<span id="DEL-<%=av.getOpus() %>" style="display:none;" class="bgSpan" onclick="fnDeleteOpus('<%=av.getOpus() %>')">Del</span>
								</dd>
							</dl>
						</td>
					</tr>
				</table>
			</div>
		</li>
		<% } %>
	</ul>
	<% } else if("box".equals(listViewType)) { %>
	<ul>
		<% for(AVOpus av : list) { %>	
		<li id="<%=av.getOpus() %>" class="boxLI">
			<div class="opusBoxDiv">
				<dl style="background-image:url('image.jsp?opus=<%=av.getOpus() %>'); background-size:300px 200px; height:200px;">
					<dt><span class="bgSpan" id="titleSpan"><%=av.getTitle() %></span></dt>
					<dd><span class="bgSpan" id="studioSpan"  onclick="fnStudioSearch('<%=av.getStudio() %>')"><%=av.getStudio() %></span></dd>
					<dd><span class="bgSpan" id="opusSpan"><%=av.getOpus() %></span></dd>
					<dd><% for(String actressName : av.getActressList()) { %>
						<span class="bgSpan" id="actressSpan" onclick="fnActressSearch('<%=actressName %>')"><%=actressName %></span>
						<% } %></dd>
					<dd><span class="bgSpan <%=av.existVideo()     ? "existFile" : "nonExistFile" %>" onclick="fnPlay('<%=av.getOpus() %>')"          title="<%=av.getVideoPath() %>">Video</span></dd>
					<dd><span class="bgSpan <%=av.existCover()     ? "existFile" : "nonExistFile" %>" onclick="fnImageView('<%=av.getOpus() %>')"     title="<%=av.getCover()%>">Cover</span></dd>
					<dd><span class="bgSpan <%=av.existSubtitles() ? "existFile" : "nonExistFile" %>" onclick="fnEditSubtitles('<%=av.getOpus() %>')" title="<%=av.getSubtitles() %>">smi</span></dd>
					<dd><span class="bgSpan <%=av.existOverview()  ? "existFile" : "nonExistFile" %>" onclick="fnEditOverview('<%=av.getOpus() %>')"  title="<%=av.getOverviewTxt() %>">Overview</span>
						<span id="DEL-<%=av.getOpus() %>" style="display:none;" class="bgSpan" onclick="fnDeleteOpus('<%=av.getOpus() %>')">Del</span>
					</dd>
				</dl>
			</div>
		</li>
		<% } %>
	</ul>
	<% } else if("sbox".equals(listViewType)) { %>
	<ul>
		<% for(AVOpus av : list) { %>	
		<li id="<%=av.getOpus() %>" class="sboxLI">
			<div class="opusSBoxDiv">
				<span class="bgSpan" id="titleSpan"><%=av.getTitle() %></span>
				<span class="bgSpan" id="studioSpan"  onclick="fnStudioSearch('<%=av.getStudio() %>')"><%=av.getStudio() %></span>
				<span class="bgSpan" id="opusSpan"><%=av.getOpus() %></span>
				<% for(String actressName : av.getActressList()) { %>
				<span class="bgSpan" id="actressSpan" onclick="fnActressSearch('<%=actressName %>')"><%=actressName %></span>
				<% } %>
				<span class="bgSpan <%=av.existVideo()     ? "existFile" : "nonExistFile" %>" onclick="fnPlay('<%=av.getOpus() %>')"          title="<%=av.getVideoPath() %>">V</span>
				<span class="bgSpan <%=av.existCover()     ? "existFile" : "nonExistFile" %>" onclick="fnImageView('<%=av.getOpus() %>')"     title="<%=av.getCover()%>" >C</span>
				<span class="bgSpan <%=av.existSubtitles() ? "existFile" : "nonExistFile" %>" onclick="fnEditSubtitles('<%=av.getOpus() %>')" title="<%=av.getSubtitles() %>">s</span>
				<span class="bgSpan <%=av.existOverview()  ? "existFile" : "nonExistFile" %>" onclick="fnEditOverview('<%=av.getOpus() %>')"  title="<%=av.getOverviewTxt() %>">O</span>
				<span id="DEL-<%=av.getOpus() %>" style="display:none;" class="bgSpan" onclick="fnDeleteOpus('<%=av.getOpus() %>')">Del</span>
			</div>
		</li>
		<% } %>
	</ul>
	<% } else if("table".equals(listViewType)) {%>
		<table class="listTable">
			<tr>
				<th>Studio</th>
				<th>Opus</th>
				<th>Title</th>
				<th>Actress</th>
				<th>Info</th>
			</tr>
		<% for(AVOpus av : list) { %>	
			<tr>
				<td><span class="" id="studioSpan"  onclick="fnStudioSearch('<%=av.getStudio() %>')"><%=av.getStudio() %></span></td>		
				<td><span class="" id="opusSpan"><%=av.getOpus() %></span></td>		
				<td><span class="" id="titleSpan"><%=av.getTitle() %></span></td>		
				<td><% for(String actressName : av.getActressList()) { %>
					<span class="" id="actressSpan" onclick="fnActressSearch('<%=actressName %>')"><%=actressName %></span>
				<% } %></td>	
				<td><span class="bgSpan <%=av.existVideo()     ? "existFile" : "nonExistFile" %>" onclick="fnPlay('<%=av.getOpus() %>')"          title="<%=av.getVideoPath() %>">V</span>
					<span class="bgSpan <%=av.existCover()     ? "existFile" : "nonExistFile" %>" onclick="fnImageView('<%=av.getOpus() %>')"     title="<%=av.getCover()%>">C</span>
					<span class="bgSpan <%=av.existSubtitles() ? "existFile" : "nonExistFile" %>" onclick="fnEditSubtitles('<%=av.getOpus() %>')" title="<%=av.getSubtitles() %>">s</span>
					<span class="bgSpan <%=av.existOverview()  ? "existFile" : "nonExistFile" %>" onclick="fnEditOverview('<%=av.getOpus() %>')"  title="<%=av.getOverviewTxt() %>">O</span></td>		
			</tr>
		<% } %>
		</table>
	<% } %>
</div>

<form name="playFrm" target="ifrm" action="execProc.jsp" method="post">
	<input type="hidden" name="selectedOpus" id="selectedOpus">
	<input type="hidden" name="selectedMode" id="selectedMode">
</form>
<iframe name="ifrm" style="display:none; width:100%;"></iframe>

</body>
</html>