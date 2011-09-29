
//naver API 클래스 정의 및 생성자 메소드 선언 및 구현한다.
NaverRSS =  function (id) {
  //XML 문서 객체 관리 멤버 객체 생성한다.
  this.xmlObj = new ajax.xml.Document();
  this.id = id;
}

//NaverRSS 클래스  멤버 메소드 정의 및 구현한다.
NaverRSS.prototype = {
  /////////////////////////////////////////////////////
  //NaverRSS 목록을 얻는 메소드를 구현한다.
  search : function (search, target) {
    var param = {
      url      : "http://openapi.naver.com/search"
      ,key     : "3e001fd1baa595a3f6adc415b17a8552"
      ,query   : search
      ,display : "5"
      ,start   : "1"
      ,target  : target
      ,sort    : "sim"
    }
	new  ajax.xhr.Request({
                          url              : "proxy.jsp", 
                          params           : param, 
                          callbackObject   : this,
                          callbackFunction : this.loadBooksXML,
                          method           : "GET" 
                          });
	new  ajax.xhr.Request({
                          url              : "jsp/naver.xsl", 
                          params           : null, 
                          callbackObject   : this,
                          callbackFunction : this.loadBooksXSL,
                          method           : "POST" 
                          });
  },

  /////////////////////////////////////////////////////
  //내부에서 콜백 함수로 사용되는 부분  
  loadBooksXML : function (reg) {
    if (reg.readyState == 4) {
      if (reg.status == 200) {
        this.xmlObj.xmlDoc = reg.responseXML;
        this.doXSLT();
      }
    }
  },
  
  loadBooksXSL : function (reg) {
    if (reg.readyState == 4) {
      if (reg.status == 200) {
        this.xmlObj.xslDoc = reg.responseXML;
        this.doXSLT();
      }
    }
  },
  
  doXSLT : function () {
    if (this.xmlObj.xmlDoc == null || this.xmlObj.xslDoc == null) return;
    
    var list = document.getElementById(this.id);
    
    this.xmlObj.transformXmlDoc(list);
  } 
}
