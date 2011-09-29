<%@ page language="java" contentType="text/plain; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql" %>
<%
//케싱이 되지 않도록 해더를 설정한다.
response.setHeader("Pragma", "No-cache");
response.setHeader("Expires", "0");
response.setHeader("Cache-Control", "no-cache");
%>

<%--댓글 목록을 얻기 위한 SQL 구문을 작성한다. --%>
<sql:query var="rs" dataSource="jdbc/MySQLDB">
select board_id, title, writer, makedate from t_board order by board_id desc 
</sql:query>

[
<c:forEach var="row" items="${rs.rows}" varStatus="s">
	${s.count != 1 ? "," : ""} {board_id : ${row.board_id}, title : '${row.title}',writer : '${row.writer}', makedate :'${row.makedate}'}
</c:forEach>
]
