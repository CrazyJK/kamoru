//XMLHttpRequest ��ü�� ������ �������� 
var httpRequest = null;

//�������� ���� XMLHttpRequest ��ü�� ������ �ִ� �Լ�
function getXMLHttpRequest() {
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
}

//XMLHttpRequest�� ����ؼ� ������ URL�� ������ ��û ���ڸ� ���۹��(GET/POST)����  �� ������ ��û�� �����Ѵ�.
//������ ���� ����� callback ���� ������ �ݹ��Լ��� ȣ���Ѵ�.
function sendRequest(url, params, callback, method) {
  httpRequest = getXMLHttpRequest();
  //���۹���� ������ ��� �⺻����  GET ������� �����Ѵ�.
  var httpMethod = method ? method : 'GET';
  // ���� ����� GET/POST�ܴ̿�  ������ GET ������� �����Ѵ�.
  if (httpMethod != 'GET' && httpMethod != 'POST') {
    httpMethod = 'GET';
  }
  //��û ������ �⺻���� �����Ѵ�.
  var httpParams = "";
  if (params != null && params != '') {
    for (var key in params) {
      if (httpParams == "") {
        httpParams = key + '=' + encodeURIComponent(params[key]);
      } else {
        httpParams += '&' + key + '=' + encodeURIComponent(params[key]);
      }
    }
  }
    
  var httpUrl = url;
  //���� ����� GET ����̸鼭 ��û���ڰ� ������ ��� URL�ڿ� ��û���ڸ� �߰��Ѵ�.
  if (httpMethod == 'GET' && httpParams != "") {
    httpUrl = httpUrl + "?" + httpParams;
  }
  //���� ����� URL�� �����Ѵ�.
  httpRequest.open(httpMethod, httpUrl, true);
  //���� ����� POST�̸� ������ �������� Ÿ���� �����Ѵ�.
  if (httpMethod == 'POST') {
    httpRequest.setRequestHeader('Content-Type', 
                                 'application/x-www-form-urlencoded');
  }
  //readyState �Ӽ��� ����ɶ����� ȣ��� �ݹ��Լ��� �����Ѵ�.
  httpRequest.onreadystatechange = callback;
  //���۹���� POST�̸� ��û���ڸ� send()�� ���ڷ� �����Ѵ�.
  httpRequest.send(httpMethod == 'POST' ? httpParams : null);
}
