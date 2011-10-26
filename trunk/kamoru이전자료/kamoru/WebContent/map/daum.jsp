<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.*, com.hs.frmwk.db.*, java.sql.*" %>
<%@ include file="method.jspf" %>

<% // 접속자 정보
String myUserID = "500018321";
String myDeptID = "000010314";

%>
<% // parameter 받기
request.setCharacterEncoding("UTF-8");

String latitude 	= isnull(request.getParameter("latitude"), "37.51647829244714");
String longitude 	= isnull(request.getParameter("longitude"), "126.97448293879174");
String level		= isnull(request.getParameter("level"), "8");
String mode			= isnull(request.getParameter("mode"), "view");
String locationName		= request.getParameter("locationName");
String locationDesc		= request.getParameter("locationDesc");

String goSite		= request.getParameter("goSite");
String goTask		= request.getParameter("goTask");
String goTime		= request.getParameter("goTime");

String selectUserID		= request.getParameter("selectUserID");

//out.println(" latitude > " + latitude);
//out.println(" longitude> " + longitude);
//out.println(" level> " + level);
//out.println(" mode> " + mode);

%>
<%-- // mode에 따른 액션
if(mode.equalsIgnoreCase("saveLocation")){
	saveLocation(locationName, latitude, longitude, locationDesc);
}else if(mode.equalsIgnoreCase("savePlaceToGo")){
	savePlaceToGo(selectUserID, goSite, goTask, goTime, latitude, longitude);
} 
--%>
<% // 부서원 정보 가져오기
// 관리할 부서 목록
String[] deptIDs = new String[]{
	"500004182", "000010311", "000010312", "000010313", "000010314"
};
int myDeptIdx = getMydeptIdx(deptIDs, myDeptID);

String[] deptNames = getDeptNames(deptIDs);

ArrayList[] userListArr = getUserinfo(deptIDs);
%>
<% // 고객사이트 정보 가져오기
ArrayList siteList = getSiteList(); 
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Daum 지도 API</title>
<link rel="stylesheet" type="text/css" href="map.css">
<script type="text/javascript" src="http://apis.daum.net/maps/maps2.js?apikey=beb89a69bb4f49349af02392c00df4e88d21b316" charset="utf-8"></script>
<script type="text/javascript">
var deptLength = <%= deptIDs.length %>;
var myDeptIdx = <%= myDeptIdx %>;
var myUserID = <%= selectUserID == null ? myUserID : selectUserID %>;
var savemode = false;
var saveMark;
var markmode = false;
var map = null;           

// onLoad 함수
window.onload = function() {
	jsMapInit();
	showDept(myDeptIdx);
	selectUserID = document.getElementById("selectUserID");
	selectUserID.value = myUserID;
}

// 지도 컨트롤 함수
/** 입력된 위.경도로 지동 이동
 */
function goDirection(lat, lng, tridx, userID, userName){
	if(tridx != null){
		markUser(tridx, userID, userName);
	}
	if(lat != null && lng != null){
		map.setCenter(new DLatLng(lat, lng), map.getLevel());
	}
}

// 로직 컨트롤 함수
/** 입력된 부서index값으로 부서테이블 display
 */
function showDept(idx){
	for(i=0 ; i<deptLength ; i++){
		var deptDiv = document.getElementById("dept" + i);
		if(i == idx){
			deptDiv.style.display = "";
		}else{
			deptDiv.style.display = "none";
		}
	}
}

/** 입력된 유저정보가 선택되었음을 display
 */
function markUser(idx, userID, name){
	for(i=0 ; i<deptLength ; i++){
		for(j=0 ; j<20 ; j++){
			var userTR = document.getElementById("user"+i+"_"+j);
			if(userTR != null)
				userTR.style.background = "#FFFFFF";
		}
	}
	var userTR = document.getElementById(idx);
	userTR.style.background = "#D9DFF0";
	selectUserID = document.getElementById("selectUserID");
	selectUserID.value = userID;

	markPlaceToGoBtn = document.getElementById("markPlaceToGoBtn");
	markPlaceToGoBtn.value = name + "의 행선지 저장";
}

/** 고객사 위치를 표시하기 위한 준비. mark display
 */
