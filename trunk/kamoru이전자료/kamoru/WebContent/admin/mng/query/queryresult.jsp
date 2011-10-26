<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%><%@ page import="java.util.*, 				 javax.naming.*,				 javax.sql.*,
				 java.sql.*"%>    
<%! public final boolean DEBUG = true; 
public int parseInt(String str){
	try{
		return Integer.parseInt(str);
	}catch(Exception e){
		return 0;
	}
}
%>
<%
final String DEFAULT_EXCEL_TITLE = "Query_result";
String contextPath = request.getContextPath();
String imgPath = contextPath + "/images";

request.setCharacterEncoding("euc-kr");
String isExcel			= request.getParameter("isExcel");			// is EXCEL or screen
String excel_title  	= request.getParameter("excel_title");		// excel file name
String db_conn			= request.getParameter("db_conn");			//select Connection
String BWADK_DB_ALIAS 	= request.getParameter("BWADK_DB_ALIAS");	// BWADK_DB_ALIAS
String query			= request.getParameter("query");			// get query
String DB_URL   		= request.getParameter("DB_URL");			// DB_URL
String DB_USER   		= request.getParameter("DB_USER");			// DB_USER
String DB_PASSWORD   	= request.getParameter("DB_PASSWORD");		// DB_PASSWORD
String DB_JDBC_DRIVER 	= request.getParameter("DB_JDBC_DRIVER");	// DB_JDBC_DRIVER
String row_limit		= request.getParameter("row_limit");		// result row limitString jndi_name		= request.getParameter("JNDI_NAME");		// jndi name

if(excel_title == null){
	excel_title = DEFAULT_EXCEL_TITLE;
} 

// execute query
String final_query = null;
if(row_limit == null || parseInt(row_limit) == 0){
	final_query = query;
}else{
	final_query = "SELECT * FROM(" + query + ") WHERE rownum <= " + row_limit;
}

if(DEBUG){
	out.println(final_query);
}
if(db_conn.equals("bwadk")){
	executeQueryBWADK(BWADK_DB_ALIAS, final_query);}else if(db_conn.equals("jdbc")){	setJDBC(DB_URL, DB_USER, DB_PASSWORD, DB_JDBC_DRIVER);
	executeQueryJDBC(final_query);
}else if(db_conn.equals("jndi")){	executeQueryJNDI(jndi_name, final_query);}else{	return;}
String[] colname = getColumnName();ArrayList row	 = getResult();int columnCnt	 = getColumnCount();%>
<%
//EXCEL
if (isExcel != null && "EXCEL".equals(isExcel))
{
	String strClient = request.getHeader("User-Agent");

	response.reset() ;
	response.setContentType("application/vnd.ms-excel; charset=euc-kr");

	if( strClient.indexOf("MSIE 5.5") != -1 ) 
	{ 
		// explorer 5.5 
		response.setHeader("Content-Disposition", "inline; filename=" + excel_title + ".xls"); 
		response.setHeader("Content-Description", "JSP Generated Data");
	}
	else 
	{
		// not explorer 5.5
		response.setHeader("Content-Disposition", "attachment; filename=" + excel_title + ".xls"); 
		response.setHeader("Content-Description", "JSP Generated Data");
	}

	response.setHeader("Content-Transfer-Encoding", "binary;");
	response.setHeader("Pragma", "no-cache;");
	response.setHeader("Expires", "-999999999;");
	response.addHeader("Cache-Control","must-revalidate, post-check=0,pre-check=0");
} 
%>


<html>
<head>
<title><%=excel_title%></title>
</head>
<body>
<%
// If print a screen, add EXCEL menu.
	if (!"EXCEL".equals(isExcel))	{
%>
<form name="f" method="post">
<table border="0" cellpadding="0" cellspacing="0">
	<tr>
		<td>
			<input type="hidden" name="isExcel"			value="EXCEL"/>
			<input type="hidden" name="query"			value="<%=query%>"/>
			<input type="hidden" name="db_conn"			value="<%=db_conn%>"/>
			<input type="hidden" name="DB_URL"			value="<%=DB_URL%>"/>
			<input type="hidden" name="DB_USER"			value="<%=DB_USER%>"/>
			<input type="hidden" name="DB_PASSWORD"     value="<%=DB_PASSWORD%>"/>
			<input type="hidden" name="DB_JDBC_DRIVER"  value="<%=DB_JDBC_DRIVER%>"/>
			<input type="hidden" name="BWADK_DB_ALIAS"  value="<%=BWADK_DB_ALIAS%>"/>
			<input type="hidden" name="row_limit"  		value="<%=row_limit%>"/>			<input type="hidden" name="JNDI_NAME"  		value="<%=jndi_name%>"/>
			<input type="text"   name="excel_title"		value="<%=excel_title%>"/>
			<input type="button"						value="EXCEL" onClick="javascript:submit()"/>
		</td>
	</tr>	
</table>
</form> 
<%	}%>
<!-- query result start -->
<table border="1" width="100%" style="border:1 solid navy" cellpadding="1" cellspacing="0">
<thead>	<tr>		<th colspan="<%=columnCnt+1%>" align="left">		record count :<%= row.size() %></th>	</tr>
	<tr>
		<th bgcolor="lightblue">&nbsp;</th><%	
// header print
	for(int i=0 ; i<columnCnt ; i++){
%>		
		<th align="center" bgcolor="lightblue" nowrap="nowrap"><%= colname[i] %></th><%	}%>	</tr></thead>
