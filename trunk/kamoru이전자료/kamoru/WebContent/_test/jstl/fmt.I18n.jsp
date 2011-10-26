<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>JSTL &gt; I18n </title>
</head>
<body>

<h2>Setting Locale</h2>
<h3>Localization context tag : setLocale, setTimeZone, timeZone</h3>

<ul>로케일 설정
<fmt:setLocale value="ko_KR" scope="page" variant="WIN"/>
</ul>

<ul>사용가능한 타임존명 파악 
	<input type="button" value="보기" onclick="document.getElementById('timezoneids').style.display='';"/>
	<input type="button" value="숨기기" onclick="document.getElementById('timezoneids').style.display='none';"/>
<div id="timezoneids" style="display:none">
<%String[] ids = java.util.TimeZone.getAvailableIDs();
for(String id : ids)
	out.println("&nbsp;" + id);%>
</div>
</ul>

<ul>타임존
<li/>현재의 타임존 파악 : <%= java.util.TimeZone.getDefault() %>
<li>&lt;fmt:setTimeZone value="타임존명 or java.util.TimeZone" var="인스턴스화된 timezone값이 var에 저장되고, 현재의 타임존은 변화없음" scope="scope" /&gt;
</li>
</ul>


<h2>Messaging</h2>
<h3>Message tags : setBundle, bundle, message, param</h3>
<ul>
<fmt:setLocale value="ko_KR"/>
<fmt:bundle basename="fmt">
	<fmt:message key="test.Greeting.greeting">
		<fmt:param value="남종관"/>
	</fmt:message>
</fmt:bundle>
<br/>
<fmt:setLocale value="en_US"/>
<fmt:bundle basename="fmt">
	<fmt:message key="test.Greeting.greeting">
		<fmt:param value="남종관"/>
	</fmt:message>
</fmt:bundle>
</ul>


<h2>Number and Date Formatting</h2>
<h3>Date tags : formatDate, parseDate</h3>
<ul>formatDate
	<jsp:useBean id="date" class="java.util.Date"/>
	<li/>c:out <c:out value="${date}"/>
	<li/>Default : <fmt:formatDate value="${date}"/>
	<li/>type both : <fmt:formatDate value="${date}" type="both"/>
	<li/>style default : <fmt:formatDate value="${date}" type="both" dateStyle="default" timeStyle="default"/>
	<li/>style short : <fmt:formatDate value="${date}" type="both" dateStyle="short" timeStyle="short"/>
	<li/>style medium : <fmt:formatDate value="${date}" type="both" dateStyle="medium" timeStyle="medium"/>
	<li/>style long : <fmt:formatDate value="${date}" type="both" dateStyle="long" timeStyle="long"/>
	<li/>style full : <fmt:formatDate value="${date}" type="both" dateStyle="full" timeStyle="full"/>
	<li/>pattern : <fmt:formatDate value="${date}" pattern="yyyy/MM/dd hh:mm:ss"/>
</ul>

<ul>parseDate
<c:set var="dateString">06. 4. 1 오후 7:03</c:set>
<fmt:parseDate 
	value="${dateString}" 
	parseLocale="ko_KR"
	type="both"
	dateStyle="short"
	timeStyle="short"
	var="pDate"	/>
<li/>${pDate}
</ul>

<h3>Number tags : formatNumber, parseNumber</h3>
<ul>formatNumber : currency
<fmt:setLocale value="ko_KR"/>
<li/>locale ko_KR <fmt:formatNumber value="200000" type="currency"/>
<fmt:setLocale value="en_US"/>
<li/>locale en_US <fmt:formatNumber value="200000" type="currency"/>
<fmt:setLocale value="en_GB"/>
<li/>locale en_GB <fmt:formatNumber value="200000" type="currency"/>
</ul>
<ul>formatNumber : number
<li/><fmt:formatNumber value="200000" type="number" groupingUsed="true"/>
<li/><fmt:formatNumber value="200000" type="number" groupingUsed="false"/>
</ul>
<ul>formatNumner : percent
<li/><fmt:formatNumber value="0.14" type="percent"/>
</ul>
<ul>formatNumner : pattern
<li/><fmt:formatNumber value="1239876543.612345" pattern="000000.000"/>
<li/><fmt:formatNumber value="1239876543.612345" pattern="#,#00.0#"/>
</ul>


</body>
</html>