<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="./common.jsp" %>
<%@ page import="java.util.*" %>
<%
	String uid = request.getParameter("id");

	String todoCnt = "";
	String runCnt  = "";
	try
	{
		ArrayList list = null;
		HashMap map    = null;
		
		list = executeQuery(3101, uid, requestURI);
		map = (HashMap)list.get(0);
		todoCnt = (String)map.get("CNT");
		
		list = executeQuery(3102, uid, requestURI);
		map = (HashMap)list.get(0);
		runCnt = (String)map.get("CNT");
		
		debug("todo:" + todoCnt + " run:" + runCnt);
	}
	catch(Exception e)
	{
		e.printStackTrace();
	}
%>
[{
	TODOCNT : <%=todoCnt %>,
	RUNCNT  : <%=runCnt %>
}]