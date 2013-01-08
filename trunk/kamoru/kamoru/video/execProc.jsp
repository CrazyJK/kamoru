<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*, kamoru.app.video.av.*, kamoru.frmwk.util.ServletUtils" %>
<%
request.setCharacterEncoding("UTF-8");
//parameter
String selectedOpus = ServletUtils.getParameter(request, "selectedOpus");

List<AVOpus> list = (List<AVOpus>)session.getAttribute("avlist");

for(AVOpus av : list) {
	if(selectedOpus.equals(av.getOpus()));
		av.playVideo();
}
%>