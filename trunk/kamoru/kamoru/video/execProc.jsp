<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*, kamoru.app.video.av.*, kamoru.frmwk.util.ServletUtils" %>
<%
request.setCharacterEncoding("UTF-8");
//parameter
String selectedOpus = ServletUtils.getParameter(request, "selectedOpus");
String selectedMode = ServletUtils.getParameter(request, "selectedMode");

AVCollectionCtrl ctrl = new AVCollectionCtrl();
List<AVOpus> list = (List<AVOpus>)session.getAttribute("avlist");

if("subtitles".equals(selectedMode)) {
	for(AVOpus av : list) {
		if(selectedOpus.equals(av.getOpus()))
			av.editSubtitles();
	}
} else if("play".equals(selectedMode)) {
	for(AVOpus av : list) {
		if(selectedOpus.equals(av.getOpus()))
			av.playVideo();
	}
} else if("random".equals(selectedMode)) {
	Map<String, String> history = (Map<String, String>)session.getAttribute("randomHistory");
	Random oRandom = new Random();
    int index = oRandom.nextInt(list.size());
    Set<String> keySet = history.keySet();
	int loopCount = 0;    
    while(history.containsKey(String.valueOf(index))){
    	if(loopCount++ > 100) {
    		// 100번 뽑아도 안나온다.
    	    // 다 봤음.. 뭐하지...
    	    //System.out.println("All video played");
    		out.println("<script>alert('100번 뽑아도 안나온다. 다음 기회에');</script>");
    		return;
    	}
    	index = oRandom.nextInt(list.size());
    }
	AVOpus av = list.get(index);
    //System.out.println("selected Opus : " + av.getOpus());
	av.playVideo();
	out.println("<script>parent.fnOpusFocus('" + av.getOpus() + "');</script>");
	history.put(String.valueOf(index), "play");
	//System.out.println(history.size() + " " + history.toString());
	session.setAttribute("randomHistory", history);
} else if("delete".equals(selectedMode)) {
	for(AVOpus av : list) {
		if(selectedOpus.equals(av.getOpus())) {
			av.deleteOpus();
			ctrl.avData.remove(selectedOpus);
		}
	}
	out.println("<script>parent.opener.document.forms[0].submit();</script>");
}
%>