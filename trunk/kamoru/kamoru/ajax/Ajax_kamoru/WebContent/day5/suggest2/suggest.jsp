<%@ page language="java" contentType="text/plain; charset=EUC-KR" pageEncoding="EUC-KR"%>
<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql" %>
<% 
  //요청인자 캐릭터 셋을 utf8로 변환한다.
  request.setCharacterEncoding("utf-8");
%>
<%! public static long callCount = 0; %>
<%
//케싱이 되지 않도록 해더를 설정한다.
response.setHeader("Pragma", "No-cache");
response.setHeader("Expires", "0");
response.setHeader("Cache-Control", "no-cache");

System.out.println("suggest2 call " + ++callCount);
%>

<%--댓글 목록을 얻기 위한 SQL 구문을 작성한다. --%>
<sql:query var="rs" dataSource="jdbc/MySQLDB">
  select contents from tb_suggest where keyword like ? order by rank DESC limit 0,3
  <sql:param value="%${param.keyword}%"/>
</sql:query>

<%--댓글 목록을 csv 로 출력한다. --%>
<c:forEach var="row" items="${rs.rows}" varStatus="status"><c:if test="${status.count != 1}">,</c:if>${row.contents}</c:forEach>
