//XMLHttpRequest 객체를 저정할 전역변수 
var httpRequest = null;

//브라우저에 따라 XMLHttpRequest 객체를 생성해 주는 함수
function getXMLHttpRequest() {
  if (window.ActiveXObject) {
    //IE에서 XMLHttpRequest 객체 구하기 
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
    //IE를 제외한 파이어 폭스, 오페라와 같은 브라우저에서 XMLHttpRequest 객체를 구한다.
    return new XMLHttpRequest;
  }
  
  return null;
}

//XMLHttpRequest를 사용해서 지정된 URL로 지정된 요청 인자를 전송방식(GET/POST)으로  웹 서버에 요청을 전송한다.
//서버의 응답 결과는 callback 으로 지정된 콜백함수를 호출한다.
function sendRequest(url, params, callback, method) {
  httpRequest = getXMLHttpRequest();
  //전송방식이 생략된 경우 기본으로  GET 방식으로 설정한다.
  var httpMethod = method ? method : 'GET';
  // 전송 방법이 GET/POST이외는  무조건 GET 방식으로 설정한다.
  if (httpMethod != 'GET' && httpMethod != 'POST') {
    httpMethod = 'GET';
  }
  //요청 인자의 기본값을 설정한다.
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
  //전송 방법이 GET 방법이면서 요청인자가 존재할 경우 URL뒤에 요청인자를 추가한다.
  if (httpMethod == 'GET' && httpParams != "") {
    httpUrl = httpUrl + "?" + httpParams;
  }
  //전송 방법과 URL을 설정한다.
  httpRequest.open(httpMethod, httpUrl, true);
  //전송 방법이 POST이면 전송할 켄텐츠의 타입을 지정한다.
  if (httpMethod == 'POST') {
    httpRequest.setRequestHeader('Content-Type', 
                                 'application/x-www-form-urlencoded');
  }
  //readyState 속성이 변경될때마다 호출될 콜백함수를 지정한다.
  httpRequest.onreadystatechange = callback;
  //전송방법이 POST이면 요청인자를 send()의 인자로 전달한다.
  httpRequest.send(httpMethod == 'POST' ? httpParams : null);
}
