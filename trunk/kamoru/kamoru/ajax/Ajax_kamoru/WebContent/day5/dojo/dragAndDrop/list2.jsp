<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql" %>

<%--댓글 목록을 얻기 위한 SQL 구문을 작성한다. --%>
<sql:query var="rs" dataSource="jdbc/MySQLDB">
select id, name from tb_product
</sql:query>

    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en">
<head>
<title>DOJO 드래그 앤 드롭 사용법</title>
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

//드래그 앤 드롭 처리를 위해서는 반드시 아래의 패키지를 포함해야한다.
dojo.require("dojo.dnd.*"); 
//HTML UI 객체를 사용하기 위해서 widget 패키지를 포함한 것 
dojo.require("dojo.widget.*");
//debug 창 때문에 포함한것
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
  
  //동적으로 입력약식을 생성한다.  
  form1.innerHTML = html;
  //생성된 동적양식의 값을 서버에 전송한다.
  form1.submit();
}

dojo.addOnLoad(init);

</script>
</head>
<body>
<div id="container">
  <div id="header"><h1>DOJO 드래그 앤 드롭 사용법</h1></div>
  <div id="leftDiv">
    <h3>선택 가능한 제품</h3>
    <ul id="list1" >
      <c:forEach var="row" items="${rs.rows}" varStatus="status">
        <li id="item${status.count}" code="${row.id}">${row.name}</li>
      </c:forEach>
    </ul>
  </div>
  <div id="rightDiv">
    <h3>선택한 제품</h3>
    <ul id="list2" ></ul>
  </div>
  <div id="centerDiv">
    <button dojotype="Button" id="submitBTN" >확인</button>
  </div>
</div>
<form name="form1" method="post" action="submit.jsp">
<!-- 입력인자의 이름은 code, value로 한다. -->
</form>

<!--  디버깅 윈도우  -->
<div dojoType="FloatingPane" title="Debug Console"
  constrainToContainer="1"
  style="width: 100%; height: 100px; left: 0px; bottom: 20px;">
  <div id="_dojoDebugConsole"></div>
</div>
</body>
</html>
