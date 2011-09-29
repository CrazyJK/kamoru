<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql" %>

<%
response.setHeader("Pragma", "No-cache");
response.setHeader("Expires", "0");
response.setHeader("Cache-Control", "no-cache");
System.out.println("list2.jsp called");
%>

<sql:query var="rs" dataSource="jdbc/MySQLDB">
	select * from t_board order by board_id desc limit 0, 10
</sql:query>

[
	<c:forEach var="row" items="${rs.rows}" varStatus="s">
		<c:if test="${s.count != 1}">,</c:if>
		{
		 board_id : '${row.board_id }'
		,writer   : '${row.writer }'
		,title    : '${row.title }'
		,makedate : '${row.makedate}'	
		,ref_cnt  : '${row.ref_cnt}'
		}
	</c:forEach>
]
