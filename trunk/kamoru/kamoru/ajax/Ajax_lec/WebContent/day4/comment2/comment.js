
//comment 클래스 정의 및 생성자 메소드 선언 및 구현한다.
Comment =  function () {
  //XML 문서 객체 관리 멤버 객체 생성한다.
  this.xmlObj = new ajax.xml.Document();
}

//comment 클래스  멤버 메소드 정의 및 구현한다.
Comment.prototype = {
  /////////////////////////////////////////////////////
  //comment 목록을 얻는 메소드를 구현한다.
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
  //id에 대한 comment 노드들 찾아 화면에 출력한다.
  updateForm : function (id, bntObj) {
    
    var node = this.xmlObj.selectSingleNode("//comment[@id = '"+id+"']");
    
    if (node == null) return;
    //수정화면을 보여준다.
    this.insertFormVisible(false);

    var updateForm = document.updateForm;

    updateForm.id.value      = node.getAttribute("id");
    updateForm.name.value    = this.xmlObj.getNodeValue(node, "name");
    updateForm.content.value = this.xmlObj.getNodeValue(node, "content");
    
    //현재버튼이 속한 UL 개체를 얻는다.    
    this.ulObj = bntObj.parentNode.parentNode;
  },
  
  /////////////////////////////////////////////////////
  //수정화면에 입력된 자료를 이용하여 comment 노드의 자료를 수정한다.
  update : function () {
    
    var updateForm = document.updateForm;
    var node = this.xmlObj.selectSingleNode("//comment[@id = '"+updateForm.id.value+"']");
    
    if (node == null) return;
    //등록화면을 보여준다.
    this.insertFormVisible(true);
    
    this.xmlObj.setNodeValue(node, "name", updateForm.name.value);
    this.xmlObj.setNodeValue(node, "content", updateForm.content.value);
    
    //화면에 변경된 자료를 작용한다.
    this.xmlObj.transformNodes(this.ulObj, node, false);

    //comment 노드를 출력한 UL 개체를 초기화 한다.    
    this.ulObj = null;
    
  },

  /////////////////////////////////////////////////////
  //수정화면에 입력된 자료를 취소한다.
  cancel : function () {
    //등록화면을 보여준다.
    this.insertFormVisible(true);
  },

  /////////////////////////////////////////////////////
  //id를 이용하여 comment 노드를 찾아 삭제하고 화면에 반영한다.
  remove : function (id, obj) {
    //XPath를 이용하여 xml 문서객체에서 제거한다.
    this.xmlObj.removeNodes("//comment[@id = '"+id+"']");
    
    //현재버튼이 속한 UL 개체의 부모노드를 얻는다.    
    var parentNode = obj.parentNode.parentNode.parentNode;
    //현재버튼이 속한 UL 개체를 제거한다.
    parentNode.removeChild(obj.parentNode.parentNode);
    
    //등록화면을 보여준다.
    this.insertFormVisible(true);
  },
  
  /////////////////////////////////////////////////////
  //입력화면에 입력된 값을 이용하여 comment 노드를 생서하고 XML 문서에 추가한다.
  //추가된 comment 노드를 화면에 반영한다.
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

  //덧글 등록 및 수정 양식 화면관리하는 함수 
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
    
    var list = document.getElementById("list");
    
    this.xmlObj.transformXmlDoc(list);
  } 
}
