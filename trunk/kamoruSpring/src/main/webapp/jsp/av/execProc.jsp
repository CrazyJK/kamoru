<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*, kamoru.app.spring.av.*, kamoru.frmwk.util.ServletUtils" %>
<%
//parameter
String selectedOpus = ServletUtils.getParameter(request, "selectedOpus");
String selectedMode = ServletUtils.getParameter(request, "selectedMode");

AVCollectionCtrl ctrl 		= new AVCollectionCtrl();
List<AVOpus> list 			= (List<AVOpus>)session.getAttribute("avlist");
Map<String, String> history = (Map<String, String>)session.getAttribute("playHistory");

if("subtitles".equals(selectedMode)) {
	for(AVOpus av : list) {
		if(selectedOpus.equals(av.getOpus()))
			av.editSubtitles();
	}
} 
else if("play".equals(selectedMode)) {
	for(AVOpus av : list) {
		if(selectedOpus.equals(av.getOpus())) {
			av.playVideo();
			history.put(av.getOpus(), "play");
		}
	}
} 
else if("random".equals(selectedMode)) {
	int listSize = list.size();
	int loopCount = 0;    
	Random random = new Random();
	AVOpus av = list.get(random.nextInt(listSize));

    while(history.containsKey(av.getOpus())) {
    	if(loopCount++ > 100) {
    		out.println("<script>alert('100번 뽑아도 안나온다. 다음 기회에');</script>");
    		return;
    	}
    	av = list.get(random.nextInt(listSize));
    }
	av.playVideo();
	history.put(av.getOpus(), "play");
	out.println("<script>parent.fnOpusFocus('" + av.getOpus() + "');</script>");
} 
else if("delete".equals(selectedMode)) {
	for(AVOpus av : list) {
		if(selectedOpus.equals(av.getOpus())) {
			av.deleteOpus();
			ctrl.avData.remove(selectedOpus);
		}
	}
	out.println("<script>parent.document.forms[0].submit();</script>");
}
session.setAttribute("playHistory", history);
System.out.println(history.toString());
%>