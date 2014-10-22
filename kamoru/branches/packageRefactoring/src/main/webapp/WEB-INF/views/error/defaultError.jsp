<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html>
<html>
<head>
<title><s:message code="default.error"/></title>
<script type="text/javascript">
if (self.innerHeight == 0)
	alert('${exception.message}');
</script>
</head>
<body>

<h1><s:message code="default.error"/></h1>
<h2 style="color:red;">${exception.message}</h2>

<div>
	<span class="label" onclick="$('#stack-trace').toggle()"><s:message code="default.stacktrace"/></span>
	<span class="label" onclick="$('#request-attribute').toggle()"><s:message code="default.request-attribute"/></span>
</div>

<div id="stack-trace" style="display:none;">
	<h4>${exception}</h4>
	<ul class="code-view">
		<c:forEach items="${exception.stackTrace}" var="stackTrace">
		<li><code class="code-value">${stackTrace }</code></li>
		</c:forEach>
	</ul>
</div>

<div id="request-attribute" style="display:none;">
	<h4><s:message code="default.request-attribute"/></h4>
	<ol class="code-view">
<%
@SuppressWarnings("rawtypes")
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