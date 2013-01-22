<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.*, kamoru.app.video.av.*, kamoru.frmwk.util.ServletUtils" %>
<%
String selectedOpus = ServletUtils.getParameter(request, "opus");
String overViewTxt  = ServletUtils.getParameter(request, "overViewTxt");

List<AVOpus> list = (List<AVOpus>)session.getAttribute("avlist");

for(AVOpus av : list) {
	if(selectedOpus.equals(av.getOpus())) {
		// overview save
		if(overViewTxt != null) {
			av.saveOverViewTxt(overViewTxt);	
		}
	}
}
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Overview SAVE - AV World</title>
<link rel="stylesheet" href="/kamoru/video/video.css" />
<style type="text/css">
.overviewTxt {width:100%; height:275px;}
</style>
<!--[if lt IE 9]>
<script src="http://html5shiv.googlecode.com/svn/trunk/html5.js"></script>
<![endif]-->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.8.3/jquery.min.js"></script>
<script type="text/javascript">
$(document).ready(function(){
	//var reload = parent.opener.document.getElementById("reloadData");
	//reload = "on";
	parent.opener.document.forms[0].submit();
	self.close();
});
</script>
</head>
<body>

</body>
</html>