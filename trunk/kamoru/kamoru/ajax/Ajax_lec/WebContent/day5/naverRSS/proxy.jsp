<%@page import="java.io.InputStreamReader"%>
<%@page import="org.apache.commons.httpclient.HttpStatus"%>
<%@page import="org.apache.commons.httpclient.methods.GetMethod"%>
<%@page import="java.util.Enumeration"%>
<%@page import="org.apache.commons.httpclient.methods.PostMethod"%>
<%@page import="org.apache.commons.httpclient.HttpClient"%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<%
  System.out.println("aaaa");
  //케싱이 되지 않도록 해더를 설정한다.
  response.setHeader("Pragma", "No-cache");
  response.setHeader("Expires", "0");
  response.setHeader("Cache-Control", "no-cache");
  
  String url = request.getParameter("url");
  HttpClient client = new HttpClient();
  String queryString = request.getQueryString();
  if (queryString != null) {
    url += "?" + queryString;
  }
  System.out.println("url = " + url);
  
  if ("GET".equals(request.getMethod())) {
    GetMethod method = new GetMethod(url);
    try {
      int statusCode = client.executeMethod(method);
  
      out.clearBuffer();
      response.reset();
      
      response.setStatus(statusCode);
      
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
      if (method != null) method.releaseConnection();
    }
  } else {
    PostMethod method = new PostMethod(url);
    // Prepare parameters
    Enumeration e = request.getParameterNames();
    String name, value;
    
    while (e.hasMoreElements()) {
      name = (String) e.nextElement();
      if ("url".equals(url) == false) {
        value = request.getParameter(name);
        method.addParameter(name, value);
      }
    }
    
    try {
      int statusCode = client.executeMethod(method);
  
      out.clearBuffer();
      response.reset();
      
      response.setStatus(statusCode);
      
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
      if (method != null) method.releaseConnection();
    }
  }
  
%>