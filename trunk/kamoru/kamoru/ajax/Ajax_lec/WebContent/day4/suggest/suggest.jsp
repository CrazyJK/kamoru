<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/plain; charset=EUC-KR" pageEncoding="EUC-KR"%>
<% 
  //��û���� ĳ���� ���� utf8�� ��ȯ�Ѵ�.
  request.setCharacterEncoding("utf-8");

  //�˻�� ��´�.
  String keyword = request.getParameter("keyword");
  
  /*�˻��� ����� ���� �Ѵ�*/
  String [] keywords = {
     "AJAX �ʱ�"
    ,"AJAX �߱�"
    ,"AJAX ���"
    ,"�ڹ�"    
    ,"�ڹ� ���α׷���"    
    ,"�ڹ� ���� ������"    
    ,"�ڹٽ��͵�"    
    ,"�ڹټ���"    
    ,"���� �ڹټ���"    
  };

  //�˻��� ��Ͽ��� �˻���� �����ϴ� ����� ��´�.
  if (keywords != null && "".equals(keyword) == false) {

    keyword = keyword.toUpperCase();
    boolean bStart = true;
    
    for (int i=0;i<keywords.length;i++) {
      if (keywords[i].startsWith(keyword)) {
        //���ڿ��� ó���� �ƴϸ� ,�� ����Ѵ�. �� cvs �������� ����Ѵ�.
        if (!bStart) {
          out.print(",");
        } else {
          bStart = false;
        }
        out.print(keywords[i]);
        
      }
    }
  }
%>
