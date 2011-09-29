
ajax.xhr.Request = function(url, params, callback, method) {
  this.url = url;
  this.params = params;
  this.callback = callback;
  this.method = method;
  this.send();
}

ajax.xhr.Request.prototype = {
  //�������� ���� XMLHttpRequest ��ü�� ������ �ִ� �Լ�
  getXMLHttpRequest: function() {
    if (window.ActiveXObject) {
      //IE���� XMLHttpRequest ��ü ���ϱ� 
      try {
        return new ActiveXObject("MSXML2.XMLHTTP");
      } catch (e) {
        try {
          return new ActiveXObject("Microsoft.XMLHTTP");
        } catch (e1) {
          return null;
        }
      }
    } else if (window.XMLHttpRequest) {
      //IE�� ������ ���̾� ����, ������ ���� ���������� XMLHttpRequest ��ü�� ���Ѵ�.
      return new XMLHttpRequest;
    }
    
    return null;
  },

//XMLHttpRequest�� ����ؼ� ������ URL�� ������ ��û ���ڸ� 
//���۹��(GET/POST)����  �� ������ ��û�� �����Ѵ�.
//������ ���� ����� callback ���� ������ �ݹ��Լ��� ȣ���Ѵ�.
  send : function () {
    this.httpRequest = this.getXMLHttpRequest();
    //���۹���� ������ ��� �⺻����  GET ������� �����Ѵ�.
    var httpMethod = this.method ? this.method : 'GET';
    
    // ���� ����� GET/POST�ܴ̿�  ������ GET ������� �����Ѵ�.
    if (httpMethod != 'GET' && httpMethod != 'POST') {
      httpMethod = 'GET';
    }
    
    //��û ������ �⺻���� �����Ѵ�.
    var httpParams = "";
    if (this.params != null && this.params != '') {
      for (var key in this.params) {
        if (httpParams == "") {
          httpParams=key+'='+encodeURIComponent(this.params[key]);
        } else {
          httpParams+='&'+key+'='+encodeURIComponent(this.params[key]);
        }
      }
    }
      
    var httpUrl = this.url;
    //���� ����� GET ����̸鼭 ��û���ڰ� ������ ��� URL�ڿ� 
    //��û���ڸ� �߰��Ѵ�.
    if (httpMethod == 'GET' && httpParams == "") {
      httpUrl = httpUrl + "?" + httpParams;
    }
    
    //���� ����� URL�� �����Ѵ�.
    this.httpRequest.open(httpMethod, httpUrl, true);
    
    //���� ����� POST�̸� ������ �������� Ÿ���� �����Ѵ�.
    if (httpMethod == 'POST') {
      this.httpRequest.setRequestHeader('Content-Type', 
                               'application/x-www-form-urlencoded');
    }
    
    //readyState �Ӽ��� ����ɶ����� ȣ��� �ݹ��Լ��� �����Ѵ�.
    var localThis = this;
    this.httpRequest.onreadystatechange = function () {
      //����ڰ� ������ �ݹ��Լ��� ȣ���Ѵ�.
      localThis.callback(localThis.httpRequest);
    }
    
    //���۹���� POST�̸� ��û���ڸ� send()�� ���ڷ� �����Ѵ�.
    this.httpRequest.send(httpMethod == 'POST' ? httpParams : null);
  
  }
}

