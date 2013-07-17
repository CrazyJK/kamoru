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
<title>Search :: Video collection</title>
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
		var queryUrl = context + 'video/search.json?q=' + $(this).val(); 
		$("#debug").html(queryUrl);
		$("#opus").val($(this).val());
		$.getJSON(queryUrl ,function(data) {
			$('#foundList').empty();
			var row = data['videoList'];
			$.each(row, function(entryIndex, entry) {
				
				var studio 		   = entry['studio'];
				var opus 		   = entry['opus'];
				var title 		   = entry['title'];
				var actress 	   = entry['actress'];
				var existVideo 	   = entry['existVideo'];
				var existCover 	   = entry['existCover'];
				var existSubtitles = entry['existSubtitles'];
				
				var li  = $("<li>");
				var div = $("<div>");

				var studioDom 		  = $("<span>").addClass("label").attr("onclick", "fnViewStudioDetail('" + studio +"')").html(studio);				
				var opusDom 		  = $("<span>").addClass("label").html(opus);
				var titleDom 		  = $("<span>").addClass("label").attr("onclick", "fnViewVideoDetail('" + opus +"')").html(title);
				var actressDom 		  = $("<span>").addClass("label").attr("onclick", "fnViewActressDetail('" + actress +"')").html(actress);
				var existVideoDom 	  = $("<span>").addClass("label").addClass((existVideo == "true" ? "exist" : "nonexist" )).html("V");
				var existCoverDom 	  = $("<span>").addClass("label").addClass((existCover == "true" ? "exist" : "nonexist" )).html("C");
				var existSubtitlesDom = $("<span>").addClass("label").addClass((existSubtitles == "true" ? "exist" : "nonexist" )).html("S");
				
				var html = '<li>[' + entry['studio'] + '][' + entry['opus'] + '][' + entry['title'] + '][' + entry['actress'] + "]";
				html += '&nbsp;V:' + entry['existVideo'] + '&nbsp;C:' + entry['existCover'] + '&nbsp;S:' + entry['existSubtitles'];
				var span = $("<span>").addClass("label").html("["+entry['studio']+"]["+entry['opus']+"]["+entry['title']+"]["+entry['actress']+"]"
						+ "&nbsp;V:" + entry['existVideo'] + "&nbsp;C:" + entry['existCover'] + "&nbsp;S:" + entry['existSubtitles']);

				div.append(studioDom);
				div.append(opusDom);
				div.append(titleDom);
				div.append(actressDom);
				div.append(existVideoDom);
				div.append(existCoverDom);
				div.append(existSubtitlesDom);
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
	var resultDivHeight = $("#resultDiv").outerHeight();
	var calculatedDivHeight = windowHeight - debugDivHeight - queryDivHeight - resultDivHeight - 20 * 3; 
	/* 
	$("#debug").html("windowHeight=" + windowHeight + ", debugDivHeight=" + debugDivHeight 
			+ ", queryDivHeight=" + queryDivHeight + ", resultDivHeight=" + resultDivHeight 
			+ ", calculatedDivHeight=" + calculatedDivHeight 
			+ " : " + (debugDivHeight + queryDivHeight + resultDivHeight) );
	 */
	$("#newVideo").outerHeight(calculatedDivHeight);	
}
function searchAndHighlight(searchTerm) {
    //$('.highlighted').removeClass('highlighted');

    var rexp = eval('/' + searchTerm + "/gi");
	//$("#resultDiv:contains('"+searchTerm+"')").html($('#resultDiv').html().replace(rexp,"<span class='highlighted'>"+searchTerm+"</span>"));
	$("#newVideo:contains('"+searchTerm+"')").html($('#newVideo').html().replace(rexp,"<em>"+searchTerm+"</em>"));

	$("#resultDiv span.label").each(function() {
		$(this).html($(this).html().replace(rexp,"<em>"+searchTerm+"</em>"));
	});
	
   	//if($('em:first').length) {
       	//$(window).scrollTop($('em:first').position().top);
   	//}
   	$(window).scrollTop(0);
   	
   	$("#foundList").html($("#foundList").html().replace(/true/gi, '<em>true</em>'));
}

</script>
</head>
<body>
<div id="debugDiv"></div>
<div id="queryDiv" class="div-box">
	Video Search
	<input type="text" name="query" id="query" style="width:100px;" class="searchInput"/>
	<span class="label">new 
	S<input type="text" name="studio"  id="studio"   style="width:80px;" class="searchInput"/>
	O<input type="text" name="opus"    id="opus"     style="width:80px;" class="searchInput" readonly="readonly"/>
	T<input type="text" name="title"   id="title"    style="width:100px;" class="searchInput"/>
	A<input type="text" name="actress" id="actress"  style="width:100px;" class="searchInput"/>
	E<input type="text" name="etcInfo" id="etcInfo"  style="width:100px;" class="searchInput"/>
	<span id="addVideoBtn">Add</span>
	</span>
</div>

<div id="resultDiv" class="div-box">
	<span id="debug"></span>
	<ol id="foundList"></ol>
</div>

<div id="newVideo" class="div-box">
</div>
</body>
</html>