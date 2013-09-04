<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>kAmOrU</title>
</head>
<body style="background: #FFFFFF url(/images/cat_bg.jpg) repeat left; margin:0;">
<table width="100%" border="0" cellpadding="0" cellspacing="0">
	<tr height="7" onclick="javascript:location.href='/'">
		<td bgcolor="#006400"></td>
	</tr>
</table>

<div id="content" align="center" width="1100">
<table border="0" cellspacing="5">
	<tr valign="top">
		<td width="200" align="left">
			<jsp:include page="/favorites/favorites_inc_l.html"/>
		</td>
		<td width="700" align="left">
			<jsp:include page="/bbs/index.jsp" flush="false" />
		</td>
		<td width="200" align="left">
			<jsp:include page="/favorites/favorites_inc_r.html"/>
		</td>
	</tr>
</table>
</div>
</body>
</html>