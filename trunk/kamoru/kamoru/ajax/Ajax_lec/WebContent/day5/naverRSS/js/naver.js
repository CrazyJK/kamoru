
//naver API 클래스 정의 및 생성자 메소드 선언 및 구현한다.
NaverRSS =  function (id) {
  this.id = id;
  this.xmlDoc = null;
  this.xslDoc = null;
}

//NaverRSS 클래스  멤버 메소드 정의 및 구현한다.
NaverRSS.prototype = {
  /////////////////////////////////////////////////////
  //NaverRSS 목록을 얻는 메소드를 구현한다.
  search : function (form) {
    this.xslDoc = new dojo.xml.XslTransform("jsp/naver.xsl");
    
    dojo.io.bind({
        //성공적인 응답후에 실행되는 콜백 함수
        load : this.loadXML,
        error : function(type,data,evt) { 
          /*오류 발생시 처리를 하기위한 부분*/
          dojo.debug("aaa : 오류 발생 : " + data.message);
        },
        //리턴되어지는 데이타의 타입
        mimetype: "text/xml",
        formNode : document.getElementById(form),
        encoding : "utf-8"
        
    });


  }, 
  loadXML : function(type,data,evt) { 
    /*데이타 처리를 하기위한 부분*/
    dojo.debug("type= " + type + "  data = "+ data + "evt = " + evt);
    this.doXSLT();
    alert("aaa");
  },
  doXSLT : function (xmlDoc) {
  alert("this.xmlDoc =" + xmlDoc  + "  this.xslDoc= " +this.xslDoc);
    if (xmlDoc == null || this.xslDoc == null) return;
    alert("aaaa");
    var dom = this.xslDoc.getResultDom(xmlDoc,null);
    alert(dom);    
  }  

}
