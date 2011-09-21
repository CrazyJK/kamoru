<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.*, java.io.*" %>
<%
String filepath = "";
ArrayList book = new ArrayList();
String line = null;
BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(new File(filepath)), "EUC-KR"));
while((line = br.readLine()) != null) {
	book.add(line);
}
br.close();

%>
<!DOCTYPE html>
<html>
<head>
<meta charset=UTF-8>
<title>book view</title>
</head>
<body>

<header></header>

<article>

</article>

<footer></footer>

</body>
</html>
