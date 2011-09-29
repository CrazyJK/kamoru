

var req = null;


function getXMLHttpRequest() {
	if (window. XMLHttpRequest) {
		return new XMLHttpRequest();
	} else {
		//ie 6.0 ���� ����  
		if (window.ActiveXObject) {
			try {
				//ie 6.0 ����
				return new ActiveXObject("msxml2.XMLHTTP");
			} catch (e) {
				//ie 5.0 ���� ����
				return new ActiveXObject("microsoft.XMLHTTP");
			}
		}
	}
	return null;
}

function sendRequest(url, arg, callbackFunc, method)   {
	req = 	getXMLHttpRequest();
	if (null == req) {
		alert("XMLHttpReqeust ��ü�� ������ �� �����ϴ�.");
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
