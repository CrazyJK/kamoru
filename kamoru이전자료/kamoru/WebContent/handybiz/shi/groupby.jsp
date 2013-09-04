<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.*, java.sql.*, com.hs.frmwk.db.*" %>
<%

StringBuffer strSQL = new StringBuffer();
strSQL.append(" select * from ( ") ;
strSQL.append("  select DECODE(C.STATE, 'E', 'R', SF_GET_DEADDTIMESTATE(C.STATE,SF_GET_DEADLINEDTIME(C.PROCID,C.ACTSEQ))) STATE, ") ;
strSQL.append("  D.ACTSEQ, '' as ACTNAME, D.ORGPROCDEFID, D.MAINDSCPT, A.CREATOR, FC_ACT_RUN_PRTCP(A.PROCID,C.ACTSEQ,'N') PRTCPID,A.PROCID ") ;
strSQL.append("  from TBL_MAP_MAPPING D, PROCDEF AD, PROCS A, ACTDEF CD, ACT C ") ;
strSQL.append("  where D.ORGPROCDEFID = '5039' ") ;
strSQL.append("  and D.SUBORGPROCDEFID = AD.ORGPROCDEFID ") ;
strSQL.append("  and AD.ISFINAL = 'T' ") ;
strSQL.append("  and AD.ENVTYPE = 'O' ") ;
strSQL.append("  and AD.ORGPROCDEFID = A.ORGPROCDEFID ") ;
strSQL.append("  and A.STATE in ('R', 'V', 'E') ") ;
strSQL.append("  and D.SUBDSCPT = CD.DSCPT ") ;
strSQL.append("  and AD.PROCDEFID = CD.PROCDEFID ") ;
strSQL.append("  and CD.TYPE in ('P', 'W') ") ;
strSQL.append("  and A.PROCID = C.PROCID ") ;
strSQL.append("  and CD.ACTDEFSEQ = C.ACTSEQ ") ;
strSQL.append("  and C.STATE in  ('R', 'V', 'E') ") ;
strSQL.append(" ) TBL ") ;
strSQL.append(" where 1=1 ") ;

ArrayList recordList = null;
BWDBHelper helper = null;
try{
	recordList = new ArrayList(50000);
	helper = BWDBHelperFactory.getBWDBHelper("bpmshi");

	helper.setPreparedSQL(strSQL.toString());
	helper.executeQuery();

	System.out.println("executeQuery " + new java.util.Date().toString());
	
	//get header name
	ResultSetMetaData metadata = helper.getMetaData();
	int columnCnt = metadata.getColumnCount();
	String[] columnName = new String[columnCnt];
	for(int i=0 ; i<columnCnt ; i++){
		columnName[i] = metadata.getColumnName(i+1);
	}

	System.out.println("getMetaData " + new java.util.Date().toString());

	//get data
	int count = 0;
	while(helper.next()){
		HashMap record = new HashMap();
		for(int i=0 ; i<columnCnt ; i++){
			record.put(columnName[i], helper.getRSString(columnName[i]));
		}
		recordList.add(record);
		
		if(count++ % 1000 == 0){
			System.out.println("helper.next " + count + ":" + new java.util.Date().toString());
		}
	}	

	System.out.println("helper.next " + new java.util.Date().toString());

}catch(Exception e){
	throw e;
}finally{
	if(helper != null) 
		try{helper.close();}catch(Exception e){		}
}

out.println("size " + recordList.size());

%>
<%!
/*
state actseq orgprocdefid maindscpt creator     prtcpid     procid
V	5		5039	MS60_ACT_030	0000007652	0000009656	607055
V	5		5039	MS60_ACT_030	0000007652	0000009656	607061
V	5		5039	MS60_ACT_030	0000007651	0000008992	607183

STATE, ORGPROCDEFID, ACTSEQ, MAINDSCPT
*/

public ArrayList groupby(ArrayList data){
	
	return null;
}
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>

</body>
</html>