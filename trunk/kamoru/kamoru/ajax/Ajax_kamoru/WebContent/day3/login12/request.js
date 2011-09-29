
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
	}, 

	sendRequest : function ()   {
		this.req = 	this.getXMLHttpRequest();
		if (null == this.req) {
			alert("XMLHttpReqeust ��ü�� ������ �� �����ϴ�.");
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
				if (localThis.req.readyState == 4 && localThis.req.status == 200) {
					localThis.callbackFunc(localThis.req);
				} else if( localThis.req.readyState == 4 && localThis.req.status != 200) {
					alert(localThis.req.status);
				}
			};
			this.req.setRequestHeader(
					'Content-Type', 'application/x-www-form-urlencoded');
			this.req.send(httpParam);
		} else  {
			this.req.open("GET", this.url + "?" + httpParam, true);
			this.req.onreadystatechange = function() {
				if (localThis.req.readyState == 4 && localThis.req.status == 200) {
					localThis.callbackFunc(localThis.req);
				} else if( localThis.req.readyState == 4 && localThis.req.status != 200) {
					alert(localThis.req.status);
				}
			};
			this.req.send(null);
		}
	}
};
