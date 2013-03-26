<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>Insert title here</title>
<script>
function getOpen(){
	var name = "남종관 한글";
	var url = "get_receive.jsp?name="+name+"&_x="+new Date();
	window.open(url,'get','top=0, left=2000, width=400,height=500');
	//ifrm.location.href = encodeURI(url);
}

</script>
</head>
<body>
<input type=button value=open onclick='getOpen()'/>
<iframe name="ifrm"></iframe>
</body>
</html>