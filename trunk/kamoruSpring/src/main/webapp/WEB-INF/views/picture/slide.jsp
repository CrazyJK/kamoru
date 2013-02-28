<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<!--[if lt IE 9]>
<script src="http://html5shiv.googlecode.com/svn/trunk/html5.js"></script>
<![endif]-->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.8.3/jquery.min.js"></script>
<script type="text/javascript">
$(document).ready(function(){
	$(document).bind("click keyup", function(event){
		//alert(event.type + " - " + event.button + ", keyValue=" + event.keyCode);
		if(event.keyCode == 39) {
			fnViewImage(currentImageIdx + 1);
		} 
	});
});
var imagepath = '<s:url value="/image/" />';
var currentImageIdx = 0;
function fnViewImage(idx) {
	currentImageIdx = idx;
	$("#master").attr("src", imagepath + idx);
}
</script>
</head>
<body>
<div>
<%-- <c:forEach var="i" begin="0" end="${imageCount-1}" step="1"> --%>
<c:forEach var="i" begin="0" end="10" step="1">
	<span onclick="fnViewImage(${i})">
		<img src="<s:url value="/image/${i}/thumbnail" />" />
	</span>
</c:forEach>
</div>

<div>
<img id="master" src="" />
</div>

</body>
</html>
