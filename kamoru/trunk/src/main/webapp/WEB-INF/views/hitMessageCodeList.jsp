<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html>
<html>
<head>
<title><s:message code="hitMessageCodeList" text="Hit Message Code List" /></title>
<style type="text/css">
ol {
	color: red; font-size:0.8em;
}
li {
	font-size:13px;
	padding-left: 10px;
	margin-top: 1px;
}
.notExist {
	background-color: rgba(123,0,123,0.5);
	border-radius: 10px;
}
</style>
</head>
<body>

	<div>
		<h3>
			<s:message code="_title" text="Hit Message Code List" />
			<span style="float: right" onclick='$(".exist").toggle()'>Filter
				: no value code</span>
		</h3>
		<ol>
			<c:forEach items="${hitMessageCodeMap}" var="code">
				<li class="${code.value eq null ? 'notExist' : 'exist' }">
					<code class="code-name">${code.key}</code> = <code class="code-value">${code.value}</code>
				</li>
			</c:forEach>
		</ol>
	</div>

</body>
</html>