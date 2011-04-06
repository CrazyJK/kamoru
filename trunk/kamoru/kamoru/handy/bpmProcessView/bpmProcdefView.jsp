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
<%!  
    static String wasurl = "http://hywintest2.hynix.com:7001"; 
%> 
<% 
    String userName        = (String)session.getAttribute("UserName"); 
    String loginID        = (String)session.getAttribute("LoginID"); 

    String pjtuid         = getParam(request, "pjtuid"); 

    setDEBUG(true); 
     
    ArrayList itemlist     = new ArrayList(); 
     
    if(pjtuid != null && pjtuid.trim().length() > 0) 
    { 
        itemlist     = getProcessInfo(pjtuid); 
    } 

%> 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd"> 
<html> 
<head> 
<title>BPM Process Management</title> 
<style type="text/css"> 

BODY    {font:10pt Arial; background: url(SNSD_yuna.jpg.x) repeat} 
INPUT[type="text"]   {font:9pt; height:12pt; border-left:0;border-right:0;border-top:0;border-bottom:1pt solid #3B9C9C;} 
INPUT[type="button"] {font:9pt; height:14pt;border:0; background-color:lightblue;color: #4C0007;} 
INPUT[type="submit"] {font:bold 9pt Arial; height:14pt;border:0; background-color:#3B9C9C; color:white;} 
TABLE    {font:10pt ; border:0pt solid blue} 
TD        {border-bottom:1pt solid #57474A; padding:1 3 1 3;} 
FORM    {display: inline;} 
LABEL    {cursor:pointer;} 
LEGEND    {font:bold 10pt Arial;} 

.HEAD    {background-color: #4C0007; color: white; font:bold 10pt Arial; height:15pt} 
.P        {font-weight:bold;} 
.A        {color:blue;} 
</style> 
<script language="javascript" src="/bizflow/hynix/includes/WIHAction.js"></script> 
<script language="javascript" src="/bizflow/hynix/includes/common.js"></script> 
<script language="javascript" src="/bizflow/hynix/includes/prototype.js"></script> 
<script language="javascript" src="/bizflow/hynix/includes/Calendar2.js"></script> 
<script language="javascript" src="/bizflow/hynix/includes/table.js"></script> 
<script language="javascript" src="/bizflow/hynix/includes/validation.js"></script> 
<script language="javascript" src="/bizflow/hynix/includes/jquery-1.4.2.js"></script> 
<script languege="javascript"> 

$(document).ready(function() { 
    $("#loadingbar").hide(); 
}); 

function bpmMap(procid) 
{ 
    var vUrl    = "<%=wasurl %>/bizflow/common/audit.jsp?pid=" + procid; 
    var vName   = "map"+procid; 
    var vWidth  = window.screen.availWidth; 
    var vHeight = window.screen.availHeight; 
    var vLeft  = (window.screen.width- vWidth)/2; 
    var vTop = (window.screen.height - vHeight)/2; 
    var vFeature = "width="+vWidth+", height="+vHeight+", top="+vTop+", left="+vLeft; 
    vFeature = vFeature + "toolbar=0,location=0,directories=0,"+ 
          "status=0,menubar=0,scrollbars=1,"+ 
          "resizable=1,copyhistory=1"; 
     
    window.open(vUrl,vName,vFeature).focus(); 
} 
function viewMap(svrid, procid, actseq, workseq) 
{ 
    var procinfoParamName  = new Array("readonlymode"    // 0 : 실행모드, -1 : 읽기전용모드 
                                     , "sid"            // 서버 ID 
                                     , "pid"            // 프로세스 ID 
                                     , "type");            // Type 
    var procinfoParamValue = new Array("-1" 
                                     , svrid 
                                     , procid 
                                     , "sharepage"); 
     
    viewOpenEx("<%=wasurl %>/bizflow/common/viewer/flashMap.jsp" // "/bizflow/common/audit.jsp" 
            , "popFlashMap" 
            , procinfoParamName 
            , procinfoParamValue 
            , "" + window.screen.availWidth 
            , "" + window.screen.availHeight 
            , "yes"            // 스크롤바 
            , "no"                // 상태바 
            , "no"                // 툴바 
            , "yes");            // 크기조절 
} 
</script> 
</head> 
<body> 
<div id="loadingbar" align='center' style="position: absolute; left:0px; top:0px;height: 100%; width: 100%;z-index: 99;"> 
    <table width="100%" height="100%" border="0" cellpadding="0" cellspacing="0"> 
    <TR> 
        <TD align="center" valign="middle"> 
            <img src="/bizflow/hynix/img/loading.gif" width="48" height="48"> 
        </TD> 
    </TR> 
    </TABLE> 
</div> 

<span id="URL"></span> 

<h2>BPM 프로세스 정의와 HI_WFINFO 비교</h2> 


<div id=div_searchCond> 
<form> 
    <fieldset> 
        <legend>Search Condition</legend> 
        <span onclick='$("input[name=pjtuid]").val("");'>PJTUID</span> <input type="text" name="pjtuid"  value="<%=pjtuid %>" size="22" />&nbsp;&nbsp; 
        <input type=submit value="Search PJT" /> 
    </fieldset> 
</form> 
</div> 

<div id=div_result> 
    Item count : <%=itemlist.size() %> &nbsp;&nbsp;&nbsp;&nbsp;UserName : <strong><%=userName %></strong> 

    <table border="0" width="100%" cellpadding="0" cellspacing="0"> 
        <tr class="HEAD"> 
            <td>ProcDefID</td> 
            <td>ActDefseq</td> 
            <td style="display:none;">&nbsp;</td> 
            <td>Name</td> 
            <td>Type</td> 
            <td>Dscpt</td> 
            <td>HI_WHINFO</td> 
        </tr> 
<% 
for(int i=0; i<itemlist.size(); i++) 
{ 
    HashMap item = (HashMap)itemlist.get(i); 

    String itemtype        = (String)item.get("ITEMTYPE"); 
    String currProcid    = (String)item.get("PROCDEFID"); 
    String currActseq    = (String)item.get("ACTDEFSEQ"); 
    String currType        = html(item.get("TYPE")); 
    String currName        = html(item.get("NAME")); 
    String wfinfoCnt    = html(item.get("WFINFO_CNT")); 
    String dscpt        = html(item.get("DSCPT")); 
    if("SCEG".indexOf(currType) > -1){ 
        continue; 
    } 
     
    String style = ""; 
    if("0".equals(wfinfoCnt)){ 
        style += "color:red;"; 
    } 
     
    String indent = ""; 
    if(dscpt != null && dscpt.trim().length() > 5){ 
        indent = "&nbsp;&nbsp;&nbsp;&nbsp;"; 
        if(!dscpt.split("-")[2].equals("00")){ 
            indent += "&nbsp;&nbsp;&nbsp;&nbsp;"; 
        } 
    } 
     
%> 
        <tr id="ITEM_<%=i %>" class="<%=itemtype %>" style="<%=style %>"> 
            <td><%=currProcid %></td> 
            <td><%=html(currActseq) %></td> 
            <td style="display:none;"><%=itemtype.equals("P") ? "Procs" : (itemtype.equals("A") ? "Act" : "Witem") %></td> 
            <td><%=indent + currName %></td> 
            <td><%=currType %></td> 
            <td><%=dscpt %></td> 
            <td><%=wfinfoCnt %></td> 
        </tr> 
<% 
} 
%> 
    </table> 
</div> 

</body> 
</html> 
<%! 
private boolean debug = false; 

public void print()throws Exception 
{ 
     
} 

public void setDEBUG(boolean debug) 
{ 
    this.debug = debug; 
} 

private void debug(String msg) 
{ 
    if(debug) 
    { 
        System.out.println("DEBUG " + msg); 
    } 
} 

public String getPhaseID(String pjtuid)throws Exception 
{ 
    String sql = "SELECT phaseid FROM hi_wfinfo WHERE pjtuid = ? AND actgubun = 'PHASE' "; 
    ArrayList retList = executeQuery(sql, pjtuid); 
    String phaseID = ""; 
    if(retList.size() > 0) 
    { 
        HashMap map = (HashMap)retList.get(0); 
        phaseID = (String)map.get("PHASEID"); 
    } 
    return phaseID; 
} 

public ArrayList getProcdefList(String pjtUID)throws Exception 
{ 
    String phaseid = getPhaseID(pjtUID); 
    phaseid = phaseid.substring(0,2) + '_' + phaseid.substring(3,5);  
    String sql = "SELECT procdefid, name FROM procdef WHERE isfinal = 'T' AND envtype = 'O' AND dscpt like ? ORDER BY dscpt"; 
    ArrayList retList = executeQuery(sql, phaseid); 
    return retList; 
} 

public ArrayList getProcessInfo(String pjtUID)throws Exception 
{ 
    ArrayList itemlist = new ArrayList(); 
     
    ArrayList procdefidList = getProcdefList(pjtUID); 
    for(int i=0; i<procdefidList.size(); i++) 
    { 
        HashMap procdefidMap = (HashMap)procdefidList.get(i); 
        String procdefid = (String)procdefidMap.get("PROCDEFID"); 
        if(procdefid != null && procdefid.trim().length() > 0) 
        { 
            setProcessItem(itemlist, procdefid, pjtUID); 
        } 
    } 
    return itemlist; 
} 

private void setProcessItem(ArrayList itemlist, String procid, String pjtUID)throws Exception 
{ 
    debug("[procs] procid=" + procid); 
     
    StringBuffer strSQL = new StringBuffer(); 
    strSQL.append("SELECT 'P' itemtype, p.* FROM procdef p WHERE p.procdefid = ? ") ; 

    ArrayList proclist = executeQuery(strSQL.toString(), procid); 
    if(proclist.size() == 1) 
    { 
        HashMap proc = (HashMap)proclist.get(0); 
        itemlist.add(proc); 
        setActItem(itemlist, procid, pjtUID); 
    } 
} 

private void setActItem(ArrayList itemlist, String procid, String pjtUID)throws Exception 
{ 
    debug("[act] procid=" + procid); 
     
    StringBuffer strSQL = new StringBuffer(); 
    strSQL.append("SELECT 'A' itemtype, a.* FROM actdef a WHERE a.procdefid = ? ") ; 

    ArrayList actlist = executeQuery(strSQL.toString(), procid); 
     
    for(int i=0; i<actlist.size(); i++) 
    { 
        HashMap act = (HashMap)actlist.get(i); 
         
        // hi_wfinfo에 데이타 있는지 여부 
        String actid         = (String)act.get("DSCPT"); 
        String type            = (String)act.get("TYPE"); 
        String actseq         = (String)act.get("ACTDEFSEQ"); 
         
         
        if(actid != null && "PB".indexOf(type) > -1) 
        { 
             
            String sql = "SELECT nvl(count(actid), 0) cnt FROM hi_wfinfo WHERE pjtuid = ? AND actid = ? "; 
            ArrayList wfinfolist = executeQuery(sql, new String[]{pjtUID, actid}); 
            if(wfinfolist.size() > 0) 
            { 
                HashMap wfinfo = (HashMap)wfinfolist.get(0); 
                String cnt = (String)wfinfo.get("CNT"); 
                act.put("WFINFO_CNT", cnt); 
            } 
        } 
         
        itemlist.add(act); 
         

        if(type.equals("B")) 
        { 
//            setWitemItem(itemlist, procid, actseq); 

            String subprocid = (String)act.get("SUBPROCDEFID"); 
            if(procid != null) 
            { 
                setProcessItem(itemlist, subprocid, pjtUID); 
            } 
        } 
        else if(type.equals("P")) 
        { 
//            setWitemItem(itemlist, procid, actseq); 
        } 
    } 
} 

// ====================================== DB Query Method ===================================== 
public ArrayList executeQuery(String query, String param)throws Exception 
{ 
    ArrayList paramlist = new ArrayList(); 
    paramlist.add(param); 
    return executeQuery(query, paramlist); 
} 

public ArrayList executeQuery(String query, String[] paramArr)throws Exception 
{ 
    ArrayList paramlist = new ArrayList(); 
    for(int i=0; i<paramArr.length; i++) 
    { 
        paramlist.add(paramArr[i]); 
    } 
    return executeQuery(query, paramlist); 
} 

public ArrayList executeQuery(String query, ArrayList paramList)throws Exception 
{ 
    ArrayList row = new ArrayList(); 

    com.hs.frmwk.db.BWDBHelper helper = null; 
    try 
    { 
        helper = com.hs.frmwk.db.BWDBHelperFactory.getBWDBHelper(); 
        helper.setPreparedSQL(query); 
        debug("Query : " + query); 
        helper.clearParameters(); 
        for(int i=0; i<paramList.size(); i++) 
        { 
            String param = (String)paramList.get(i); 
            debug("param" + (i+1) + "=" + param); 
            helper.setString(i+1, param); 
        } 
        helper.executeQuery(); 
         
        // get header name 
        ResultSetMetaData meta     = helper.getMetaData(); 
        int columnCnt             = meta.getColumnCount(); 
        String[] columnName     = new String[columnCnt]; 
        for(int k=0 ; k<columnCnt ; k++) 
        { 
            columnName[k] = meta.getColumnName(k+1); 
        } 
         
        // get data 
        HashMap record = null; 
        while(helper.next()) 
        { 
            record = new HashMap(); 
            for(int i=0 ; i<columnCnt ; i++) 
            { 
                record.put(columnName[i], helper.getRSString(i+1)); 
            } 
            row.add(record); 
        } 
    } 
    catch(Exception e) 
    { 
        e.printStackTrace(); 
    } 
    finally 
    { 
        if(helper != null) helper.close(); 
    } 
    return row; 
} 

//====================================== Util Method ===================================== 
String getParam(HttpServletRequest request, String name) 
{ 
    return request.getParameter(name) == null ? "" : request.getParameter(name);  
} 
String date(Object obj) 
{ 
    return substring(obj, 5, 16); 
} 
String substring(Object obj, int beginIndex, int endIndex ) 
{ 
    return obj == null ? "&nbsp;" : (obj.toString().length() > endIndex ? obj.toString().substring(beginIndex, endIndex) : obj.toString());     
} 
String html(Object obj) 
{     
    if(obj == null)   
    { 
        return "&nbsp;"; 
    } 
    else 
    { 
        return obj.toString().replaceAll("<", "&lt;").replaceAll(">", "&gt;"); 
    } 
} 
%> 