function markLocation(){
	if(savemode){
		savemode=false;
		document.all.markLocationBtn.value = "위치 저장";
		document.all.saveLocationForm.style.display = "none";
		map.removeOverlay(saveMark) 
	}else{
		document.all.markLocationBtn.value = "저장 취소";
		document.all.saveLocationForm.style.display = "";
		savemode=true;
		var coordPoint = map.getCenter();
		var iw = new DInfoWindow("http://kamoru/map/howto_save.html", {width:150, height:50});
		saveMark = new DMark(coordPoint, {infowindow:iw, draggable:true})
		map.addOverlay(saveMark); // mark Overlay
	}
}

/** 팀원의 행선지를 설정하기 위한 준비. 입력란 display.
 */
function markPlaceToGo(){
	if(markmode){
		markmode=false;
		document.all.markPlaceToGoBtn.value = "행선지 저장";
		document.all.savePlaceToGoForm.style.display = "none";
	}else{
		document.all.markPlaceToGoBtn.value = "행선지 저장 취소";
		document.all.savePlaceToGoForm.style.display = "";
		markmode=true;
	}
}

/** 팀원의 행선지를 설정하기 위한 mark display. 
 */
function selPlaceToGo(){
	if(saveMark != null){
		map.removeOverlay(saveMark);
		saveMark = null;
	}else{
		var iw = new DInfoWindow("http://kamoru/kamoru/map/howto_save.html", {width:150, height:50});
		saveMark = new DMark(map.getCenter(), {infowindow:iw, draggable:true})
		map.addOverlay(saveMark); // mark Overlay
	}
}

/** 고객사 위치를 저장하기 위한 submit함수
 */
function saveLocation(){
	if(confirm("지정한 위치를 사이트로 저장하시겠습니까?")){

		var coordPoint = saveMark.getPoint();
		lat = coordPoint.y;
		lng = coordPoint.x;
		//alert(lat + "," + lng);
		document.forms[0].latitude.value = lat;
		document.forms[0].longitude.value = lng;
		document.forms[0].level.value = map.getLevel();
		document.forms[0].mode.value = "saveLocation";
		document.forms[0].submit();
	}
}

/** 팀원 행선지를 저장하기 위한 submit함수
 */
function savePlaceToGo(){
	var goSiteSelect = document.getElementById("goSiteSelect");
	var goSite       = document.getElementById("goSite");
	var goTask       = document.getElementById("goTask");
	var goTime       = document.getElementById("goTime");
	if(goSite.value == "" && goSiteSelect.options[goSiteSelect.selectedIndex].value == ""){
		alert("행선지를 적어주세요");
		document.forms[0].goSite.focus();
		return;
	}else if(goTask.value == ""){
		alert("업무를 적어주세요");
		document.forms[0].goTask.focus();
		return;
	}else if(goTime.value == ""){
		alert("시간를 적어주세요");
		document.forms[0].goTime.focus();
		return;
	}

	if(confirm("지정한 위치를 행선지로 저장하시겠습니까?"))
	{
		var coordPoint = saveMark.getPoint();
		lat = coordPoint.y;
		lng = coordPoint.x;
		//alert(lat + "," + lng);
		document.forms[0].latitude.value = lat;
		document.forms[0].longitude.value = lng;
		document.forms[0].level.value = map.getLevel();
		document.forms[0].mode.value = "savePlaceToGo";
		document.forms[0].submit();
	}
}
function jsGoDirection(selObj){
	var selectedValue = selObj.options[selObj.selectedIndex].value;
	var goSite        = document.getElementById("goSite");
	if(selectedValue != ""){
		var coord = selectedValue.split(",");
	//	goSite.value = coord[0];
		goDirection(coord[1],coord[2]);
	}
}
function jsMapInit() {
	map = new DMap("map", {point:new DLatLng(<%= latitude %>, <%= longitude %>), level:<%= level %>}); 
	map.setMapType("TYPE_SKYVIEW");
	map.addControl(new DMapTypeControl());
	map.addControl(new DZoomControl());
	
	// 고객사 위치 표시
	<%	for(int i=0 ; i<siteList.size() ; i++) { 
			HashMap siteMap = (HashMap)siteList.get(i); %>
	
		var dscpt = new DInfoWindow("http://kamoru/kamoru/map/showDscpt.jsp?dscpt=<%= siteMap.get("NAME") + ";" + siteMap.get("DSCPT") %>", {width:150, height:50});
		var icon = new DIcon("${contextpath}/images/map/s.png", new DSize(32, 32));
		var mark = new DMark(new DLatLng(<%= siteMap.get("LATITUDE") %>, <%= siteMap.get("LONGITUDE") %>), {infowindow:dscpt, mark:icon, offset:new DPoint(-16,-30)});
		map.addOverlay(mark);
		map.addOverlay(new DText(new DLatLng(<%= siteMap.get("LATITUDE") %>, <%= siteMap.get("LONGITUDE") %>),"<%= siteMap.get("NAME") %>"));

	<%	} %>

	// 팀원 위치 표시
	<%	for(int i=0 ; i<deptIDs.length ; i++) {
			for(int j=0 ; j<userListArr[i].size() ; j++) { 
				HashMap userMap = (HashMap)userListArr[i].get(j); 
				String site = (String)userMap.get("SITE");
				if(site != null) { %>
		var dscpt = new DInfoWindow("http://kamoru/kamoru/map/showDscpt.jsp?dscpt=<%= userMap.get("SITE") + ";" + userMap.get("TASK") %>", {width:150, height:50});
		var icon = new DIcon("${contextpath}/images/map/<%= userMap.get("USER_ID") %>.png", new DSize(64, 32));
		var mark = new DMark(new DLatLng(<%= userMap.get("LATITUDE") %>, <%= userMap.get("LONGITUDE") %>), {infowindow:dscpt, mark:icon, offset:new DPoint(0,-30)});
		map.addOverlay(mark);

	<% 	}}} %>
}

