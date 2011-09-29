<%@ page language="java" contentType="text/html; charset=utf8" pageEncoding="utf8"%>
<%@ taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf8">
<title>Insert title here</title>
<script type="text/javascript" src="event.js"></script>

<script type="text/javascript">

  window.onload=function() {
    var insertBTN = document.getElementById("insertBTN");
    
    Event.addListener(insertBTN, "click", insert);
  }
  
  function insert() {
    var form1 = document.form1;
    var title = form1.title;
    var writer = form1.writer;
    var passwd = form1.passwd;
    var content = form1.content;
    
    if (title.value == "") {
      alert("제목은 필수 입력입니다.");
      title.focus();
      return false;
    } 
     
    if (writer.value == "") {
      alert("작성자는 필수 입력입니다.");
      writer.focus();
      return false;
    } 

    if (passwd.value == "") {
      alert("비밀번호는 필수 입력입니다.");
      passwd.focus();
      return false;
    } 
     
    if (content.value == "") {
      alert("내용은 필수 입력입니다.");
      content.focus();
      return false;
    } 
    
    form1.submit();
    return true;
  }
  
</script>
</head>
<body>

게시물 등록 
<form name="form1" method="post" action="insert.jsp">
  <table border="1">
    <tr>
      <td>제목</td>
      <td><input type="text" name="title"></td>
    </tr>
    <tr>
      <td>작성자</td>
      <td><input type="text" name="writer"></td>
    </tr>
    <tr>
      <td>비밀번호</td>
      <td><input type="text" name="passwd"></td>
    </tr>
    <tr>
      <td>내용</td>
      <td><textarea name="content" rows="10" cols="80"></textarea></td>
    </tr>
    <tr>
      <td  colspan="2">
        <input type="button" value="등록"  id="insertBTN">
        <input type="reset"  value="초기화">
      </td>
    </tr>
  </table>
</form>

</body>
</html>