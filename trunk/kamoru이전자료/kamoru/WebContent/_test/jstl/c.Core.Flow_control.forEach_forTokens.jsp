<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>JSTL &gt; Core &gt; Flow control &gt; c:forEach, c:forEach </title>
<style type="text/css">
div {border:1px; padding:10;}
</style>
</head>
<body>

<ul>세션정보 출력
<c:forEach var="sessions" items="${sessionScope}" varStatus="status">
	<li><c:out value="${status.count} - "></c:out>
	<c:out value="${sessions.key}=${sessions.value}"/>
</c:forEach>
</ul>

<ul>10사이의 짝수의 제곱을 구함
<c:forEach var="x" begin="0" end="10" step="2">
	<li><c:out value="${x} * ${x} = ${x*x}"/>
</c:forEach>
</ul>

<ul>TP헤더를 출력(Map컬렉션)
<c:forEach var="x" items="${header}" varStatus="status">
	<li><c:out value="${status.count}"/>
	<c:out value="${x.key}=${x.value}"/>
</c:forEach>
</ul>

<ul>콤마가 delimeter인 문자열 출력
<c:set var="fruits" value="banana,apple,tomato,melon,딸기"/>
<c:forEach var="x" items="${fruits}" varStatus="s">
	<li><c:out value="${x}"/></li>
</c:forEach>
</ul>

<ul>delimeter가 콤마가 아니라면 forTokens를 사용
<c:set var="fruits" value="banana_|~apple_|~tomato_|~melon_|~딸기"/>
<c:forTokens var="x" items="${fruits}" delims="_|~">
	<li/><c:out value="${x}"/>
</c:forTokens>
</ul>
</body>
</html>