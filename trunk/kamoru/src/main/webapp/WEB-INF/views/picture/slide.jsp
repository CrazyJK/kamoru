<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html>
<html> 
<head>
<meta charset="UTF-8">
<link rel="shortcut icon" type="image/x-icon" href="<c:url value="/resources/video/video-favicon.ico" />">
<title>Local Image Viewer</title>
<link rel="stylesheet" href="<c:url value="/resources/video/video.css" />" />
<style type="text/css">
* {margin:0px; padding:0px;}
.btnPosition {position:absolute; right:0px; top:0px; margin:10px 5px 0px 0px; cursor:pointer;}
</style>
<!--[if lt IE 9]>
<script src="http://html5shiv.googlecode.com/svn/trunk/html5.js"></script>
<![endif]-->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.8.3/jquery.min.js"></script>
<script type="text/javascript">
$(document).ready(function(){
	$(window).bind("mousewheel", function(e){
		var delta = 0;
		var event=window.event || e;
		if (event.wheelDelta) {
			delta = event.wheelDelta/120;
			if (window.opera) delta = -delta;
		} else if (event.detail) 
			delta = -event.detail/3;
		if (delta) {
			if (delta > 0) {
		        //alert("마우스 휠 위로~");
				fnPrevImageView();
		    }
		    else {
				//alert("마우스 휠 아래로~");
				fnNextImageView();
		    }
		}
		//alert("detail=" + evt.detail + " wheelDelta=" + evt.wheelDelta);
	});
	$(window).bind("keyup", function(e){
		var event=window.event || e;
		//alert(event.keyCode); // 37:right, 38:up,  39:left, 40:down, 32:space
		switch(event.keyCode) {
		case 37:
			fnPrevImageView(); break;
		case 32:
		case 39:
			fnNextImageView(); break;
		case 38:
			fnRandomImageView(); break;
		}
		
	});
	$("#imageDiv").bind("click", function(e){
		var event=window.event || e;
		//alert(event.type + " - " + event.button + ", keyValue=" + event.keyCode);
		
		event.preventDefault();
		event.stopPropagation();
		if(event.button == 0) {
			fnRandomImageView();
		} 
	});
	fnRandomImageView();
});
var imagepath = '<s:url value="/image/" />';
var imgWidth, imgHeight;
var img;
var selectedNumber;
var url;
var imageCount = <c:out value="${imageCount}" />;
function fnViewImage() {
	url = imagepath + selectedNumber;
	img = $("<img id='img'/>");
	img.hide();
	img.attr("src", url);
	img.bind('load', resizeImage);
	$("#imageDiv").empty().append(img);
	$("#fullImageBtn").html("F" + selectedNumber);
}
function resizeImage() {
	imgWidth  = $(this).width();
	imgHeight = $(this).height();
	var divWidth  = $(window).width();
	var divHeight = $(window).height()-5;
	var width  = 0;
	var height = 0;
	
	if(imgWidth <= divWidth && imgHeight <= divHeight) { // 1. x:- y:-
		width  = imgWidth;
		height = imgHeight;
	}else if(imgWidth <= divWidth && imgHeight > divHeight) { // 2. x:- y:+
		width  = ratioSize(divHeight, imgWidth, imgHeight);
		height = divHeight;
	}else if(imgWidth > divWidth && imgHeight <= divHeight) { // 3. x:+ y:-
		width  = divWidth;
		height = ratioSize(divWidth, imgHeight, imgWidth);
	}else if(imgWidth > divWidth && imgHeight > divHeight) { // 4. x:+ y:+
		width  = divWidth;
		height = ratioSize(width, imgHeight, imgWidth);
		if(height > divHeight) {
			width  = ratioSize(divHeight, imgWidth, imgHeight);
			height = divHeight;
		}
	}
	$(this).attr("width", width);
	$(this).attr("height", height);
	$(this).fadeIn('fast');
	$("#debug").html("image:" + imgWidth + "x" + imgHeight + " window:" + divWidth + "x" + divHeight + " resize:" + width + "x" + height);
}
function ratioSize(numerator1, numerator2, denominator) {
	return parseInt(numerator1 * numerator2 / denominator);
}
function fnFullyImageView(){
	$("#img").attr("width", imgWidth);
	$("#img").attr("height", imgHeight);
}
function fnPrevImageView() {
	selectedNumber = selectedNumber == 0 ? imageCount - 1 : selectedNumber - 1;
	fnViewImage();
}
function fnNextImageView() {
	selectedNumber = selectedNumber == imageCount -1 ? 0 : selectedNumber + 1;
	fnViewImage();
}
function fnRandomImageView() {
	selectedNumber = Math.floor(Math.random() * imageCount);	
	fnViewImage();
}
</script>
</head>
<body>
<span id="debug" style="display:none;"></span>
<div class="btnPosition">
<span class="bgSpan" onclick="fnPrevImageView()">&lt;</span>
<span id="fullImageBtn" onclick="fnFullyImageView();" class="bgSpan">F</span>
<span class="bgSpan" onclick="fnNextImageView()">&gt;</span>
</div>
<div id="imageDiv" style="text-align:center;"></div>
</body>
</html>
