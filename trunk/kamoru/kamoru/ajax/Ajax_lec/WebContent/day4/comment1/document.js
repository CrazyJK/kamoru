//XML document ���� Ŭ���� ������ �Լ�  ���� 
ajax.xml.Document = function() {
  this.xmlDoc = null;
  this.xslDoc = null;
  this.nsMap = null;
}

ajax.xml.Document.prototype = {

  //URL�� �̿��Ͽ� XML ��ü�� �����Ѵ�.
  createFromURL : function (url) {
    var xmlDoc = null;
    
    if (window.ActiveXObject != null) {
      //MS XML Document ��ü ���� ���� 
      xmlDoc = new ActiveXObject("MSXML.DOMDocument");
      //���� ���� XML ���� �б� ����
      xmlDoc.async = false;
      
      // xml �� ���Ϸ� �ҷ��ö�.
      xmlDoc.load(url);
      //IE���� ���� ó�� �ϴ� ��� 
      if (xmlDoc.parseError.errorCode != 0) {
        var msg = "������ �߻��߽��ϴ�.\n";
        msg += "���� �ڵ� : " + xmlDoc.parseError.errorCode + "\n" ;
        msg += "���� ����: " + xmlDoc.parseError.reason;
        alert(msg);
        return false;
      }
    } else {
      //���̾� �������� XML Document ��ü ����
      var xmlDoc = document.implementation.createDocument("", "", null);
      //���� ���� XML ���� �б� ����
      xmlDoc.async = false;
      
      // xml �� ���Ϸ� �ҷ��ö�.
      xmlDoc.load(url);

      //���̾� �������� ���� ó���ϴ� ���
      if (xmlDoc.documentElement.nodeName == "parsererror") {
        var msg = "������ �߻��߽��ϴ�.\n";
        msg += "���� ����: " + xmlDoc.documentElement.childNodes[0].nodeValue;
        alert(msg);
        return false;
      }
    }
    
    this.xmlDoc = xmlDoc;
    return true;
  },

  //���ڿ��� ������ XML�� XML ��ü�� �����Ѵ�.
  createFromString : function (str) {
    var xmlDoc = null;
  
    if (window.ActiveXObject) {
      //MS XML Document ��ü ���� ���� 
      xmlDoc = new ActiveXObject("MSXML.DOMDocument");
      
      // ���ڿ��� XML ������ �ۼ��Ѵ�.
      xmlDoc.loadXML(str);
      
      //IE���� ���� ó�� �ϴ� ��� 
      if (xmlDoc.parseError.errorCode != 0) {
        var msg = "������ �߻��߽��ϴ�.\n";
        msg += "���� �ڵ� : " + xmlDoc.parseError.errorCode + "\n" ;
        msg += "���� ����: " + xmlDoc.parseError.reason;
        alert(msg);
        return false;
      }
    } else {
      //���̾� �������� XML Parser ��ü ����
      var parser = new DOMParser();
      xmlDoc = parser.parseFromString(str, "text/xml");
  
      //���̾� �������� ���� ó���ϴ� ���
      if (xmlDoc.documentElement.nodeName == "parsererror") {
        var msg = "������ �߻��߽��ϴ�.\n";
        msg += "���� ����: " + xmlDoc.documentElement.childNodes[0].nodeValue;
        alert(msg);
        return false;
      }
    }

    this.xmlDoc = xmlDoc;
    return true;
  }, 

  //XML ��ü�� ������ ���ڿ��� ��ȯ�Ͽ� �����Ѵ�.
  toString : function() {
    if (this.xmlDoc == null) return "";
    
    //MS XML document�� ��� xml Document�� ���� ���ڿ��� ��´�.
    if (this.xmlDoc.xml) {                                            
      return this.xmlDoc.xml;                                         
    } else {                                                     
      //���̾� ���� �� ���                                      
      var serial = new XMLSerializer();                          
      return serial.serializeToString(this.xmlDoc);                   
    }                                                            
    return "";                                                   
  },

  //����� ���ڿ� ���� ��´�.  
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

  //����� ���ڿ� ���� �����Ѵ�.  
  setNodeValue : function (node, name, value) {
    if (node == null) return "";
    
    var childNodes = node.childNodes;
    for (var i=0;i<childNodes.length;i++) {
      if (childNodes[i].nodeType == 3 && null == name) {
        //����� ���ڿ��� ��� ó�� 
        childNodes[i].nodeValue = value;
        break;
      } else if (childNodes[i].nodeType == 1 && childNodes[i].nodeName == name) {
        //����� �̸��� ã���� �ϴ� ��� 
        this.setNodeValue(childNodes[i], null, value);        
        break;
      }
    }
  },

  //Namespace�� �����Ѵ�.
  setNamespace : function(namespaces) {
    if (window.ActiveXObject != null) {
      //nacespace�� �����Ѵ� .
      this.xmlDoc.setProperty("SelectionNamespaces", namespaces);
      
      //XPath�� �˻� ���� �����Ѵ� .
      this.xmlDoc.setProperty("SelectionLanguage", "XPath");
    } else {
      //������ �߽����� ���ڿ� �и��Ѵ�.
      var arr = namespaces.split(' ');
      var tmp;
      var key;
      var value;
      
      for (var i=0;i<arr.length;i++) {
        //'='�� �߽����� ���ڿ��� �и��Ѵ�.
        tmp = arr[i].split('=');
        if (tmp != null && tmp.length == 2) {
          //':' ������ �߽����� prefix���ڿ��� ��´�.
          key = tmp[0].split(':');
          if (key == null || key.length == 1) {
            //default namespace�� ��� prefix�� default�� �Ѵ�.
            key = "default";
          } else {
            key = key[1];
          }
          //"'"�� �߽����� namespace URI�� ��´�.
          value =  tmp[1].split("'")[1];
        }
        if (this.nsMap == null) {
          //�迭 ��ü�� �����Ѵ�.
          this.nsMap = new Array();
        }
        //JSON ǥ����� ���� ������ �迭�� �����Ѵ�.
        this.nsMap[key] = value;
      }
    }
  },

  //���̾� �������� namespace�� ó���ϱ� ���� ��� �޼ҵ��̴�.
  nsResolver : function (prefix) {
    return this.nsMap[prefix] || null;
  },

  //XPath ������ �̿��Ͽ� 1 ���� ��带 ��ȸ�Ѵ�.
  selectSingleNode : function(exp) {
    if (window.ActiveXObject != null) {
      //IE���� XPath�� 1���� ��带 ã�´�.
      return this.xmlDoc.selectSingleNode(exp);
    } else {
  
      if (this.nsMap != null) {
        //XML Document��ü ������ ���� ������ �����Ѵ�.
        //callback function���� ��ü �޼ҵ带 ȣ�� �� �� �ֵ��� ��ü�� ���������� �����ϴ� ���̴�.
        var localThis = this;
        //�����Լ��� nsResolver() �Լ��� �����ϰ� ���ο��� Ŭ������ ��� �Լ��� ȣ�� �� �� �ֵ��� �Ѵ�.
        //Ŭ������ ��� �Լ��� callback function�� �� ����.
        function nsResolver(prefix) {
          return localThis.nsResolver(prefix);
        }
      }
            
      //���̾� �������� XPath �������� 1 ���� ��带 ã�´�.
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
  
  //XPath ������ �̿��Ͽ� �������� ��带 ��ȸ�Ͽ� �迭�� �����Ѵ�.
  selectNodes : function (exp) {
    if (window.ActiveXObject != null) {
      //IE���� XPath�� ���� ���� ��带 ã�´�.
      return this.xmlDoc.selectNodes(exp);
    } else {

      //XML Document��ü ������ ���� ������ �����Ѵ�.
      //callback function���� ��ü �޼ҵ带 ȣ�� �� �� �ֵ��� ��ü�� ���������� �����ϴ� ���̴�.
      var localThis = this;
      //�����Լ��� nsResolver() �Լ��� �����ϰ� ���ο��� Ŭ������ ��� �Լ��� ȣ�� �� �� �ֵ��� �Ѵ�.
      //Ŭ������ ��� �Լ��� callback function�� �� ����.

      function nsResolver(prefix) {
        return localThis.nsResolver(prefix);
      }
      //���̾� �������� XPath �������� ����  ���� ��带 ã�´�.
      var resultSet = document.evaluate(exp, 
                                      this.xmlDoc.documentElement, 
                                      this.nsMap == null ? null : nsResolver, 
                                      XPathResult.ORDERED_NODE_SNAPSHOT_TYPE, 
                                      null);
      //�˻��� ��带 ������ �迭��ü�� �����Ѵ�.
      var nodes = new Array();
      for (var i=0;i<resultSet.snapshotLength;i++) {
        //������ �迭 ��ü�� ��带 �߰��Ѵ�.
        nodes[i] = resultSet.snapshotItem (i);
      }
      //�˻��� ��� �迭�� �����Ѵ�.
      return nodes;
    }
  },
  
  //XPath ������ �̿��Ͽ� �������� ��带 �����Ѵ�.
  removeNodes : function (exp) {
    //xmlDoc ���� ��ü�� null�̸� �����Ѵ�.
    if (this.xmlDoc == null) return;
    //������ ��� �迭�� ��´�.
    var nodes = this.selectNodes(exp);
    if (nodes == null || nodes.length == 0) return;
    //�ֻ��� ��Ʈ ��带 ��´�.
    var root = this.xmlDoc.documentElement;
    
    for (var i=nodes.length-1;i>=0;i--) {
      root.removeChild(nodes[i]);
    }
  },

  //XML������ XSLT ������ �����ϴ� �Լ� 
  transformXmlDoc : function(id) {
    //xmlDoc �Ǵ� xslDoc ��ü�� �������� ������ �۾��� ����Ѵ�.
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
  
  //��峪 ��� �迭��ü�� ������ XML ���� ��ü�� �����ϰ� root 
  //��忡 ���ڷ� ���޵� ��� �Ǵ� ��� �迭�� �߰��Ѵ�.
  setTransXmlDoc2Nodes : function(param) {
    if (this.transXMLDoc == null) {
      this.transXMLDoc = this.xmlDoc.cloneNode(false);
      this.transXMLDoc.appendChild(this.xmlDoc.documentElement.cloneNode(false));
    }
    var root = this.transXMLDoc.documentElement;
    var childNodes = root.childNodes;
    //��� �ڽ� ��带 �����Ѵ�.
    if (childNodes != null) {
      for (var i=childNodes.length-1;i>=0;i--) {
        root.removeChild(childNodes[i]);
      }
    }

    if (param.length) {
      //��� �迭�� transXMLDoc�� ��Ʈ�� �߰��Ѵ�.
      for (var i=0;i<param.length;i++) {
        root.appendChild(param[i].cloneNode(true));       
      }
    } else {
      //1 �� ��� �� ��� 
        root.appendChild(param.cloneNode(true));       
    }
    
    return this.transXMLDoc;
  }, 
  
  //���迭�� XSLT ������ �����ϴ� �Լ� 
  transformNodes : function(id, param, append) {
    if (param == null) return;
      
    //xmlDoc�� null�̸� �Ǵ� �ش� �޼ҵ带 ȣ���� �� ���ڸ� �������� ������ Ŭ������ ��� ��ü�� �̿��Ͽ� �����Ѵ�.
    var xmlDoc = this.setTransXmlDoc2Nodes(param);
    
    //xmlDoc �Ǵ� xslDoc ��ü�� �������� ������ �۾��� ����Ѵ�.
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
  
  //tag �̸����� ��Ҹ� ã�� �޼ҵ�
  getElementsByTagName : function (name) {
    if (this.xmlDoc == null) return null;
    return this.xmlDoc.getElementsByTagName(name);
  } ,
  
  //��带 �����Ѵ�.
  createElement : function (param) {
    //��Ҹ� �����Ѵ�.
    var node = this.xmlDoc.createElement(param.name);

    //����� ���ڿ� ���� �����Ѵ�.
    if (param.value) {
    	node.appendChild(this.xmlDoc.createTextNode(param.value));
    } 
    
    //��ҿ� �Ӽ��� �����ϸ� �Ӽ��� �߰��Ѵ�.
    if (param.attributes) {
        for (var i=0;i<param.attributes.length;i++) {
          node.setAttribute(param.attributes[i].name, param.attributes[i].value);
        }
    } 

    //����� ���� ��尡 �����ϸ� ������带 �����Ͽ� �߰��Ѵ�.
    if (param.childNodes) {
        for (var i=0;i<param.childNodes.length;i++) {
          node.appendChild(this.createElement(param.childNodes[i]));
        }
    }

    return node;
  },

  //�ֻ��� ��Ʈ ��忡 ��带 �����Ѵ�.
  appendChild : function (node) {
    if (this.xmlDoc == null) return;
    this.xmlDoc.documentElement.appendChild(node);
  }
  
}


