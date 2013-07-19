function popup(url, name, width, height, positionMethod, spec) {
	var vUrl = url;
	var vName = name.replace(/-/gi, '');
	if (!width)
		width = window.screen.width/2;
	if (!height)
		height = window.screen.height/2;
	var left = (window.screen.width  - width)/2;
	var top  = (window.screen.height - height)/2;
	var specs = "toolbar=0,location=0,directories=0,titlebar=0,status=0,menubar=0,scrollbars=1,resizable=1";
	if (positionMethod) {
		if(positionMethod == 'Window.Center') {
			left = (window.screen.width  - width)/2;
			top  = (window.screen.height - height)/2;
		} 
		else if (positionMethod == 'Mouse') {
			try {
				left = window.event.x;
				top  = window.event.y;
			} catch(e) {}
		}
	}
	else {
		left = (window.screen.width  - width)/2;
		top  = (window.screen.height - height)/2;
	}
	if(spec) {
		specs = spec;
	}
	specs = "width="+width+",height="+height+",top="+top+", left="+left + "," + specs;
//	alert(vUrl + "\n" + vName + "\n" + specs);
	window.open(vUrl, vName, specs);
}
function popupImage(url) {
	var img = $("<img />");
	img.hide();
	img.attr("src", url);
	img.bind('load', function() {
		mw_image_window(this);
	});
}
function fnViewFullImage(image) {
	var img = $("<img />");
	img.hide();
	img.attr("src", image.src);
	img.bind('load', function(){
		var imgWidth  = $(this).width() + 20;
		var imgHeight = $(this).height() + 20;
		mw_image_window(image, imgWidth, imgHeight);
	});
}

function mw_image_window(img, w, h)
{
	if (!w || !h)
	{
        w = img.width; 
        h = img.height; 
	}

	var winl = (screen.width-w)/2; 
	var wint = (screen.height-h)/3; 

	if (w >= screen.width) { 
		winl = 0; 
		h = (parseInt)(w * (h / w)); 
	} 

	if (h >= screen.height) { 
		wint = 0; 
		w = (parseInt)(h * (w / h)); 
	} 

	var js_url = "<script language='JavaScript1.2'> \n"; 
		js_url += "<!-- \n"; 
		js_url += "var ie=document.all; \n"; 
		js_url += "var nn6=document.getElementById&&!document.all; \n"; 
		js_url += "var isdrag=false; \n"; 
		js_url += "var x,y; \n"; 
		js_url += "var dobj; \n"; 
		js_url += "function movemouse(e) \n"; 
		js_url += "{ \n"; 
		js_url += "  if (isdrag) \n"; 
		js_url += "  { \n"; 
		js_url += "    dobj.style.left = nn6 ? tx + e.clientX - x : tx + event.clientX - x; \n"; 
		js_url += "    dobj.style.top  = nn6 ? ty + e.clientY - y : ty + event.clientY - y; \n"; 
		js_url += "    return false; \n"; 
		js_url += "  } \n"; 
		js_url += "} \n"; 
		js_url += "function selectmouse(e) \n"; 
		js_url += "{ \n"; 
		js_url += "  var fobj      = nn6 ? e.target : event.srcElement; \n"; 
		js_url += "  var topelement = nn6 ? 'HTML' : 'BODY'; \n"; 
		js_url += "  while (fobj.tagName != topelement && fobj.className != 'dragme') \n"; 
		js_url += "  { \n"; 
		js_url += "    fobj = nn6 ? fobj.parentNode : fobj.parentElement; \n"; 
		js_url += "  } \n"; 
		js_url += "  if (fobj.className=='dragme') \n"; 
		js_url += "  { \n"; 
		js_url += "    isdrag = true; \n"; 
		js_url += "    dobj = fobj; \n"; 
		js_url += "    tx = parseInt(dobj.style.left+0); \n"; 
		js_url += "    ty = parseInt(dobj.style.top+0); \n"; 
		js_url += "    x = nn6 ? e.clientX : event.clientX; \n"; 
		js_url += "    y = nn6 ? e.clientY : event.clientY; \n"; 
		js_url += "    document.onmousemove=movemouse; \n"; 
		js_url += "    return false; \n"; 
		js_url += "  } \n"; 
		js_url += "} \n"; 
		js_url += "document.onmousedown=selectmouse; \n"; 
		js_url += "document.onmouseup=new Function('isdrag=false'); \n"; 
		js_url += "//--> \n"; 
		js_url += "</"+"script> \n"; 

	var settings;
	var g4_is_gecko = true;
	if (g4_is_gecko) {
		settings  ='width='+(w+20)+','; 
		settings +='height='+(h+20)+','; 
	} else {
		settings  ='width='+w+','; 
		settings +='height='+h+','; 
	}
	settings +='top='+wint+','; 
	settings +='left='+winl+','; 
	settings +='scrollbars=no,'; 
	settings +='resizable=yes,'; 
	settings +='status=no'; 

	var g4_charset = "UTF-8";
	var click;
	var titleTooltip;
	var size = w + " x " + h;
	if(w >= screen.width || h >= screen.height) { 
		titleTooltip = size+" \n\n 이미지 사이즈가 화면보다 큽니다. \n 왼쪽 버튼을 클릭한 후 마우스를 움직여서 보세요. \n\n 더블 클릭하면 닫혀요.";
		click = "ondblclick='window.close();' style='cursor:move' "; 
	} 
	else {
		titleTooltip = size+" \n\n 클릭하면 닫혀요.";
		click = "onclick='window.close();' style='cursor:pointer' ";
	}
	var title = img.src + " " + titleTooltip;

	
	
	win=window.open("","image_window",settings); 
	win.document.open(); 
	win.document.write ("<html><head> \n<meta http-equiv='imagetoolbar' CONTENT='no'> \n<meta http-equiv='content-type' content='text/html; charset="+g4_charset+"'>\n"); 
	win.document.write ("<title>"+title+"</title> \n");
	if(w >= screen.width || h >= screen.height) { 
		win.document.write (js_url); 
	} 
	win.document.write ("<style>.dragme{position:relative;}</style> \n"); 
	win.document.write ("</head> \n\n"); 
	win.document.write ("<body leftmargin=0 topmargin=0 bgcolor=#dddddd style='cursor:arrow;'> \n"); 
	win.document.write ("<table width=100% height=100% cellpadding=0 cellspacing=0><tr><td align=center valign=middle><img src='"+img.src+"' width='"+w+"' height='"+h+"' border=0 class='dragme' "+click+"></td></tr></table>");
	win.document.write ("</body></html>"); 
	win.document.close(); 

	if(parseInt(navigator.appVersion) >= 4){win.window.focus();} 

}
