<%@ page language="java" contentType="text/plain; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql" %>

<% 
response.setHeader("Pragma","No-cache");
response.setHeader("Expires","0");
response.setHeader("Cache-Control","no-cache");
%>
<%--댓글 목록을 얻기 위한 SQL 구문을 작성한다. --%>
<sql:query var="rs" dataSource="jdbc/kamoruDB">
SELECT bbsid, tags, title, content, creator, creatdtime, hit, state, modifier, modifydtime FROM bbs WHERE rownum < 10
</sql:query>

[
<c:forEach var="row" items="${rs.rows}" varStatus="s">
  ${s.count == 1 ? "" : ","}
    {
       bbsid        :  ${row.bbsid}
     , tags         : '${row.tags}'
     , title        : '${row.title       }'   
     , content      : '${row.content     }'
     , creator      : '${row.creator     }'
     , creatdtime   : '${row.creatdtime  }'
     , hit          :  ${row.hit         }
     , state        : '${row.state       }'
     , modifier     : '${row.modifier    }'
     , modifydtime  : '${row.modifydtime }'
    }
</c:forEach>
]
