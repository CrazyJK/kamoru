
var ajax = {};

/**
 * XMLHttpRequest 구현 함수
 * @param param JSON 표기 
 * SYNOPSIS
 * 			ajax.Request({url, [arg|form], [load|callbackFunc], [error], [method]});
 * OPTIONS
 * 			url 호출할 서버 URL
 * 			[arg | form] PARAM 전달 형태
 *   			arg  - 연관배열 형식
 *   			form - html form id이름
 * 			[load|callbackFunc], [error] CALLBACK 함수 
 * 				load  - 성공시 responseText를 인자로 받는 함수명
 * 				callbackFunc - load가 지정되지 않았을 경우 req를 인자로 받을 함수명
 *              error - 실패시 req를 인자로 받을 함수명. 
 *         				지정되지 않을경우 alert으로 status 수행 
 * 			[method]
 * 				method : 대문자 POST 일경우만 POST로 동작   
 */
ajax.Request = function(param) {
	this.url          = param.url;
	this.arg          = param.arg;
	this.form         = param.form;
	this.load         = param.load;
	this.error        = param.error;
	this.callbackFunc = param.callbackFunc;
	this.method       = param.method;
	
	this.req          = null;	

	this.sendRequest();
}

ajax.Request.prototype = {
	getXMLHttpRequest : function () {
		if(window.XMLHttpRequest) { // ie7 over, firefox, safari ...
			return new XMLHttpRequest();
		}else{   
			if(window.ActiveXObject) { //ie 6.0 under
				try{					
					return new ActiveXObject("msxml2.XMLHTTP"); //ie 6.0
				}catch(e){
					try{
						return new ActiveXObject("microsoft.XMLHTTP"); //ie 5.0
					}catch(e1){
						return null;
					}
				}
			}else{
				return null;
			}
		}
	}, 

	sendRequest : function () {
		
		/*
		 * XMLHttpRequest 생성
		 */
		this.req = this.getXMLHttpRequest();
		if (null == this.req) {
			alert("XMLHttpReqeust 객체를 생성할 수 없습니다.");
			return ;
		}

		/*
		 * method 에 따라 구분
		 * 인자에 빠진 경우, 값이 POST가 아닌경우 모두 GET으로 판단, fasle 설정
		 */
		var isPOST =  this.method ? (this.method == 'POST' ? true : false) : false;

		
		/*
		 * 파라미터 구성시 우선순위
		 * 1.arg : JSON표기법의 연관배열
		 * 2.form : FORM 객체
		 */
		var httpParam = "";
		if (this.arg) {
			for (var name in this.arg) {
				httpParam += name + "=" + encodeURIComponent(this.arg[name]) + "&"; 
			}
		} else if (this.form) {
			var elements = this.form.elements;
			for (var i=0;i<elements.length;i++) {
				httpParam += elements[i].name + "=" + encodeURIComponent(elements[i].value) + "&"; 
			}
		}

		/*
		 * url 설정
		 */
		var httpUrl = this.url;
		if(!isPOST){
			httpUrl += httpParam == "" ? httpParam : "?" + httpParam ;
		}
		
		/*
		 * 요청 초기화
		 * POST일경우 컨텐츠 타입 지정
		 */
		this.req.open(isPOST ? "POST" : "GET", httpUrl, true);
		if(isPOST){
			this.req.setRequestHeader("Content-Type","application/x-www-form-urlencoded");
		}

		/*
		 * 콜백 함수 지정
		 */
		var localThis = this;
		this.req.onreadystatechange = function() {
			if(localThis.req.readyState == 4) { // 서버로부터 데이타를 전부 받은 상태
				if(localThis.req.status == 200) { // 서버 처리 정상
					if(localThis.load) {// load 함수가 있으면
						localThis.load(localThis.req.responseText);
					}else if(localThis.callbackFunc) {
						localThis.callbackFunc(localThis.req);
					}
				}else{
					if(localThis.error) { // 에러 처리 함수가 있으면
						localThis.error(localThis.req);
					} else {
						alert("웹 서버에서 오류 발생\n"
			                + "오류 코드 : " + localThis.req.status + "\n"
			                + "오류 문자열 : " + localThis.req.statusText + "\n");
					}
				}
			}
		};

		/*
		 * 요청전송
		 */
		this.req.send(isPOST ? httpParam : null);
	},
	
	// 전송 취소시
	abort : function() {
		this.req.abort();
	}
};
