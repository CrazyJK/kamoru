var deptids = "000010312,000010313,000010314,500004182";

var dmap = new Object();
var initLat =  37.51647829244714;
var initLng = 126.97448293879174;
var initLvl = 8;	
var initMapType = "TYPE_SKYVIEW";

var memberArray = new Array();

// 문자열 상수
var LIST_SHOW = "목록 펴기";
var LIST_HIDE = "목록 접기";
var MEMBER_SHOW = "모두 보이기";
var MEMBER_HIDDEN = "모두 숨기기";
var MEMBER_MODIFY_ON = "수정 모드";
var MEMBER_MODIFY_OFF = "보기 모드";
var MODIFY = true;
var VIEW = false;

$(document).ready(function(){
	initDMap();

	$.getJSON('ajax/memberinfo.jsp?reqtype=M,C&deptids=' + deptids, function(data){
		initMemberObject(data);
		addListener();
		$("#customerListToggleBtn").click();
		//$("#memberListToggleBtn").click();
	});
});

function addListener(){
	$("#customerListToggleBtn").html(LIST_HIDE);
	$("#customerListToggleBtn").toggle(
		function(){
			$("#customerDiv").hide('normal');
			$(this).html(LIST_SHOW);
		},
		function(){
			$("#customerDiv").show('normal');
			$(this).html(LIST_HIDE);
		}
	);
	$("#memberListToggleBtn").html(LIST_HIDE);
	$("#memberListToggleBtn").toggle(
		function(){
			$("#memberDiv").hide('normal');
			$(this).html(LIST_SHOW);
		},
		function(){
			$("#memberDiv").show('normal');
			$(this).html(LIST_HIDE);
		}
	);
	$("#memberAllToggleBtn").html(MEMBER_SHOW);
	$("#memberAllToggleBtn").toggle(
		function() {
			$(this).html(MEMBER_HIDDEN);
			for(var i=0 ; i<memberArray.length ; i++) {
				if(memberArray[i].type == 'M')
					memberArray[i].view(true);
			}
		},
		function() {
			$(this).html(MEMBER_SHOW);
			for(var i=0 ; i<memberArray.length ; i++) {
				if(memberArray[i].type == 'M')
					memberArray[i].view(false);
			}
		}
	);	
	$("#memberModifyToggleBtn").html(MEMBER_MODIFY_OFF);
	$("#memberModifyToggleBtn").toggle(
			function() {
				$(this).html(MEMBER_MODIFY_ON);
				setMemberMode(MODIFY);
			},
			function() {
				$(this).html(MEMBER_MODIFY_OFF);
				setMemberMode(VIEW);
			}
		);
	$(".memberSpan").bind("click", function(){
		if(memberArray[$(this).attr("idx")].isShow){ 
			memberArray[$(this).attr("idx")].view(false); // HIDE
		}else{
			memberArray[$(this).attr("idx")].viewAndGo(true); // SHOW
		}
	});
	$("#selectCustomer").bind("change",function(e){
		//	alert($(this).val());
		goLoc($(this).val());
	});
	
	

}

function setMemberMode(bool) {
	for(var i=0 ; i<memberArray.length ; i++) {
		memberArray[i].setDragmode(bool);
	}
}

