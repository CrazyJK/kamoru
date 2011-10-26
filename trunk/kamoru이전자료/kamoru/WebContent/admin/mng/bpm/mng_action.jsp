<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"
    import="java.util.*,java.io.*,
    		com.hs.frmwk.db.*,
    		com.hs.frmwk.xml.dom.*, 
    		com.hs.bf.web.beans.*, 
    		com.hs.frmwk.web.locale.*, 
    		com.hs.frmwk.web.resource.*,
    		com.hs.bf.web.xmlrs.*"%>
  
<% 
long startedTime = 0;
startedTime = System.currentTimeMillis();
%>   

<%-- ---------------------------- bizflow session check ---------------------------- --%>
<jsp:useBean id="hwSessionInfo"     class="com.hs.bf.web.beans.HWSessionInfo"           scope="session"/>
<jsp:useBean id="hwSessionFactory"  class="com.hs.bf.web.beans.HWSessionFactory"        scope="application"/>
<jsp:useBean id="resource" 			class="com.hs.bf.web.xslt.resource.ResourceBag" 	scope="application"/>
<%
String selectFun 				= request.getParameter("selectFun") == null ? "" 					:  request.getParameter("selectFun");
String startdtime 				= request.getParameter("startdtime") == null ? "" 					: request.getParameter("startdtime");
String cmpltdtime 			= request.getParameter("cmpltdtime") == null ? "" 				: request.getParameter("cmpltdtime");
String inputID 					= request.getParameter("inputID") == null ? "" 						: request.getParameter("inputID");
String forceremove 			= request.getParameter("forceremove") == null ? "false" 		: request.getParameter("forceremove");
String isarchive 					= request.getParameter("isarchive") == null ? "true" 				: request.getParameter("isarchive");
String act 							= request.getParameter("act") == null ? "view" 						: request.getParameter("act");
String customSQLCheck 	= request.getParameter("customSQLCheck") == null ? "view" : request.getParameter("customSQLCheck");
String customSQL 			= request.getParameter("customSQL") == null ? "view" 			: request.getParameter("customSQL");

	// 사이트 별로 이름 확인.
	String bwadkdbname = "bwadk";


	String[] procState = request.getParameterValues("procState");

	// 메서드에 맞게 쿼리 작성
	String sql = "";
	
	
	if(customSQLCheck.equals("on"))
	{
		sql += customSQL;
	}
	else
	{
	
		sql += "SELECT * FROM procs ";
		
		// where procs.state
		if(procState == null){
			procState = new String[]{"N"};
		}
		sql += "WHERE state IN (";
		for(int i=0 ; i<procState.length ; i++){
			sql += "'" + procState[i] + "'";
			if(i != (procState.length-1)){
				sql += ",";
			}
		}
		sql += ") ";
	
		// and procs.creationdtime
	
		if(startdtime.trim().length() !=0){
			sql += " AND creationdtime >= to_date('" + startdtime + " 00:00:00', 'yyyy-mm-dd hh24:mi:ss') ";
		}
		if(cmpltdtime.trim().length() !=0){
			sql += " AND creationdtime <= to_date('" + cmpltdtime + " 23:59:59', 'yyyy-mm-dd hh24:mi:ss') ";
		}
		
		// and procs.procid
		if("P".equals(selectFun)){
			sql += " AND procid = " + inputID;		
		}else if("O".equals(selectFun)){
			sql += " AND orgprocdefid = " + inputID;
		}
		
	}

	// 프로세스 검색
	BWDBHelper helper = null;
	List procidList = new ArrayList();
	try{
	
		helper = BWDBHelperFactory.getBWDBHelper(bwadkdbname);
		helper.setPreparedSQL(sql);
		helper.executeQuery();
		while(helper.next()){
			procidList.add(
				new String[]{
					helper.getRSString("procid"), helper.getRSString("name"), helper.getRSString("state"),helper.getRSString("creationdtime")
				}
			);
		}
	}catch(Exception e){
		e.printStackTrace();
		out.println(e.getMessage());
	}finally{
		if(helper != null){ helper.close(); }
	}
	
	// BPM 프로세스 작업 delete, suspend, resume or close
	String language 	= (String) session.getAttribute("Language");
	String msgError 	= "";
	String processMsg 	= "";

	try{ 
		HWSession hwSession = hwSessionFactory.newInstance();
	    String ServerID = hwSessionInfo.get("ServerID");

		int procID 			= 0;
		boolean forceRemove = forceremove.equalsIgnoreCase("true") ? true : false;
		boolean isArchive 	= isarchive.equalsIgnoreCase("true") ? true : false;
		String[] processes = null;
		int errorCount = 0;
		int totalCount = procidList.size();
		for(int i=0; i<totalCount ; i++){	
			processes = (String[])procidList.get(i);
  			procID = Integer.parseInt(processes[0]);
			processMsg += "<li>[" + procID + ", " + processes[2] + ", " + processes[1] + ", " + processes[3];
			if(!act.equals("view"))
			{
				try{
					if("del".equals(act))
					{
						hwSession.deleteProcess(hwSessionInfo.toString(), procID, forceRemove, isArchive);
						processMsg += "] ";
						
					}
					else if ("suspend".equals(act))
					{
						hwSession.changeProcessState(hwSessionInfo.toString(), ServerID, procID, 'S');
						processMsg += "] ";
					}
					else if ("resume".equals(act))
					{
						hwSession.changeProcessState(hwSessionInfo.toString(), ServerID, procID, 'R');
						processMsg += "] ";
					}
					else if ("close".equals(act))
					{
						hwSession.changeProcessState(hwSessionInfo.toString(), ServerID, procID, 'T');
						processMsg += "] ";
					}

				}catch(HWException hwe){
					processMsg += ":<font color=\"#cc0000\">(" + hwe.getMessage() + ")" + resource.getString(language, "exception", String.valueOf(hwe.getNumber())) + "</font>] ";
			        hwe.printStackTrace();
			        errorCount++;
					continue;
				}
			}else{
				processMsg += "] ";
			}
  		}
		processMsg =  "<font color=red>" + act.toUpperCase() + "</font> Total : " + totalCount + ". error : " + errorCount + "<br>" + processMsg;
 	}catch(Exception e){ 
        msgError = e.toString();
        e.printStackTrace();
	} 
%>
<html>
<head>
<title>Process management action</title>
<style>
body		{margin:0;padding:0;background:#ffffff;color:#000;}
body, td, th, textarea, select, h2, h3, h4, h5, h6
			{font: 12px/1.25em arial, sans-serif;} 
</style>
<link REL="stylesheet" TYPE="text/css" HREF="form.css">
<script>
document.write("Process finding....<br>");
document.write("<%= sql %> <hr>");
//alert(document.location);
function init(){
	var msgError = '<%=msgError%>';
	if(msgError == ""){
		alert("OK");
	}else{
		alert("Fail");
	}
}

function clean(){
	document.write("");
}
</script>
</head>
<body onLoad="init()">
<% 
//out.println(sql + "<hr>");
out.println("Overall time : " + ((System.currentTimeMillis() - startedTime)/1000) + "sec<br>");

if(!"".equals(msgError)){
	out.println("ERROR..[" + msgError + "]");
}
out.println(processMsg);
%>
</body>
</html>
