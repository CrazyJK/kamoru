
//naver API Ŭ���� ���� �� ������ �޼ҵ� ���� �� �����Ѵ�.
NaverRSS =  function (id) {
  //XML ���� ��ü ���� ��� ��ü �����Ѵ�.
  this.xmlObj = new ajax.xml.Document();
  this.id = id;
}

//NaverRSS Ŭ����  ��� �޼ҵ� ���� �� �����Ѵ�.
NaverRSS.prototype = {
  /////////////////////////////////////////////////////
  //NaverRSS ����� ��� �޼ҵ带 �����Ѵ�.
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
  //���ο��� �ݹ� �Լ��� ���Ǵ� �κ�  
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
