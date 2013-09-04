<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<%!
// ����Ʈ�� �°� ����
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
		alert("CSV������ ������ �ּ���");
		return;
	}
	document.csvfileinputform.submit();
}
</script>
</head>
<body>
<form name="csvfileinputform" target="csv2dbactionframe" action="csv2db.jsp"  method="POST" enctype="multipart/form-data">
<h4>CSV File Loader</h4>

<p class="SLIDE"><a href="#" class="btn-slide">1. CSV ���� �غ�</a></p>
<div id="panel">
		CSV������ ������ ���� �����̾�� �մϴ�.
	<table width="100%" border="0" cellpadding="1" cellspacing="0" class="TABLE_BORDER1">
		<tr>
			<th class="TH_BORDER1">Line</th>
			<th class="TH_BORDER2">����</th>
			<th class="TH_BORDER2">��</th>
		</tr>
		<tr>
			<td class="TD_BORDER1">1</td>
			<td class="TD_BORDER2">���̺� ��</td>
			<td class="TD_BORDER2">tsrm_apply,,,,</td>
		</tr>
		<tr>
			<td class="TD_BORDER1">2</td>
			<td class="TD_BORDER2">�÷���</td>
			<td class="TD_BORDER2">svr_id,user_jn,co_nm_kr,applicant_nm,applicant_pos</td>
		</tr>
		<tr>
			<td class="TD_BORDER1">3 ~ </td>
			<td class="TD_BORDER2">����Ÿ</td>
			<td class="TD_BORDER2">C20080001X,6666661111XXX,��O,��O��,����</td>
		</tr>
		<tr>
			<td colspan="3">�����Ϳ� ,(�ĸ�)�� �� ��� ""�� �ο��� �־�� �մϴ�.(MS Excel���� ����� ��� �ڵ����� ������) 
							<br>&nbsp;&nbsp;&nbsp;ex) C20080XXX,666666111XXX,<span style="color:RED;font-weight:bold">"</span><span style="font-weight:bold">hellpworld co. ,  Itd</span><span style="color:RED;font-weight:bold">"</span>,��OO,��ǥ�̻�
			</td>
		</tr>
	</table>
</div>


<p class="SLIDE"><a href="#" class="dbinfo_btn-slide">2. DB ���� ����/����</a></p>
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


<p class="SLIDE">3. CSV ���� ���� <input type="file" name="csvfile"/></p>

<p class="SLIDE"><a href="javascript:datasubmit()" class="save-button">4. ��� ����</a></p>

</form>
<fieldset>
<legend> ó����� </legend>
<iframe name="csv2dbactionframe" scrolling="auto" width="100%" height="400" frameborder="0"></iframe>
</fieldset>
</body>
</html>