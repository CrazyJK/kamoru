<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<%@ page import="java.util.*, com.hs.frmwk.db.*"%>
<%
BWDBHelper helper = null;
String tags = new String();
String[] tagArr = null;
ArrayList tagList = null;
try{
	helper = BWDBHelperFactory.getBWDBHelper();
	helper.beginTransaction();

	helper.setPreparedSQL("SELECT tags FROM bbs WHERE state = 'N' ");
	helper.executeQuery();
	while(helper.next()){
		tags += "," + helper.getRSString(1);
	}
	
	
	helper.setPreparedSQL("UPDATE bbs_tag SET status = 0");
	helper.executeUpdate();
	
	tagArr = tags.split(",");
	helper.setPreparedSQL("UPDATE bbs_tag SET status = status +1 WHERE tagid = ?");
	for(int i=0 ; i<tagArr.length ; i++){
		helper.setString(1,tagArr[i]);		
		helper.executeUpdate();
	}

	helper.setPreparedSQL("SELECT * FROM bbs_tag ORDER BY status DESC ");
	helper.executeQuery();
	tagList = new ArrayList();
	while(helper.next()){
		tagList.add(new String[]{
				helper.getRSString(1), helper.getRSString(2), helper.getRSString(3), helper.getRSString(4)
		});
	}
	
	helper.commit();
}catch(Exception e){
	if(helper != null) helper.rollback();
	out.println("<h2 style='color=red'>" + e.getMessage() + "</h2>");
	e.printStackTrace();
}finally{
	if(helper != null) helper.close();
}
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
<title> Tag Count Á¤¸®</title>
</head>
<body>
tags : <%= tags %><br>
taglength : <%= tagArr.length %>
<table>
	<thead>
		<tr>
			<th>TagID</th>
			<th>TagName</th>
			<th>Status</th>
			<th>Creatdtime</th>
		</tr>
	</thead>
	<tbody>
	<% for(int i=0 ; i<tagList.size() ; i++) { tagArr = (String[]) tagList.get(i); %>
		<tr>
			<td><%= tagArr[0] %></td>
			<td><%= tagArr[1] %></td>
			<td><%= tagArr[2] %></td>
			<td><%= tagArr[3] %></td>
		</tr>
	<% } %>
	</tbody>
</table>
</body>
</html>