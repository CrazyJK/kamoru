<?xml version="1.0" encoding="euc-kr" ?>
<%@ page language="java" contentType="text/xml; charset=EUC-KR" pageEncoding="EUC-KR"%>
<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>

<comments>
  <c:forEach begin="1" end="20" step="2" var="i">
    <comment id='${i}'>
      <name>홍길동_${i}</name>
      <content>AJAX 프로그래밍 ${i}</content>
    </comment>
  </c:forEach>
</comments>