</script>
</head>
<body>
<form name="mapForm" method="post" target="mapaction" action="mapaction.jsp">
<table border="1" width="100%">
	<tr valign="top">
		<td width="100">
			<table border="1" width="100%" cellpadding="3" cellspacing="0">
				<tr>
					<th>사이트</th>
				</tr>
			<% for(int i=0 ; i<siteList.size() ; i++){ 
				HashMap siteMap = (HashMap)siteList.get(i); 
			%>
				<tr>
					<td><span class="ANCHOR" title="<%= siteMap.get("DSCPT") %>" onclick="goDirection(<%= siteMap.get("LATITUDE") %>, <%= siteMap.get("LONGITUDE") %>)"><%= siteMap.get("NAME") %></span></td>
				</tr>
			<% } %>
				<tr>
					<td>
						<fieldset>
						<legend><button id="markLocationBtn" onclick="markLocation()">위치 저장</button></legend>
						<div id="saveLocationForm" style="display:none;">
							<table border="0" cellpadding="0" cellspacing="0" width="100%">
								<tr>
									<td><input class="INPUTTEXT" type="text" name="locationName" style="width:100%" value="이름" onfocus="javascript:this.value=''"/></td>
								</tr>
								<tr>
									<td><input class="INPUTTEXT" type="text" name="locationDesc" style="width:100%" value="설명" onfocus="javascript:this.value=''"/><td>
								</tr>
								<tr>
									<td><button id="saveLocationBtn" onclick="saveLocation()">저장하기</button></td>
								</tr>
							</table>
						</div>
						</fieldset>
						<button id="" onclick="">위치 수정</button>
					</td>
				</tr>
			</table>
		</td>
		<td width="300">
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
				<% for(int i=0 ; i<deptNames.length ; i++){  %>
					<th onclick="showDept(<%= i %>)"><%= deptNames[i] %></th>
				<% } %>
				</tr>
				<tr>
					<td colspan="<%= deptNames.length %>">
					<% for(int i=0 ; i<deptIDs.length ; i++){  %>
						<div id="dept<%= i %>" style="display:<%= i==0 ? "" : "none" %>;">
						<table width="100%" border="1" cellspacing="0" cellpadding="3">
							<tr>
								<th>이름</th>
								<th>직위</th>
								<th>행선지</th>
								<th>업무</th>
								<th>시간</th>
							</tr>
							<% for(int j=0 ; j<userListArr[i].size() ; j++){ HashMap userMap = (HashMap)userListArr[i].get(j); %>
							<tr id="user<%= i + "_" + j %>" onclick="goDirection(<%= userMap.get("LATITUDE") %>, <%= userMap.get("LONGITUDE") %>, 'user<%= i + "_" + j %>', '<%= userMap.get("USER_ID") %>', '<%= userMap.get("LOGIN_ID") %>')">
								<td><%= userMap.get("LOGIN_ID") %></td>
								<td><%= userMap.get("POS") %></td>
								<td><%= isnull(userMap.get("SITE"),"&nbsp;") %></td>
								<td><%= isnull(userMap.get("TASK"),"&nbsp;") %></td>
								<td><%= isnull(userMap.get("STARTINGTIME"),"&nbsp;") %></td>
							</tr>
							<% } %>
						</table>
						</div>
					<% } %>
					</td>
				</tr>
			</table>
			<fieldset>
			<legend><button id="markPlaceToGoBtn" onclick="markPlaceToGo()">나의 행선지 저장</button></legend>
			<div id="savePlaceToGoForm" style="display:none;">
				<table border="1" cellpadding="0" cellspacing="0">
					<tr>
						<td width="50" align="right">행선지</td>
						<td width="*">
							<select id="goSiteSelect" onclick="" onchange="jsGoDirection(this)">
								<option value="">-사이트 선택-</option>
								<% for(int i=0 ; i<siteList.size() ; i++){ 
									HashMap siteMap = (HashMap)siteList.get(i); 
								%>
								<option value="<%= siteMap.get("NAME") %>,<%= siteMap.get("LATITUDE") %>,<%= siteMap.get("LONGITUDE") %>" ><%= siteMap.get("NAME") %></option>
								<% } %>
							</select>
							<button id="selPlaceToGoBtn" onclick="selPlaceToGo()">지도에서 선택</button>
							<input class="INPUTTEXT" type="text" id="goSite" name="goSite" style="width:100%"/>
						</td>
					</tr>
					<tr>
						<td align="right">업무</td>
						<td><input class="INPUTTEXT" type="text" id="goTask" name="goTask" style="width:100%"/></td>
					</tr>
					<tr>
						<td align="right">시간</td>
						<td><input class="INPUTTEXT" type="text" id="goTime" name="goTime" style="width:100%"/></td>
					</tr>
					<tr>
						<td colspan="2"><button id="saveLocationBtn" onclick="savePlaceToGo()">저장하기</button></td>
					</tr>
				</table>
			</div>
			</fieldset>
		</td>
		<td width="*">
			<!-- Daum MAP Area S -->
			<div id="map" style="width:800px;height:600px;border:solid 1px;"></div>
			<!-- <div id="r" style="font-size:12px;width:590px;height:30px;border:solid 1px black;padding:5px;"></div> -->
			<!-- Daum MAP Area End -->
		</td>
	</tr>
