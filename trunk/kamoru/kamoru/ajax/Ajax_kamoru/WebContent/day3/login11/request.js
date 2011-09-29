
var ajax = {};
ajax.xhr = {};

ajax.xhr.Request = function(url, arg, callbackFunc, method) {
	this.url  =url;
	this.arg = arg;
	this.callbackFunc = callbackFunc;
	this.method = method;
	this.req = null;
	
	this.sendRequest();
}

ajax.xhr.Request.prototype = {
	getXMLHttpRequest : function () {
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
	}, 

	sendRequest : function ()   {
		this.req = 	this.getXMLHttpRequest();
		if (null == this.req) {
			alert("XMLHttpReqeust 객체를 생성할 수 없습니다.");
			return ;
		}
		
		//name=value&name=value&"
		var httpParam = "";
		for (var name in this.arg) {
			httpParam += name + "=" + encodeURIComponent(this.arg[name]) + "&"; 
		}
		var localThis = this;
		
		if (this.method == "POST") {
			this.req.open("POST", this.url, true);
			this.req.onreadystatechange = function() {
				localThis.callbackFunc(localThis.req);
			};
			this.req.setRequestHeader(
					'Content-Type', 'application/x-www-form-urlencoded');
			this.req.send(httpParam);
		} else  {
			this.req.open("GET", this.url + "?" + httpParam, true);
			this.req.onreadystatechange = function() {
				localThis.callbackFunc(localThis.req);
			};
			this.req.send(null);
		}
	}
};