function initMemberObject(data){
	var i = 0;
	var parentDOM = $("<SELECT/>").attr("size",10).attr("ID","selectCustomer").attr("multiple", true);
	for(; i<data.customers.length ; i++) {
		memberArray[i] = new memberObj(i, data.customers[i].MEMBERID, data.customers[i].TYPE, data.customers[i].NAME, '',
				'', data.customers[i].CUSTOMID, data.customers[i].LATITUDE, data.customers[i].LONGITUDE, data.customers[i].STATUS,
				data.customers[i].SITE, data.customers[i].TASK, data.customers[i].ADDRESS, data.customers[i].STARTTIME, 
				data.customers[i].ARRIVALTIME, data.customers[i].DSCPT);

		$(parentDOM).append(memberArray[i].html());
	}
	$("#customerDiv").append(parentDOM);

	parentDOM = $("<TABLE/>").attr("ID","selectMember");
	var deptArr = deptids.split(',');
	var tdArr = new Array();
	for(var j=0 ; j<deptArr.length ; j++) {
		tdArr[j] = $("<TD/>").attr("id", deptArr[j]);
	}
	for(; i<data.members.length ; i++) {
		memberArray[i] = new memberObj(i, data.members[i].MEMBERID, data.members[i].TYPE, data.members[i].NAME, data.members[i].DEPT_ID,
				data.members[i].DEPT_NAME, data.members[i].CUSTOMID, data.members[i].LATITUDE, data.members[i].LONGITUDE, data.members[i].STATUS,
				data.members[i].SITE, data.members[i].TASK, data.members[i].ADDRESS, data.members[i].STARTTIME, 
				data.members[i].ARRIVALTIME, data.members[i].DSCPT);

		for(var j=0 ; j<deptArr.length ; j++) {
			if(tdArr[j].attr("id") == memberArray[i].dept_id){
				tdArr[j].append($(memberArray[i].html()).addClass("memberDeSelected")).append($("<BR/>"));
			}
		}
	}
	var trDOM = $("<TR/>").attr("valign", "top");
	for(var j=0 ; j<deptArr.length ; j++) {
		$(trDOM).append(tdArr[j]);
	}
	$("#memberDiv").append(parentDOM.append(trDOM));
//	alert($("#memberDiv").html());
}
function methodCall(str){
	alert(str);
}


/**
 *      이름 : memberObj
 * <br/>설명 : 임직원의 행선지 관련 정보를 보여주고 갱신
 * <br/>외부 제공 기능 : 
 * <br/>	지도에 보기, 보이면서 위치로 이동 : view(true), viewAndGo(true), show()
 * <br/>	지도에서 숨기기 : view(false), viewAndGo(false), hide()
 * <br/>	html 표현 : html()
 * <br/>	드래그 가능 상태 변경 : setDragmode(true/false)
 * <br/>내부 메서드
 * <br/>	위치 변경시 상태 업데이트 : memberUpdate
 * <br/>	
 * */
