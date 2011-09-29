

var req = null;


function getXMLHttpRequest() {
	if (window. XMLHttpRequest) {
		return new XMLHttpRequest();
	} else {
		//ie 6.0 이하 버전  
		if (window.ActiveXObject) {
			try {
				//ie 6.0 버전
				return new ActiveXObject("msxml2.XMLHTTP");
			} catch (e) {
				//ie 5.0 이하 버전
				return new ActiveXObject("microsoft.XMLHTTP");
			}
		}
	}
	return null;
}

function sendRequest(url, arg, callbackFunc, method)   {
	req = 	getXMLHttpRequest();
	if (null == req) {
		alert("XMLHttpReqeust 객체를 생성할 수 없습니다.");
		return ;
	}
	
	var httpParam = "";
	//name=value&name=value&"
	for (var name in arg) {
		httpParam += name + "=" + encodeURIComponent(arg[name]) + "&"; 
	}
	
	if (method == "POST") {
		req.open("POST", url, true);
		req.onreadystatechange = callbackFunc; 
		req.setRequestHeader(
				'Content-Type', 'application/x-www-form-urlencoded');
		req.send(httpParam);
	} else  {
		req.open("GET", url + "?" + httpParam, true);
		req.onreadystatechange = callbackFunc; 
		req.send(null);
	}
}
