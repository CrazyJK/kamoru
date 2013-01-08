<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*, java.io.*, kamoru.app.video.av.*, kamoru.frmwk.util.ServletUtils" %>
<%
String selectedOpus = ServletUtils.getParameter(request, "opus");

List<AVOpus> list = (List<AVOpus>)session.getAttribute("avlist");
for(AVOpus av : list) {
	if(selectedOpus.equals(av.getOpus())) {
		av.viewImage(response);
	}
}
%>