function memberObj(idx, memberid, type, name, dept_id, dept_name, customid, latitude, 
	       longitude, status, site, task, address, starttime, arrivaltime, dscpt) {
	
	var localThis = this;
	
	this.idx = idx;
	this.memberid = memberid;
	this.type = type;
	this.name = name;
	this.dept_id = dept_id;
	this.dept_name = dept_name;
	this.customid = customid;
	this.latitude = latitude;
	this.longitude = longitude;
	this.status = status;
	this.site = site;
	this.task = task;
	this.address = address;
	this.starttime = starttime;
	this.arrivaltime = arrivaltime;
	this.dscpt = dscpt;
	
	this.dLatLng = null;
	this.dText = null;
	this.viewDMark = null;
	this.draggableDMark = null;
	
	this.isShow = false;
	this.dragmode = false;
/*
	this.toString = function(){
		vat str = 
			"=" + this. + ";" +
			"=" + this. + ";" +
			"=" + this. + ";" +
			"=" + this. + ";" +
			"=" + this. + ";" +
			"=" + this. + ";" +
			"=" + this. + ";" +
			"=" + this. + ";" +
			"=" + this. + ";" +
	};
	*/
	// 지도에 보기, 보이면서 위치로 이동 : view(true), viewAndGo(true), show()
	// 지도에서 숨기기 : view(false), viewAndGo(false), hide()
	this.viewAndGo = function(bool) {
		this.view(bool);
		if(bool)
			this.goLoc();
	};
	this.view = function(bool) {
		if(bool)
			this.show();
		else
			this.hide();
	};
	this.show = function() {
		$("#" + this.memberid).removeClass().addClass("memberSelected");
		this.isShow = true;
		if(this.dragmode){
			dmap.addOverlay(this.getDraggableDMark());
		}else{//View
			dmap.addOverlay(this.getViewDMark());
		}
	};
	this.hide = function() {
		$("#" + this.memberid).removeClass().addClass("memberDeSelected");
		this.isShow = false;
		if(this.dragmode){
			dmap.removeOverlay(this.getDraggableDMark());
		}else{//View
			dmap.removeOverlay(this.getViewDMark());
		}
	};
	// html 표현 : html()
	this.html = function() {
		if(this.type == 'M')
			return '<span class="memberSpan" idx="'+this.idx+'" id="'+this.memberid+'">'+this.name+' - '+this.getStatus()+'</span>';
		else
			return '<OPTION value="'+this.memberid+'">'+this.name+'</OPTION>';
	};
	// 드래그 가능 상태 변경 : setDragmode(true/false)
	this.setDragmode = function (bool) {
		this.dragmode = bool;
		if(this.isShow){
			if(bool){
				dmap.removeOverlay(this.getViewDMark());
				dmap.addOverlay(this.getDraggableDMark());
			}else{//view
				dmap.removeOverlay(this.getDraggableDMark());
				dmap.addOverlay(this.getViewDMark());
			}
		}
	};
	
	// 위치 변경시 상태 업데이트 : memberUpdate
	this.memberUpdate = function() {
	//	$("#modifyDiv").show('normal');
	//	$("#regName").html(this.name);
	//	$("#regAddress").val(this.getPoint().x + ":" + this.getPoint().y);
	};
	this.memberSave = function(){
		this.latitude = this.draggableDMark.getPoint().x;
		this.longitude = this.draggableDMark.getPoint().y;
		this.status = $("#regStatus").val();
		this.site = $("#regSite").val();
		this.task = $("#regTask").val();
		this.address = $("#regAddress").val();
		this.starttime = $("#regStart").val();
		this.arrivaltime = $("#regArrival").val();
		// Update DB
		
		alert(this.toString());
	};
	// 내부 로직 메서드
	this.goLoc = function() {
		var refMapLevel = 6;
		var currMapLevel = dmap.getLevel();
		if(currMapLevel < refMapLevel)
			dmap.setCenter(dmap.getCenter(), refMapLevel);
//		$("#debug").append('goLoc=>' + this.getDLatLng()).append("\n");
//		dmap.panTo(this.getDLatLng());
		dmap.setCenter(this.getDLatLng(), currMapLevel);
//		$("#debug").append('panTo').append("\n");
	};

	this.getDLatLng = function() {
		return new DLatLng(this.latitude, this.longitude); 
	};
	this.makeDText = function() {
		this.dText = new DText(this.getDLatLng(), this.name);
		$("#debug").append('makeDText').append("\n");
	};
	this.getViewDMark = function(isUpdate) {
		if(this.viewDMark == null || isUpdate) {
			var contents = this.dept_name + '.' + this.name + ' : ' + this.getStatus() + '<br/>' 
				+ '사이트:' + nvl(this.site) + '<br/>' 
				+ '업무:' + nvl(this.task) + '<br/>' 
				+ '위치:' + nvl(this.address) + '<br/>' 
				+ '출발시간:' + nvl(this.starttime) + '<br/>' 
				+ '복귀시간:' + nvl(this.arrivaltime) + '<br/>' 
				+ '비고:' + nvl(this.dscpt) + ";dragmode=" + this.dragmode;
	
			var dscpt = new DInfoWindow(contents, {width:150, height:120, removable:false});
			this.viewDMark = new DMark(this.getDLatLng(), {infowindow:dscpt, label:this.name, draggable:false, infowindow_mouseover:true});
//			$("#debug").append('make ViewDMark :' + this.name).append("\n");
		}
		return this.viewDMark;
	};
	this.getDraggableDMark = function(isUpdate) {
		if(this.draggableDMark == null || isUpdate) {
			var tbl = $("<table cellpadding=0 cellspacing=0 />");
			$("<tr/>").append($("<td colspan=2/>").html("<strong>"+this.dept_name+"."+this.name + " 행선지 정보 수정하기"+"</strong")).appendTo(tbl);
			$("<tr/>")
				.append($("<td/>").html("상태"))
				.append($("<td/>")
						.append($("<input type=radio name=regStatus id=reg"+this.memberid+"statusD class=regInput "+(this.status=='D'?'checked="checked"':'')+"><label for=reg"+this.memberid+"statusD>본사</label>"))
						.append($("<input type=radio name=regStatus id=reg"+this.memberid+"statusO class=regInput "+(this.status=='O'?'checked="checked"':'')+"><label for=reg"+this.memberid+"statusO>외근</label>"))
						.append($("<input type=radio name=regStatus id=reg"+this.memberid+"statusB class=regInput "+(this.status=='B'?'checked="checked"':'')+"><label for=reg"+this.memberid+"statusB>출장</label>"))
						).appendTo(tbl);
			$("<tr/>").append($("<td/>").text("행선지")).append($("<td/>").append($("<input class=regInput type=text id=reg"+this.memberid+"site value=\""+nvl(this.site)+"\"/>"))).appendTo(tbl);
			$("<tr/>").append($("<td/>").text("업무")).append($("<td/>").append($("<input class=regInput type=text id=reg"+this.memberid+"task value=\""+nvl(this.task)+"\"/>"))).appendTo(tbl);
			$("<tr/>").append($("<td/>").text("위치")).append($("<td/>").append($("<input class=regInput type=text id=reg"+this.memberid+"address value=\""+nvl(this.address)+"\"/>"))).appendTo(tbl);
			$("<tr/>").append($("<td/>").text("출발시간")).append($("<td/>").append($("<input class=regInput type=text id=reg"+this.memberid+"starttime value=\""+nvl(this.starttime)+"\"/>"))).appendTo(tbl);
			$("<tr/>").append($("<td/>").text("복귀시간")).append($("<td/>").append($("<input class=regInput type=text id=reg"+this.memberid+"arrivaltime value=\""+nvl(this.arrivaltime)+"\"/>"))).appendTo(tbl);
			$("<tr/>").append($("<td/>").text("비고")).append($("<td/>").append($("<input class=regInput type=text id=reg"+this.memberid+"dscpt value=\""+nvl(this.dscpt)+"\"/>"))).appendTo(tbl);
			$("<tr/>").append($("<td/>").text("")).append($("<td align=right/>").append($("<input type=button id=reg"+this.memberid+"btn value=\"저장\" />"))).appendTo(tbl);
			
			$("#reg"+this.memberid+"btn").bind("click", function(){
				
			});
			
			var contents = '<table>'+tbl.html()+'</table>';
			var dscpt = new DInfoWindow(contents, {width:210, height:250, removable:false});
			var icon = new DIcon("/kamoru/images/map/s.png", new DSize(32, 32));
			this.draggableDMark = new DMark(this.getDLatLng(), {infowindow:dscpt, label:this.name, draggable:true, infowindow_mouseover:false, mark:icon});
			DEvent.addListener(this.draggableDMark, "enddrag", this.memberUpdate);
//			$("#debug").append('make DraggableDMark :' + this.name).append("\n");
		}
		return this.draggableDMark;
	};

	this.getStatus = function() {
		switch(status){
			case 'O':
				return '외근';
			case 'B':
				return '출장';
			case 'D':
				return '내근';
			default :
				return '실종';
		}		
	};
/*	this.setDLatLng = function(lat, lng) {
		this.dLatLng.setPoint(lat, lng);
		this.isUpdate = true;
		$("#debug").append('setDLatLng' + lat + "-" + lng).append("\n");
	}; */
}

