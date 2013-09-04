<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.*, kamoru.util.*"  %>
<%@ page errorPage="/common/error.jsp"%>
<%@ include file="class.jspf" %>
<%
request.setCharacterEncoding("UTF-8");

String msg = "";
boolean actionResult = false;

String action 	 = RequestUtil.getParameter(request, "action");
String selectTag = RequestUtil.getParameter(request, "selectTag");
String newTag 	 = RequestUtil.getParameter(request, "newTag", "");
String title 	 = RequestUtil.getParameter(request, "title");
String content 	 = RequestUtil.getParameter(request, "content");
String bbsid 	 = RequestUtil.getParameter(request, "bbsid");
String writer 	 = RequestUtil.getParameter(request, "writer",	"kamoru");

// 신규 태그에 대한 처리
if(newTag.length() > 0){
	// 구분자 ,(컴마) 로 단일화
	newTag = newTag.replaceAll(";", ",");
	// ,(컴마) 사이의 공백 제거
	String[] newTags = newTag.split(",");
	newTag = "";
	for(int i=0 ; i<newTags.length ; i++){
		newTag += newTags[i].trim() + ",";
	}
}
logger.debug("[action." + action + "] selectTag [" + selectTag + "]");
logger.debug("[action." + action + "] newTag    [" + newTag + "]");
logger.debug("[action." + action + "] title     [" + title + "]");
//	logger.debug("[action." + action + "] content   [" + content.substring(0, 4) + "]");
logger.debug("[action." + action + "] bbsid     [" + bbsid + "]");


if(action == null)
{
	return;
}
else if(action.equals("write"))
{
	Vector vParams = new Vector();
	vParams.add(selectTag.trim());
	vParams.add(newTag.trim());
	vParams.add(title.trim());
	vParams.add(content.trim());
	vParams.add(writer.trim());
	
	Vector vDirections = new Vector();
	vDirections.add(new Integer(1));
	vDirections.add(new Integer(1));
	vDirections.add(new Integer(1));
	vDirections.add(new Integer(1));
	vDirections.add(new Integer(1));

	executeProcedure("SP_INSERT_BBS", vParams, vDirections);
	msg = "작성 완료";
	actionResult = true;
}
else if(action.equals("edit"))
{
	Vector vParams = new Vector();
	vParams.add(selectTag.trim());
	vParams.add(newTag.trim());
	vParams.add(title.trim());
	vParams.add(content.trim());
	vParams.add(writer.trim());
	vParams.add(bbsid.trim());

	Vector vDirections = new Vector();
	vDirections.add(new Integer(1));
	vDirections.add(new Integer(1));
	vDirections.add(new Integer(1));
	vDirections.add(new Integer(1));
	vDirections.add(new Integer(1));
	vDirections.add(new Integer(1));

	executeProcedure("SP_UPDATE_BBS", vParams, vDirections);
	msg = "수정 완료";
	actionResult = true;
}
else if(action.equals("delete"))
{
	Vector vParams = new Vector();
	vParams.add(bbsid.trim());

	Vector vDirections = new Vector();
	vDirections.add(new Integer(1));

	executeProcedure("SP_DELETE_BBS", vParams, vDirections);
	msg = "삭제 완료";
	actionResult = true;
}
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><%= action %> action page</title>
<link rel="stylesheet" type="text/css" href="/bbs/bbs.css">
<script type="text/javascript" src="/js/kamoru.js"></script>
<script type="text/javascript" src="/bbs/bbs.js"></script>
<script>
function windowOnload(){
	if(<%= actionResult %>){
//		alert(parent.opener.location.href);
//		alert(parent.opener.opener.location.href);
		if(parent.opener){
			parent.opener.location.reload();
			if('<%= action %>' == 'delete'){
				//parent.opener.window.close();
			} 
		}
		try{
			if(parent.opener.opener){
							parent.opener.opener.location.reload();
			}
		}catch(e){}
		parent.window.close();
	}
}
</script>
</head>
<body onload="windowOnload()" leftmargin="0" topmargin="0" marginheight="0" marginwidth="0">
<div style="font:normal 11pt 맑은 고딕">	<%= msg %></div>
</body>
</html>