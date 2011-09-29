<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql" %>

<%--��� ����� ��� ���� SQL ������ �ۼ��Ѵ�. --%>
<sql:query var="rs" dataSource="jdbc/MySQLDB">
select id, name from tb_product
</sql:query>

    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en">
<head>
<title>DOJO �巡�� �� ��� ����</title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr" />
<link rel="stylesheet" type="text/css" href="../theme/dojolab.css" />
<style type="text/css">
  ul {
    list-style-type: none;
    padding: 0px;
    margin: 0px;
    width: 20em;
    font-size: 13px;
    font-family: Arial, sans-serif;
    height: 200px;
    background-color: red;
 }
 ul li {
    cursor:move;
    padding: 2px 2px;
    border: 1px solid #ccc;
    background-color: #eee;
 }

 img {
    border-style: none;
    float: right;
 }
</style>
<script type="text/javascript">
      var djConfig = {isDebug: true
              ,debugAtAllCosts : false
              ,debugContainerId : "_dojoDebugConsole"
              };
</script>
<script language = "JavaScript" type = "text/javascript" src = "../dojoAjax/dojo.js"></script>

<script type = "text/javascript" language = "JavaScript">

var list1;
var list2;

//�巡�� �� ��� ó���� ���ؼ��� �ݵ�� �Ʒ��� ��Ű���� �����ؾ��Ѵ�.
dojo.require("dojo.dnd.*"); 
//HTML UI ��ü�� ����ϱ� ���ؼ� widget ��Ű���� ������ �� 
dojo.require("dojo.widget.*");
//debug â ������ �����Ѱ�
dojo.require("dojo.widget.FloatingPane");

function dropHandler1(e) {
  list1.appendChild( e.dragObject.domNode );
  return true;
}

function dropHandler2(e) {
  list2.appendChild( e.dragObject.domNode );
  return true;
}

function init ()
{
  list1 = dojo.byId("list1");
  list2 = dojo.byId("list2");
  
   var leftTarget  = new dojo.dnd.HtmlDropTarget( list1, [ "*" ] );
   dojo.lang.forEach( list1.getElementsByTagName("li"), function ( child )
   {
       var dragSource = new dojo.dnd.HtmlDragSource( child, "*" );
       dojo.event.connect(dragSource, "onDragStart", function(e){ dojo.debug( "Procesing onDragStart()" ); });
       dojo.event.connect(dragSource, "onDragEnd", function(e){ dojo.debug( "Procesing onDragEnd()" ); });
   });
   
   var righthandDropTarget = new dojo.dnd.HtmlDropTarget( list2, [ "*" ] );

   dojo.event.connect( new dojo.dnd.HtmlDropTarget( dojo.byId( "rightDiv" ), [ "*" ] ), "onDrop", dropHandler2);
   dojo.event.connect( new dojo.dnd.HtmlDropTarget( dojo.byId( "leftDiv" ), [ "*" ] ), "onDrop", dropHandler1);

  var submitBTN = dojo.widget.manager.getWidgetById("submitBTN");
  dojo.debug("submitBTN->" + submitBTN);
  dojo.event.connect(submitBTN, "onClick", onclick_submit);
  

    dojo.debug("Initialization complete");
}

function onclick_submit(e) {
  var form1 = document.form1;
  var items = list2.getElementsByTagName("li");
  var html = "";
  
  for (var i=0;i<items.length;i++) {
    html += "<input type='hidden' name='code' value='" + items[i].getAttribute("code") + "'>";
    html += "<input type='hidden' name='value' value='" + items[i].innerHTML + "'>";
  }
  
  //�������� �Է¾���� �����Ѵ�.  
  form1.innerHTML = html;
  //������ ��������� ���� ������ �����Ѵ�.
  form1.submit();
}

dojo.addOnLoad(init);

</script>
</head>
<body>
<div id="container">
  <div id="header"><h1>DOJO �巡�� �� ��� ����</h1></div>
  <div id="leftDiv">
    <h3>���� ������ ��ǰ</h3>
    <ul id="list1" >
      <c:forEach var="row" items="${rs.rows}" varStatus="status">
        <li id="item${status.count}" code="${row.id}">${row.name}</li>
      </c:forEach>
    </ul>
  </div>
  <div id="rightDiv">
    <h3>������ ��ǰ</h3>
    <ul id="list2" ></ul>
  </div>
  <div id="centerDiv">
    <button dojotype="Button" id="submitBTN" >Ȯ��</button>
  </div>
</div>
<form name="form1" method="post" action="submit.jsp">
<!-- �Է������� �̸��� code, value�� �Ѵ�. -->
</form>

<!--  ����� ������  -->
<div dojoType="FloatingPane" title="Debug Console"
  constrainToContainer="1"
  style="width: 100%; height: 100px; left: 0px; bottom: 20px;">
  <div id="_dojoDebugConsole"></div>
</div>
</body>
</html>
