
//comment Ŭ���� ���� �� ������ �޼ҵ� ���� �� �����Ѵ�.
Comment =  function () {
  //XML ���� ��ü ���� ��� ��ü �����Ѵ�.
  this.xmlObj = new ajax.xml.Document();
}

//comment Ŭ����  ��� �޼ҵ� ���� �� �����Ѵ�.
Comment.prototype = {
  /////////////////////////////////////////////////////
  //comment ����� ��� �޼ҵ带 �����Ѵ�.
  loadList : function () {
	new  ajax.xhr.Request({
                          url              : "list.jsp", 
                          params           : null, 
                          callbackObject   : this,
                          callbackFunction : this.loadBooksXML,
                          method           : "GET" 
                          });
	new  ajax.xhr.Request({
                          url              : "comment.xsl", 
                          params           : null, 
                          callbackObject   : this,
                          callbackFunction : this.loadBooksXSL,
                          method           : "GET" 
                          });
  },

  /////////////////////////////////////////////////////
  //id�� ���� comment ���� ã�� ȭ�鿡 ����Ѵ�.
  updateForm : function (id, bntObj) {
    
    var node = this.xmlObj.selectSingleNode("//comment[@id = '"+id+"']");
    
    if (node == null) return;
    //����ȭ���� �����ش�.
    this.insertFormVisible(false);

    var updateForm = document.updateForm;

    updateForm.id.value      = node.getAttribute("id");
    updateForm.name.value    = this.xmlObj.getNodeValue(node, "name");
    updateForm.content.value = this.xmlObj.getNodeValue(node, "content");
    
    //�����ư�� ���� UL ��ü�� ��´�.    
    this.ulObj = bntObj.parentNode.parentNode;
  },
  
  /////////////////////////////////////////////////////
  //����ȭ�鿡 �Էµ� �ڷḦ �̿��Ͽ� comment ����� �ڷḦ �����Ѵ�.
  update : function () {
    
    var updateForm = document.updateForm;
    var node = this.xmlObj.selectSingleNode("//comment[@id = '"+updateForm.id.value+"']");
    
    if (node == null) return;
    //���ȭ���� �����ش�.
    this.insertFormVisible(true);
    
    this.xmlObj.setNodeValue(node, "name", updateForm.name.value);
    this.xmlObj.setNodeValue(node, "content", updateForm.content.value);
    
    //ȭ�鿡 ����� �ڷḦ �ۿ��Ѵ�.
    this.xmlObj.transformNodes(this.ulObj, node, false);

    //comment ��带 ����� UL ��ü�� �ʱ�ȭ �Ѵ�.    
    this.ulObj = null;
    
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
    //XPath�� �̿��Ͽ� xml ������ü���� �����Ѵ�.
    this.xmlObj.removeNodes("//comment[@id = '"+id+"']");
    
    //�����ư�� ���� UL ��ü�� �θ��带 ��´�.    
    var parentNode = obj.parentNode.parentNode.parentNode;
    //�����ư�� ���� UL ��ü�� �����Ѵ�.
    parentNode.removeChild(obj.parentNode.parentNode);
    
    //���ȭ���� �����ش�.
    this.insertFormVisible(true);
  },
  
  /////////////////////////////////////////////////////
  //�Է�ȭ�鿡 �Էµ� ���� �̿��Ͽ� comment ��带 �����ϰ� XML ������ �߰��Ѵ�.
  //�߰��� comment ��带 ȭ�鿡 �ݿ��Ѵ�.
  insert : function () {
    var next_id = document.getElementById("next_id");
    var param = {
      name  : "comment", 
      attributes : [
        { name : "id", value : next_id.value }
      ],
      childNodes : [
        { name : "name"   , value : document.addForm.name.value },
        { name : "content", value : document.addForm.content.value }
      ]
    };     
    next_id.value = parseInt(next_id.value) + 1;
    
    var node = this.xmlObj.createElement(param);
    this.xmlObj.appendChild(node);

    var list = document.getElementById("list");
    this.xmlObj.transformNodes(list, node, true);

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
