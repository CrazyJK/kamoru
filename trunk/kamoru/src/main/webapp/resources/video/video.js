function resizeContentDivHeight() {
	var windowHeight = $(window).height();
	var searchDivHeight = $("#headerDiv").outerHeight();
	var resizeContentDivHeight = windowHeight - searchDivHeight - 16 - 20 - 20; 
	$("#contentDiv").height(resizeContentDivHeight); //alert(resizeContentDivHeight);
}
function setBackgroundImage() {
	currBGImageUrl = context + "image/" + selectedNumber;
	$("#contentDiv").css("background-image", "url(" + currBGImageUrl + ")");
}
/**
 * background-size:contain; Scale the image to the largest size such that both its width and its height can fit inside the content area
 * 이 설정과 같이 움직이도록 하는 함수 
function resizeBackgroundImage() {
	currBGImageUrl = context + "image/" + selectedNumber;
	
	var img = $("<img />");
	img.hide();
	img.attr("src", currBGImageUrl);
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
		//debug("background-image resize :{"+imgWidth+","+imgHeight+"}->{"+width+","+height+"}");
		$("#contentDiv").css("background-image", "url(" + currBGImageUrl + ")");
		$("#contentDiv").css("background-size", width + "px " + height + "px");
	});
	$("body").append(img);
}
function ratioSize(numerator1, numerator2, denominator) {
	return parseInt(numerator1 * numerator2 / denominator);
}
 */

function fnStudioDivToggle() {
	$("#studioDiv").toggle();
	resizeContentDivHeight();
	debug("fnStudioDivToggle");
}
function fnActressDivToggle() {
	$("#actressDiv").toggle();
	resizeContentDivHeight();
	debug("fnActressDivToggle");
}
function fnSearch(txt) {
	if(txt)
		$("#searchText").val(txt);
	var frm = document.forms[0];
	frm.submit();
}
function fnDeleteOpus(selectedOpus) {
	if(confirm("Really? Are you sure to delete this opus?")) 
		if(confirm("Are you kidding? D.E.L.E.T.E [" + selectedOpus + "]?")) {
			$("#hiddenHttpMethod").val("delete");
			// hide it's box
			$("#opus-" + selectedOpus).hide();
			// remove element
			for(var i=0; i<opusArray.length; i++) 
				if(selectedOpus == opusArray[i]) {
					opusArray.splice(i, 1);
					break;
				}
			
			var frm = document.forms["actionFrm"];
			frm.action = context + "video/" + selectedOpus;
			frm.submit();
			debug("delete " + selectedOpus);
		}
	
}
function fnEditSubtitles(selectedOpus) {
	debug("edit subtitles " + selectedOpus);
	$("#actionIframe").attr("src", context + "video/" + selectedOpus + "/subtitles");
}
function fnPlay(selectedOpus) {
	debug("Video play " + selectedOpus);
	$("#actionIframe").attr("src", context + "video/" + selectedOpus + "/play");
	fnVideoDetail(selectedOpus);
}
function fnRandomPlay() {
	debug("Random play start");
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
	if (listViewType == 'S') {
		var idx = $("#opus-" + opus).attr("tabindex");
		fnHideVideoSlise(currentVideoIndex);
		currentVideoIndex = idx;
		fnShowVideoSlise();
	}
	else {
		$("#opus-" + opus).animate({
			opacity: 0.5,
		}, 1000, function(){
			$(this).addClass("li-box-played");
		});
		var topValue = $("#opus-" + opus).position().top - $("#headerDiv").outerHeight() - 20;
		$("#contentDiv").scrollTop(topValue);
	}
}
function fnBGImageView() {
	popup(context + "image?n=" + selectedNumber, "ImageView" + selectedNumber, 800, 600);
}
function fnImageView(opus) {
	debug("Cover image view : " + opus);
	popupImage(context + "video/" + opus + "/cover");
}
function fnEditOverview(opus) {
	debug("Overview Popup : " + opus);
    popup(context + "video/" + opus + "/overview", "overview-"+opus, 400, 300, 'Mouse');
}
function fnVideoDetail(opus) {
    popup(context + "video/" + opus, "detailview-"+opus, 850, 800);
}
function fnRank(opus) {
	var rank = $("#Rank-"+opus);
	fnRankColor(rank);
	var frm;
	if(opener) {
		$("#Rank-"+opus, opener.document).val(rank.val());
		opener.fnRankColor($("#Rank-"+opus, opener.document));
		$("#hiddenHttpMethod", opener.document).val("put");
		frm = opener.document.forms["actionFrm"];
	}
	else {
		$("#hiddenHttpMethod").val("put");
		frm = document.forms["actionFrm"];
	}
	frm.action = context + "video/" + opus + "/rank/" + rank.val();
	frm.submit();
}
function fnRankColor(rank) {
	if(rank.val() == 0) {
		rank.css("background-color", "cyan");
	}
	else if(rank.val() > 0) {
		rank.css("background-color", "red");
	}
	else {
		rank.css("background-color", "blue");
	}
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

function debug(msg) {
	$("#debug").html(msg);
}

// for slide
function fnPrevVideoView() {
	fnHideVideoSlise(currentVideoIndex);
	if (currentVideoIndex == 1)
		currentVideoIndex = totalVideoSize + 1;
	currentVideoIndex--;
	fnShowVideoSlise();
}
function fnNextVideoView() {
	fnHideVideoSlise(currentVideoIndex);
	if (currentVideoIndex == totalVideoSize)
		currentVideoIndex = 0;
	currentVideoIndex++;
	fnShowVideoSlise();
}
function fnRandomVideoView() {
	fnHideVideoSlise(currentVideoIndex);
	currentVideoIndex = Math.floor(Math.random() * totalVideoSize);
	fnShowVideoSlise();
}
function fnShowVideoSlise() {
	$("div[tabindex='" + currentVideoIndex + "']").fadeIn();
	$("#slideNumber").html(currentVideoIndex + " / " + totalVideoSize);
	
	$("#video_slide_bar").empty();
	var startIdx = parseInt(currentVideoIndex) - 1;
	var endIdx = parseInt(currentVideoIndex) + 1;
	for (var i=startIdx; i<=endIdx; i++) {
		var previewIndex = i;
		if (previewIndex == 0)
			previewIndex = totalVideoSize;
		else if (previewIndex == totalVideoSize + 1)
			previewIndex = 1;
		
		var item = $("<div class='video-box' style='display:inline-block;'>");
		item.append($("div[tabindex='" + previewIndex + "']").html());
		item.children("dl").removeClass("video-slide-bg").addClass("video-box-bg");
		item.children().children().children().each(function() {
			$(this).removeClass("label-large").addClass("label");
		});
		//item.append("<span style='color:red;'>" + startIdx + ":" + previewIndex + ":" + i + ":" + endIdx + "</span>");
		$("#video_slide_bar").append(item);
	}
}
function fnHideVideoSlise(idx) {
	$("div[tabindex='" + idx + "']").hide();
}
