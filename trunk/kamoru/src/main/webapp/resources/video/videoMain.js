
var currBGImageUrl;
var selectedNumber = Math.floor(Math.random() * bgImageCount);

$(document).ready(function(){
	
	// Add listener : if window resize, contentDiv height resize.
	$(window).bind("resize", resizeContentDivHeight);
	
	// Add listener : implement checkbox element
	$('span[id^="checkbox"]')
		.bind("click", function(){
			var hiddenCheckbox = $("#" + $(this).attr("id").split("-")[1]);
			if(hiddenCheckbox.val() == "true") {
				hiddenCheckbox.val("false");
				$(this).removeClass("checkbox-on");
			} else {
				hiddenCheckbox.val("true");
				$(this).addClass("checkbox-on");
			}
		})
		.each(function(){
			var hiddenCheckbox = $("#" + $(this).attr("id").split("-")[1]);
			if(hiddenCheckbox.val() == "true") {
				$(this).addClass("checkbox-on");
			} else {
				$(this).removeClass("checkbox-on");
			}
		});
	
	// Add listener : implement radio element
	$('span[id^="radio"]')
		.bind("click", function(){
			var idArr = $(this).attr("id").split("-");
			$("#" + idArr[1]).val(idArr[2]);
			$('span[id^="radio-' + idArr[1] + '"]').removeClass("radio-on");
			$(this).addClass("radio-on");
		})
		.each(function(){
			var idArr = $(this).attr("id").split("-");
			if($("#" + idArr[1]).val() == idArr[2]) {
				$(this).addClass("radio-on");
			} else {
				$(this).removeClass("radio-on");
			}
		});

	// Add listener : video box click. add border, opacity
	$("li[id^='opus-']").toggle(
		function() {
			$(this).animate({
				opacity: 0.75
				}, 500, function(){
					$(this).addClass("li-box-select");
					$("#DEL-"+$(this).attr("id")).css("display", "");
				});
		}, function() {
			$(this).animate({
				opacity: 1
				}, 500, function(){
					$(this).removeClass("li-box-select");
					$("#DEL-"+$(this).attr("id")).css("display", "none");
				});
		});

	// Add listener : addCond click if its child clicked
 	$('span[id^="checkbox-exist"]').bind("click", function(){
 		if($("#addCond").val() == "false") {
 			$("#debug").html("addCond click");
 			$("#checkbox-addCond").click();
 		}
	});

 	// Add listener : input text enter
 	$("input.schTxt").live('keypress', function(e) {
 		if(e.which == 13) {
 			fnSearch();
 		}
 	});
 	
 	// init visible studioDiv 
 	if($("#viewStudioDiv").val() != "on" && $("#viewStudioDiv").val() != "true") {
 		$("#studioDiv").css("display", "none");
 	} else {
 		$("#studioDiv").css("display", "block");
 	}

 	// init visible actressDiv 
 	if($("#viewActressDiv").val() != "on" && $("#viewActressDiv").val() != "true") {
 		$("#actressDiv").css("display", "none");
 	} else {
 		$("#actressDiv").css("display", "block");
 	}
 	
 	// resize contentDiv height
	resizeContentDivHeight();
	// set background image
	setBackgroundImage();
	
	// for slide view
	if(listViewType == 'S') {
		fnShowVideoSlise();
		$("#video-slide-wrapper").bind("mousewheel DOMMouseScroll", function(e) {
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
					fnPrevVideoView(); //alert("마우스 휠 위로~");
			    else 	
					fnNextVideoView(); //alert("마우스 휠 아래로~");
			}
		});
		
		$(window).bind("keyup", function(e) {
			var event = window.event || e;
			//alert(event.keyCode);
			switch(event.keyCode) {
			case 37: // left
			case 40: // down
				fnPrevVideoView(); break;
			case 39: // right
			case 38: // up
				fnNextVideoView(); break;
			case 32: // space
				fnRandomVideoView(); break;
			case 13: // enter
				break;
			}
		});

	}

});
