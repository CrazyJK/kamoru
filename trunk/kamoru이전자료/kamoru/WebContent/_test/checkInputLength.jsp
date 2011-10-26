<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title> 입력 글자수 체크 </title>
<script>
function checkLength(obj, limit, isUTF8)
{
	var value = obj.value;
	var i;
	var length = 0;
	var increment = 1;
	if (isUTF8)
		increment = 2;

	for (i = 0; i < value.length; i++)
	{
//		alert("1. " + value.charCodeAt(i));
		if (value.charCodeAt(i) > 127)
			length += increment;
		else
			length++;
	}

	document.lengthTestForm.txtlen_checkLength.value = length;

	/*
	if (length > limit)
	{
		alert("the limit length of this field is " + limit + ", input length is " + length + ".");
		obj.focus();
		return false;
	}
	else
		return true;
	*/
}

/*
' ------------------------------------------------------------------
' Function : fc_chk_byte(aro_name)
' Description : 입력한 글자수를 체크
' Argument : Object Name(글자수를 제한할 컨트롤)
' Return : 
' ReMark:< textarea name="txt_aaa" rows="5" cols="60" onkeyup="fc_chk_byte(this,10);">
' ------------------------------------------------------------------
*/
function fc_chk_byte(aro_name,ari_max)
{

   var ls_str = aro_name.value; // 이벤트가 일어난 컨트롤의 value 값
   var li_str_len = ls_str.length; // 전체길이

   // 변수초기화
   var li_max = ari_max; // 제한할 글자수 크기
   var i = 0; // for문에 사용
   var li_byte = 0; // 한글일경우는 2 그밗에는 1을 더함
   var li_len = 0; // substring하기 위해서 사용
   var ls_one_char = ""; // 한글자씩 검사한다
   var ls_str2 = ""; // 글자수를 초과하면 제한할수 글자전까지만 보여준다.

   for(i=0; i< li_str_len; i++)
   {
      // 한글자추출
      ls_one_char = ls_str.charAt(i);

//	alert("2. escape(" + escape(ls_one_char));

      // 한글이면 2를 더한다.
      if (escape(ls_one_char).length > 4)
      {
         li_byte += 2;
      }
      // 그밗의 경우는 1을 더한다.
      else
      {
         li_byte++;
      }

      // 전체 크기가 li_max를 넘지않으면
      if(li_byte <= li_max)
      {
         li_len = i + 1;
      }
   }

	document.lengthTestForm.txtlen_fc_chk_byte.value = li_byte;
	   
   
   // 전체길이를 초과하면
   /*
   if(li_byte > li_max)
   {
      alert( li_max + " 글자를 초과 입력할수 없습니다. \n 초과된 내용은 자동으로 삭제 됩니다. ");
      ls_str2 = ls_str.substr(0, li_len);
      aro_name.value = ls_str2;

   }
   aro_name.focus(); 
	*/
}

function getLength(str)
{ 
	var max_byte = str.length + (escape(str)+"%u").match(/%u/g).length - 1;
 
// var str_len = document.form.content.value.length;


	document.lengthTestForm.txtlen_getLength.value = max_byte;
/*
 if (max_byte >= 80)
 {
  alert("한글 2글자, 영문 1글자가 80Byte를 넘을수 없습니다.\r\n더이살 쓸수 없습니다.")
  document.form.content.value = document.form.content.value.substring(0, str_len-1);
  document.form.txtlen.value = max_byte;
  return;
 }
 else
 {
  document.form.txtlen.value = max_byte;
 }
*/
}

</script>
<style type="text/css">
TEXTAREA {overflow:auto;border:1px;width:160px;font-size:9pt;background-color:#E5F1FB;}
INPUT 	 {border:1px;background-color:#DBEBF8;text-align:right;width:91px;height:20px;color:blue;}
</style>
</head>
<body>
<form name="lengthTestForm">

<textarea name="txtarea" cols="20" rows="7" onKeydown="" onKeyUp="javascript:getLength(this.value);checkLength(this, 80, true);fc_chk_byte(this,80)"></textarea>

<fieldset>
<legend>HandyBPM 방법</legend>
<input type="text" name="txtlen_checkLength" value="0">
</fieldset>

<fieldset>
<legend>javascript:getLength(content.value)</legend>
<input type="text" name="txtlen_getLength" value="0">
</fieldset>

<fieldset>
<legend>fc_chk_byte(this,80)</legend>
<input type="text" name="txtlen_fc_chk_byte" value="0">
</fieldset>

</form>
</body>
</html>