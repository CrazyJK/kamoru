$(document).ready(function(){
	// Add listener : if window resize, contentDiv height resize.
	$(window).bind("resize", resizeDivHeight);
	// Add class : elements in onclick attribute add class
	$("*[onclick]").addClass("onclick");
	// Add listener : if labal click, empty input text value
	$("label").bind("click", function(){
		var id = $(this).attr("for");
		$("#" + id).val("");
	});
	// Add listener : implement checkbox element
	$('span[id^="checkbox"]').bind("click", 
			function(){
				var idArr = $(this).attr("id").split("-");
				if($("#" + idArr[1]).val() == "on" || $("#" + idArr[1]).val() == "true") {
					$("#" + idArr[1]).val("off");
					$(this).removeClass("checkbox-on");
				} else {
					$("#" + idArr[1]).val("on");
					$(this).addClass("checkbox-on");
				}
	}).each(function(){
		var idArr = $(this).attr("id").split("-");
		if($("#" + idArr[1]).val() == "on" || $("#" + idArr[1]).val() == "true") {
			$(this).addClass("checkbox-on");
		} else {
			$(this).removeClass("checkbox-on");
		}
	});
	// Add listener : implement radio element
	$('span[id^="radio"]').bind("click", 
			function(){
				var idArr = $(this).attr("id").split("-");
				$("#" + idArr[1]).val(idArr[2]);
				$('span[id^="radio-' + idArr[1] + '"]').removeClass("radio-on");
				$(this).addClass("radio-on");
	}).each(function(){
		var idArr = $(this).attr("id").split("-");
		if($("#" + idArr[1]).val() == idArr[2]) {
			$(this).addClass("radio-on");
		} else {
			$(this).removeClass("radio-on");
		}
	});

	// video box click. add border, opacity
	$("li").toggle(
		function() {
			$(this).animate({
				opacity: 0.75
				}, 1000, function(){
					$(this).css("background-color", "green");
					$("#DEL-"+$(this).attr("id")).css("display", "");
				});
		}, function(){
			$(this).animate({
				opacity: 1
				}, 500, function(){
					$(this).css("background-color", "");
					$("#DEL-"+$(this).attr("id")).css("display", "none");
				});
		});
 	$('span[id^="checkbox-exist"]').bind("click", function(){
 		if($("#addCond").val() == "off" || $("#addCond").val() == "" || $("#addCond").val() == "false") {
 			$("#debug").html("addCond click");
 			$("#checkbox-addCond").click();
 		}
	});

 	// init visible studioDiv & actressDiv 
 	if($("#viewStudioDiv").val() != "on" && $("#viewStudioDiv").val() != "true") {
 		$("#studioDiv").css("display", "none");
 	} else {
 		$("#studioDiv").css("display", "block");
 	}
 	if($("#viewActressDiv").val() != "on" && $("#viewActressDiv").val() != "true") {
 		$("#actressDiv").css("display", "none");
 	} else {
 		$("#actressDiv").css("display", "block");
 	}
 	
 	// resize contentDiv height
	resizeDivHeight();
});

function resizeDivHeight() {
	var windowHeight = $(window).height();
	//var documentHeight = $(document).outerHeight();
	var searchDivHeight = $("#headerDiv").outerHeight();
	var resizeContentDivHeight = windowHeight - searchDivHeight - 16 - 20 - 20; 
	//alert(resizeContentDivHeight);
	$("#contentDiv").height(resizeContentDivHeight);
	resizeBackgroundImage();
}
var currBGImageUrl;
var selectedNumber = Math.floor(Math.random() * bgImageCount);
function resizeBackgroundImage() {
	currBGImageUrl = context + "image/" + selectedNumber;
	
	var img = $("<img />");
	img.hide();
	img.bind('load', function(){
		var imgWidth  = $(this).width();
		var imgHeight = $(this).height();
		var divWidth  = $("#contentDiv").width() + 30;
		var divHeight = $("#contentDiv").height() + 20;
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
		//$("#debug").html("background-image resize :{"+imgWidth+","+imgHeight+"}->{"+width+","+height+"}");
		$("#contentDiv").css("background-image", "url(" + currBGImageUrl + ")");
		$("#contentDiv").css("background-size", width + "px " + height + "px");
	});
	$("body").append(img);
	img.attr("src", currBGImageUrl);
}
function ratioSize(numerator1, numerator2, denominator) {
	return parseInt(numerator1 * numerator2 / denominator);
}
function fnStudioDivToggle() {
	$("#studioDiv").toggle();
	resizeDivHeight();
	$("#debug").html("fnStudioDivToggle");
}
function fnActressDivToggle() {
	$("#actressDiv").toggle();
	resizeDivHeight();
	$("#debug").html("fnActressDivToggle");
}
function fnStudioSearch(studio) {
	$("input:text").each(function(){
		$(this).val("");
	});
	$("#studio").val(studio);
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
	var frm = document.forms[0];
	frm.submit();
}
function fnDeleteOpus(selectedOpus) {
	if(confirm("Really? Are you sure to delete this opus?")) {
		if(confirm("Are you kidding? D.E.L.E.T.E [" + selectedOpus + "]?")) {
			$("#debug").html("delete " + selectedOpus);
			$("#hiddenHttpMethod").val("delete");
			var frm = document.forms["actionFrm"];
			frm.action = context + "video/" + selectedOpus;
			frm.submit();
		}
	}
}
function fnEditSubtitles(selectedOpus) {
	$("#debug").html("edit subtitles " + selectedOpus);
	$("#actionIframe").attr("src", context + "video/" + selectedOpus + "/subtitles");
}
function fnPlay(selectedOpus) {
	$("#debug").html("Video play " + selectedOpus);
	$("#actionIframe").attr("src", context + "video/" + selectedOpus + "/play");
}
function fnRandomPlay() {
	$("#debug").html("Random play start");
	if(opusArray.length == 0) {
		alert("다 봤슴당");
		return;
	}
	var selectedNumber = Math.floor(Math.random() * opusArray.length);
	var selectedOpus = opusArray[selectedNumber];
	opusArray.splice(selectedNumber, 1);
	fnOpusFocus(selectedOpus);
	fnPlay(selectedOpus);
}
function fnOpusFocus(opus) {
	//alert("fnOpusFocus " + opus);
	$("#" + opus).animate({
		opacity: 0.75,
	}, 1000, function(){
		$(this).css("background-color", "cyan");
	});
	$("#contentDiv").scrollTop($("#" + opus).position().top);
}
function fnBGImageView() {
	popupImage(currBGImageUrl);
}
function fnImageView(opus) {
	$("#debug").html("Cover image view : " + opus);
	popupImage(context + "video/" + opus + "/cover");
}
function fnEditOverview(opus) {
	$("#debug").html("Overview Popup : " + opus);
    popup(context + "video/" + opus + "/overview", "overview-"+opus, 400, 300, 'Mouse');
}
function fnVideoDetail(opus) {
    popup(context + "video/" + opus, "detailview-"+opus, 850, 800);
}

function popupImage(url) {
	var img = $("<img />");
	img.hide();
	img.attr("src", url);
	img.bind('load', function(){
		mw_image_window(this);
	});
	return img;
}

function fnViewActressDetail(name) {
	popup(context + "video/actress/" + name, "actressDetail-" + name, 800, 600);
}

function fnViewStudioDetail(name) {
	popup(context + "video/studio/" + name, "studioDetail-" + name, 800, 600);
}

function fnViewVideoDetail(opus) {
	popup(context + "video/" + opus, "videoDetail-" + opus, 800, 600);
}
