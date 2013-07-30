<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"      uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn"     uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix='form'   uri='http://www.springframework.org/tags/form'%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>RequestMapping List</title>
<style type="text/css">
ol {color: red; font-size:0.8em;}
li {}
dl {border-bottom: 1px solid orange; border-radius: 10px; margin: 0;}
dt {font-size:10pt; color:lightgray;}
dd {color:gray; font-size:9pt; display: none;}
em {color:navy; font-size:1em; font-style: normal;}
</style>
<!--[if lt IE 9]>
<script src="http://html5shiv.googlecode.com/svn/trunk/html5.js"></script>
<![endif]-->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.8.3/jquery.min.js"></script>
<script type="text/javascript">
$(document).ready(function() {// /[/gi
//	$("body").html($('body').html().replace(/\[/g,"[<span class='highlighted'>"));
//	$("body").html($('body').html().replace(/\]/g,"</span>]"));
	
	$("dt").each(function() {
		$(this).html($(this).html().replace(/\[/g,"[<em>"));
		$(this).html($(this).html().replace(/\]/g,"</em>]"));
	});
	
});
</script>
</head>
<body>
<ol>
	<c:forEach items="${handlerMethodMap}" var="handlerMethod">
	<li>
		<dl>
			<dt title="<c:out value="${handlerMethod.value}" escapeXml="true"/>">${handlerMethod.key}</dt>
			<dd><c:out value="${handlerMethod.value}" escapeXml="true"/></dd>	
		</dl>
	</c:forEach>
</ol>
</body>
</html>
