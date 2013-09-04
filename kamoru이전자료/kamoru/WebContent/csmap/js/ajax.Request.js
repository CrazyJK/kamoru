
var ajax = {};

/**
 * XMLHttpRequest ���� �Լ�
 * @param param JSON ǥ�� 
 * SYNOPSIS
 * 			ajax.Request({url, [arg|form], [load|callbackFunc], [error], [method]});
 * OPTIONS
 * 			url ȣ���� ���� URL
 * 			[arg | form] PARAM ���� ����
 *   			arg  - �����迭 ����
 *   			form - html form id�̸�
 * 			[load|callbackFunc], [error] CALLBACK �Լ� 
 * 				load  - ������ responseText�� ���ڷ� �޴� �Լ���
 * 				callbackFunc - load�� �������� �ʾ��� ��� req�� ���ڷ� ���� �Լ���
 *              error - ���н� req�� ���ڷ� ���� �Լ���. 
 *         				�������� ������� alert���� status ���� 
 * 			[method]
 * 				method : �빮�� POST �ϰ�츸 POST�� ����   
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
		 * XMLHttpRequest ����
		 */
		this.req = this.getXMLHttpRequest();
		if (null == this.req) {
			alert("XMLHttpReqeust ��ü�� ������ �� �����ϴ�.");
			return ;
		}

		/*
		 * method �� ���� ����
		 * ���ڿ� ���� ���, ���� POST�� �ƴѰ�� ��� GET���� �Ǵ�, fasle ����
		 */
		var isPOST =  this.method ? (this.method == 'POST' ? true : false) : false;

		
		/*
		 * �Ķ���� ������ �켱����
		 * 1.arg : JSONǥ����� �����迭
		 * 2.form : FORM ��ü
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
		 * url ����
		 */
		var httpUrl = this.url;
		if(!isPOST){
			httpUrl += httpParam == "" ? httpParam : "?" + httpParam ;
		}
		
		/*
		 * ��û �ʱ�ȭ
		 * POST�ϰ�� ������ Ÿ�� ����
		 */
		this.req.open(isPOST ? "POST" : "GET", httpUrl, true);
		if(isPOST){
			this.req.setRequestHeader("Content-Type","application/x-www-form-urlencoded");
		}

		/*
		 * �ݹ� �Լ� ����
		 */
		var localThis = this;
		this.req.onreadystatechange = function() {
			if(localThis.req.readyState == 4) { // �����κ��� ����Ÿ�� ���� ���� ����
				if(localThis.req.status == 200) { // ���� ó�� ����
					if(localThis.load) {// load �Լ��� ������
						localThis.load(localThis.req.responseText);
					}else if(localThis.callbackFunc) {
						localThis.callbackFunc(localThis.req);
					}
				}else{
					if(localThis.error) { // ���� ó�� �Լ��� ������
						localThis.error(localThis.req);
					} else {
						alert("�� �������� ���� �߻�\n"
			                + "���� �ڵ� : " + localThis.req.status + "\n"
			                + "���� ���ڿ� : " + localThis.req.statusText + "\n");
					}
				}
			}
		};

		/*
		 * ��û����
		 */
		this.req.send(isPOST ? httpParam : null);
	},
	
	// ���� ��ҽ�
	abort : function() {
		this.req.abort();
	}
};
