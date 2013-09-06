<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"      uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<title>Error</title>
</head>
<body>

<h1>Error</h1>
<h2 style="color:red;">${exceptionMsg.message}</h2>

<div>
	<span class="label" onclick="$('#stack-trace').toggle()">StackTrace</span>
	<span class="label" onclick="$('#request-attribute').toggle()">request Attribute</span>
</div>

<div id="stack-trace" style="display:none;">
	<h4>${exceptionMsg}</h4>
	<ul class="code-view">
		<c:forEach items="${exceptionMsg.stackTrace}" var="stackTrace">
		<li><code class="code-value">${stackTrace }</code></li>
		</c:forEach>
	</ul>
</div>

<div id="request-attribute" style="display:none;">
	<h4>request attribute</h4>
	<ol class="code-view">
<%
java.util.Enumeration e = request.getAttributeNames();
while (e.hasMoreElements()) {
	String name = (String)e.nextElement();
	Object value = request.getAttribute(name);
	String clazz = value.getClass().getName();
%>
		<li><code class="code-name"><%=name %></code>   
			<code class="code-value"><%=value %></code>
			<code class="code-clazz"><%=clazz %></code>
		</li>
<%
}
%>
	</ol>
</div>
</body>
</html>