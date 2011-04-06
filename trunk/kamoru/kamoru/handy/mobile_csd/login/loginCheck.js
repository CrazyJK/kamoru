/**
 *	로그인 체크 
 */
var login_state = sessionStorage.getItem('CSD_LOGIN_STATE');
var userid = localStorage.getItem('CSD_USERID');
if(login_state != "0"){
	//alert("로그인 정보가 없습니다. 다시 로그인 해주세요");
	location.href = "/mobile2/";
}

