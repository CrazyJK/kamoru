
//comment 클래스 정의 및 생성자 메소드 선언 및 구현한다.
Comment =  function () {
  //XML 문서 객체 관리 멤버 객체 생성한다.
  this.xmlObj = new ajax.xml.Document();
  this.id     = null; //댓글 아이디
  this.bntObj = null;// 사용자 버튼 객체 
}

//comment 클래스  멤버 메소드 정의 및 구현한다.
Comment.prototype = {
  /////////////////////////////////////////////////////
  //comment 목록을 얻는 메소드를 구현한다.
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
  //id에 대한 comment 노드들 찾아 화면에 출력한다.
  updateForm : function (id, bntObj1) {
    
    var node = this.xmlObj.selectSingleNode("//comment[@id = '"+id+"']");
    
    if (node == null) return;
    //수정화면을 보여준다.
    this.insertFormVisible(false);

    var updateForm = document.updateForm;

    updateForm.id.value      = node.getAttribute("id");
    updateForm.name.value    = this.xmlObj.getNodeValue(node, "name");
    updateForm.content.value = this.xmlObj.getNodeValue(node, "content");
    
    //현재버튼이 속한 UL 개체를 얻는다.    
    this.bntObj = bntObj1.parentNode.parentNode;
  },
  
  /////////////////////////////////////////////////////
  //수정화면에 입력된 자료를 이용하여 서버의 update.jsp파일을 호출한다..
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
  //수정화면에 입력된 자료를 이용하여 comment 노드의 자료를 수정한다.
  updateComplate : function (reg) {
    if (reg.readyState == 4) {
      if (reg.status == 200) {
        //응답결과를 XML 결과로 받는다.
        //루트 노드를 얻는다.
        var root = reg.responseXML.documentElement;
        
        //상태 코드를 얻는다.
        if (this.xmlObj.getNodeValue(root, "code") == "error") {
          alert(this.xmlObj.getNodeValue(root, "msg"));
          return;
        }

        var updateForm = document.updateForm;
        var node = this.xmlObj.selectSingleNode("//comment[@id = '"+updateForm.id.value+"']");
        
        if (node == null) return;
        //등록화면을 보여준다.
        this.insertFormVisible(true);
        
        this.xmlObj.setNodeValue(node, "name", updateForm.name.value);
        this.xmlObj.setNodeValue(node, "content", updateForm.content.value);
        
        //화면에 변경된 자료를 작용한다.
        this.xmlObj.transformNodes(this.bntObj, node, false);
    
        //comment 노드를 출력한 UL 개체를 초기화 한다.    
        this.bntObj = null;
      }
    }
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
        //응답결과를 XML 결과로 받는다.
        //루트 노드를 얻는다.
        var root = reg.responseXML.documentElement;
        
        //상태 코드를 얻는다.
        if (this.xmlObj.getNodeValue(root, "code") == "error") {
          alert("오류가 발생하였습니다");
          return;
        }

        //XPath를 이용하여 xml 문서객체에서 제거한다.
        this.xmlObj.removeNodes("//comment[@id = '"+this.id+"']");
        
        //현재버튼이 속한 UL 개체의 부모노드를 얻는다.    
        var parentNode = this.bntObj.parentNode.parentNode.parentNode;
        //현재버튼이 속한 UL 개체를 제거한다.
        parentNode.removeChild(this.bntObj.parentNode.parentNode);
        
        //등록화면을 보여준다.
        this.insertFormVisible(true);
      }
    }
  },
    
  /////////////////////////////////////////////////////
  //입력화면에 입력된 값을 이용하여 서버에 댓글을 등록하도록 요청한다.
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
  //입력화면에 입력된 값을 이용하여 comment 노드를 생서하고 XML 문서에 추가한다.
  //추가된 comment 노드를 화면에 반영한다.
  insertComplate : function (reg) {
    if (reg.readyState == 4) {
      if (reg.status == 200) {
        //응답결과를 XML 결과로 받는다.
        //루트 노드를 얻는다.
        var root = reg.responseXML.documentElement;
        
        //상태 코드를 얻는다.
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
        
        //입력 화면 초기화
        document.addForm.name.value = "";
        document.addForm.content.value = "";
      }
    }
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
