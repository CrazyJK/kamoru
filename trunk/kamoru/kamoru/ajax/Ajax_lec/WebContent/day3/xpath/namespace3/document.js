//XML document 관리 클래스 생성자 함수  선언 
ajax.xml.Document = function() {
  this.xmlDoc = null;
  this.xslDoc = null;
  this.nsMap = null;
}

ajax.xml.Document.prototype = {

  //URL을 이용하여 XML 객체를 생성한다.
  createFromURL : function (url) {
    var xmlDoc = null;
    
    if (window.ActiveXObject != null) {
      //MS XML Document 객체 생성 가능 
      xmlDoc = new ActiveXObject("MSXML.DOMDocument");
      //동기 모드로 XML 문서 읽기 설정
      xmlDoc.async = false;
      
      // xml 을 파일로 불러올때.
      xmlDoc.load(url);
      //IE에서 오류 처리 하는 방법 
      if (xmlDoc.parseError.errorCode != 0) {
        var msg = "오류가 발생했습니다.\n";
        msg += "오류 코드 : " + xmlDoc.parseError.errorCode + "\n" ;
        msg += "오류 원인: " + xmlDoc.parseError.reason;
        alert(msg);
        return false;
      }
    } else {
      //파이어 폭스에서 XML Document 객체 생성
      var xmlDoc = document.implementation.createDocument("", "", null);
      //동기 모드로 XML 문서 읽기 설정
      xmlDoc.async = false;
      
      // xml 을 파일로 불러올때.
      xmlDoc.load(url);

      //파이어 폭스에서 오류 처리하는 방법
      if (xmlDoc.documentElement.nodeName == "parsererror") {
        var msg = "오류가 발생했습니다.\n";
        msg += "오류 원인: " + xmlDoc.documentElement.childNodes[0].nodeValue;
        alert(msg);
        return false;
      }
    }
    
    this.xmlDoc = xmlDoc;
    return true;
  },

  //문자열로 구성된 XML을 XML 객체로 생성한다.
  createFromString : function (str) {
    var xmlDoc = null;
  
    if (window.ActiveXObject) {
      //MS XML Document 객체 생성 가능 
      xmlDoc = new ActiveXObject("MSXML.DOMDocument");
      
      // 문자열로 XML 문서를 작성한다.
      xmlDoc.loadXML(str);
      
      //IE에서 오류 처리 하는 방법 
      if (xmlDoc.parseError.errorCode != 0) {
        var msg = "오류가 발생했습니다.\n";
        msg += "오류 코드 : " + xmlDoc.parseError.errorCode + "\n" ;
        msg += "오류 원인: " + xmlDoc.parseError.reason;
        alert(msg);
        return false;
      }
    } else {
      //파이어 폭스에서 XML Parser 객체 생성
      var parser = new DOMParser();
      xmlDoc = parser.parseFromString(str, "text/xml");
  
      //파이어 폭스에서 오류 처리하는 방법
      if (xmlDoc.documentElement.nodeName == "parsererror") {
        var msg = "오류가 발생했습니다.\n";
        msg += "오류 원인: " + xmlDoc.documentElement.childNodes[0].nodeValue;
        alert(msg);
        return false;
      }
    }

    this.xmlDoc = xmlDoc;
    return true;
  }, 

  //XML 객체의 내용을 문자열로 변환하여 리턴한다.
  toString : function() {
    if (this.xmlDoc == null) return "";
    
    //MS XML document일 경우 xml Document로 부터 문자열을 얻는다.
    if (this.xmlDoc.xml) {                                            
      return this.xmlDoc.xml;                                         
    } else {                                                     
      //파이어 폭스 일 경우                                      
      var serial = new XMLSerializer();                          
      return serial.serializeToString(this.xmlDoc);                   
    }                                                            
    return "";                                                   
  },

  //노드의 문자열 값을 얻는다.  
  getNodeValue : function (node, name) {
    if (node == null) return "";
    
    var childNodes = node.childNodes;
    for (var i=0;i<childNodes.length;i++) {
      if (childNodes[i].nodeType == 3 && null == name) {
        return childNodes[i].nodeValue;
      } else if (childNodes[i].nodeType == 1 && childNodes[i].nodeName == name) {
        return this.getNodeValue(childNodes[i]);        
      }
    }
    return "";
  },

  //Namespace을 설정한다.
  setNamespace : function(namespaces) {
    if (window.ActiveXObject != null) {
      //nacespace를 설정한다 .
      this.xmlDoc.setProperty("SelectionNamespaces", namespaces);
      
      //XPath를 검색 언어로 지정한다 .
      this.xmlDoc.setProperty("SelectionLanguage", "XPath");
    } else {
      //공백을 중심으로 문자열 분리한다.
      var arr = namespaces.split(' ');
      var tmp;
      var key;
      var value;
      
      for (var i=0;i<arr.length;i++) {
        //'='을 중심으로 문자열을 분리한다.
        tmp = arr[i].split('=');
        if (tmp != null && tmp.length == 2) {
          //':' 문자을 중심으로 prefix문자열을 얻는다.
          key = tmp[0].split(':');
          if (key == null || key.length == 1) {
            //default namespace이 경우 prefix를 default로 한다.
            key = "default";
          } else {
            key = key[1];
          }
          //"'"을 중심으로 namespace URI을 얻는다.
          value =  tmp[1].split("'")[1];
        }
        if (this.nsMap == null) {
          //배열 객체를 생성한다.
          this.nsMap = new Array();
          //this.nsMap = {};
        }
        //JSON 표기법과 같은 구조로 배열을 구성한다.
        this.nsMap[key] = value;
      }
    }
  },

  //파이어 폭스에서 namespace을 처리하기 위한 멤버 메소드이다.
  nsResolver : function (prefix) {
    return this.nsMap[prefix] || null;
  },

  //XPath 구문을 이용하여 1 개의 노드를 조회한다.
  selectSingleNode : function(exp) {
    if (window.ActiveXObject != null) {
      //IE에서 XPath로 1개의 노드를 찾는다.
      return this.xmlDoc.selectSingleNode(exp);
    } else {
  
      if (this.nsMap != null) {
        //XML Document객체 변수를 지역 변수에 설정한다.
        //callback function에서 객체 메소드를 호출 할 수 있도록 객체를 지역변수에 설정하는 것이다.
        var localThis = this;
        //지역함수로 nsResolver() 함수를 구현하고 내부에서 클래스의 멤버 함수를 호출 할 수 있도록 한다.
        //클래스의 멤버 함수는 callback function될 수 없다.
        function nsResolver(prefix) {
          return localThis.nsResolver(prefix);
        }
      }
            
      //파이어 폭스에서 XPath 구문으로 1 개의 노드를 찾는다.
      var result = xmlDoc.evaluate(exp, 
                                    this.xmlDoc.documentElement, 
                                    this.nsMap == null ? null : nsResolver, 
                                    XPathResult.FIRST_ORDERED_NODE_TYPE, 
                                    null);
      if (result == null) {
        return null;
      }
      return result.singleNodeValue;
    }
  },
  
  //XPath 구문을 이용하여 여러개의 노드를 조회하여 배열로 리턴한다.
  selectNodes : function (exp) {
    if (window.ActiveXObject != null) {
      //IE에서 XPath로 여러 개의 노드를 찾는다.
      return this.xmlDoc.selectNodes(exp);
    } else {

      //XML Document객체 변수를 지역 변수에 설정한다.
      //callback function에서 객체 메소드를 호출 할 수 있도록 객체를 지역변수에 설정하는 것이다.
      var localThis = this;
      //지역함수로 nsResolver() 함수를 구현하고 내부에서 클래스의 멤버 함수를 호출 할 수 있도록 한다.
      //클래스의 멤버 함수는 callback function될 수 없다.

      function nsResolver(prefix) {
        return localThis.nsResolver(prefix);
      }

      //파이어 폭스에서 XPath 구문으로 여러  개의 노드를 찾는다.
      var resultSet = xmlDoc.evaluate(exp, 
                                      this.xmlDoc.documentElement, 
                                      this.nsMap == null ? null : nsResolver, 
                                      XPathResult.ORDERED_NODE_SNAPSHOT_TYPE, 
                                      null);
      //검색된 노드를 저정할 배열객체를 생성한다.
      var nodes = new Array();
      
      for (var i=0;i<resultSet.snapshotLength;i++) {
        //생성된 배열 객체에 노드를 추가한다.
        nodes[i] = resultSet.snapshotItem (i);
      }
      //검색된 노드 배열을 리턴한다.
      return nodes;
    }
  }
}


