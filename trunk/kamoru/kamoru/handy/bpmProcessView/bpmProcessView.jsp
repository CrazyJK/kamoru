<%-- 
    * 파 일 명    : processview.jsp 
    * 파일설명    : bpm process의 서브, 참여자, 상태등의 정보를 확인하고 수정할수 있는 관리자용 페이지  
    * 
    * 작 성 자    : 남종관 
    * 작 성 일    : 2010. 11. 18. 오후 7:52:15 
    * 변경이력    : 날짜 ,수정자 ,변경사유 
--%> 
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%> 
<%@ page import="com.hs.frmwk.db.*, java.util.*, java.sql.*" %> 
<%@ include file="bpmProcessInc.jsp" %> 
<%!
	static String wasurl = "http://csd.handysoft.co.kr"; 
%> 
<% 
	String userName	= (String)session.getAttribute("UserName"); 
	String loginID	= (String)session.getAttribute("LoginID"); 

	String procid	= getParam(request, "procid"); 
	String customid	= getParam(request, "customid"); 
	String filter	= getParam(request, "filter"); 

	setDEBUG(false); 
     
	ArrayList itemlist = new ArrayList(); 
     
	if(procid != null && procid.trim().length() > 0) 
	{ 
		itemlist = getProcessInfo(procid); 
	} 
%>
<!doctype html> 
<html> 
<head> 
<title>BPM Process Management</title> 
<link rel="stylesheet" href="./bpmProcessView.css"/> 
<script src="./jquery-1.5.js"></script> 
<script src="./bpmProcessView.js"></script> 
<script> 
var wasurl   = "<%=wasurl %>"; 
var customid = "<%=customid %>"; 
var procid   = "<%=procid %>"; 
var filter   = "<%=filter %>"; 

$(document).ready(function() { 
    $("#loadingbar").hide(); 
     
    $("input:radio[name^=filter]").bind("click", function(){ 
        $("input:submit").click(); 
    }); 

    $("input[name^=radio_procid]").bind("click", function(){ 
        $("input:submit").click(); 
    }); 

    $("#customid").val(customid); 
    $("#procid").val(procid); 
    $("#filter"+filter).attr("checked","checked"); 
}); 

</script> 
</head> 
<body> 

<header> 
    <hgroup> 
        <h1>Handysoft BPM Process Management</h1> 
        <h2>BPM Info - UserName : <strong><%=userName %></strong></h2> 
    </hgroup> 
</header> 

<nav> 
    <ul> 
        <li><a href="#" onclick="bpmMap(<%=procid %>)">Process MAP</a></li> 
        <li><a href="#" onclick='$("#dbview").toggle();'>DB View</a></li> 
    </ul> 
</nav> 


<article> 

<section> 
    <fieldset> 
        <legend>Search Condition</legend> 
        <form action=""> 
            <label for="customid">CustomID</label><input type="text" id="customid" name="customid" placeholder="Enter CustomID" size="16" dir="rtl"/> 
            <label for="procid"  >ProcID</label>  <input type="text" id="procid"   name="procid"   placeholder="Enter ProcID"   size="22" dir="rtl"/> 

            <input type=submit value="Search Process" /> 

            <input type="radio" name="filter" value="ALL" id="filterALL" /><label for="filterALL">All item</label> 
            <input type="radio" name="filter" value="RUN" id="filterRUN" /><label for="filterRUN">Runing</label> 
        </form> 
    </fieldset> 
    <fieldset id="dbview" style="display:;"> 
        <legend>DB Check</legend> 
        <input type="button" value="hi_wfinfo"        onclick="viewDB('hi_wfinfo'       )" /> 
        <input type="button" value="hi_wfresplan"     onclick="viewDB('hi_wfresplan'    )" /> 
        <input type="button" value="hi_planinfo"      onclick="viewDB('hi_planinfo'     )" /> 
        <input type="button" value="hi_wfroleplan"    onclick="viewDB('hi_wfroleplan'   )" /> 
        <input type="button" value="hi_pjtplan"       onclick="viewDB('hi_pjtplan'      )" /> 
        <input type="button" value="hi_pjtphschedule" onclick="viewDB('hi_pjtphschedule')" /> 
        <input type="button" value="bpm_queue"        onclick="viewDB('bpm_queue'       )" /> 
    </fieldset>     
