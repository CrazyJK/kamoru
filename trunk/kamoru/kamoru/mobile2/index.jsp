<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title> Mobile CSD </title>
<meta charset="UTF-8">
<link rel="stylesheet" href="./css/mobile.css" />
<script src="./jqtouch/jquery.1.3.2.min.js" type="text/javascript" charset="utf-8"></script>
<script src="./jqtouch/jqtouch.min.js" type="application/x-javascript" charset="utf-8"></script>
<script type="text/javascript" charset="utf-8">
var jQT = new $.jQTouch({
    icon: 'csd.png',
    addGlossToIcon: false,
    startupScreen: 'csd_startup.png',
    statusBar: 'black',
    preloadImages: [
        './themes/jqt/img/back_button.png',
        './themes/jqt/img/back_button_clicked.png',
        './themes/jqt/img/button_clicked.png',
        './themes/jqt/img/grayButton.png',
        './themes/jqt/img/whiteButton.png',
        './themes/jqt/img/loading.gif'
        ]
});

var loginid, pwd, autologin;

$(document).ready(function(){
//	setTimeout(scrollTo, 0, 0, 1);
	//$("#loginMsg").html(navigator.userAgent);
	loginid   = localStorage.getItem("CSD_LOGINID");
	pwd       = localStorage.getItem("CSD_PWD");
	autologin = localStorage.getItem("CSD_AUTOLOGIN");
	$("#inputName").val(loginid);
	$("#inputPasswd").val(pwd);
	if(autologin == "on"){
		$("#autoLogin").attr("checked", "checked");
		if(loginid != null && pwd != null){
			login();
		}
	}else{
		$("#autoLogin").removeAttr("checked");
	}
	
	$("#loginBtn").bind("click", function(){
		login();
	});
});
function login(){
	loginid   = $("#inputName").val();
	pwd       = $("#inputPasswd").val();
	autologin = $("#autoLogin").val();
	if(jQuery.trim(loginid) != "" && jQuery.trim(pwd) != "")
	{
		$(this).attr("disabled", "disabled");
		$.ajax({
			type: "POST",
			url: "./login/login.jsp",
			data: "loginid=" + loginid + "&pwd=" + pwd + "&autologin=" + autologin,
			success: function(data){
				data = jQuery.trim(data);
				var loginResultArray = eval(data);
				var loginResult = loginResultArray[0];
				if(loginResult.resultCode == "0"){
					localStorage.setItem('CSD_LOGINID',   loginResult.loginId);
					localStorage.setItem('CSD_USERID',    loginResult.userId);
					localStorage.setItem('CSD_DEPTID',    loginResult.deptId);
					localStorage.setItem('CSD_DEPTNM',    loginResult.deptNm);
					localStorage.setItem('CSD_EMPCODE',   loginResult.empCode);
					localStorage.setItem('CSD_AUTOLOGIN', autologin);
					if(autologin == "on"){
						localStorage.setItem('CSD_PWD',       pwd);
					}
					sessionStorage.setItem('CSD_LOGIN_STATE', loginResult.resultCode);

					location.href = "./mindex.jsp";
				}else{
					$("#loginMsg").html(loginResult.resultMsg);
					$("#loginBtn").attr("disabled", "");
				}
			}
		});
	}
}
</script>
</head>
<body>
<section>
	<article>
		<table width="100%">
			<tr height="150px" valign="bottom">
				<td width="140px"></td>
				<td>
					<p id="loginMsg"></p>
				</td>
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
						<tr>
							<td>
								자동 로그인 : <span class="toggle"><input id="autoLogin" type="checkbox"/></span>
							</td>
						</tr>
					</table>
				</td>
			</tr>			
		</table>
	</article>
</section>
</body>
</html>