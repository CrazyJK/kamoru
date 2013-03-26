<%@ page import="com.hs.frmwk.db.*, java.util.*, java.sql.*" %>
<%
// charactorEncoding
request.setCharacterEncoding("UTF-8");

String requestURI = request.getRequestURI();
debug(requestURI);

String userId = (String)session.getAttribute("userId");
String deptId = (String)session.getAttribute("deptId");
%>
<%!
public final String POOLNAME = "mboard";
public final String SETID = "_mobile";
/**
 * Execute query method
 */
public ArrayList executeQuery(int qryID, String param, String caller)throws Exception 
{ 
    ArrayList paramlist = new ArrayList(); 
    paramlist.add(param); 
    return executeQuery(qryID, paramlist, caller); 
} 

public ArrayList executeQuery(int qryID, String[] paramArr, String caller)throws Exception 
{
    ArrayList paramlist = new ArrayList(); 
    for(int i=0; i<paramArr.length; i++) 
    { 
        paramlist.add(paramArr[i]); 
    } 
    return executeQuery(qryID, paramlist, caller); 
} 

public ArrayList executeQuery(int qryID, ArrayList paramList, String caller)throws Exception 
{ 
    ArrayList row = new ArrayList(); 

    com.hs.frmwk.db.BWDBHelper helper = null; 
    try 
    { 
        helper = com.hs.frmwk.db.BWDBHelperFactory.getBWDBHelper(POOLNAME, caller); 
        helper.setPreparedSQL(SETID, qryID); 
        debug("QueryID : " + qryID); 
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
        while(helper.next()) 
        { 
        	HashMap record = new HashMap(); 
            for(int i=0 ; i<columnCnt ; i++) 
            { 
                record.put(columnName[i], helper.getRSString(i+1)); 
            } 
            row.add(record); 
        } 
        debug("selected row " + row.size());
    } 
    catch(Exception e) 
    { 
        debug(e.getMessage());
		throw e; 
    } 
    finally 
    { 
        if(helper != null) helper.close(); 
    } 
    return row; 
} 

void debug(Object obj){
	System.out.println(obj);
}
void debug(int obj){
	System.out.println(obj);
}
Object nvl(Object o){
	return nvl(o, "");
}
Object nvl(Object o, String s){
	return o == null || o.equals("null") ? s : o;
}

String getParam(HttpServletRequest request, String name) 
{ 
    return getParam(request, name, "");  
} 
String getParam(HttpServletRequest request, String name, String defaultValue) 
{ 
    return request.getParameter(name) == null ? defaultValue : request.getParameter(name);  
} 
%>