</section> 

<section> 
    <fieldset> 
        <legend>Item count : <%=itemlist.size() %></legend> 
         
        <div id="listDiv"> 
         
    <table border="0" width="100%" cellpadding="0" cellspacing="0"> 
        <tr class="HEAD"> 
            <td>ProcID</td> 
            <td>Actseq</td> 
            <td>Wseq</td> 
            <td style="display:none;">&nbsp;</td> 
            <td>Name</td> 
            <td>Type</td> 
            <td>State</td> 
            <td>Action</td> 
            <td>StartDTime</td> 
            <td>Prtcp</td> 
            <td>CmpltDTime</td> 
            <td>CmpltUsr</td> 
            <td>Dscpt</td> 
        </tr> 
<%     
for(int i=0; i<itemlist.size(); i++) 
{ 
    HashMap item = (HashMap)itemlist.get(i); 

    String itemtype        = (String)item.get("ITEMTYPE"); 
    String currProcid    = (String)item.get("PROCID"); 
    String currActseq    = (String)item.get("ACTSEQ"); 
    String currWitemseq    = (String)item.get("WITEMSEQ"); 
    String currState    = (String)item.get("STATE"); 
    String currType        = html(item.get("TYPE")); 
    String currName        = html(itemtype.equals("W") ? item.get("ACTNAME") : item.get("NAME")); 
    String startdtime     = date(itemtype.equals("A") ? item.get("STARTDTIME") : item.get("CREATIONDTIME")); 
    String cmpltdtime    = date(item.get("CMPLTDTIME")); 
    String wfinfoCnt    = html(item.get("WFINFO_CNT")); 
    String pv_cnt        = html(item.get("PV_CNT")); 
     
    if("SCE".indexOf(currType) > -1){ 
        continue; 
    } 
    if(filter.equals("RUN") && ("RIE".indexOf(currState) < 0)){ 
        continue; 
    } 
     
    // about style 
    String subIndent    = ""; 
    String typeIndent    = ""; 
    String trStyle         = "style='"; 
    String tabSpace        = "&nbsp;"; 
    if(currProcid.equals(procid)) 
    { 
        trStyle += "font-weight:;"; 
    } 
    else 
    { 
        subIndent = tabSpace + tabSpace + tabSpace; 
        trStyle += "color:#990000;"; 
    } 
    if(itemtype.equals("A")) 
    { 
        typeIndent = tabSpace + "ⓐ "; 
    } 
    else if(itemtype.equals("W")) 
    { 
        typeIndent = tabSpace + tabSpace + "ⓦ "; 
        if("CNDTW".indexOf(currState) < 0){ 
            trStyle += "background-color: #C8BBBE;"; 
        } 
    } 
    trStyle += "'"; 
%> 
        <tr id="ITEM_<%=currState %>" class="<%=itemtype %>" <%=trStyle %>> 
            <td><%=currProcid %></td> 
            <td><%=html(currActseq) %></td> 
            <td><%=html(currWitemseq) %></td> 
            <td style="display:none;"><%=itemtype.equals("P") ? "Procs" : (itemtype.equals("A") ? "Act" : "Witem") %></td> 
            <td><%=subIndent + typeIndent + currName %></td> 
            <td><%=currType %></td> 
            <td><%=currState.equals("E") ? "<font color=red>E</font>" : currState %></td> 
            <td> 
<%    if(itemtype.equals("P")) {%> 
                <a href="javascript:procDetail()" title="상세보기"> 
                    <img src="<%=wasurl %>/bizflow/themes/metro/icons/icon_monitor_detail.gif"> 
                </a> 
                <a href="javascript:bpmMap(<%=currProcid %>)" title="진행상태 보기"> 
                    <img src="<%=wasurl %>/bizflow/hynix/img/icon/icon_map.gif" alt="진행상태"> 
                </a> 
                <a href="javascript:procsVal(<%=currProcid %>)" title='프로세스 변수(<%=pv_cnt %>개) 보기'> 
                    <img src="<%=wasurl %>/bizflow/themes/metro/icons/icon_monitor_variables.gif"> 
                </a> 
<%    }else if("CNDTW".indexOf(currState) < 0) {%> 
                <a href="javascript:bpmAction('<%=itemtype %>',<%=currProcid %>,<%=currActseq %>,<%=currWitemseq %>,'<%=currState %>','<%=currType %>','<%="BatchComplete" %>')" 
                    title="일괄처리"> 
                    <img src="<%=wasurl %>/bizflow/themes/metro/icons/submit.gif" alt="일괄처리"> 
                </a> 
<%    }%>  
<%    if(itemtype.equals("W") && !currState.equals("W")) {%> 
                <a href="javascript:bpmAction('<%=itemtype %>',<%=currProcid %>,<%=currActseq %>,<%=currWitemseq %>,'<%=currState %>','<%=currType %>','<%="WIH" %>')" 
                   title="업무처리기 실행(App.<%=item.get("APPCNT") %>개)"> 
                   <img src="<%=wasurl %>/bizflow/themes/metro/icons/icon_worklist_open.gif"> 
                </a> 
                <a href="javascript:bpmAction()" 
                    title="업무 위임"> 
                    <img src="<%=wasurl %>/bizflow/themes/metro/icons/icon_worklist_forward.gif" alt="위임"> 
                </a> 
<%    }%> 
                &nbsp; 
            </td> 
            <td><%=startdtime %></td> 
            <td><%=html(item.get("CREATORNAME")) %><span title="[<%=html(item.get("PRTCPTYPE")) %>]<%=html(item.get("PRTCP")) %>"><%=html(item.get("PRTCPNAME")) %></span></td> 
            <td><%=cmpltdtime %></td> 
            <td><span title="<%=html(item.get("CMPLTUSR")) %>"><%=html(item.get("CMPLTUSRNAME")) %></span></td> 
            <td><%= substring(html(item.get("DSCPT")),0,33) %></td> 
        </tr> 
<% 
} 
%> 
    </table> 
        </div> 
    </fieldset> 
