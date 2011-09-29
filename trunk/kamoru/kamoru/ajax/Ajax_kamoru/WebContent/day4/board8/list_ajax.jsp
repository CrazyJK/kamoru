<%@ page language="java" contentType="text/plain; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql" %>

<%
response.setHeader("Pragma","No-cache");
response.setHeader("Expires","0");
response.setHeader("Cache-Control","no-cache");
%>
<%--댓글 목록을 얻기 위한 SQL 구문을 작성한다. --%>
<sql:query var="rs" dataSource="jdbc/MySQLDB">
select board_id, title, writer, makedate, content from t_board ORDER BY board_id DESC limit 0,10
</sql:query>

[
<c:forEach var="row" items="${rs.rows}" varStatus="s">
  ${s.count == 1 ? "" : ","}
    {
      board_id :  ${row.board_id}
     ,title    : '${row.title}'
     ,content  : '${row.content}'
     ,writer   : '${row.writer}'
     ,makedate : '${row.makedate}'
    }
</c:forEach>
]
