<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%
String encodingStr = request.getParameter("encodingStr");
encodingStr = encodingStr != null ? encodingStr : "";
String decodingStr = java.net.URLDecoder.decode(encodingStr);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<h3>URL 인코딩된 문자열 디코드 하기</h3>
<form>
<table>
	<tr>
		<td>
			<fieldset>			
			<legend>인코딩 문자열</legend>
			<textarea rows="15" cols="30" name="encodingStr"><%= encodingStr %></textarea>
			</fieldset>
		</td>
		<td>
			==>
		</td>
		<td>
			<fieldset>
			<legend>디코딩 문자열</legend>
			<textarea rows="15" cols="30" name="decodingStr"><%= decodingStr %></textarea>
			</fieldset>
		</td>
	</tr>
	<tr>
		<td colspan="3">
			<input type="submit"/>
		</td>
	</tr>
</table>
</form>
</body>
</html>