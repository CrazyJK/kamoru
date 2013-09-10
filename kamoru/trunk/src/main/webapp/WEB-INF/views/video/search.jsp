<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"      uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn"     uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix='form'   uri='http://www.springframework.org/tags/form'%>
<!DOCTYPE html>
<html>
<head>
<title><spring:message code="text.video"/> <spring:message code="text.search"/></title>
<script type="text/javascript">
$(document).ready(function(){
	$(window).bind("resize", resizeDivHeight);
	
	$("#query").bind("keyup", function(event) {
		var keyword = $(this).val();
		var queryUrl = context + 'video/search.json?q=' + keyword; 
		$("#debug").html(queryUrl);
		$("#opus").val(keyword);
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

				var studioDom 		  = $("<span>").addClass("search-item").attr("onclick", "fnViewStudioDetail('" + studio +"')").html(studio);				
				var opusDom 		  = $("<span>").addClass("search-item").html(opus);
				var titleDom 		  = $("<span>").addClass("search-item").attr("onclick", "fnViewVideoDetail('" + opus +"')").html(title);
				var actressDom 		  = $("<span>").addClass("search-item").attr("onclick", "fnViewActressDetail('" + actress +"')").html(actress);
				var existVideoDom 	  = $("<span>").addClass("search-item").addClass((existVideo == "true" ? "exist" : "nonexist" )).html("V");
				var existCoverDom 	  = $("<span>").addClass("search-item").addClass((existCover == "true" ? "exist" : "nonexist" )).html("C");
				var existSubtitlesDom = $("<span>").addClass("search-item").addClass((existSubtitles == "true" ? "exist" : "nonexist" )).html("S");
				var addButton		  = $("<span>").addClass("button").html(" add").attr("onclick", "fnAddResult(this)");
				
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
				div.append(addButton);
				li.append(div);
				$('#foundList').append(li);
			});
			$('#foundList').append("<li><div>EOF : " + keyword + "</div>");
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
function fnAddResult(dom) {
	var html = dom.parentNode.parentNode.innerHTML;
	var li = $("<li>").html(html);
	$("#newVideo > ol").append(li);
	$("div#newVideo.div-box ol li div span").last().css("display", "none");
}
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
	<label for="query"><spring:message code="text.video"/> <spring:message code="text.search"/></label>
	<input type="search" name="query" id="query" style="width:100px;" class="searchInput" placeHolder="Search"/>
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
<ol>
</ol>
</div>
</body>
</html>