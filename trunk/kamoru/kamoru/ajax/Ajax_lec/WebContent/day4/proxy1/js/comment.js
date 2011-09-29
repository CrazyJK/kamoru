
//comment Ŭ���� ���� �� ������ �޼ҵ� ���� �� �����Ѵ�.
Comment =  function () {
  //XML ���� ��ü ���� ��� ��ü �����Ѵ�.
  this.xmlObj = new ajax.xml.Document();
  this.id     = null; //��� ���̵�
  this.bntObj = null;// ����� ��ư ��ü 
}

//comment Ŭ����  ��� �޼ҵ� ���� �� �����Ѵ�.
Comment.prototype = {
  /////////////////////////////////////////////////////
  //comment ����� ��� �޼ҵ带 �����Ѵ�.
  loadList : function () {
	new  ajax.xhr.Request({
                          url              : "jsp/list.jsp", 
                          params           : null, 
                          callbackObject   : this,
                          callbackFunction : this.loadBooksXML,
                          method           : "GET" 
                          });
	new  ajax.xhr.Request({
                          url              : "jsp/comment.xsl", 
                          params           : null, 
                          callbackObject   : this,
                          callbackFunction : this.loadBooksXSL,
                          method           : "GET" 
                          });
  },

  /////////////////////////////////////////////////////
  //id�� ���� comment ���� ã�� ȭ�鿡 ����Ѵ�.
  updateForm : function (id, bntObj1) {
    
    var node = this.xmlObj.selectSingleNode("//comment[@id = '"+id+"']");
    
    if (node == null) return;
    //����ȭ���� �����ش�.
    this.insertFormVisible(false);

    var updateForm = document.updateForm;

    updateForm.id.value      = node.getAttribute("id");
    updateForm.name.value    = this.xmlObj.getNodeValue(node, "name");
    updateForm.content.value = this.xmlObj.getNodeValue(node, "content");
    
    //�����ư�� ���� UL ��ü�� ��´�.    
    this.bntObj = bntObj1.parentNode.parentNode;
  },
  
  /////////////////////////////////////////////////////
  //����ȭ�鿡 �Էµ� �ڷḦ �̿��Ͽ� ������ update.jsp������ ȣ���Ѵ�..
  update : function () {
    
    var updateForm = document.updateForm;
    new  ajax.xhr.Request({
                            url              : "proxy.jsp", 
                            params           : {
                                                  url     : "http://www.server.com:8080/ajax/day4/proxy/jsp/update.jsp",
                                                  id      : updateForm.id.value,
                                                  name    : updateForm.name.value,
                                                  content : updateForm.content.value
                                               }, 
                            callbackObject   : this,
                            callbackFunction : this.updateComplate,
                            method           : "POST" 
                            });
  },

  /////////////////////////////////////////////////////
  //����ȭ�鿡 �Էµ� �ڷḦ �̿��Ͽ� comment ����� �ڷḦ �����Ѵ�.
  updateComplate : function (reg) {
    if (reg.readyState == 4) {
      if (reg.status == 200) {
        //�������� XML ����� �޴´�.
        //��Ʈ ��带 ��´�.
        var root = reg.responseXML.documentElement;
        
        //���� �ڵ带 ��´�.
        if (this.xmlObj.getNodeValue(root, "code") == "error") {
          alert(this.xmlObj.getNodeValue(root, "msg"));
          return;
        }

        var updateForm = document.updateForm;
        var node = this.xmlObj.selectSingleNode("//comment[@id = '"+updateForm.id.value+"']");
        
        if (node == null) return;
        //���ȭ���� �����ش�.
        this.insertFormVisible(true);
        
        this.xmlObj.setNodeValue(node, "name", updateForm.name.value);
        this.xmlObj.setNodeValue(node, "content", updateForm.content.value);
        
        //ȭ�鿡 ����� �ڷḦ �ۿ��Ѵ�.
        this.xmlObj.transformNodes(this.bntObj, node, false);
    
        //comment ��带 ����� UL ��ü�� �ʱ�ȭ �Ѵ�.    
        this.bntObj = null;
      }
    }
  },
  
  /////////////////////////////////////////////////////
  //����ȭ�鿡 �Էµ� �ڷḦ ����Ѵ�.
  cancel : function () {
    //���ȭ���� �����ش�.
    this.insertFormVisible(true);
  },

  /////////////////////////////////////////////////////
  //id�� �̿��Ͽ� comment ��带 ã�� �����ϰ� ȭ�鿡 �ݿ��Ѵ�.
  remove : function (id, obj) {
    this.id = id;
    this.bntObj = obj;
    
    new  ajax.xhr.Request({
                            url              : "proxy.jsp", 
                            params           : {
                                                url : "http://www.server.net:8080/ajax/day4/proxy/jsp/remove.jsp",
                                                id  : this.id
                                                }, 
                            callbackObject   : this,
                            callbackFunction : this.removeComplate,
                            method           : "POST" 
                            });
  },

  removeComplate : function (reg) {
    if (reg.readyState == 4) {
      if (reg.status == 200) {
        //�������� XML ����� �޴´�.
        //��Ʈ ��带 ��´�.
        var root = reg.responseXML.documentElement;
        
        //���� �ڵ带 ��´�.
        if (this.xmlObj.getNodeValue(root, "code") == "error") {
          alert("������ �߻��Ͽ����ϴ�");
          return;
        }

        //XPath�� �̿��Ͽ� xml ������ü���� �����Ѵ�.
        this.xmlObj.removeNodes("//comment[@id = '"+this.id+"']");
        
        //�����ư�� ���� UL ��ü�� �θ��带 ��´�.    
        var parentNode = this.bntObj.parentNode.parentNode.parentNode;
        //�����ư�� ���� UL ��ü�� �����Ѵ�.
        parentNode.removeChild(this.bntObj.parentNode.parentNode);
        
        //���ȭ���� �����ش�.
        this.insertFormVisible(true);
      }
    }
  },
    
  /////////////////////////////////////////////////////
  //�Է�ȭ�鿡 �Էµ� ���� �̿��Ͽ� ������ ����� ����ϵ��� ��û�Ѵ�.
  insert : function () {
    new  ajax.xhr.Request({
                            url              : "proxy.jsp",
                            params           : {
                                                url     : "http://www.server.co.kr:8080/ajax/day4/proxy/jsp/insert.jsp", 
                                                name    : document.addForm.name.value,
                                                content : document.addForm.content.value
                                                }, 
                            callbackObject   : this,
                            callbackFunction : this.insertComplate,
                            method           : "POST" 
                            });
  }, 

  /////////////////////////////////////////////////////
  //�Է�ȭ�鿡 �Էµ� ���� �̿��Ͽ� comment ��带 �����ϰ� XML ������ �߰��Ѵ�.
  //�߰��� comment ��带 ȭ�鿡 �ݿ��Ѵ�.
  insertComplate : function (reg) {
    if (reg.readyState == 4) {
      if (reg.status == 200) {
        //�������� XML ����� �޴´�.
        //��Ʈ ��带 ��´�.
        var root = reg.responseXML.documentElement;
        
        //���� �ڵ带 ��´�.
        if (this.xmlObj.getNodeValue(root, "code") == "error") {
          alert(this.xmlObj.getNodeValue(root, "msg"));
          return;
        }
        
        var next_id = this.xmlObj.getNodeValue(root, "id");
        var param = {
          name  : "comment", 
          attributes : [
            { name : "id", value : next_id }
          ],
          childNodes : [
            { name : "name"   , value : document.addForm.name.value },
            { name : "content", value : document.addForm.content.value }
          ]
        };     
        
        var node = this.xmlObj.createElement(param);
        this.xmlObj.appendChild(node);
    
        var list = document.getElementById("list");
        this.xmlObj.transformNodes(list, node, true);
        
        //�Է� ȭ�� �ʱ�ȭ
        document.addForm.name.value = "";
        document.addForm.content.value = "";
      }
    }
  },
    

  //���� ��� �� ���� ��� ȭ������ϴ� �Լ� 
  insertFormVisible : function (visible) {
    var insert = document.getElementById("insert");
    var update = document.getElementById("update");
    
    if (visible == true) {
      insert.style.display = "block";    
      update.style.display = "none";    
    } else {
      insert.style.display = "none";    
      update.style.display = "block";    
    }
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
    
    var list = document.getElementById("list");
    
    this.xmlObj.transformXmlDoc(list);
  } 
}
