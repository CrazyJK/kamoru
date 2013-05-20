function popup(url, name, width, height, positionMethod, spec) {
	var vUrl = url;
	var vName = name.replace(/-/gi, '');
	var left = (window.screen.width  - width)/2;
	var top  = (window.screen.height - height)/2;
	var specs = "toolbar=0,location=0,directories=0,titlebar=0,status=0,menubar=0,scrollbars=0,resizable=1";
	if(positionMethod) {
		if(positionMethod == 'Window.Center') {
			left = (window.screen.width  - width)/2;
			top  = (window.screen.height - height)/2;
		} else if(positionMethod == 'Mouse') {
			left = window.event.x;
			top  = window.event.y;
		}
	}
	if(spec) {
		specs = spec;
	}
	specs = "width="+width+",height="+height+",top="+top+", left="+left + "," + specs;
//	alert(vUrl + "\n" + vName + "\n" + specs);
	window.open(vUrl, vName, specs);
}

function fnViewFullImage(image) {
	
	var img = $("<img />");
	img.hide();
	img.bind('load', function(){
		var imgWidth  = $(this).width() + 20;
		var imgHeight = $(this).height() + 20;
		popup(image.src, "", imgWidth, imgHeight);
	});
	$("body").append(img);
	img.attr("src", image.src);
	
}