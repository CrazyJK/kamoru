<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script type="text/javascript">
function jsAction(){
	frm.target = "ifrm2";
	frm.submit();
}
</script>
</head>
<body>
<h2>ifrm1.jsp</h2>
<input type="button" value="submit" onclick="jsAction()">
<form method="post" action="ifrm2.jsp" name="frm" target="ifrm2">
<input type="text" name="param1" value="param1-value"/>
<input type="text" name="param2" value="param2-value"/>
<input type="text" name="param3" value="param3-value"/>
</form>
<iframe name="ifrm2" style="width:100%;height:300px;"></iframe>
</body>
</html>