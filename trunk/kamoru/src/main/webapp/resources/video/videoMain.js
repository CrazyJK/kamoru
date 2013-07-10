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
	$("li").toggle(
		function() {
			$(this).animate({
				opacity: 0.75
				}, 500, function(){
					$(this).addClass("boxLIselect");
					//$(this).css("background-color", "lightgreen");
					$("#DEL-"+$(this).attr("id")).css("display", "");
				});
		}, function() {
			$(this).animate({
				opacity: 1
				}, 500, function(){
					$(this).removeClass("boxLIselect");
					//$(this).css("background-color", "");
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
 			fnDetailSearch();
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
 	
 	//rank coloring
 	$('input[type="range"]').each(function() {
 		fnRankColor($(this));
 	});
 	
 	// resize contentDiv height
	resizeDivHeight();
	setBackgroundImage();
});
