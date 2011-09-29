
var ajax = {};
ajax.xhr = {};

ajax.xhr.Request = function(param) {
	this.url  =param.url;
	this.arg = param.arg;
	this.form = param.form;
	this.load = param.load;
	this.error = param.error;
	this.callbackFunc = param.callbackFunc;
	this.method = param.method;
	this.req = 	this.getXMLHttpRequest();	

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
		if (null == this.req) {
			alert("XMLHttpReqeust 객체를 생성할 수 없습니다.");
			return ;
		}
		
		//name=value&name=value&"
		var httpParam = "";
		if (this.arg) {
			for (var name in this.arg) {
				httpParam += name + "=" + encodeURIComponent(this.arg[name]) + "&"; 
			}
		} else if (this.form) {
			var elements = this.form.elements;
			for (var i=0;i<elements.length;i++)  {
				httpParam += elements[i].name + "=" + encodeURIComponent(elements[i].value) + "&"; 
			}
			alert(httpParam);
		}
		var localThis = this;
		
		if (this.method == "POST") {
			this.req.open("POST", this.url, true);
			this.req.onreadystatechange = function() {
				if (localThis.req.readyState == 4 && localThis.req.status == 200) {
					
					if (localThis.load) {
						localThis.load(localThis.req.responseText);
					} else if (localThis.callbackFunc) {
						localThis.callbackFunc(localThis.req);
					}
					
				} else if( localThis.req.readyState == 4 && localThis.req.status != 200) {
					if (localThis.error) {
						localThis.error(localThis.req);
					}  else {
						alert(localThis.req.status);
					}
				}
			};
			this.req.setRequestHeader(
					'Content-Type', 'application/x-www-form-urlencoded');
			this.req.send(httpParam);
		} else  {
			this.req.open("GET", this.url + "?" + httpParam, true);
			this.req.onreadystatechange = function() {
				if (localThis.req.readyState == 4 && localThis.req.status == 200) {
					
					if (localThis.load) {
						localThis.load(localThis.req.responseText);
					} else if (localThis.callbackFunc) {
						localThis.callbackFunc(localThis.req);
					}
					
				} else if( localThis.req.readyState == 4 && localThis.req.status != 200) {
					if (localThis.error) {
						localThis.error(localThis.req);
					}  else {
						alert(localThis.req.status);
					}
				}
			};
			this.req.send(null);
		}
	}
};
