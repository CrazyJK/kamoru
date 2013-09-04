<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%! 
static org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger("JSP");

static org.apache.log4j.Logger logger2 = org.apache.log4j.Logger.getLogger("JSP.bbs");
%>
<%
logger.debug("JSP");

logger2.debug("jsp.bbs: logger2.debug");

if(logger2.isDebugEnabled()) logger2.debug("jsp.bbs: if(logger2.isDebugEnabled()) logger2.debug");

%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>

</body>
</html>