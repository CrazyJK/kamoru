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
<title>Video collection</title>
<link rel="stylesheet" href="<c:url value="/resources/video/video.css" />" />
<!--[if lt IE 9]>
<script src="http://html5shiv.googlecode.com/svn/trunk/html5.js"></script>
<![endif]-->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.8.3/jquery.min.js"></script>
<script type="text/javascript">
var context = '<spring:url value="/"/>';

$(document).ready(function(){
	$("#query").bind("keyup", function(event){
		var queryUrl = context + 'videosearch/q=' + $(this).val(); 
		$("#debug").html(queryUrl);
		$("#opus").val($(this).val());
		$.getJSON(queryUrl ,function(data) {
			$('#foundUL').empty();
			$.each(data, function(entryIndex, entry) {
				var html = '<li>[' + entry['studio'] + '][' + entry['opus'] + '][' + entry['title'] + '][' + entry['actress'];
				html += '][V:' + entry['existVideo'] + '][C:' + entry['existCover'] + '][S:' + entry['existSubtitles'] + ']';
				$('#foundUL').append(html);
			});
		});
	});
	$("#addVideoBtn").bind("click", function() {
		var html = "<p>["+$("#studio").val()+"]["+$("#opus").val()+"]["+$("#title").val()+"]["+$("#actress").val()+"]["+$("#etcInfo").val()+"]</p>";
		$("#newVideo").append(html);
	});
});
</script>
</head>
<body>
<div><span id="debug"></span></div>
<div>
	<input type="text" name="query" id="query" style="width:100px;" />
	<span class="bgSpan">
	<input type="text" name="studio"  id="studio"   style="width:80px;" />
	<input type="text" name="opus" 	  id="opus"     style="width:80px;" />
	<input type="text" name="title"   id="title"    style="width:100px;" />
	<input type="text" name="actress" id="actress"  style="width:100px;" />
	<input type="text" name="etcInfo" id="etcInfo"  style="width:100px;" />
	<span id="addVideoBtn">Add</span>
	</span>
</div>

<div>
	<ul id="foundUL"></ul>
</div>

<div id="newVideo">
	
</div>
</body>
</html>