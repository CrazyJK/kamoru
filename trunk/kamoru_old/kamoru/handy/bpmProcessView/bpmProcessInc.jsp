<%@ page import="com.hs.frmwk.db.*, java.util.*, java.sql.*" %> 
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

public String getPJTUIDByProcID(String procid)throws Exception 
{ 
    String sql = "SELECT value FROM rlvntdata WHERE rlvntdataname = 'sProjUID' AND procid = ? "; 
    ArrayList retList = executeQuery(sql, procid); 
    String pjtUID = "";  
    if(retList.size() > 0) 
    { 
        HashMap map = (HashMap)retList.get(0); 
        pjtUID = (String)map.get("VALUE"); 
    } 
    return pjtUID; 
} 

public String getPJTUID(String pcode)throws Exception 
{ 
    String sql = "SELECT pjtuid FROM hi_pcodereq WHERE tmppjtcd = ? "; 
    ArrayList retList = executeQuery(sql, pcode); 
    String pjtUID = "";  
    if(retList.size() > 0) 
    { 
        HashMap map = (HashMap)retList.get(0); 
        pjtUID = (String)map.get("PJTUID"); 
    } 
    return pjtUID; 
} 

public String getPCode(String pjtuid)throws Exception 
{ 
    String sql = "SELECT tmppjtcd FROM hi_pcodereq WHERE pjtuid = ? "; 
    ArrayList retList = executeQuery(sql, pjtuid); 
    String pjtCD = ""; 
    if(retList.size() > 0) 
    { 
        HashMap map = (HashMap)retList.get(0); 
        pjtCD = (String)map.get("TMPPJTCD"); 
    } 
    return pjtCD; 
} 

public ArrayList getProcessInfo(String procid)throws Exception 
{ 
    ArrayList itemlist = new ArrayList(); 
     
    if(procid != null && procid.trim().length() > 0) 
    { 
        setProcessItem(itemlist, procid); 
    } 
    return itemlist; 
} 

private void setProcessItem(ArrayList itemlist, String procid)throws Exception 
{ 
    debug("[procs] procid=" + procid); 
     
    StringBuffer strSQL = new StringBuffer(); 
    strSQL.append("SELECT  'P' itemtype, ") ; 
    strSQL.append("        p.SVRID, p.PROCID, p.URGENT, p.DEADLINETYPE, p.SUBPROCTYPE, p.PASSWDFLAG, p.STATE, ") ; 
    strSQL.append("        p.DMSAVEFLAG, p.DMIDTYPE, p.CALTYPE, p.INTERNALID, p.CMNTCNT, p.ATTACHCNT, ") ; 
    strSQL.append("        p.ORGPROCDEFID, p.REVISIONID, p.INSTFLDRID, p.VER, p.ARCHIVEFLDRID, p.NAME, ") ; 
    strSQL.append("        (p.CREATIONDTIME + 9/24) CREATIONDTIME, p.CREATOR, p.CREATORNAME, p.DEADLINE, ") ; 
    strSQL.append("        p.PRESVRID, p.PREPROCDEFID, p.PREPROCDEFNAME, p.USRGRPHID, p.CALMEMBERID, ") ; 
    strSQL.append("        p.CUSTOMID, (p.CMPLTDTIME + 9/24) CMPLTDTIME, p.DEADLINEDTIME, p.RETURNSVRID, ") ; 
    strSQL.append("        p.PARENTSVRID, p.PARENTPROCID, p.PARENTACTSEQ, p.PARENTACTTYPE, p.CHECKOUTUSR, ") ; 
    strSQL.append("        (p.MODIFYDTIME + 9/24) MODIFYDTIME, p.MODIFIER, p.MODIFIERNAME, p.DMSVRID, p.DMFLDRID, p.DSCPT, ") ; 
    strSQL.append("        (SELECT count(*) FROM rlvntdata r WHERE  r.procid = p.procid) pv_cnt ") ; 
    strSQL.append("FROM   procs p ") ; 
    strSQL.append("WHERE  p.procid = ? ") ; 

    ArrayList proclist = executeQuery(strSQL.toString(), procid); 
    if(proclist.size() == 1) 
    { 
        HashMap proc = (HashMap)proclist.get(0); 
        itemlist.add(proc); 
        setActItem(itemlist, procid); 
    } 
} 

