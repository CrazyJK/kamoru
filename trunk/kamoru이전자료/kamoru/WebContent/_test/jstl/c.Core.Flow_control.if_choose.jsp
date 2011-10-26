<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>JSTL &gt; Core &gt; Flow control &gt; c:if, c:choose</title>
</head>
<body>
<ul>header정보 출력, 첫번째에 추가 메시지 출력
<c:forEach var="x" items="${header}" varStatus="status">
	<c:if test="${status.first}">
		[HTTP Header]<br/>
	</c:if>
	<li/>${status.count}.${x.key}=${x.value}
</c:forEach>
</ul>

<ul>choose구문을 이용하여 scheme가 무엇인지 확인하고 메시지 출력

<c:choose>
	<c:when test='${pageContext.request.scheme == "http"}'>
		This is an insecure Web session.
	</c:when>
	<c:when test='${pageContext.request.scheme eq "https"}'>
		This is an secure Web session.
	</c:when>
	<c:otherwise>
		You are using an unrecognized Web protocol. How did this happen?
	</c:otherwise>
</c:choose>
</ul>
</body>
</html>