
//naver API Ŭ���� ���� �� ������ �޼ҵ� ���� �� �����Ѵ�.
NaverRSS =  function (id) {
  this.id = id;
  this.xmlDoc = null;
  this.xslDoc = null;
}

//NaverRSS Ŭ����  ��� �޼ҵ� ���� �� �����Ѵ�.
NaverRSS.prototype = {
  /////////////////////////////////////////////////////
  //NaverRSS ����� ��� �޼ҵ带 �����Ѵ�.
  search : function (form) {
    this.xslDoc = new dojo.xml.XslTransform("jsp/naver.xsl");
    
    dojo.io.bind({
        //�������� �����Ŀ� ����Ǵ� �ݹ� �Լ�
        load : this.loadXML,
        error : function(type,data,evt) { 
          /*���� �߻��� ó���� �ϱ����� �κ�*/
          dojo.debug("aaa : ���� �߻� : " + data.message);
        },
        //���ϵǾ����� ����Ÿ�� Ÿ��
        mimetype: "text/xml",
        formNode : document.getElementById(form),
        encoding : "utf-8"
        
    });


  }, 
  loadXML : function(type,data,evt) { 
    /*����Ÿ ó���� �ϱ����� �κ�*/
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
