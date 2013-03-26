<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
Object sid = session.getAttribute("sid");
String userid = (String)session.getAttribute("userid");
if(sid != null && userid != null)
{
%>
<script type="text/javascript">
<!--
	location.href = "mindex.jsp";
//-->
</script>
<%	
	return;
}
%>
<!DOCTYPE html>
<html>
<head>
<title> Mobile CSD </title>
<meta charset="UTF-8">
<meta name="viewport" content="initial-scale=1.0; maximum-scale=1.0; minimum-scale=1.0; user-scalable=no;" />
<meta name="viewport" content="height=device-height,width=device-width" />
<meta name="apple-mobile-web-app-capable" content="yes" />
<meta name="apple-mobile-web-app-status-bar-sstyle" content="black" />
<link rel="apple-touch-icon" href="csd.png" />
<link rel="apple-touch-startup-image" href="csd_startup.png" />
<link rel="stylesheet" href="./css/mobile.css" />
<script src="./jqtouch/jquery.1.3.2.min.js"></script>
<script>
$(document).ready(function(){
	$("input[type=button]").bind("click", function(){
		var name = $("#inputName").val();
		var passwd = $("#inputPasswd").val();
		if(jQuery.trim(name) != "" && jQuery.trim(passwd) != ""){
			$(this).attr("disabled", "disabled");
			$.ajax({
				type: "POST",
				url: "./login/login.jsp",
				data: "name=" + name + "&passwd=" + passwd,
				success: function(data){
					data = jQuery.trim(data);
					if(data == "0"){
						goMain();
					}else{
						viewError(data);
					}
				}
			});
		}
	});
	setTimeout(scrollTo, 0, 0, 1);
});
function goMain(){
	location.href = "./mindex.jsp";
}
function viewError(data){
	$("#loginMsg").html(data);
	$("#loginBtn").attr("disabled", "");
}
</script>
</head>
<body>
<section>
	<article>
		<table width="100%">
			<tr height="150px">
				<td width="140px"></td>
				<td></td>
			</tr>
			<tr>
				<td></td>
				<td>
					<table>
						<tr>
							<td>
								<input id="inputName" type="text" placeholder="이름" tabindex="1"/>
							</td>
						</tr>
						<tr>
							<td>
								<input id="inputPasswd" type="password" placeholder="비밀번호" tabindex="2"/>
							</td>
						</tr>
						<tr>
							<td>
								<input id="loginBtn" type="button" value="Login" tabindex="3" style="width:100%;"/>
							</td>
						</tr>
					</table>
				</td>
			</tr>			
		</table>
	</article>
	<article>
		<p id="loginMsg"></p>
	</article>
</section>
</body>
</html>