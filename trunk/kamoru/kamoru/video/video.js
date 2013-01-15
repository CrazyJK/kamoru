$(document).ready(function(){
	$(window).bind("resize", resizeDivHeight);
	$("*[onclick]").css("cursor", "pointer");
	$("label").bind("click", function(){
		var id = $(this).attr("for");
		$("#" + id).val("");
	});
	$('span[id^="checkbox"]').bind("click", function(){
		var idArr = $(this).attr("id").split("-");
		if($("#" + idArr[1]).val() == "on") {
			$("#" + idArr[1]).val("off");
			$(this).removeClass("checkbox-on");
			$(this).addClass("checkbox-off");
		} else {
			$("#" + idArr[1]).val("on");
			$(this).removeClass("checkbox-off");
			$(this).addClass("checkbox-on");
		}
	}).each(function(){
		var idArr = $(this).attr("id").split("-");
		if($("#" + idArr[1]).val() == "on") {
			$(this).removeClass("checkbox-off");
			$(this).addClass("checkbox-on");
		} else {
			$(this).removeClass("checkbox-on");
			$(this).addClass("checkbox-off");
		}
	});
	$('span[id^="radio"]').bind("click", function(){
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
	$("li").toggle(
		function() {
			//$("#debug").html("toggle 1");
			$(this).animate({
				opacity: 0.75
				}, 1000, function(){
					$(this).css("background-color", "green");
				});
		}, function(){
			//$("#debug").html("toggle 2");
			$(this).animate({
				opacity: 1
				}, 500, function(){
					$(this).css("background-color", "");
				});
		});
 	$('span[id^="checkbox-exist"]').bind("click", function(){
 		if($("#addCond").val() == "off") {
 			$("#checkbox-addCond").click();
 			$("#debug").html("addCond click");
 		}
	});
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
function resizeBackgroundImage() {
	var url = $('#contentDiv').css('background-image').replace(/url\(|\)$/ig, "");
	var img = $("<img />");
	img.hide();
	img.bind('load', function(){
		var imgWidth  = $(this).width();
		var imgHeight = $(this).height();
		var divWidth  = $("#contentDiv").width();
		var divHeight = $("#contentDiv").height();
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
		//$("#debug").html("background-image resize :{"+imgWidth+","+imgHeight+"}->{"+width+","+height+"}");
		$("#contentDiv").css("background-size", width + "px " + height + "px");
	});
	$("body").append(img);
	img.attr("src", url);
}
function ratioSize(numerator1, numerator2, denominator) {
	return parseInt(numerator1 * numerator2 / denominator);
}
function fnStudioDivToggle() {
	$("#studioDiv").toggle();
	$("#viewStudioDiv").val($("#studioDiv").css("display"));	
	$("#debug").html("fnStudioDivToggle");
	resizeDivHeight();
}
function fnActressDivToggle() {
	$("#actressDiv").toggle();
	$("#viewActressDiv").val($("#actressDiv").css("display"));	
	$("#debug").html("fnActressDivToggle");
	resizeDivHeight();
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