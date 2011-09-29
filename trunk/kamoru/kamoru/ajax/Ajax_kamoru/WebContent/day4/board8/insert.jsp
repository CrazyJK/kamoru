<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql" %>
<%--한글 처리를 위해 utf8 로 변경한다 --%> 
<%request.setCharacterEncoding("utf-8");%>

<!-- 
DROP TABLE IF EXISTS `test`.`t_board`;
CREATE TABLE  `test`.`t_board` (
  `board_id` int(10) unsigned NOT NULL auto_increment,
  `title` varchar(255) NOT NULL,
  `writer` varchar(45) NOT NULL,
  `passwd` varchar(45) NOT NULL,
  `content` text NOT NULL,
  `makedate` datetime NOT NULL,
  PRIMARY KEY  (`board_id`)
) ENGINE=InnoDB DEFAULT CHARSET=euckr;


insert into `test`.`t_board` (title, writer, passwd, content, makedate) values ('title1', 'hong', '1111', 'contents', now());
insert into `test`.`t_board` (title, writer, passwd, content, makedate) values ('title2', 'hong', '1111', 'contents', now());
insert into `test`.`t_board` (title, writer, passwd, content, makedate) values ('title3', 'hong', '1111', 'contents', now());
insert into `test`.`t_board` (title, writer, passwd, content, makedate) values ('title4', 'hong', '1111', 'contents', now());
insert into `test`.`t_board` (title, writer, passwd, content, makedate) values ('title5', 'hong', '1111', 'contents', now());
insert into `test`.`t_board` (title, writer, passwd, content, makedate) values ('title6', 'hong', '1111', 'contents', now());
insert into `test`.`t_board` (title, writer, passwd, content, makedate) values ('title7', 'hong', '1111', 'contents', now());
insert into `test`.`t_board` (title, writer, passwd, content, makedate) values ('title8', 'hong', '1111', 'contents', now());
insert into `test`.`t_board` (title, writer, passwd, content, makedate) values ('title9', 'hong', '1111', 'contents', now());
insert into `test`.`t_board` (title, writer, passwd, content, makedate) values ('title10', 'hong', '1111', 'contents', now());
insert into `test`.`t_board` (title, writer, passwd, content, makedate) values ('title11', 'hong', '1111', 'contents', now());
insert into `test`.`t_board` (title, writer, passwd, content, makedate) values ('title12', 'hong', '1111', 'contents', now());
insert into `test`.`t_board` (title, writer, passwd, content, makedate) values ('title13', 'hong', '1111', 'contents', now());
insert into `test`.`t_board` (title, writer, passwd, content, makedate) values ('title14', 'hong', '1111', 'contents', now());
insert into `test`.`t_board` (title, writer, passwd, content, makedate) values ('title15', 'hong', '1111', 'contents', now());
insert into `test`.`t_board` (title, writer, passwd, content, makedate) values ('title16', 'hong', '1111', 'contents', now());
insert into `test`.`t_board` (title, writer, passwd, content, makedate) values ('title17', 'hong', '1111', 'contents', now());
insert into `test`.`t_board` (title, writer, passwd, content, makedate) values ('title18', 'hong', '1111', 'contents', now());
insert into `test`.`t_board` (title, writer, passwd, content, makedate) values ('title19', 'hong', '1111', 'contents', now());
insert into `test`.`t_board` (title, writer, passwd, content, makedate) values ('title20', 'hong', '1111', 'contents', now());
insert into `test`.`t_board` (title, writer, passwd, content, makedate) values ('title21', 'hong', '1111', 'contents', now());
insert into `test`.`t_board` (title, writer, passwd, content, makedate) values ('title22', 'hong', '1111', 'contents', now());
insert into `test`.`t_board` (title, writer, passwd, content, makedate) values ('title23', 'hong', '1111', 'contents', now());
insert into `test`.`t_board` (title, writer, passwd, content, makedate) values ('title24', 'hong', '1111', 'contents', now());




















 -->
 
<c:catch var="e">
  <%-- 댓글을 등록한다 --%>
  <sql:update var="update" dataSource="jdbc/MySQLDB">
    insert into t_board (title, writer, passwd, content, makedate) values (?, ?, ?, ?, now())
    <%-- EL 구문을 이용하여 요청 인자를 읽어  값을 설정한다.--%>
    <sql:param value="${param.title}"/>
    <sql:param value="${param.writer}"/>
    <sql:param value="${param.passwd}"/>
    <sql:param value="${param.content}"/>
  </sql:update> 
  
  <%--  등록된 신규 아이디를 얻는다  --%>
  <sql:query var="rs" dataSource="jdbc/MySQLDB">
    select board_id from t_board where board_id = LAST_INSERT_ID()
  </sql:query>
  
</c:catch>

  <c:choose>
    <%-- 오류 발생 --%>
    <c:when test="${e != null}">
      ${e}
    </c:when>
    <%-- 작업 성공시  --%>
    <c:otherwise>
      <c:redirect url="list.jsp" />
    </c:otherwise>
  </c:choose> 
 

