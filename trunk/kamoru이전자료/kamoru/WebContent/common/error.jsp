<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" isErrorPage="true"%>
<%@ page import="java.util.*, java.io.*,java.util.Date" %>
<%!
	public String makeErrorReport (HttpServletRequest req,Throwable e){
		
		StringBuffer buffer = new StringBuffer();
		
		reportException(buffer,e);
		reportRequest(buffer,req);
		reportParameters(buffer,req);
		reportHeaders(buffer,req);
		reportCookies(buffer,req);
		
		return buffer.toString();
	}
	
	public void reportException(StringBuffer buffer,Throwable e){
		StringWriter writer = new StringWriter();
		e.printStackTrace(new PrintWriter(writer));
		buffer.append(writer.getBuffer());
		buffer.append('\n');
	}
	
	public void reportRequest(StringBuffer buffer, HttpServletRequest req){
		buffer.append("Request : ");
		buffer.append(req.getMethod());
		buffer.append(' ');
		buffer.append(HttpUtils.getRequestURL(req));
		
		String queryString = req.getQueryString();
		if(queryString != null){
			buffer.append('?');
			buffer.append(queryString);
		}
		buffer.append("\nSession ID : ");
		String sessionId = req.getRequestedSessionId();
		
		if(sessionId == null){
			buffer.append("none");
		} else if(req.isRequestedSessionIdValid()){
			buffer.append(sessionId);
			buffer.append(" (from ");
			if(req.isRequestedSessionIdFromCookie())
				buffer.append("cookie)\n");
			else if(req.isRequestedSessionIdFromURL())
				buffer.append("url)\n");
			else 
				buffer.append("unknown)\n");
		} else {
			buffer.append("invalid\n");
		}
	}

	public void reportParameters(StringBuffer buffer,HttpServletRequest req){
		Enumeration names = req.getParameterNames();
		
		if(names.hasMoreElements()){
			buffer.append("Parameters:\n");
			while(names.hasMoreElements()){
				String name = (String) names.nextElement();
				String[] values = req.getParameterValues(name);
				
				for(int i=0; i< values.length; ++i){
					buffer.append("   ");
					buffer.append(name);
					buffer.append(" = ");
					buffer.append(values[i]);
					buffer.append('\n');
				}
			}
		}		
	}	
	
	public void reportHeaders(StringBuffer buffer,HttpServletRequest req){
		Enumeration names = req.getHeaderNames();
		
		if(names.hasMoreElements()){
			buffer.append("Headers:\n");
			while(names.hasMoreElements()){
				String name = (String) names.nextElement();
				String value = (String) req.getHeader(name);				
				buffer.append("   ");
				buffer.append(name);
				buffer.append(" = ");
				buffer.append(value);
				buffer.append('\n');
			}
		}		
	}		

	public void reportCookies(StringBuffer buffer,HttpServletRequest req){
		Cookie[] cookies = req.getCookies();
		int l = cookies.length;
		
		if(l > 0){
			buffer.append("Cookies:\n");
			for(int i=0;i<l;++i){
				Cookie cookie = cookies[i];
				buffer.append("   ");
				buffer.append(cookie.getName());
				buffer.append(" = ");
				buffer.append(cookie.getValue());
				buffer.append('\n');
			}
		}
	}		

%>
<html>
<head>
<title>프로그램 에러</title>
<link rel="stylesheet" type="text/css" href="/css/default.css">
</head>
<script>
window.onload = function(){
	alert("Error:<%= exception %>");
}
</script>
<body>
<table width="100%" cellpadding="3" cellspacing="5">
  <tr>
    <td>
		<h1>해당 프로그램에 에러가 있습니다.</h1>
    </td>
  </tr>
  <tr>
    <td>
	에러발생 :<br>
	<font color="red"><%= exception %></font>
	<pre><%= makeErrorReport(request,exception) %></pre>
	</td>
  </tr>
</table>
<hr size="1">
<%= new Date().toString() %> : <a href="mailto:kamoru@kamoru.kr">kamoru@kamoru.kr</a>
</body>
</html>
