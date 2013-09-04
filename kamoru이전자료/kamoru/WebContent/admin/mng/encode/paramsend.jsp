<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title> Param encoding test </title>
<link rel="stylesheet" type="text/css" href="../mng.css">
<script>
function formSubmit(){
	pV = document.all.p.value;
	document.forms[0].param.value = pV;

	document.forms[0].target="if1";
	document.forms[0].action="receive.jsp";
	document.forms[0].submit();
	
	document.forms[0].target="if2";
	document.forms[0].action="receive2.jsp";
	document.forms[0].submit();

	document.forms[1].param.value = pV;
	
	document.forms[1].target="if3";
	document.forms[1].action="receive.jsp";
	document.forms[1].submit();

	document.forms[1].target="if4";
	document.forms[1].action="receive2.jsp";
	document.forms[1].submit();
}
</script>
</head>
<body>
<h3>Content-Type  text/html; charset=UTF-8</h3>
<h4>Input text
<input type="text" name="p" value="가나다라Abc123" />
<button onclick="formSubmit()">Submit</button>
</h4>
<fieldset>
	<legend class="TITLE"> method post </legend>
	<form method="post" >
	<input type="hidden" name="param" />
	<input type="hidden" name="method" value="post"/>
	</form>
	<table width="100%"><tr><td>
		<iframe name="if1" width="100%" height="100"></iframe>
	</td><td>
		<iframe name="if2" width="100%" height="100"></iframe>
	</td></tr></table>
</fieldset>

<fieldset>
	<legend class="TITLE"> method get </legend>
	<form method="get" >
	<input type="hidden" name="param" />
	<input type="hidden" name="method" value="get"/>
	</form>
	<table width="100%"><tr><td>
		<iframe name="if3" width="100%" height="100"></iframe>
	</td><td>
		<iframe name="if4" width="100%" height="100"></iframe>
	</td></tr></table>
</fieldset>


</body>
</html>