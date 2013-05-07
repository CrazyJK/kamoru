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
<style type="text/css">
.highlighted {background-color:yellow;}
</style>
<!--[if lt IE 9]>
<script src="http://html5shiv.googlecode.com/svn/trunk/html5.js"></script>
<![endif]-->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.8.3/jquery.min.js"></script>
<script type="text/javascript">
var context = '<spring:url value="/"/>';

$(document).ready(function(){
	$(window).bind("resize", resizeDivHeight);
	resizeDivHeight();
	$("#query").bind("keyup", function(event){
		var queryUrl = context + 'video/search.json?q=' + $(this).val(); 
		$("#debug").html(queryUrl);
		$("#opus").val($(this).val());
		$.getJSON(queryUrl ,function(data) {
			$('#foundList').empty();
			var row = data['videoList'];
			$.each(row, function(entryIndex, entry) {
				var html = '<li>[' + entry['studio'] + '][' + entry['opus'] + '][' + entry['title'] + '][' + entry['actress'] + "]";
				html += '&nbsp;V:' + entry['existVideo'] + '&nbsp;C:' + entry['existCover'] + '&nbsp;S:' + entry['existSubtitles'];
				$('#foundList').append(html);
			});
			searchAndHighlight($("#query").val());
			resizeDivHeight();
		});
	});
	$("#addVideoBtn").bind("click", function() {
		var html = "<p>["+$("#studio").val()+"]["+$("#opus").val().toUpperCase()+"]["+$("#title").val()+"]["+$("#actress").val()+"]["+$("#etcInfo").val()+"]</p>";
		$("#newVideo").append(html);
	});	
});
function resizeDivHeight() {
	var windowHeight = $(window).height();

	var  debugDivHeight = $("#debugDiv").outerHeight();
	var  queryDivHeight = $("#queryDiv").outerHeight();
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
    $('.highlighted').removeClass('highlighted');

    var rexp = eval('/' + searchTerm + "/gi");
	$("#resultDiv:contains('"+searchTerm+"')").html($('#resultDiv').html().replace(rexp,"<span class='highlighted'>"+searchTerm+"</span>"));
	$("#newVideo:contains('"+searchTerm+"')").html($('#newVideo').html().replace(rexp,"<span class='highlighted'>"+searchTerm+"</span>"));

   	if($('.highlighted:first').length) {
       	$(window).scrollTop($('.highlighted:first').position().top);
   	}
   	
   	$("#foundList").html($("#foundList").html().replace(/true/gi, '<font color="red">true</font>'));
}

</script>
</head>
<body>
<div id="debugDiv"><span id="debug"></span></div>
<div id="queryDiv">
	Search<input type="text" name="query" id="query" style="width:100px;" />
	<span class="bgSpan">new 
	S<input type="text" name="studio"  id="studio"   style="width:80px;" />
	O<input type="text" name="opus"    id="opus"     style="width:80px;" readonly="readonly"/>
	T<input type="text" name="title"   id="title"    style="width:100px;" />
	A<input type="text" name="actress" id="actress"  style="width:100px;" />
	E<input type="text" name="etcInfo" id="etcInfo"  style="width:100px;" />
	<span id="addVideoBtn">Add</span>
	</span>
</div>

<div id="resultDiv">
	<ol id="foundList"></ol>
</div>

<div id="newVideo" class="boxDiv">
</div>
</body>
</html>