<tbody>
<%	
// data print
	for(int i=0 ; i<row.size() ; i++){
%>			<tr>
		<td align="right" nowrap="nowrap"><%= (i+1) %></td>
<%	
		ArrayList record = (ArrayList)row.get(i);
		for(int j=0 ; j<record.size() ; j++){
%>			
		<td style="1px solid #CCCCFF" align="center" nowrap="nowrap"><%= record.get(j) %></td><%		}%>		</tr>
<%	}%>
</tbody>			</table>
</body>
</html>
<%!public ArrayList row		= null;public String[] columnName	= null;
String DB_URL;   		
String DB_USER;   		
String DB_PASSWORD;   	
String DB_JDBC_DRIVER;

public void setJDBC(String DB_URL, String DB_USER, String DB_PASSWORD, String DB_JDBC_DRIVER){
	this.DB_URL = DB_URL;
	this.DB_USER = DB_USER; 		
	this.DB_PASSWORD = DB_PASSWORD;  
	this.DB_JDBC_DRIVER = DB_JDBC_DRIVER;
}

public int getColumnCount(){	return columnName.length;
}
public String[] getColumnName(){	return columnName;
}
public ArrayList getResult(){	return row;
}

public void error(Exception e){
	row = new ArrayList();
	ArrayList record = new ArrayList();
	record.add(e.getMessage());
	row.add(record);

	columnName = new String[]{"error..."};
}

public void executeQueryJDBC(String query)throws Exception{
	Connection 			conn 	= null; 
	PreparedStatement 	pstmt 	= null; 
	ResultSet 			rs 		= null;

	row = new ArrayList();
	ArrayList record = null;
	int columnCnt = 0;
	try{
		Class.forName(DB_JDBC_DRIVER); 
		conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD); 
		pstmt = conn.prepareStatement(query);
		rs = pstmt.executeQuery();
		
		//get header name
		ResultSetMetaData meta = rs.getMetaData();
		columnCnt = meta.getColumnCount();
		columnName = new String[columnCnt];
		for(int k=0 ; k<columnCnt ; k++){
			columnName[k] = meta.getColumnName(k+1);
		}
		
		//get data
		while(rs.next()){
			record = new ArrayList();
	
			for(int i=0 ; i<columnCnt ; i++){
				Object obj = rs.getObject(i+1);
				String col = String.valueOf(obj);
				if(col == null || col.equals("null")) {col="(null)";}
				else if(col.trim().length() > 0){
					col = col.replaceAll("<","&lt;");
					col = col.replaceAll(">","&gt;");
				}else{
					col = col.replaceAll(" ","&nbsp;");
				}
				record.add(col);
			}
			row.add(record);
		}
	}catch(Exception e){
		error(e);
	}finally{
		if(rs != null) 		{	rs.close();		}
		if(pstmt != null) 	{	pstmt.close();	}
		if(conn != null) 	{	conn.close();	}
	}
}
public void executeQueryBWADK(String BWADK_DB_ALIAS, String query)throws Exception{
	
		com.hs.frmwk.db.BWDBHelper helper = null;
		row = new ArrayList();
		ArrayList record = null;
		int columnCnt = 0;

		try{
			if(BWADK_DB_ALIAS == null || BWADK_DB_ALIAS.trim().equals("")){
				helper = com.hs.frmwk.db.BWDBHelperFactory.getBWDBHelper();
			}else{
				helper = com.hs.frmwk.db.BWDBHelperFactory.getBWDBHelper(BWADK_DB_ALIAS);
			}
			helper.executeQuery(query);
			
			//get header name
			ResultSetMetaData meta = helper.getMetaData();
			columnCnt = meta.getColumnCount();
			columnName = new String[columnCnt];
			for(int k=0 ; k<columnCnt ; k++){
				columnName[k] = meta.getColumnName(k+1);
			}
			
			//get data
			while(helper.next()){
				record = new ArrayList();
				for(int i=0 ; i<columnCnt ; i++){
						String col = String.valueOf(helper.getRSObject(i+1));
						if(col == null || col.equals("null")) {col="(null)";}
						else if(col.trim().length() > 0){
							col = col.replaceAll("<","&lt;");
							col = col.replaceAll(">","&gt;");
						}else{
							col = col.replaceAll(" ","&nbsp;");
						}
						record.add(col);
				}
				row.add(record);
			}
		}catch(Exception e){
			error(e);
		}finally{
			if(helper != null) helper.close();
		}
	
}public void executeQueryJNDI(String jndi_name, String final_query)throws Exception{			row = new ArrayList();		ArrayList record = null;		int columnCnt = 0;		Connection conn = null;		try{			Context initCtx = new InitialContext();			Context envCtx = (Context) initCtx.lookup("java:comp/env");			DataSource ds = (DataSource) envCtx.lookup(jndi_name);			conn = ds.getConnection();			Statement stmt = conn.createStatement();			ResultSet rs = stmt.executeQuery(final_query);						//get header name			ResultSetMetaData meta = rs.getMetaData();			columnCnt = meta.getColumnCount();			columnName = new String[columnCnt];			for(int k=0 ; k<columnCnt ; k++){				columnName[k] = meta.getColumnName(k+1);			}						//get data			while(rs.next()){				record = new ArrayList();				for(int i=0 ; i<columnCnt ; i++){						String col = String.valueOf(rs.getObject(i+1));						if(col == null || col.equals("null")) {col="(null)";}						else if(col.trim().length() > 0){							col = col.replaceAll("<","&lt;");							col = col.replaceAll(">","&gt;");						}else{							col = col.replaceAll(" ","&nbsp;");						}						record.add(col);				}				row.add(record);			}		}catch(Exception e){			error(e);		}finally{			if(conn != null) conn.close();		}	}
%>