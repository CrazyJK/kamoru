<%@ page language="java" contentType="text/plain; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.*, com.hs.frmwk.db.*, java.sql.*" %>
<%@ page import="net.sf.json.*" %>
<%@ include file="../method.jspf" %>
<%-- JSON lib reference
	http://blog.naver.com/PostView.nhn?blogId=locusty&logNo=90072462606&redirect=Dlog&widgetTypeCall=true
	
	memberinfo.jsp?deptid=000010314&reqtype=M,C
--%>
<%
String reqtype = request.getParameter("reqtype");
String deptids = request.getParameter("deptids");
String memberid = request.getParameter("memberid");
//System.out.println("memberinfo.jsp?reqtype="+reqtype+"&deptids="+deptids);
if(reqtype == null) return;

Map map = new HashMap();
String[] reqtypes = reqtype.split(",");
for(int i=0 ; i<reqtypes.length ; i++) {
	if(reqtypes[i].equals("M")) {
		if(deptids != null) {
			map.put("members", JSONArray.fromObject(getMemberListByDeptID(deptids)));
		}
		if(memberid != null) {
			map.put("members", JSONArray.fromObject(getMemberByMemberID(memberid)));
		}
	}else if(reqtypes[i].equals("C")) {
		map.put("customers", JSONArray.fromObject(getCustomerListALL()));
	}
}
JSONObject jsonObject = JSONObject.fromObject(map);
out.println(jsonObject);
%>