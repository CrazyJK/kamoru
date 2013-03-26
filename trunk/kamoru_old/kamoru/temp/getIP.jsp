<%@ page language="java" contentType="text/plain; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c"   uri="http://java.sun.com/jstl/core"%>
<%@ taglib prefix="x"   uri="http://java.sun.com/jstl/xml"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt"%>
<%@ taglib prefix="sql" uri="http://java.sun.com/jstl/sql"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%
String clientIP = request.getRemoteAddr();
out.println(clientIP);
%>

${header.cookie}
<br>
<c:out value="bbbb"/>
<br>
${fn.trim("  aaaa     ")}
