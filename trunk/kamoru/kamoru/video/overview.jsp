<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.*, kamoru.app.video.av.*, kamoru.frmwk.util.ServletUtils" %>
<%
String selectedOpus = ServletUtils.getParameter(request, "opus");

List<AVOpus> list = (List<AVOpus>)session.getAttribute("avlist");

String overviewTxt = new String();
for(AVOpus av : list) {
	if(selectedOpus.equals(av.getOpus())) {
		overviewTxt = av.getOverviewTxt();
	}
}

%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Overview - AV World</title>
<link rel="stylesheet" href="/kamoru/video/video.css" />
<style type="text/css">
.overviewTxt {width:100%; height:275px;}
</style>
<!--[if lt IE 9]>
<script src="http://html5shiv.googlecode.com/svn/trunk/html5.js"></script>
<![endif]-->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.8.3/jquery.min.js"></script>
<script type="text/javascript">
function overviewSave()
{
	var frm = document.forms['overviewFrm'];
	frm.submit();
}
</script>
</head>
<body>
<form method="post" name="overviewFrm" action="overviewSave.jsp">
<input type="hidden" name="opus" value="<%=selectedOpus %>">
<textarea class="overviewTxt" name="overViewTxt"><%=overviewTxt %></textarea>
</form>
<div tabindex="1" style="position:absolute; right:0px; top:0px; margin:10px 5px 0px 0px; background-color:lightblue; cursor:pointer;" onclick="overviewSave()">SAVE</div>
</body>
</html>