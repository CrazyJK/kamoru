<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic"%>

<!DOCTYPE html>
<html:html>
<head>
<meta charset="utf-8" />
<title>로그인 폼</title>
<style type="text/css">
* {font-family: 나눔고딕코딩}
</style>
</head>
<body>

	<h1>로그인 폼</h1>
	<logic:present name="userInfo" scope="session">
	<section>
		<b>이미 로그인 하셨습니다.</b>
		<br />
		로그인한 사용자명 : <bean:write name="userInfo" property="userName" scope="session" />
		<br />
		전화번호 : <bean:write name="userInfo" property="phone" scope="session" />
		<br />
		이메일 : <bean:write name="userInfo" property="email" scope="session" />
		<br />
		<html:link action="/login2/logout">로그아웃하기</html:link>
	</section>
	</logic:present>
	<logic:notPresent name="uesrInfo" scope="session">
	<section>
		<b>로그인 하십시오.</b>
		<br />
		<html:form action="/login2/login" method="POST" focus="username">
			<html:messages id="msg" message="true">
				<b><bean:write name="msg" /></b>
				<br />
			</html:messages>
		Username : <html:text property="username" />
			<html:messages id="msg" property="invalidUsernameError">
				<b><bean:write name="msg" /></b>
			</html:messages>
			<br />
		Password : <html:password property="password" redisplay="false" />
			<html:messages id="msg" property="invalidPasswordError">
				<b><bean:write name="msg" /></b>
			</html:messages>
			<br />
			<html:submit value="로그인" /> &nbsp; <html:reset value="초기화" />
		</html:form>
	</section>
	</logic:notPresent>
</body>
</html:html>