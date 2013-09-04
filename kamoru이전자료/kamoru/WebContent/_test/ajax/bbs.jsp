<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title> Ajax 응용 게시판 </title>
<script type="text/javascript" src="event.js"></script>
<script type="text/javascript" src="ajax.Request.js"></script>
<script type="text/javascript">
var debug = null;

window.onload = function(){
	var updateListBtn = document.getElementById("updateListBtn");
	Event.addEvent(updateListBtn, 'click', function(e) {
		new ajax.Request({
			  url : "list_ajax.jsp"
			, load : function(responseText){
			jsUpdateList(eval(responseText));
			}
		});
	});
}

function jsUpdateList(data){
	var listDiv = document.getElementById("listDiv");
	var html = "";
	var dataSize = data.length;

	html += "<table border=1>";
	for(var i=0 ; i<dataSize ; i++){
		html += "<tr>";
		html += "<td>";
		html += data[i].bbsid;       
		html += "</td><td>";
		html += data[i].tags;        
		html += "</td><td>";
		html += data[i].title;       
		html += "</td><td>";
//		html += data[i].content;     
//		html += "</td><td>";
		html += data[i].creator;     
		html += "</td><td>";
		html += data[i].creatdtime;  
		html += "</td><td>";
		html += data[i].hit;         
		html += "</td><td>";
		html += data[i].state;       
		html += "</td><td>";
		html += data[i].modifier;    
		html += "</td><td>";
		html += data[i].modifydtime; 
		html += "</td>";
		html += "</tr>";
	}
	html += "</table>";
	listDiv.innerHTML = html;
}

</script>
</head>
<body>
<div id="listDiv"></div>

<input type="button" id="updateListBtn" value="목록보기"/>

<div id="debug"></div>
</body>
</html>