<%-- 
    * 파 일 명    :  
    * 파일설명    :  
    * 
    * 작 성 자    : 남종관 
    * 작 성 일    : 2010. 11. 25. 오후 9:49:09 
    * 변경이력    : 날짜 ,수정자 ,변경사유 
--%>
<%@ page language="java" contentType="text/plain; charset=UTF-8" pageEncoding="UTF-8"%> 
<%@ page import="java.util.*,java.io.*, 
            com.hs.frmwk.db.*, 
            com.hs.frmwk.xml.dom.*,  
            com.hs.bf.web.beans.*,  
            com.hs.frmwk.web.locale.*,  
            com.hs.frmwk.web.resource.*, 
            com.hs.bf.web.xmlrs.*"%> 
<jsp:useBean id="hwSessionInfo"     class="com.hs.bf.web.beans.HWSessionInfo"           scope="session"/> 
<jsp:useBean id="hwSessionFactory"  class="com.hs.bf.web.beans.HWSessionFactory"        scope="application"/> 

<% 
    String itemtype = request.getParameter("itemtype"); 
    String procid     = request.getParameter("procid"); 
    String actseq     = request.getParameter("actseq"); 
    String witemseq    = request.getParameter("witemseq"); 
    String state    = request.getParameter("state"); 
    String type     = request.getParameter("type"); 
    String cmd        = request.getParameter("cmd"); 

    HWSession hwSession = hwSessionFactory.newInstance(); 
    String ServerID     = hwSessionInfo.get("ServerID"); 
    String result        = ""; 
    if("P".equals(itemtype)) 
    { 
        if("RE".indexOf(state) > -1) 
        { 
            System.out.println("Process Terminate procid=" + procid); 
            result = changeProcessState(hwSessionFactory, hwSessionInfo, procid, "T"); 
        } 
        else 
        { 
            System.out.println("Nothing to do"); 
        } 
    } 
    else if("A".equals(itemtype)) 
    { 
        if("REW".indexOf(state) > -1) 
        { 
            System.out.println("Activity Complete procid=" + procid + " actseq=" + actseq); 
            if("B".equals(type)) 
            {     
                // 서브 액티비티는 대기/진행으로 변경 
                executeUpdate("UPDATE act SET type = 'W', state = 'R' WHERE procid = ? AND actseq = ? ", new String[]{procid, actseq}); 
                // L4완료 
                result = completeActivity(hwSessionFactory, hwSessionInfo, procid, actseq); 
                // type 원복 
                //executeUpdate("UPDATE act SET type = 'B' WHERE procid = ? AND actseq = ? ", new String[]{procid, actseq}); 
            } 
            else 
            { 
                result = completeActivity(hwSessionFactory, hwSessionInfo, procid, actseq); 
            } 
        } 
        else 
        { 
            System.out.println("Nothing to do"); 
        } 
    } 
    else if("W".equals(itemtype)) 
    { 
        if("IE".indexOf(state) > -1) 
        { 
            System.out.println("WorkItem Complete procid=" + procid + " actseq=" + actseq + " witemseq=" + witemseq); 
            result = completeWorkitemBySync(hwSessionFactory, hwSessionInfo, procid, actseq, witemseq); 
        } 
        else 
        { 
            System.out.println("Nothing to do"); 
        } 
    } 
    out.println(result); 
%> 
<%! 
public String changeProcessState(HWSessionFactory hwSessionFactory, HWSessionInfo hwSessionInfo, String procid, String state) 
{ 
    HWSession hwSession = hwSessionFactory.newInstance(); 
    String ServerID     = hwSessionInfo.get("ServerID"); 
    try 
    { 
        hwSession.changeProcessState(hwSessionInfo.toString(), ServerID, Integer.parseInt(procid), state.charAt(0)); 
        return "0"; 
    } 
    catch(Exception e) 
    { 
        return e.getMessage(); 
    } 
} 
public String completeActivity(HWSessionFactory hwSessionFactory, HWSessionInfo hwSessionInfo, String procid, String actseq) 
{ 
    HWSession hwSession = hwSessionFactory.newInstance(); 
    String ServerID     = hwSessionInfo.get("ServerID"); 
    try 
    { 
        hwSession.completeActivity(hwSessionInfo.toString(), ServerID, Integer.parseInt(procid), Integer.parseInt(actseq)); 
        return "0"; 
    } 
    catch(Exception e) 
    { 
        return e.getMessage(); 
    } 
} 
public String completeWorkitemBySync(HWSessionFactory hwSessionFactory, HWSessionInfo hwSessionInfo, String procid, String actseq, String witemseq) 
{ 
    HWSession hwSession = hwSessionFactory.newInstance(); 
    String ServerID     = hwSessionInfo.get("ServerID"); 
    try 
    { 
        hwSession.completeWorkitemBySync(hwSessionInfo.toString(), ServerID,  
                                     Integer.parseInt(procid), Integer.parseInt(actseq), Integer.parseInt(witemseq),  
                                     0, 1, 100, null, null, null, null, null, null, null, null, null, null); 
        return "0"; 
    } 
    catch(Exception e) 
    { 
        return e.getMessage(); 
    } 
} 

//====================================== DB Query Method ===================================== 
public int executeUpdate(String query, String param)throws Exception 
{ 
    ArrayList paramlist = new ArrayList(); 
    paramlist.add(param); 
    return executeUpdate(query, paramlist); 
} 

public int executeUpdate(String query, String[] paramArr)throws Exception 
{ 
    ArrayList paramlist = new ArrayList(); 
    for(int i=0; i<paramArr.length; i++) 
    { 
        paramlist.add(paramArr[i]); 
    } 
    return executeUpdate(query, paramlist); 
} 

public int executeUpdate(String query, ArrayList paramList)throws Exception 
{ 
    int rnt = -1; 
    com.hs.frmwk.db.BWDBHelper helper = null; 
    try 
    { 
        helper = com.hs.frmwk.db.BWDBHelperFactory.getBWDBHelper(); 
        helper.setPreparedSQL(query); 
        //debug("Query : " + query); 
        helper.clearParameters(); 
        for(int i=0; i<paramList.size(); i++) 
        { 
            String param = (String)paramList.get(i); 
            //debug("param" + (i+1) + "=" + param); 
            helper.setString(i+1, param); 
        } 
        rnt = helper.executeUpdate(); 
        helper.commit(); 
    } 
    catch(Exception e) 
    { 
        e.printStackTrace(); 
    } 
    finally 
    { 
        if(helper != null) helper.close(); 
    } 
    return rnt; 
} 

%> 