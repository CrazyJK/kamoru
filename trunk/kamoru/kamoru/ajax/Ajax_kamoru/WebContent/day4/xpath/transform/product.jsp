<?xml version="1.0" encoding="EUC-KR"?>
<%@ page language="java" contentType="text/xml; charset=EUC-KR" pageEncoding="EUC-KR"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<catalog>
  <c:forEach begin="1" end="100"  var="i">
    <product     id="${i}">
      <name>홍길동_${i}</name>
      <price>${i}</price>
    </product>
  </c:forEach>
</catalog>
