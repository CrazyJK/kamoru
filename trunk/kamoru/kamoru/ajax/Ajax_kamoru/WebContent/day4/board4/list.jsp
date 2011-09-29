<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Insert title here</title>
</head>
<body>

<%--댓글 목록을 얻기 위한 SQL 구문을 작성한다. --%>
<sql:query var="rs" dataSource="jdbc/MySQLDB">
select board_id, title, writer, makedate, content from t_board
</sql:query>

<table border="1">
    <tr>
      <th width="10%">번호</th>
      <th >제목</th>
      <th >내용</th>
      <th width="10%">작성자</th>
      <th width="30%">작성일시</th>
    </tr>
<c:forEach var="row" items="${rs.rows}">
    <tr>
      <td>${row.board_id}</td>
      <td>${row.title}</td>
      <td>${row.content}</td>
      <td>${row.writer}</td>
      <td>${row.makedate}</td>
    </tr>
</c:forEach>
</table>

<a href="insertForm.jsp">등록</a>
<a href="delete.jsp">삭제</a>

</body>
</html>