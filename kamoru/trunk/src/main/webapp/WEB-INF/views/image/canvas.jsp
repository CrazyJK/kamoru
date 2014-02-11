<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8" />
<link rel="shortcut icon" type="image/x-icon" href="<c:url value="/resources/video/video-favicon.ico" />">
<title>Canvas <s:message code="image.image-viewer"/></title>
<style type="text/css">
nav#img-nav ul {
	list-style: none;
	padding: 0px;
	display: block;
	clear: right;
	background-color: #666;
	padding-left: 4px;
	height: 24px;
	text-align: center;
}
nav#img-nav ul li {
	display: inline;
	padding: 0px 10px 5px 10px;
	height: 24px;
	border-right: 0px solid #ccc;
}
nav#img-nav ul li a {
	color: #EFD3D3;
	text-decoration: none;
	font-size: 13px;
	font-weight: bold;
}
nav#img-nav ul li a:hover {
	color: #fff;
}
nav#img-nav ul li a.selected {
	color: #993333;
}
#goPage {
	margin-bottom: 3px;
	font-size: 12px;
	width:15pt;
	text-align:right;
}
#cv {
	border: 1px solid #888;
	-moz-border-radius: 10px;
	-webkit-border-radius: 10px;
	border-radius: 10px;	
	background-color: #000;
}
#img-section {
	text-align:center;
}
</style>	
<script>
var canvas, context;

var imagepath = '<s:url value="/image/" />';
var selectedNumber = ${selectedNumber};
var imageCount = ${imageCount};
var imageMap = ${imageNameJSON};

$(document).ready(function(){

	$("#cv").bind("click", viewNextImage);
	$(window).bind("mousewheel DOMMouseScroll", function(e) {
		var delta = 0;
		var event = window.event || e;
		if (event.wheelDelta) {
			delta = event.wheelDelta/120;
			if (window.opera) delta = -delta;
		} 
		else if (event.detail)  
			delta = -event.detail/3;
		else
			delta = parseInt(event.originalEvent.wheelDelta || -event.originalEvent.detail);
		if (delta) {
			if (delta > 0) 
				viewPrevImage(); //alert("마우스 휠 위로~");
		    else 	
		    	viewNextImage(); //alert("마우스 휠 아래로~");
		}
	});
	$(window).bind("keyup", function(e) {
		var event = window.event || e;
		switch(event.keyCode) {
		case 37: // left
		//case 40: // down
			viewPrevImage(); break;
		case 39: // right
		//case 38: // up
			viewNextImage(); break;
		case 32: // space
			viewRandomImage();
		case 13: // enter
			break;
		}
	});


	canvas = document.getElementById("cv");
	context = canvas.getContext("2d");

	resizeSecondDiv();

});	

function loadImage(current){
	if (parseInt(current) > -1)
		selectedNumber = parseInt(current);
	var canW = canvas.width;
	var canH = canvas.height;
	var image = new Image();
	image.src = imagepath + selectedNumber;
	image.onload = function(){
		var imgW = image.width;
		var imgH = image.height;
		if(canW < imgW){
			imgH = imgH*(canW/imgW);
			imgW = canW;
		}
		if(canH < imgH){
			imgW = imgW*(canH/imgH);
			imgH = canH;
		}
		var xPos = (canW-imgW)/2;
		var yPos = (canH-imgH)/2;
		resetCanvas();
		context.drawImage(image, xPos, yPos, imgW, imgH);
		
		$("#imageName").html(imageMap[selectedNumber]);
		initNav();
		$("nav ul li a[id^=anker]").removeClass();
		$("#anker"+selectedNumber).addClass("selected");
		$("#goPage").val(selectedNumber+1);
	}
}
function resetCanvas(){
	canvas.width = canvas.width;        
}

function getPrevNumber() {
	return selectedNumber == 0 ? imageCount - 1 : selectedNumber - 1;
}
function getNextNumber() {
	return selectedNumber == imageCount -1 ? 0 : selectedNumber + 1;
}
function getRandomNumber() {
	return Math.floor(Math.random() * imageCount);
}

function viewPrevImage(){
	selectedNumber = getPrevNumber();
	loadImage();
}
function viewNextImage(){
	selectedNumber = getNextNumber();
	loadImage();
}
function viewRandomImage() {
	selectedNumber = getRandomNumber();
	loadImage();
}
function initNav(){
	var ul = $("#navUL");
	ul.empty();
	var startIdx = 0;
	var endIdx = 10;
	
	if(selectedNumber > 5){
		startIdx = selectedNumber - 5;
		endIdx = selectedNumber + 10;
	}
	endIdx = endIdx < imageCount ? endIdx : imageCount;
	ul.append("<input size=3 placeholder='GoPage' id='goPage'><li><a onclick='goPage()'>Move</a></li>")
	
	ul.append("<li><a id='ankerFirst' onclick='loadImage(0);'>First</a></li>");
	for(var i=startIdx; i<endIdx; i++){
		ul.append("<li><a id='anker" + i + "' onclick='loadImage(" + i + ");'>" + (i+1) + "</a></li>");
	}
	ul.append("<li><a id='ankerLast' onclick='loadImage(" + (imageCount-1) + ");'>Last</a></li>");
}
function goPage(){
	var pNo = $("#goPage").val();
	if (pNo > 0 || pNo < imageCount + 1) {
		selectedNumber = pNo - 1;
		loadImage();
	}
}
function resizeSecondDiv() {
	var contentDivHeight = $("#deco_section").outerHeight();
	var imgNavHeight = $("#img-nav").outerHeight();
	var calculatedDivHeight = contentDivHeight - imgNavHeight - 16 - 20 - 25; // margin:16, padding:20, extra 
	//$("#cv").outerHeight(calculatedDivHeight);
	$("#cv").attr("height", calculatedDivHeight);
//	alert(contentDivHeight +"-"+ imgNavHeight +"="+ calculatedDivHeight);
	if (selectedNumber > -1)
		loadImage();
	else
		viewRandomImage();

}

</script>
</head>
<body>

<article id="img-article">
	<section id="img-section">
		<canvas id="cv" width="900px" height="500px"></canvas>
	</section>
</article>

<nav id="img-nav">
	<div style="text-align:center;">
		<span class="label" onclick="viewPrevImage()" style="float:left;">Prev</span>
		<span id="imageName"></span>
		<span class="label" onclick="viewNextImage()" style="float:right;">Next</span>
	</div>
	<ul id="navUL">
	</ul>
</nav>

</body>
</html>