<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.hs.frmwk.db.*, java.util.*" %>
<%@ include file="./common.jsp" %>
<%
	String pParam = request.getParameter("p");
	String vParam = request.getParameter("v");
	
	final int listCountPerPage = 40;
	int p = Integer.parseInt(null == pParam ? "1" : pParam);
	int startList = (p - 1) * listCountPerPage + 1;
	int endList   = startList + listCountPerPage - 1;
	int totalList = 0;
	int lastPage = 0;
	
	ArrayList param = new ArrayList();
	param.add(vParam == null ? "" : vParam + "%");
	param.add(String.valueOf(startList));
	param.add(String.valueOf(endList));

	ArrayList list = new ArrayList();
	try
	{
		list = executeQuery(1001, param, requestURI);
		lastPage = list.size() / listCountPerPage;
	}
	catch(Exception e)
	{
		e.printStackTrace();
	}
%>
<!DOCTYPE HTML>
<html>
<head>
<meta charset="UTF-8">
<title> CSD 문의 목록 </title>
<link rel="stylesheet" href="styles.css" />
<!--[if IE]>
<script src="http://html5shiv.googlecode.com/svn/trunk/html5.js"></script>
<![endif]-->
<script src="./jquery-1.5.js"></script> 
<script>
function goRQST(id)
{
	var csdkey = document.getElementById("csdkey");
	if(csdkey.value == ""){
		var val = prompt("CSD Key is required.");
		if(val != null)
		{
			csdkey.value = val;
			goRQST(id);	
		}
		else
		{
			return;
		}
	}
	
	var vUrl    = "http://csd.handysoft.co.kr:8080/jsp/rqst/view/RqstView.jsp?key=" + csdkey.value + "&rqstID=" + id;
  	var vName   = "rqst"+id;
  	var vWidth  = 650;
  	var vHeight = 650;
  	var vLeft  = (window.screen.width- vWidth)/2;
  	var vTop = (window.screen.height - vHeight)/2;
  	var vFeature = "width="+vWidth+", height="+vHeight+", top="+vTop+", left="+vLeft;
  		vFeature = vFeature + "toolbar=0,location=0,directories=0,"+
        "status=0,menubar=0,scrollbars=0,"+
        "resizable=0,copyhistory=1";
  
  	window.open(vUrl,vName,vFeature).focus();
}
function viewRQST(id)
{
	$("table[id^=tb_rqst]").hide();
	$("#tb_rqst_"+id).show();
	
	$("li a").removeClass("SELECTED");
	$("li[id=nav_ul_li_" + id + "] a").addClass("SELECTED");
}
</script>
</head>
<body>

<header>
	<hgroup>
		<h1>GW 6.7.5 최근 문의 목록 <input id="csdkey" placeholder="Insert CSD key"/></h1>
	</hgroup>
</header>

<section>
	<table>
		<tr>
			<td>
				<div>
					<ul id="nav_ul">
					<% for(int i=0; i<list.size(); i++){ HashMap data = (HashMap)list.get(i); totalList = Integer.parseInt(data.get("TOTAL").toString());%>
						<li id="nav_ul_li_<%=data.get("RQST_ID") %>"><a href="javascript:void(0)" onclick="viewRQST(<%=data.get("RQST_ID") %>)">[<%=data.get("RQST_ID") %>] <%=data.get("SUBJECT") %></a></li>
					<% } %>
					</ul>
					<p class="pageNav">
					<% 
					lastPage = totalList / listCountPerPage + 1;
					for(int i=1; i<lastPage; i++) { 
						if(i > 0) {
							if(i == p) {
								out.println(i);
							} else {
					%>
						<a href="?p=<%=i %>&v=<%=vParam %>"><%=i %></a>
					<% 		}
						}
					} %>
					<a href="?p=<%=lastPage %>&v=<%=vParam %>"><%=lastPage %></a>
					</p>
				</div>
			</td>
			<td>
				<div>
					<% for(int i=0; i<list.size(); i++){ HashMap data = (HashMap)list.get(i);%>
					<table id="tb_rqst_<%=data.get("RQST_ID") %>" style="display:none;">
						<tr>
							<td id="subjectTD">
								<input type="button" onclick="goRQST(<%=data.get("RQST_ID") %>)" value="원문보기"/>
								<%=html(data.get("SUBJECT")) %>
							</td>
						</tr>
						<tr>
							<td id="contentTD"><%=html(data.get("CONTENT")) %>
							</td>
						</tr>
						<tr>
							<td id="answerTD"><%=html(data.get("ANSWER")) %>
							</td>
						</tr>
					</table>
					<% } %>
				</div>
			</td>
		</tr>
	</table>
</section>

<footer>
	<p>&copy; HANDYSOFT. All rights reserved.</p>
</footer>
</body>
</html>