function goLoc(idx){
	var mid = new String(idx).split(",");
	var refMapLevel = 6;
	var currMapLevel = dmap.getLevel();
	if(mid.length > 0)
		dmap.clearOverlay();
	for(var i=0 ; i<memberArray.length ; i++) {
		for(var j=0 ; j<mid.length ; j++) {
			if(memberArray[i].memberid == mid[j]) {
				if(currMapLevel < refMapLevel)
					dmap.setCenter(dmap.getCenter(), refMapLevel);
				if(memberArray[i].dMark != null)
					dmap.addOverlay(memberArray[i].dMark);
				dmap.addOverlay(memberArray[i].dText);
				dmap.panTo(memberArray[i].dLatLng);
//				break;
			}
		}
	}
}

function initDMap(){
	dmap = new DMap("map", {point:new DLatLng(initLat, initLng), level:initLvl, map_type:initMapType, autosize:true, offsetX:0, offsetY:100}); 

	var indexMapControl = new DIndexMapControl(true);
	var zoomControl = new DZoomControl();
	var mapTypeControl = new DMapTypeControl();

	dmap.addControl(indexMapControl);
	dmap.addControl(zoomControl);
	dmap.addControl(mapTypeControl);
}

function dataSearch(){
	var schWord = $("#dataInput").val();
	if(schWord == '') return;
	if(schWord != "") $("#dataSearchResult").empty();

	for(var i=0 ; i<memberArray.length ; i++) {
		if(memberArray[i].name.indexOf(schWord) > -1) {
			$("#dataSearchResult").append("&nbsp;");
			$("#dataSearchResult").append($("<a/>").attr("href","javascript:goLoc('" + memberArray[i].memberid + "')").html("*." + memberArray[i].name));
		}
	}
}

function nvl(str, disp){
	return str == null ? (disp ? disp : "&nbsp;") : str;
}