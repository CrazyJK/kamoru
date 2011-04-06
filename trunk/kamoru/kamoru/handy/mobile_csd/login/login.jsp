<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.hs.frmwk.db.*, java.util.*" %>
<%
System.out.println(request.getRequestURI());

request.setCharacterEncoding("UTF-8");
String loginId 	  = request.getParameter("loginid");
String pwd 	      = request.getParameter("pwd");
String autoLogin  = request.getParameter("autologin");

String resultCode = null;
String resultMsg  = null;
String userId     = null;
String deptId     = null;
String deptNm     = null;
String empCode    = null;

BWDBHelper helper = null;
try
{
	helper = BWDBHelperFactory.getBWDBHelper("mboard", "login.jsp");
	helper.setPreparedSQL("_mobile", 1001);
	helper.setString(1, loginId);
	helper.executeQuery();
	
	if(helper.next())
	{
		int totalRow = helper.getRSInt("TOTAL");
		if(totalRow == 1)
		{
			String dbPwd = helper.getRSString("PWD");
			if(dbPwd.equals(encode(pwd)))
			{
				// 로그인 정보 맞음.
				resultCode = "0";
				resultMsg  = "Login success";
				userId     = helper.getRSString("USER_ID");
				deptId     = helper.getRSString("DEPT_ID");
				deptNm     = helper.getRSString("DEPT_NAME");
				empCode    = helper.getRSString("EMP_CODE");
				session.setAttribute("userId", userId);
				session.setAttribute("deptId", deptId);
			}
			else
			{
				// 패스워드 틀림
				resultCode = "1001";
				resultMsg = "올바른 사용자 ID와 비밀번호가 아닙니다.";
			}
		}
		else
		{
			// 복수의 이름 있음
			resultCode = "1002";
			resultMsg = "중복된 사용자 ID가 존재 합니다.";
		}
	}
	else
	{
		// 이름 없음
		resultCode = "1003";
		resultMsg = "존재하지 않는 사용자 입니다.";
	}
}
catch(Exception e)
{
	resultCode = "9999";
	resultMsg = "Error:" + e.getMessage();
}
finally
{
	if(helper != null) helper.close();
}
%>
[{
	resultCode : '<%=resultCode %>',
	resultMsg  : '<%=resultMsg %>',
	userId : '<%=userId %>',
	loginId : '<%=loginId %>',
	deptId : '<%=deptId %>',
	deptNm : '<%=deptNm %>',
	empCode : '<%=empCode %>'
}]
<%!
String encode(String pwd)throws Exception{
	com.hs.hip.common.Crypt crypt = new com.hs.hip.common.Crypt(); 
	String decodePwd = null;
	try { 
		decodePwd = crypt.Encode(pwd);
	} catch(Exception e) {
		e.printStackTrace(); 
	}	
	return decodePwd;	
}
%>
