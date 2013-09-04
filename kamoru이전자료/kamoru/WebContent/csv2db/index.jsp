<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<%!
// 사이트에 맞게 수정
final String dburl = "jdbc:oracle:thin:@123.212.190.11:1521:CSD";
final String dbusr = "kamoru";
final String dbpwd = "kamoru";
final String dbdrv = "oracle.jdbc.driver.OracleDriver";
%>    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
<title> CSV 2 DB </title>
<link rel="stylesheet" type="text/css" href="csv2db.css">
<script type="text/javascript" src="jquery.js"></script>
<script type="text/javascript">
$(document).ready(function(){

	$(".btn-slide").click(function(){
		$("#panel").slideToggle("slow");
		$(this).toggleClass("active"); return false;
	});
	
	$(".dbinfo_btn-slide").click(function(){
		$("#dbinfo_panel").slideToggle("slow");
		$(this).toggleClass("active"); return false;
	});
	 
});

function datasubmit(){
	if(document.csvfileinputform.csvfile.value == ""){
		alert("CSV파일을 선택해 주세요");
		return;
	}
	document.csvfileinputform.submit();
}
</script>
</head>
<body>
<form name="csvfileinputform" target="csv2dbactionframe" action="csv2db.jsp"  method="POST" enctype="multipart/form-data">
<h4>CSV File Loader</h4>

<p class="SLIDE"><a href="#" class="btn-slide">1. CSV 파일 준비</a></p>
<div id="panel">
		CSV파일은 다음과 같은 형식이어야 합니다.
	<table width="100%" border="0" cellpadding="1" cellspacing="0" class="TABLE_BORDER1">
		<tr>
			<th class="TH_BORDER1">Line</th>
			<th class="TH_BORDER2">설명</th>
			<th class="TH_BORDER2">예</th>
		</tr>
		<tr>
			<td class="TD_BORDER1">1</td>
			<td class="TD_BORDER2">테이블 명</td>
			<td class="TD_BORDER2">tsrm_apply,,,,</td>
		</tr>
		<tr>
			<td class="TD_BORDER1">2</td>
			<td class="TD_BORDER2">컬럼명</td>
			<td class="TD_BORDER2">svr_id,user_jn,co_nm_kr,applicant_nm,applicant_pos</td>
		</tr>
		<tr>
			<td class="TD_BORDER1">3 ~ </td>
			<td class="TD_BORDER2">데이타</td>
			<td class="TD_BORDER2">C20080001X,6666661111XXX,성O,김O혁,사장</td>
		</tr>
		<tr>
			<td colspan="3">데이터에 ,(컴마)가 들어간 경우 ""로 싸여져 있어야 합니다.(MS Excel에서 저장된 경우 자동으로 생성됨) 
							<br>&nbsp;&nbsp;&nbsp;ex) C20080XXX,666666111XXX,<span style="color:RED;font-weight:bold">"</span><span style="font-weight:bold">hellpworld co. ,  Itd</span><span style="color:RED;font-weight:bold">"</span>,윤OO,대표이사
			</td>
		</tr>
	</table>
</div>


<p class="SLIDE"><a href="#" class="dbinfo_btn-slide">2. DB 정보 보기/수정</a></p>
<div id="dbinfo_panel">
	<table width="100%" border="0" cellpadding="0" cellspacing="0">
		<tr>
			<td width="40" align="left">URL</td>
			<td width="360"><input class="DBINFOINPUT" type="text" name="dburl" value="<%= dburl %>"/></td>
		</tr>
		<tr>
			<td>USER</td>
			<td><input class="DBINFOINPUT" type="text" name="dbuser" value="<%= dbusr %>"/></td>				
		</tr>
		<tr>
			<td>PWD</td>
			<td><input class="DBINFOINPUT" type="password" name="dbpwd" value="<%= dbpwd %>"/></td>				
		</tr>
		<tr>
			<td>Driver</td>
			<td><input class="DBINFOINPUT" type="text" name="driver" value="<%= dbdrv %>"/></td>				
		</tr>
	</table>
</div>


<p class="SLIDE">3. CSV 파일 선택 <input type="file" name="csvfile"/></p>

<p class="SLIDE"><a href="javascript:datasubmit()" class="save-button">4. 디비에 저장</a></p>

</form>
<fieldset>
<legend> 처리결과 </legend>
<iframe name="csv2dbactionframe" scrolling="auto" width="100%" height="400" frameborder="0"></iframe>
</fieldset>
</body>
</html>