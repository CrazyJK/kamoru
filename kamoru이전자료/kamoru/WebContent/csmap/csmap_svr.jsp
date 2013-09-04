<%@ page language="java" contentType="text/plain; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.*, com.hs.frmwk.db.*, java.sql.*" %>
<%@ include file="jspf/helper.jspf" %>
<%
response.setHeader("Pragma","No-cache");
response.setHeader("Expires","0");
response.setHeader("Cache-Control","no-cache");
%>
<c:choose>
	<c:when test="${param.reqid eq 'sitelist'}">
		<c:out value="<%= executeQueryGetArrayText("SELECT * FROM handymap ORDER BY gubun, name") %>" escapeXml="false"/>
	</c:when>
	<c:otherwise>
		[{error : "Unknown request"}]
	</c:otherwise>
</c:choose>

