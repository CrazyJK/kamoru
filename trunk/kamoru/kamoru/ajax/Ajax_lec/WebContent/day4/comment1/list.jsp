<?xml version="1.0" encoding="euc-kr" ?>
<%@ page language="java" contentType="text/xml; charset=EUC-KR" pageEncoding="EUC-KR"%>
<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>

<comments>
  <c:forEach begin="1" end="20" step="2" var="i">
    <comment id='${i}'>
      <name>ȫ�浿_${i}</name>
      <content>AJAX ���α׷��� ${i}</content>
    </comment>
  </c:forEach>
</comments>
