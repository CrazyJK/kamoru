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
<script type="text/javascript">
$(document).ready(function(){
	$("label").bind("click", function(){
		var id = $(this).attr("for");
		$("#" + id).val("");
	});
	$("li").toggle(
		function() {
			$("#debug").html("toggle 1");
			$(this).animate({
				opacity: 0.75
				}, 1000, function(){
					$(this).css("background-color", "green");
				});
		}, function(){
			$("#debug").html("toggle 2");
			$(this).animate({
				opacity: 1
				}, 500, function(){
					$(this).css("background-color", "");
				});
		});

	$("*[onclick]").css("cursor", "pointer");
	$(window).bind("resize", resizeDivHeight);
	resizeDivHeight();
});
function resizeDivHeight() {
	var windowHeight = $(window).height();
	//var documentHeight = $(document).outerHeight();
	var searchDivHeight = $("#searchDiv").outerHeight();
	var resizeListDivHeight = windowHeight - searchDivHeight - 16 - 20 - 20; 
	//alert(resizeListDivHeight);
	$("#listDiv").height(resizeListDivHeight);
	resizeBackgroundImage();
}
function resizeBackgroundImage() {
	var url = $('#listDiv').css('background-image').replace(/url\(|\)$/ig, "");
	var img = $("<img />");
	img.hide();
	img.bind('load', function(){
		var imgWidth  = $(this).width();
		var imgHeight = $(this).height();
		var divWidth  = $("#listDiv").width();
		var divHeight = $("#listDiv").height();
		var width  = 0;
		var height = 0;
		
		if(imgWidth < divWidth && imgHeight < divHeight) { // 1. x:- y:-
			width  = imgWidth;
			height = imgHeight;
		}else if(imgWidth < divWidth && imgHeight > divHeight) { // 2. x:- y:+
			width  = ratioSize(divHeight, imgWidth, imgHeight);
			height = divHeight;
		}else if(imgWidth > divWidth && imgHeight < divHeight) { // 3. x:+ y:-
			width  = divWidth;
			height = ratioSize(divWidth, imgHeight, imgWidth);
		}else if(imgWidth > divWidth && imgHeight > divHeight) { // 4. x:+ y:+
			width  = ratioSize(divHeight, imgWidth, imgHeight);
			height = divHeight;
		}
		$("#debug").html("background-image resize :{"+imgWidth+","+imgHeight+"}->{"+width+","+height+"}");
		$("#listDiv").css("background-size", width + "px " + height + "px");
	});
	$("body").append(img);
	img.attr("src", url);
}
function ratioSize(numerator1, numerator2, denominator) {
	return parseInt(numerator1 * numerator2 / denominator);
}
function fnLabelSearch(label) {
	$("input:text").each(function(){
		$(this).val("");
	});
	$("#label").val(label);
	fnDetailSearch();
}
function fnActressSearch(actress) {
	$("input:text").each(function(){
		$(this).val("");
	});
	$("#actress").val(actress);
	fnDetailSearch();
}
function fnDetailSearch() {
	var frm = document.forms["frm"];
	frm.submit();
}
function fnPlay(selectedOpus) {
	$("#debug").html("Video play " + selectedOpus);
	$("#selectedOpus").val(selectedOpus);
	var frm = document.forms["playFrm"];
	frm.submit();
}
function fnRandomPlay() {
	$("#debug").html("Random play start");
	$("#selectedOpus").val("random");
	var frm = document.forms["playFrm"];
	frm.submit();
}
function fnOpusFocus(opus) {
	//alert("fnOpusFocus " + opus);
	$("#" + opus).animate({
		opacity: 0.75,
	}, 1000, function(){
		$(this).css("background-color", "green");
	});
}
function fnImageView(opus) {
	$("#debug").html("Cover image view : " + opus);
	var vUrl    = "image.jsp?opus="+opus;
    var vName   = "imageview-"+opus;
    var vWidth  = 800;
    var vHeight = 536;
    var vLeft   = (window.screen.width  - vWidth)/2;
    var vTop    = (window.screen.height - vHeight)/2;
    var vSpecs  = "width="+vWidth+", height="+vHeight+", top="+vTop+", left="+vLeft
    			 + "toolbar=0,location=0,directories=0,titlebar=0"+
          		   "status=0,menubar=0,scrollbars=0,resizable=1";
    window.open(vUrl, vName, vSpecs);	
}
function fnEditOverview(opus) {
	$("#debug").html("Overview Popup : " + opus);
	var vUrl    = "overview.jsp?opus="+opus;
    var vName   = "overview-"+opus;
    var vWidth  = 400;
    var vHeight = 300;
    var vLeft   = window.event.x;
    var vTop    = window.event.y;
    var vSpecs  = "width="+vWidth+", height="+vHeight+", top="+vTop+", left="+vLeft
    			 + "toolbar=0,location=0,directories=0,titlebar=0"+
          		   "status=0,menubar=0,scrollbars=0,resizable=1";
    window.open(vUrl, vName, vSpecs);	
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
		<input type="button" value="Random" onclick="fnRandomPlay()">
	<hr/>
	<%
	for(String key : labelMap.keySet()) {
		Integer count = labelMap.get(key);
	%>
	<span onclick="fnLabelSearch('<%=key %>')" class="labelSpanBtn"><%=key %>(<%=count %>)</span>
	<%
	}
	%>
	<hr/>
	<%
	for(String key : actressMap.keySet()) {
		Integer count = actressMap.get(key);
	%>
	<span onclick="fnActressSearch('<%=key %>')" class="actressSpanBtn"><%=key %>(<%=count %>)</span>
	<%
	}
	%>
	</form>
</div>
<div id="listDiv" class="boxDiv" style="background-image:url('image.jsp?opus=listImg')">
	<span id="totalCount">Total <%=list.size() %></span><span id="debug"></span>
	<ul>
	<%
	for(AVOpus av : list) {
	%>	
		<li id="<%=av.getOpus() %>">
			<div class="opusDiv">
	 			<span class="titleSpan"><%=av.getTitle() %></span><span class="selectBtn" for="<%=av.getOpus() %>">SEl■</span>
				<table>
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
									<%-- <span class="<%=av.existCover()     ? "existFile" : "nonExistFile" %>" title="<%=av.getCover()%>">Cover</span> --%>
									<span class="<%=av.existSubtitles() ? "existFile" : "nonExistFile" %>">smi</span>
									<span class="<%=av.existOverview()  ? "existFile" : "nonExistFile" %>" title="<%=av.getOverviewTxt() %>" onclick="fnEditOverview('<%=av.getOpus() %>')">Overview</span>
								</dd>
								<%-- <dd class="overviewDD">
									<%=av.getOverviewTxt() %>
								</dd> --%>
							</dl>
						
						</td>
				</table>
			</div>
		</li>
	<%
	}
	%>
	</ul>
</div>

<form name="playFrm" target="ifrm" action="execProc.jsp" method="post">
	<input type="hidden" name="selectedOpus" id="selectedOpus">
</form>
<iframe name="ifrm" style="display:none; width:100%;"></iframe>

</body>
</html>