</table>
<input type="hidden" name="latitude" value=""/>
<input type="hidden" name="longitude" value=""/>
<input type="hidden" name="level" value=""/>
<input type="hidden" name="mode" value=""/>
<input type="hidden" name="dscpt" value=""/>
<input type="text" id="selectUserID" name="selectUserID" value=""/>
</form>
<a href="daum.jsp">초기화면</a> 

<pre>
ToDo 1. 행선지 위치 선정시 Ajax활용한 리스트 보여주기.
ToDo 2. javascript 다듬기, 함수명, args, 캡슐화.
</pre>
<iframe name="mapaction" style="width:100%;height:100"></iframe>
</body>
</html>
<script type="text/javascript">
//<![CDATA[

//var iw = new DInfoWindow("http://local.daum.net/localn/include/cms_map.html", {width:270, height:160});
//var m = new DMark(new DLatLng(37.48304622452023, 126.93792471364008), {infowindow:iw, draggable:true});
//map.addOverlay(m); // mark Overlay
//var icon = new DIcon("http://localimg.daum-img.net/localimages/07/2008/map/i_mks_b1.gif", new DSize(13, 16));
//map.addOverlay(new DMark(new DLatLng(37.48304622452023, 126.93792471364008), {mark:icon})); // mark Overlay
/*
DEvent.addListener(map, "mousemove", function(e) {
	var coordPoint = new DPoint(e.x, e.y);
	var r = document.getElementById("r");
	
	// Coord -> Pixel
	var pixByCoord = map.getPixByCoordPoint(coordPoint);
	
	// Pixel -> Coord
	var coordByPix = map.getCoordByPixPoint(pixByCoord);
	
	r.innerHTML = "경도 = " + coordByPix.x + ", 위도 = " + coordByPix.y;
});
DEvent.addListener(map, "click", function(e) {
	var coordPoint = new DPoint(e.x, e.y);
	saveLocation(coordPoint.x, coordPoint.y);
   });
*/
//]]>
</script>