private void setActItem(ArrayList itemlist, String procid)throws Exception 
{ 
    debug("[act] procid=" + procid); 
     
    StringBuffer strSQL = new StringBuffer(); 
    strSQL.append("SELECT  'A' itemtype, ") ; 
    strSQL.append("        a.svrid, a.procid, a.actseq, a.type, a.jointype, a.casetype, a.deadlinetype, a.transtype, ") ; 
    strSQL.append("        a.caltype, a.existinfofile, a.existcmntrans, a.isrepsign, a.subproctype, a.sendapp, ") ; 
    strSQL.append("        a.sendattach, a.sendcmnt, a.state, a.prestate, a.checkpostcond, a.cmpltopt, a.rbackopt, ") ; 
    strSQL.append("        a.eventtype, a.actlooptype, a.defsvrid, a.defprocdefid, a.name, a.priority, a.actauth, ") ; 
    strSQL.append("        a.actinfo, a.deadline, a.respgrpseq, a.respseq, a.capacity, a.cost, a.providerid, a.loopcnt, ") ; 
    strSQL.append("        a.cmpltcnt, a.spcfromnode, a.attaddcnt, a.waitingtime, a.workingtime, a.planstarttime, ") ; 
    strSQL.append("        a.plancmplttime, a.subsvrid, a.subprocdefid, a.suborgprocdefid, a.postcondseq, ") ; 
    strSQL.append("        a.mergecondseq, a.splitcondseq, a.dscpt, a.resplist, ") ; 
    strSQL.append("        (a.deadlinedtime + 9/24) deadlinedtime, (a.planstartdtime + 9/24) planstartdtime, ") ; 
    strSQL.append("        (a.plancmpltdtime + 9/24) plancmpltdtime, (a.startdtime + 9/24) startdtime, (a.cmpltdtime + 9/24) cmpltdtime ") ; 
    strSQL.append("FROM    act a ") ; 
    strSQL.append("WHERE   a.procid = ? ") ; 

    ArrayList actlist = executeQuery(strSQL.toString(), procid); 
     
    for(int i=0; i<actlist.size(); i++) 
    { 
        HashMap act = (HashMap)actlist.get(i); 
         
        // hi_wfinfo에 데이타 있는지 여부 
        String actid         = (String)act.get("DSCPT"); 
        String type            = (String)act.get("TYPE"); 
        String actseq         = (String)act.get("ACTSEQ"); 
         
         
        if(actid != null && "PB".indexOf(type) > -1) 
        { 
            String pjtUID = getPJTUIDByProcID(procid); 
             
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
            setWitemItem(itemlist, procid, actseq); 

            String subprocid = getSubProcID(procid, actseq); 
            if(procid != null) 
            { 
                setProcessItem(itemlist, subprocid); 
            } 
        } 
        else if(type.equals("P")) 
        { 
            setWitemItem(itemlist, procid, actseq); 
        } 
    } 
} 

private void setWitemItem(ArrayList itemlist, String procid, String actseq)throws Exception 
{ 
    debug("[witem] procid=" + procid + ", actseq=" + actseq); 
     
    StringBuffer strSQL = new StringBuffer(); 
    strSQL.append("SELECT  'W' itemtype, ") ; 
    strSQL.append("        w.svrid, w.procid, w.witemseq, w.prtcptype, w.onasync, w.state, w.urgent, ") ; 
    strSQL.append("        w.existinfofile, w.checkpostcond, w.actseq, w.priority, w.respgrpseq, w.respseq, ") ; 
    strSQL.append("        w.deadline, w.prtcp, w.prtcpname, (w.creationdtime + 9/24) creationdtime, ") ; 
    strSQL.append("        w.loopcnt, w.cmpltusr, w.calmemberid, (w.startdtime + 9/24) startdtime, ") ; 
    strSQL.append("        (w.cmpltdtime + 9/24) cmpltdtime, (w.deadlinedtime + 9/24) deadlinedtime, ") ; 
    strSQL.append("        w.cmpltusrname, w.checkoutusr, ") ; 
    strSQL.append("        (SELECT a.name ") ; 
    strSQL.append("        FROM   act a ") ; 
    strSQL.append("        WHERE  a.procid = w.procid ") ; 
    strSQL.append("        AND    a.actseq = w.actseq) actname, ") ; 
    strSQL.append("        (SELECT nvl(count(*), 0) ") ; 
    strSQL.append("        FROM   actapp aa ") ; 
    strSQL.append("        WHERE  aa.procid = w.procid ") ; 
    strSQL.append("        AND    aa.actseq = w.actseq) appCnt ") ; 
    strSQL.append("FROM    witem w ") ; 
    strSQL.append("WHERE   w.procid = ? ") ; 
    strSQL.append("AND     w.actseq = ? ") ; 

    ArrayList witemlist = executeQuery(strSQL.toString(), new String[]{procid, actseq}); 
     
    for(int i=0; i<witemlist.size(); i++) 
    { 
        HashMap witem = (HashMap)witemlist.get(i); 
        itemlist.add(witem); 
    } 
} 

