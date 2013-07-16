<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"      uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn"     uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix='form'   uri='http://www.springframework.org/tags/form'%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8" />
<link rel="shortcut icon" type="image/x-icon" href="<c:url value="/resources/video/video-favicon.ico" />">
<title>History Search :: Video collection</title>
<link rel="stylesheet" href="<c:url value="/resources/video/video.css" />" />
<link rel="stylesheet" href="<c:url value="/resources/video/video-search.css" />" />
<!--[if lt IE 9]>
<script src="http://html5shiv.googlecode.com/svn/trunk/html5.js"></script>
<![endif]-->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.8.3/jquery.min.js"></script>
<script src="<c:url value="/resources/common.js" />"></script>
<script src="<c:url value="/resources/video/video.js" />"></script>
<script src="<c:url value="/resources/image-popup.js" />"></script>
<script type="text/javascript">
var context = '<spring:url value="/"/>';

$(document).ready(function(){
	$(window).bind("resize", resizeDivHeight);
	
	$("#query").bind("keyup", function(event) {
		var queryUrl = context + 'video/history.json?q=' + $(this).val(); 
		$("#debug").html(queryUrl);
		$("#opus").val($(this).val());
		$.getJSON(queryUrl ,function(data) {
			$('#foundList').empty();
			var row = data['historyList'];
			
 			$.each(row, function(entryIndex, entry) {
				
				var date 		   = entry['date'];
				var opus 		   = entry['opus'];
				var act 		   = entry['act'];
				var desc 	   = entry['desc'];
				
				var li  = $("<li>");
				var div = $("<div>");

				var dateDom 		  = $("<span>").addClass("history-item").html(date);
				var opusDom 		  = $("<span>").addClass("history-item").attr("onclick", "fnViewVideoDetail('" + opus +"')").html(opus);
				var actDom	 		  = $("<span>").addClass("history-item").html(act);
				var descDom 		  = $("<span>").addClass("history-item").html(desc);

				div.append(dateDom);
				div.append(opusDom);
				div.append(actDom);
				div.append(descDom);
				li.append(div);

				$('#foundList').append(li);
			}); 
			searchAndHighlight($("#query").val());
			resizeDivHeight();
		});
	});
	
	$("#addVideoBtn").css("cursor", "pointer").bind("click", function() {
		var html = "<p>["+$("#studio").val()+"]["+$("#opus").val().toUpperCase()+"]["+$("#title").val()+"]["+$("#actress").val()+"]["+$("#etcInfo").val()+"]</p>";
		$("#newVideo").append(html);
	});	
	
	resizeDivHeight();
});

function resizeDivHeight() {
	var windowHeight = $(window).height();
	var debugDivHeight = $("#debugDiv").outerHeight();
	var queryDivHeight = $("#queryDiv").outerHeight();
	var calculatedDivHeight = windowHeight - debugDivHeight - queryDivHeight - 20 * 2; 
	$("#resultDiv").outerHeight(calculatedDivHeight);	
}
function searchAndHighlight(searchTerm) {
    var rexp = eval('/' + searchTerm + "/gi");

	$("#resultDiv span.history-item").each(function() {
		$(this).html($(this).html().replace(rexp,"<em>"+searchTerm+"</em>"));
	});
	
   	$(window).scrollTop(0);
}
</script>
</head>
<body>
<div id="debugDiv"></div>
<div id="queryDiv" class="div-box">
	Search<input type="text" name="query" id="query" style="width:100px;" class="searchInput"/>
	<span id="debug"></span>
</div>

<div id="resultDiv" class="div-box" style="overflow:auto">
	<ol id="foundList"></ol>
</div>

</body>
</html>