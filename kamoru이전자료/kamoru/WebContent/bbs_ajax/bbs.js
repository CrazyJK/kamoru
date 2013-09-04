function preTagselect(selectTag){
	var tags = selectTag.split(",");
	for(var i=0 ; i<tags.length-1 ; i++){
		tagSelect(document.getElementById(tags[i]),'');
	}
}
function operSelect(action){
	if(action == 'SUBMIT')
		searchSubmit();
}
function tagSelect(thisTag, action){
	// ToDo 선택시 굵은글씨는 어떻게 하는가?
	selectTag = document.getElementById("selectTag");
	if(thisTag.style.color == ""){
		thisTag.style.color = "blue";
		selectTag.value += thisTag.id + ",";
	}else{
		thisTag.style.color = "";
		var idx = selectTag.value.indexOf(thisTag.id);
		temp1 = selectTag.value.substring(0,idx);
		temp2 = selectTag.value.substring(idx + thisTag.id.length + 1, selectTag.value.length);
		selectTag.value = temp1 + temp2;
	}
	if(action == 'SUBMIT')
		searchSubmit();
}
function searchSubmit(){
	selectTag = document.getElementById("selectTag");
	searchWord = document.getElementById("searchWord");
	if(selectTag.value != "" || searchWord.value != ""){
		bbsid = document.getElementById("bbsid");
		bbsid.value = "";
		document.forms[0].submit();
	}
}
function viewContent(selectedbbsid){
	var bbsid = document.getElementById("bbsid");
	//alert(typeof(bbsid));
	if(typeof(bbsid) == 'object')
		bbsid.value = selectedbbsid;
	document.forms[0].submit();
}

// WRITE ########################################################################################
function writeTmpltOpen(){
	windowopen("/bbs/writetmplt.jsp", "", 800, 600);
}
function writeAction(){
	title = document.getElementById("title");
	selectTag = document.getElementById("selectTag");
	newTag = document.getElementById("newTag");
	
	if(checkTitleLength(title) && title.value.length != 0 && (selectTag.value.length != 0 || newTag.value.length != 0) ){
		document.forms[0].submit();
	}else{
		alert("빈 값이 있음.");
	}
}


//MODIFY ########################################################################################
function modifyAction(cmd, bbsid){
	//alert(cmd);
	if(cmd == 'EDIT'){
		windowopen('/bbs/writetmplt.jsp?bbsid='+bbsid,'',800,600);
	}else if(cmd == 'DELETE'){
		if(confirm('Are you want to delete?'))
			windowopen('/bbs/action.jsp?action=delete&bbsid='+bbsid,'action',80,60);
	}
}

function checkTitleLength(obj){
	return checkLength(obj, 100);
}
function checkLength(obj, limit){
	var byte = getLength(obj.value);
	var han  = getLength(obj.value, 'han');
	
	try{
		//debug로 현재 글자수를 볼때 사용.
		document.forms[0].titlelength_byte.value = byte;
		document.forms[0].titlelength_han.value = han;
	}catch(e){}
	if(byte > limit){
		if(confirm("제한 글자수("+limit+") 초과. 현재글자수 "+byte+". 초과된 글자를 자를까요?")){
			obj.value = obj.value.substring(0, han-1);
		}
		return false;
	}else{
		return true;
	}
}