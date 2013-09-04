<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.net.URL" %>
<%
URL classUrl = null;
String reqName = request.getParameter("reqName");
if (reqName == null || reqName.trim().length() == 0) {
	reqName = "javax.servlet.http.HttpServlet";
}
if (reqName.trim().length() != 0) {
	classUrl = this.getClass().getResource("/" + reqName.replace('.', '/').trim() + ".class");
}

%>    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<style type="text/css">
	body {font: 11pt 맑은 고딕;}
	form {display:inline}
</style>
<script type="text/javascript">
window.onload = function() {
	var reqName = document.getElementById("reqName");
	reqName.focus();
}
</script>
</head>
<body>

<fieldset>
	<legend> 웹에 로딩된 클래스 위치 찾기 </legend>
	<ul>Example
		<li>Document Builder Factory - org.apache.xerces.jaxp.DocumentBuilderFactoryImpl</li>
		<li>SAX Parser Factory - org.apache.xerces.jaxp.SAXParserFactoryImpl</li>
		<li>Transformer Factory - org.apache.xalan.processor.TransformerFactoryImpl</li>

		<li>BPM ResourceBag - com.hs.bf.web.xslt.resource.ResourceBag</li>
		<li>BPM HWSessionInfo - com.hs.bf.web.beans.HWSessionInfo</li>
		<li>BPM HWSessionFactory - com.hs.bf.web.beans.HWSessionFactory</li>
	
		<li>BPM IniFile - com.hs.frmwk.common.ini.IniFile</li>
		<li>BPM Properties - com.hs.bf.web.props.Properties</li>
			
	</ul>
</fieldset>
<br/>
<fieldset>
	<legend> 검색 </legend>
	<div style="padding-left:10pt">
		<form>
			<input type="text"   name="reqName"   value="<%= reqName %>" style="width:400px">
			<input type="submit" name="reqButton" value="FIND">
		</form>
	</div>

	<div> 
		<ul>결과
		<% 	if (classUrl == null) { %>
		<li><span style="color:red"> Not found!</span></li>
		<% 	}else{ 
				String filename = classUrl.getFile();
				String[] names = filename.split("!");
		%>
		<li><span style="font-weight:bold"> <%= names[1] %> </span> is located <br/> in 
		<span style="color:blue"> <%= names[0] %> </span></li>
		<% 	} %>
		</ul>
	</div>
</fieldset>

</body>
</html>