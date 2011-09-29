<%@page import="java.io.InputStreamReader"%>
<%@page import="org.apache.commons.httpclient.HttpStatus"%>
<%@page import="org.apache.commons.httpclient.methods.PostMethod"%>
<%@page import="org.apache.commons.httpclient.HttpClient"%>
<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<%

  //케싱이 되지 않도록 해더를 설정한다.
  response.setHeader("Pragma", "No-cache");
  response.setHeader("Expires", "0");
  response.setHeader("Cache-Control", "no-cache");

  //HttpClient 객체를 생성한다.
  HttpClient client = new HttpClient();
  //Post방식으로 URL를 생성한다.
  PostMethod method = new PostMethod("http://rss.chosun.com/rss.xml");
  try {
  
    // Post 방식으로 잔송할 이름과 값을 설정한다
    method.addParameter("name", "value");
    
    //Post방식으로 해당 다른 서버의 URL를 호출하고 실행 코드를 얻는다.
    int statusCode = client.executeMethod(method);
  
    //클라이언트로 결과를 리턴하기 위해 출력버퍼를 초기화 한다.
    out.clearBuffer();
    response.reset();

    //HttpClient의 실행 결과 코드를 응답상태 코드에 설정한다.
    response.setStatus(statusCode);
    
    //실행결과가 성공이면 응답결과 내용을 읽어 클라이언트에 출력한다.
    if (statusCode == HttpStatus.SC_OK) {
      int ch;
      InputStreamReader in = new InputStreamReader(method.getResponseBodyAsStream(), "utf-8");

      response.setContentType("text/xml; charset=utf-8");
      while ((ch = in.read()) != -1) {
        out.write(ch);
      }
      in.close();
    }
  } finally {
    //서버와의 연결 해제한다.
    if (method != null) method.releaseConnection();
  }
%>