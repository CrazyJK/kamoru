function popup(url, name, width, height, positionMethod, spec) {
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
	window.open(url, name, specs);
}