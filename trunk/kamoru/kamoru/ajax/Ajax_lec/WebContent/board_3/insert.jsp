<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf8"%>
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

insert into t_board (title, writer, passwd, content, makedate) values ('제목01', '홍길동', 'passwd', '게시물내용', '2008-11-27');
insert into t_board (title, writer, passwd, content, makedate) values ('제목02', '홍길동', 'passwd', '게시물내용', '2008-11-27');
insert into t_board (title, writer, passwd, content, makedate) values ('제목03', '홍길동', 'passwd', '게시물내용', '2008-11-27');
insert into t_board (title, writer, passwd, content, makedate) values ('제목04', '홍길동', 'passwd', '게시물내용', '2008-11-27');
insert into t_board (title, writer, passwd, content, makedate) values ('제목05', '홍길동', 'passwd', '게시물내용', '2008-11-27');
insert into t_board (title, writer, passwd, content, makedate) values ('제목06', '홍길동', 'passwd', '게시물내용', '2008-11-27');
insert into t_board (title, writer, passwd, content, makedate) values ('제목07', '홍길동', 'passwd', '게시물내용', '2008-11-27');
insert into t_board (title, writer, passwd, content, makedate) values ('제목08', '홍길동', 'passwd', '게시물내용', '2008-11-27');
insert into t_board (title, writer, passwd, content, makedate) values ('제목09', '홍길동', 'passwd', '게시물내용', '2008-11-27');
insert into t_board (title, writer, passwd, content, makedate) values ('제목10', '홍길동', 'passwd', '게시물내용', '2008-11-27');
insert into t_board (title, writer, passwd, content, makedate) values ('제목11', '홍길동', 'passwd', '게시물내용', '2008-11-27');
insert into t_board (title, writer, passwd, content, makedate) values ('제목12', '홍길동', 'passwd', '게시물내용', '2008-11-27');
insert into t_board (title, writer, passwd, content, makedate) values ('제목13', '홍길동', 'passwd', '게시물내용', '2008-11-27');
insert into t_board (title, writer, passwd, content, makedate) values ('제목14', '홍길동', 'passwd', '게시물내용', '2008-11-27');
insert into t_board (title, writer, passwd, content, makedate) values ('제목15', '홍길동', 'passwd', '게시물내용', '2008-11-27');
insert into t_board (title, writer, passwd, content, makedate) values ('제목16', '홍길동', 'passwd', '게시물내용', '2008-11-27');
insert into t_board (title, writer, passwd, content, makedate) values ('제목17', '홍길동', 'passwd', '게시물내용', '2008-11-27');
insert into t_board (title, writer, passwd, content, makedate) values ('제목18', '홍길동', 'passwd', '게시물내용', '2008-11-27');
insert into t_board (title, writer, passwd, content, makedate) values ('제목19', '홍길동', 'passwd', '게시물내용', '2008-11-27');
insert into t_board (title, writer, passwd, content, makedate) values ('제목20', '홍길동', 'passwd', '게시물내용', '2008-11-27');
insert into t_board (title, writer, passwd, content, makedate) values ('제목21', '홍길동', 'passwd', '게시물내용', '2008-11-27');
insert into t_board (title, writer, passwd, content, makedate) values ('제목22', '홍길동', 'passwd', '게시물내용', '2008-11-27');
insert into t_board (title, writer, passwd, content, makedate) values ('제목23', '홍길동', 'passwd', '게시물내용', '2008-11-27');
insert into t_board (title, writer, passwd, content, makedate) values ('제목24', '홍길동', 'passwd', '게시물내용', '2008-11-27');
insert into t_board (title, writer, passwd, content, makedate) values ('제목25', '홍길동', 'passwd', '게시물내용', '2008-11-27');
insert into t_board (title, writer, passwd, content, makedate) values ('제목26', '홍길동', 'passwd', '게시물내용', '2008-11-27');
insert into t_board (title, writer, passwd, content, makedate) values ('제목27', '홍길동', 'passwd', '게시물내용', '2008-11-27');
insert into t_board (title, writer, passwd, content, makedate) values ('제목28', '홍길동', 'passwd', '게시물내용', '2008-11-27');
insert into t_board (title, writer, passwd, content, makedate) values ('제목29', '홍길동', 'passwd', '게시물내용', '2008-11-27');
insert into t_board (title, writer, passwd, content, makedate) values ('제목30', '홍길동', 'passwd', '게시물내용', '2008-11-27');
insert into t_board (title, writer, passwd, content, makedate) values ('제목31', '홍길동', 'passwd', '게시물내용', '2008-11-27');
insert into t_board (title, writer, passwd, content, makedate) values ('제목32', '홍길동', 'passwd', '게시물내용', '2008-11-27');
insert into t_board (title, writer, passwd, content, makedate) values ('제목33', '홍길동', 'passwd', '게시물내용', '2008-11-27');
insert into t_board (title, writer, passwd, content, makedate) values ('제목34', '홍길동', 'passwd', '게시물내용', '2008-11-27');
insert into t_board (title, writer, passwd, content, makedate) values ('제목35', '홍길동', 'passwd', '게시물내용', '2008-11-27');
insert into t_board (title, writer, passwd, content, makedate) values ('제목36', '홍길동', 'passwd', '게시물내용', '2008-11-27');
insert into t_board (title, writer, passwd, content, makedate) values ('제목37', '홍길동', 'passwd', '게시물내용', '2008-11-27');
insert into t_board (title, writer, passwd, content, makedate) values ('제목38', '홍길동', 'passwd', '게시물내용', '2008-11-27');



 -->
 
<%@page import="com.multicampus.BoardCache"%>
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
      <%BoardCache.reload();%>
      <c:redirect url="board.html" />
    </c:otherwise>
  </c:choose> 
 

