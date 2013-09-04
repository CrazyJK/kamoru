<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql"%>
<%@ taglib prefix="x"   uri="http://java.sun.com/jsp/jstl/xml"%>
<%@ taglib prefix="fn"  uri="http://java.sun.com/jsp/jstl/functions"%>
<c:set var="contextpath" scope="application" value="<%=request.getContextPath()%>"/>
<% 
System.out.println(
		"[" + new java.text.SimpleDateFormat("yyyy-MM-dd' 'HH:mm:ss").format(new java.util.Date()) + "] "
	+ this.getClass().getName()		
); 
%>