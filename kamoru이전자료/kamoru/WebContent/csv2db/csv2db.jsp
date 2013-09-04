<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<%@ page import="com.oreilly.servlet.*, com.oreilly.servlet.multipart.*, java.io.*, java.util.*, java.sql.*" %>
<%!
//사이트에 맞게 수정
final String filedownpath = "E:\\temp";
final int    maxfilesize  = 3*1024*1024;
final String encoding     = "EUC-KR";
%>
<%
MultipartRequest multi = 
	new MultipartRequest(request,filedownpath,maxfilesize,encoding,new DefaultFileRenamePolicy());

// param var
String dburl = multi.getParameter("dburl");
String dbusr = multi.getParameter("dbuser");
String dbpwd = multi.getParameter("dbpwd");
String dbdrv = multi.getParameter("driver");
File   f     = multi.getFile("csvfile");

// data var
String    dbTableName  = null;
String[]  dbColumnName = null;
ArrayList dbDatalist   = new ArrayList();

// result var
int[] resultInt = null;

// read csv file
BufferedReader reader = new BufferedReader(new FileReader(f));
String line = null;
StringBuffer sb = new StringBuffer();
int lineCnt = 0;
int columnCnt = 0;
while((line = reader.readLine()) != null){
	lineCnt++;
	String[] columns = line.split(",");
	if(lineCnt == 1){
		dbTableName = columns[0];
	}else if(lineCnt == 2){
		dbColumnName = columns;
		columnCnt = columns.length;
	}else{
		dbDatalist.add(splitCSV(columns, columnCnt));
	}
}
reader.close();

// insert data
resultInt = insertDB(dburl, dbusr, dbpwd, dbdrv, dbTableName, dbColumnName, dbDatalist);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
<title> CSV 2 DB Action </title>
<link rel="stylesheet" type="text/css" href="csv2db.css">
</head>
<body>
<table border="0" cellpadding="5" cellspacing="0">
	<tr>
		<td class="UNDERLINE1">CSV File (Server side)</td>
		<td class="UNDERLINE2"><%= filedownpath + "/" + f.getName() %></td>
	</tr>
	<tr>
		<td class="UNDERLINE1">DBMS Info</td>
		<td class="UNDERLINE2"><%= dburl %><span style="color:red">/</span>
			<%= dbusr %><span style="color:red">/</span>
			<%= hiddenTxt(dbpwd) %><span style="color:red">/</span>
			<%= dbdrv %></td>
	</tr>		
	<tr>
		<td class="UNDERLINE1">Table Name</td>
		<td class="UNDERLINE2"><%= dbTableName %></td>
	</tr>		
	<tr>
		<td class="UNDERLINE1">Column list</td>
		<td class="UNDERLINE2"><%= showArrData(dbColumnName) %></td>
	</tr>		
	<tr>
		<td class="UNDERLINE1">Data size</td>
		<td class="UNDERLINE2"><%= dbDatalist.size() %></td>
	</tr>		
</table>
<br>
<table border="0" cellpadding="3" cellspacing="0" class="TABLE_BORDER1">
	<tr>
		<th class="TH_BORDER1">&nbsp;</th>
		<th class="TH_BORDER2">Result</th>
		<th class="TH_BORDER2" colspan="<%= dbColumnName.length %>">Data</th>
	</tr>
<% 	for(int i=0 ; i<dbDatalist.size() ; i++){ 
		String[] data = (String[])dbDatalist.get(i);
%>
	<tr>
		<td class="TD_BORDER1" align="right"><%= i+1 %></td>
		<td class="TD_BORDER2" align="center"><%= parseResult(resultInt[i]) %></td>
		<% for(int j=0 ; j<data.length ; j++){ %>
		<td class="TD_BORDER2"><%= replaceNull(data[j], "&nbsp;") %></td>
		<% 	} %>
	</tr>
<% 	} %>
</table>
</body>
</html>
<%!
public int[] insertDB(String dburl, String dbusr, String dbpwd, String dbdrv, String table, String[] columns, ArrayList datalist) throws Exception{
	Connection 			conn 	= null; 
	PreparedStatement 	pstmt 	= null; 
	ResultSet			rs      = null;
	String				sql     = null;
	int[]               cnt     = null;
	try{
		Class.forName(dbdrv); 
		conn = DriverManager.getConnection(dburl, dbusr, dbpwd); 
		//System.out.println("get Connection");
		
		sql = getSQL(table, columns);
		//System.out.println(sql);
		pstmt = conn.prepareStatement(sql);
		for(int i=0 ; i<datalist.size() ; i++){
			String[] dataArr = (String[])datalist.get(i);
			for(int idx=0 ; idx<dataArr.length ; idx++){
				//System.out.println(idx+1 + ":" + dataArr[idx]);
				pstmt.setString(idx+1, dataArr[idx]);
			}
			pstmt.addBatch();
		}
		cnt = pstmt.executeBatch();
		//System.out.println("execute Batch");
	}catch(Exception e){
		e.printStackTrace();
		throw e;
	}finally{
		if(conn != null){
			conn.close();
			//System.out.println("close Connection");
		}
	}
	return cnt;
}

public String getSQL(String table, String[] columns){
	StringBuffer sql = new StringBuffer();
	sql.append("INSERT INTO ").append(table).append("(");
	for(int i=0 ; i<columns.length ; i++){
		sql.append(columns[i]);
		if(i < columns.length -1){
			sql.append(",");
		}
	}
	sql.append(") VALUES(");
	for(int i=0 ; i<columns.length ; i++){
		sql.append("?");
		if(i < columns.length -1){
			sql.append(",");
		}
	}
	sql.append(")");
	return sql.toString();
}

public String[] splitCSV(String[] columns, int columnCnt){
	String[] strArr = new String[columnCnt];
	String temp = "";
	boolean conti = false;
	int cnt = 0;
	for(int i=0 ; i<columns.length ; i++){
		if(columns[i].startsWith("\"")){
			conti = true;
			temp = "";
			temp += columns[i].substring(1) + ",";
		}else if(columns[i].endsWith("\"")){
			conti = false;
			temp += columns[i].substring(0, columns[i].length()-1);
			strArr[cnt++] = temp;
		}else if(conti){
			temp += columns[i]+",";
		}else{
			strArr[cnt++] = columns[i];
		}
	}
	return strArr;
}
public String showArrData(String[] data){
	String str = "";
	for(int i=0 ; i<data.length ; i++){
		str += "[" + data[i] + "] ";
	}
	return str;
}
public String hiddenTxt(String str){
	String ret = "";
	if(str == null){
		return null;
	}
	for(int i=0 ; i<str.length() ; i++){
		ret += "*";	
	}
	return ret;
}
public String parseResult(int i){
	String ret = null;
	if(i >= 0 || i == Statement.SUCCESS_NO_INFO){
		ret = "Ok";
	}else if(i == Statement.EXECUTE_FAILED){
		ret = "Fail";
	}
	return ret;
}
public String replaceNull(String str, String rep){
	return str == null ? rep : str;
}
%>