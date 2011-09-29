//XMLHttpRequest 객체를 저장할 전역변수  
//var  req = null;

var ajax = {};
ajax.xhr = {};

var Event = {};


//생성자 클래스 함수 정의 
ajax.xhr.Request = function (arg) {
	this.url       = arg.url;
	this.param   = arg.param;
	this.load     = arg.load;
	this.error    = arg.error;
	this.method  = arg.method;

	this.req = this.getXMLHttpRequest();
	
	this.send();

}

ajax.xhr.Request.prototype = {
	getXMLHttpRequest : function () { 
		//IE 7.0 이상 비IE 에서 객체를 생성하는 방법 
		if (window.XMLHttpRequest) {
			return new  window.XMLHttpRequest();
		} else if (window.ActiveXObject){
			//IE 6.0 때에 사용
			try {  
				return new window.ActiveXObject("MSXML2.XMLHTTP");
			} catch (e) {
				//IE 5.0
				return new window.ActiveXObject("Microsoft.XMLHTTP");
			}
		}
		return null;
	},

	send : function () {
		if (null == this.req) {
			alert("XMLHttpRequest 객체를 생성할 수 없습니다.");
			return false;		
		}
		
		var httpParam = "";
		if (this.param) {
			if (typeof(this.param) == "string") {
				httpParam += this.param;
			} else if (typeof(this.param) == "object") {
				if (this.param.nodeName) {
					//양식 
					var elements = this.param.elements;
					var length = elements.length;
					for (var i=0;i<length;i++) {
						httpParam += elements[i].name + "=" + encodeURIComponent(elements[i].value) + "&";  
					}
					
				} else {
					//연관 배열 
					for (var name in this.param) {
						httpParam += name + "=" + encodeURIComponent(this.param[name]) + "&";  
					}
				}
			}
		}
		if (httpParam != "" && this.method == "GET") {
			this.url += "?" + httpParam;
		}
		var localThis = this;
		this.req.open(this.method, this.url, true);
		this.req.onreadystatechange = function() {
			if (localThis.req.readyState == 4 && localThis.req.status == 200) {
				//성공일 경우 처리함수 호출 
				localThis.load(localThis.req);
			} else if (localThis.req.readyState == 4 && localThis.req.status != 200) {
				if (localThis.error) {
					//오류 발생시 호출 함수 
					localThis.error(localThis.req)
				} else {
					//오류 함수가 없을 때 내부적으로 처리하는 부분 
					alert(localThis.req.status);
				}
			}
		}
		if (this.method == 'POST') {
			this.req.setRequestHeader('Content-Type', 
		                                 'application/x-www-form-urlencoded');
		}
		this.req.send(this.method == "POST" ? httpParam : null);
	}
}


Event.addEvent = function() {

	var element, evt, temp, eventHandler, capture;

	if (typeof(arguments[2]) == "object") {
		element       = arguments[0];
		evt            = arguments[1];
		temp          = arguments[2];
		eventHandler = arguments[3];
		if (5 == arguments.length) {
			capture = arguments[4];
		} else {
			capture = false;
		}
	} else {
		temp          = null;
		
		element       = arguments[0];
		evt            = arguments[1];
		eventHandler = arguments[2];
		if (4 == arguments.length) {
			capture = arguments[3];
		} else {
			capture = false;
		}
	} 
	
	if (element.attachEvent) {
		 //IE에서 이벤트 등록  
		element.attachEvent("on" + evt, (null != temp ? Event.bindListener(temp, eventHandler) : eventHandler));
	} else if (element.addEventListener) {
		element.addEventListener(evt, (null != temp ? Event.bindListener(temp, eventHandler) : eventHandler), capture);
	}
}


Event.removeEvent = function (element, evt, eventHandler, capture) {
	capture = capture || false;
	
	if (element.detachEvent) {
		element.detachEvent("on" + evt, eventHandler);
	} else if (element.removeEventListener) {
		element.removeEventListener(evt, eventHandler, capture);
	}
}


Event.addEvent(window, "load", function() {
	var inputs = document.getElementsByTagName("INPUT");
	var length = inputs.length;
	for (var i=0;i<length;i++) {
		if (inputs[i].getAttribute("req")) {
			inputs[i].style.background = "url(star.gif) no-repeat top right";
		}
	}
	
});	
