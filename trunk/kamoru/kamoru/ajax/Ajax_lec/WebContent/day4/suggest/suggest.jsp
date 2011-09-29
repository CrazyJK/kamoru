<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/plain; charset=EUC-KR" pageEncoding="EUC-KR"%>
<% 
  //요청인자 캐릭터 셋을 utf8로 변환한다.
  request.setCharacterEncoding("utf-8");

  //검색어를 얻는다.
  String keyword = request.getParameter("keyword");
  
  /*검색어 목록을 정의 한다*/
  String [] keywords = {
     "AJAX 초급"
    ,"AJAX 중급"
    ,"AJAX 고급"
    ,"자바"    
    ,"자바 프로그래밍"    
    ,"자바 서버 페이지"    
    ,"자바스터디"    
    ,"자바서비스"    
    ,"하하 자바서비스"    
  };

  //검색어 목록에서 검색어로 시작하는 목록을 얻는다.
  if (keywords != null && "".equals(keyword) == false) {

    keyword = keyword.toUpperCase();
    boolean bStart = true;
    
    for (int i=0;i<keywords.length;i++) {
      if (keywords[i].startsWith(keyword)) {
        //문자열의 처음이 아니면 ,을 출력한다. 즉 cvs 포멧으로 출력한다.
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
