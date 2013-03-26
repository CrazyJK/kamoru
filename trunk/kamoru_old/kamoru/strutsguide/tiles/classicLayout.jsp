<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/tlds/struts-tiles.tld" prefix="tiles" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><tiles:getAsString name="title" /></title>
</head>
<body>

<h1><tiles:getAsString name="title" /></h1>
<table border="1" width="100%">
	<tr>
		<td colspan="2"><tiles:insert attribute="header" /></td>
	</tr>
	<tr>
		<td width="140"><tiles:insert attribute="menu" /></td>
		<td><title:insert attribute="body" /></td>
	</tr>
	<tr>
		<td colspan="2"><tiles:insert attribute="footer" /></td>
	</tr>
</table>

</body>
</html>