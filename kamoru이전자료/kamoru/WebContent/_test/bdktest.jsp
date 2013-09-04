<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.hs.frmwk.db.*" %>
<%
BWDBHelper helper = BWDBHelperFactory.getBWDBHelper();
//String sql = BWQueryLoader.getQuery("MY_WORKLIST01");
helper.setPreparedSQL("SELECT * FROM bbs");
helper.executeQuery();
while(helper.next()){
	helper.getRSString(1);
}
helper.close();
%>