<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>JSTL &gt; Core &gt; URL management &gt; url, import, redirect</title>
</head>
<body>
<h3>URL</h3>
<ul>URL 예제
<li/><a href="<c:url value='/common/info.jsp'/>">anker1</a>
<li/><c:url value="/common/jrecheck.html">
	<c:param name="keyword" value="${searchTime}너때문에"/>
	<c:param name="month">02/2003</c:param>
</c:url>
</ul>

<ul>var를 지정하면 화면에 출력하지 않고 이 변수에 내용이 저장
<c:url var="myUrl" value="/common/info.jsp">
	<c:param name="keyword">애프터스쿨</c:param>
	<c:param name="month" value="34ㅈㄷ9ㅇ라"/>
</c:url>
<li/><c:out value="${myUrl}"/>
</ul>

<h3>import</h3>

<ul>import예제
<li/>include derective : jsp페이지가 컴파일될 때 포함된다
<li/>&lt;jsp:include&gt; : jsp페이지가 호출될 때 포함된다
<li/>&lt;c:import&gt; : jsp페이지가 호출될 때 포함된다. &lt;jsp:include&gt;의 확장판으로 외부의 리소스까지 include할수 있다
<li/><fieldset>
	<c:catch var="e">
	<c:import url="http://www.google.co.kr/search">
		<c:param name="q" value="하이킥"/>
	</c:import>
	</c:catch>
	<c:if test="${not empty e}">
		${e.message }
	</c:if>
</fieldset>
</ul>

<ul>var를 지정하면 화면에 출력하지 않고 내용이 저장됨</ul>
<li>
	<c:import var="google" url="http://www.google.co.kr"/>
	<c:if test="${not empty google}">
		<pre><c:out value="${google}"/></pre>
	</c:if>
</li>

<h3>redirect</h3>

<ul>javax.servlet.http.HttpServletResponse 의 sendRedirect()와 동일
<li>&lt;c:redirect url="error.jsp"/&gt;
</li>
</ul>

</body>
</html>