private String getSubProcID(String parentprocid, String parentactseq)throws Exception 
{ 
    String sql = "SELECT procid FROM procs WHERE parentprocid = ? AND parentactseq = ? "; 
    ArrayList proclist = executeQuery(sql, new String[]{parentprocid, parentactseq}); 
    if(proclist.size() > 0) 
    { 
        HashMap map = (HashMap)proclist.get(0); 
        return (String)map.get("PROCID"); 
    } 
    else 
    { 
        return null; 
    } 
} 

public ArrayList getPhaseList(String pjtuid)throws Exception 
{ 
    if(pjtuid != null && pjtuid.trim().length() > 0 ) 
    { 
        String sql = "SELECT  p.*, decode(state, 'R', '진행', 'C', '완료', 'E', '에러', 'D', '멈춤', p.state) statedesc FROM  procs p WHERE p.dscpt like ? ORDER BY p.procid "; 
        return executeQuery(sql, "%" + pjtuid + "%"); 
    } 
    else 
        return new ArrayList(); 
} 

public ArrayList getPhaseListByPCode(String pcode)throws Exception 
{ 
    if(pcode != null && pcode.trim().length() > 0 ) 
    { 
        //pjtUID = pjtuid; 
         
        StringBuffer strSQL = new StringBuffer(); 
        strSQL.append("SELECT p.*, ") ; 
        strSQL.append("       decode(state, 'R', '진행', 'C', '완료', 'E', '에러', 'D', '멈춤', p.state) statedesc ") ; 
        strSQL.append("FROM   procs p ") ; 
        strSQL.append("WHERE  p.dscpt like (SELECT '%'||pjtuid||'%' ") ; 
        strSQL.append("        FROM   hi_pcodereq ") ; 
        strSQL.append("        WHERE  tmppjtcd = ?) ") ; 
        strSQL.append("ORDER BY p.procid ") ; 
         
        return executeQuery(strSQL.toString(), pcode); 
    } 
    else 
        return new ArrayList(); 
} 

public ArrayList getLotListByPJTUID(String pjtuid)throws Exception 
{ 
    StringBuffer strSQL = new StringBuffer(); 
    strSQL.append("SELECT  r.procid, p.name, decode(p.state, 'R', '진행', 'C', '완료', 'E', '에러', 'D', '멈춤', p.state) state, p.dscpt, r.value ") ; 
    strSQL.append("FROM    rlvntdata r, procs p ") ; 
    strSQL.append("WHERE   r.procid = p.procid ") ; 
    strSQL.append("AND     r.rlvntdataname like 'sLotID' ") ; 
    strSQL.append("AND     r.value is not null AND r.value <> 'NA' ") ; 
    strSQL.append("AND     exists ( ") ; 
    strSQL.append("                SELECT  1 ") ; 
    strSQL.append("                FROM    rlvntdata r2 ") ; 
    strSQL.append("                WHERE   r2.rlvntdataname like 'sProjUID' ") ; 
    strSQL.append("                AND     r2.procid = r.procid ") ; 
    strSQL.append("                AND     r2.value = ?) ") ; 
    strSQL.append("ORDER BY value, name, state ") ; 

    return executeQuery(strSQL.toString(), pjtuid); 
} 

public ArrayList getTaskListByPJTUID(String pjtuid)throws Exception 
{ 
    StringBuffer strSQL = new StringBuffer(); 
    strSQL.append("SELECT  r.procid, p.name, decode(p.state, 'R', '진행', 'C', '완료', 'E', '에러', 'D', '멈춤', p.state) state, p.dscpt, r.value, (SELECT a.name FROM act a WHERE a.type = 'P' AND rownum = 1 AND a.procid = p.procid) actname ") ; 
    strSQL.append("FROM    rlvntdata r, procs p ") ; 
    strSQL.append("WHERE   r.procid = p.procid ") ; 
    strSQL.append("AND     r.rlvntdataname like 'sTaskID' ") ; 
    strSQL.append("AND     r.value is not null ") ; 
    strSQL.append("AND     exists ( ") ; 
    strSQL.append("                SELECT  1 ") ; 
    strSQL.append("                FROM    rlvntdata r2 ") ; 
    strSQL.append("                WHERE   r2.rlvntdataname like 'sProjUID' ") ; 
    strSQL.append("                AND     r2.procid = r.procid ") ; 
    strSQL.append("                AND     r2.value = ?) ") ; 
    strSQL.append("ORDER BY name, procid ") ; 
    return executeQuery(strSQL.toString(), pjtuid); 
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