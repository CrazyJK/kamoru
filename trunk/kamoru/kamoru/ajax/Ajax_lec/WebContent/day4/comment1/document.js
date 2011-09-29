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

  //노드의 문자열 값을 변경한다.  
  setNodeValue : function (node, name, value) {
    if (node == null) return "";
    
    var childNodes = node.childNodes;
    for (var i=0;i<childNodes.length;i++) {
      if (childNodes[i].nodeType == 3 && null == name) {
        //노드의 문자열일 경우 처리 
        childNodes[i].nodeValue = value;
        break;
      } else if (childNodes[i].nodeType == 1 && childNodes[i].nodeName == name) {
        //요소의 이름이 찾고자 하는 경우 
        this.setNodeValue(childNodes[i], null, value);        
        break;
      }
    }
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
      var result = document.evaluate(exp, 
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
      var resultSet = document.evaluate(exp, 
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
  },
  
  //XPath 구문을 이용하여 여러개의 노드를 삭제한다.
  removeNodes : function (exp) {
    //xmlDoc 문서 객체가 null이면 리턴한다.
    if (this.xmlDoc == null) return;
    //삭제할 노드 배열을 얻는다.
    var nodes = this.selectNodes(exp);
    if (nodes == null || nodes.length == 0) return;
    //최상위 루트 노드를 얻는다.
    var root = this.xmlDoc.documentElement;
    
    for (var i=nodes.length-1;i>=0;i--) {
      root.removeChild(nodes[i]);
    }
  },

  //XML문서에 XSLT 문서를 적용하는 함수 
  transformXmlDoc : function(id) {
    //xmlDoc 또는 xslDoc 객체가 존재하지 않으면 작업을 취소한다.
    if (this.xmlDoc == null || this.xslDoc == null) return;

    if (window.ActiveXObject) {
      id.innerHTML = this.xmlDoc.transformNode(this.xslDoc);
    } else {
      id.innerHTML = "";
      var xsltProc = new XSLTProcessor();
      xsltProc.importStylesheet(this.xslDoc);
      var fragment = xsltProc.transformToFragment(this.xmlDoc, document);
      id.appendChild(fragment);
    }
  },
  
  //노드나 노드 배열객체를 관리할 XML 문서 객체를 생성하고 root 
  //노드에 인자로 전달된 노드 또는 노드 배열을 추가한다.
  setTransXmlDoc2Nodes : function(param) {
    if (this.transXMLDoc == null) {
      this.transXMLDoc = this.xmlDoc.cloneNode(false);
      this.transXMLDoc.appendChild(this.xmlDoc.documentElement.cloneNode(false));
    }
    var root = this.transXMLDoc.documentElement;
    var childNodes = root.childNodes;
    //모든 자식 노드를 삭제한다.
    if (childNodes != null) {
      for (var i=childNodes.length-1;i>=0;i--) {
        root.removeChild(childNodes[i]);
      }
    }

    if (param.length) {
      //노드 배열을 transXMLDoc의 루트에 추가한다.
      for (var i=0;i<param.length;i++) {
        root.appendChild(param[i].cloneNode(true));       
      }
    } else {
      //1 개 노드 일 경우 
        root.appendChild(param.cloneNode(true));       
    }
    
    return this.transXMLDoc;
  }, 
  
  //노드배열에 XSLT 문서를 적용하는 함수 
  transformNodes : function(id, param, append) {
    if (param == null) return;
      
    //xmlDoc이 null이면 또는 해당 메소드를 호출할 때 인자를 전달하지 않으면 클래스의 멤버 객체를 이용하여 수행한다.
    var xmlDoc = this.setTransXmlDoc2Nodes(param);
    
    //xmlDoc 또는 xslDoc 객체가 존재하지 않으면 작업을 취소한다.
    if (xmlDoc == null || this.xslDoc == null) return;

    if (window.ActiveXObject) {
      if (append != true) { 
        id.outerHTML = xmlDoc.transformNode(this.xslDoc);
      } else {
        id.innerHTML += xmlDoc.transformNode(this.xslDoc);
      }
    } else {
      var xsltProc = new XSLTProcessor();
      xsltProc.importStylesheet(this.xslDoc);
      var fragment = xsltProc.transformToFragment(xmlDoc, document);
      
      if (append != true) { 
        id.parentNode.replaceChild(fragment, id);
      } else {
        id.appendChild(fragment);
      }
    }
  },
  
  //tag 이름으로 요소를 찾는 메소드
  getElementsByTagName : function (name) {
    if (this.xmlDoc == null) return null;
    return this.xmlDoc.getElementsByTagName(name);
  } ,
  
  //노드를 생성한다.
  createElement : function (param) {
    //요소를 생성한다.
    var node = this.xmlDoc.createElement(param.name);

    //요소의 문자열 값을 생성한다.
    if (param.value) {
    	node.appendChild(this.xmlDoc.createTextNode(param.value));
    } 
    
    //요소에 속성이 존재하면 속성을 추가한다.
    if (param.attributes) {
        for (var i=0;i<param.attributes.length;i++) {
          node.setAttribute(param.attributes[i].name, param.attributes[i].value);
        }
    } 

    //요소의 하위 노드가 존재하면 하위노드를 생성하여 추가한다.
    if (param.childNodes) {
        for (var i=0;i<param.childNodes.length;i++) {
          node.appendChild(this.createElement(param.childNodes[i]));
        }
    }

    return node;
  },

  //최상위 루트 노드에 노드를 삽입한다.
  appendChild : function (node) {
    if (this.xmlDoc == null) return;
    this.xmlDoc.documentElement.appendChild(node);
  }
  
}


