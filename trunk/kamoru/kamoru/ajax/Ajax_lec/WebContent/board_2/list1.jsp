<%@ page language="java" contentType="text/plain; charset=utf8" pageEncoding="utf8"%>
<%@ taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql" %>

<%--댓글 목록을 얻기 위한 SQL 구문을 작성한다. --%>
<sql:query var="rs" dataSource="jdbc/MySQLDB">
select board_id, title, writer, makedate from t_board
</sql:query>

[
<c:forEach var="row" items="${rs.rows}">
    {
      board_id : ${row.board_id}, 
      title      : ${row.title} ,
      writer    : ${row.writer}, 
      makedate :${row.makedate}
    }
</c:forEach>
]