</section> 

     
    <section> 
        <div style="display:; position: fixed; right:0px; bottom:-100px; height:360; width:170; z-index: 99; overflow:hidden;"> 
            <script src="http://widgetprovider.daum.net/view?url=http://widgetcfs1.daum.net/xml/1/widget/2010/09/28/11/32/4ca153da065fc.xml&&width=166&height=359&widgetId=947&scrap=1" type="text/javascript"></script> 
        </div> 
        <div style="display:; position: fixed; right:0px; bottom:100px; height:260; width:170; z-index: 100; overflow:hidden;"> 
            <span style="font:bold 9pt '휴먼편지체'; color:purple;">여기는 112 개발~♩♪♬</span> 
        </div> 
    </section> 
     
    <section> 
        <div id="loadingbar" align='center' style="position: absolute; left:0px; top:0px;height: 100%; width: 100%;z-index: 99;"> 
            <table width="100%" height="100%" border="0" cellpadding="0" cellspacing="0"> 
            <TR> 
                <TD align="center" valign="middle"> 
                    <img src="./loading.gif" width="64" height="44"> 
                </TD> 
            </TR> 
            </TABLE> 
        </div> 
        <span id="URL"></span> 
    </section> 


</article> 

<footer> 
    <p>&copy; 2010 kAmOrU. All rights reserved.</p>  
</footer> 


</body> 
</html> 
