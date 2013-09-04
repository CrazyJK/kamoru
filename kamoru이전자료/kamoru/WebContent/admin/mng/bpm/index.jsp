<%@ page language="java"%>
<%--
Name : bpm instance.archive process management module
Auth : Nam Jongkwan
Date : 2007-11-09
Note : if you want to delete a physical file and db data, need to below setting.
		~hwserver/system/hwsystem.ini
		[SYSTEM]
		USE_RECYCLEBIN=False
--%>
<html>
<HEAD>
<META http-equiv="Content-Type" content="text/html; charset=UTF-8">
<TITLE> BPM PROCESS MANAGEMENT TOOL </TITLE>
<link REL="stylesheet" TYPE="text/css" HREF="/css/kamoru.css">
<link rel="stylesheet" type="text/css" href="../sitemng.css">
<link REL="stylesheet" TYPE="text/css" HREF="form.css">
<script Language='JavaScript' src='datepicker.js'></script>
<script>
var VALIDATION_NULL_DATE = true;
var MSG_NO_INPUT = "No input";
var MSG_INPUT_PROCID = "input procid";
var MSG_INPUT_ORGPROCDEFID = "input orgprocdefid";

function startAction()
{
	if(document.all.customSQLCheck.checked){
		if(document.all.customSQL.value == ""){
			alert("Please, fill query");
			return;
		}
	}else {
		if(!VALIDATION_NULL_DATE){
			if(document.frm.startdtime.value == '' || document.frm.cmpltdtime.value == ''){
				alert("Please, input date");
				return;
			}
		}
		if(!document.all.radio1.checked){
			if(document.all.inputID.value == MSG_INPUT_PROCID){
				alert("Please, " + MSG_INPUT_PROCID);
				return;
			}else if (document.all.inputID.value == MSG_INPUT_ORGPROCDEFID){
				alert("Please, " + MSG_INPUT_ORGPROCDEFID);
				return;
			}
		}
	}
	if(document.forms[0].act2.checked && !confirm("You selected 'DELETE', Are you sure?")){
		return;
	}else if(document.forms[0].act3.checked && !confirm("You selected 'suspend', Are you sure?")){
		return;
	}else if(document.forms[0].act4.checked && !confirm("You selected 'resume', Are you sure?")){
		return;
	}else if(document.forms[0].act5.checked && !confirm("You selected 'close', Are you sure?")){
		return;
	}
	document.frm.target = 'ifrm';
	document.frm.action = 'mng_action.jsp';
	document.frm.submit();
}
function viewTR(no)
{
	if (no=="1")
	{
		document.all.inputID.value = MSG_NO_INPUT;
		document.all.radio1.checked = "checked";
	}
	else if (no=="2")
	{
		document.all.inputID.value = MSG_INPUT_PROCID;
		document.all.radio2.checked = "checked";
	}
	else if (no=="3")
	{
		document.all.inputID.value = MSG_INPUT_ORGPROCDEFID;
		document.all.radio3.checked = "checked";
	}
}
function clickDateView(no)
{
	if(no=="1")	document.frm.Start.value="Search";
	if(no=="2") document.frm.Start.value="Delete";
	if(no=="3") document.frm.Start.value="suspend";
	if(no=="4") document.frm.Start.value="resume";
	if(no=="5") document.frm.Start.value="close";
}
function init(){
	viewTR(2);
	document.forms[0].act1.checked = "checked"; // view select
	clickDateView(1); // display search
}
function allCheck(){
	var allcheckvalue = document.forms[0].procStateAll.checked;
	var ischeck = "";
	if(allcheckvalue){
		ischeck = "checked";
	}
	document.forms[0].check1.checked = ischeck;
	document.forms[0].check2.checked = ischeck;
	document.forms[0].check3.checked = ischeck;
	document.forms[0].check4.checked = ischeck;
	document.forms[0].check6.checked = ischeck;
	document.forms[0].check7.checked = ischeck;
	document.forms[0].check8.checked = ischeck;
}
function viewguide(){
    var vUrl    = "help_procmng.htm";
    var vName   = "";
    var vWidth  = 600;
    var vHeight = 650;
    var vLeft  = (window.screen.width)/2;
    var vTop = (window.screen.height - vHeight)/2;
    var vFeature = "width="+vWidth+", height="+vHeight+", top="+vTop+", left="+vLeft;
    vFeature = vFeature + "toolbar=0,location=0,directories=0,"+
          "status=0,menubar=0,scrollbars=1,"+
          "resizable=1,copyhistory=1";
    
    window.open(vUrl,vName,vFeature);
}

</script>
</HEAD>
<BODY onload="init()">
<form name="frm" method="get" style="margin:0;">
<table border="0" cellspacing="10" width="1000">
	<tr>
		<td colspan="2">
			<table border="0" width="100%" cellspacing="0" cellpadding="0">
				<tr>
					<td class="title">
						BPM Data <font color="#cc0000">MANAGEMENT TOOL</font> 
					</td>
					<td align="right" class="title">
						<input class="buttonSubmit" type="button" name="Help" value="Help" onClick="javascript:viewguide();" />
					</td>
				</tr>
			</table>
		</td>
	</tr>
	<tr>
		<td>
			<fieldset style="height:55;">
				<legend id='legend'><span class='legend'> METHOD </span></legend>
				<span style="line-height:50%"><br>
					<input id='radio1' type='radio' name='selectFun' value='D' onclick="viewTR(1)"><label for='radio1' title="procs.creationdtime">Date only</label>&nbsp;
					<input id='radio2' type='radio' name='selectFun' value='P' onclick="viewTR(2)"><label for='radio2' title="procs.procid">PROCID</label>&nbsp;
					<input id='radio3' type='radio' name='selectFun' value='O' onclick="viewTR(3)"><label for='radio3' title="procs.orgprocdefid">ORGPROCDEFID</label>
				</span>
			</fieldset>
		</td>
		<td>
			<fieldset style="width:100%;height:55;">
				<legend id='legend'><span class='legend'> PROCESS STATE <input id='checkall' type='checkbox' name='procStateAll' onclick="allCheck()"><label for='checkall' title="all select">All</label></span></legend>
				<span style="line-height:50%"><br>
					<input id='check1' type='checkbox' name='procState' value='R'><label for='check1' title="state=R">Running</label>&nbsp;
					<input id='check2' type='checkbox' name='procState' value='E'><label for='check2' title="state=E">Error</label>&nbsp;
					<input id='check3' type='checkbox' name='procState' value='V'><label for='check3' title="state=V">Overdue</label>&nbsp;
					<input id='check4' type='checkbox' name='procState' value='S'><label for='check4' title="state=S">Suspended</label>&nbsp;
					<input id='check6' type='checkbox' name='procState' value='T'><label for='check6' title="state=T">Terminated</label>&nbsp;
					<input id='check7' type='checkbox' name='procState' value='C'><label for='check7' title="state=C">Completed</label>&nbsp;
					<input id='check8' type='checkbox' name='procState' value='D'><label for='check8' title="state=D">Dead</label>
				</span>
			</fieldset>
		</td>
	</tr>
	<tr>	
		<td colspan="2">
			<table width="100%" border="0" cellspacing="0">
				<tr>
					<td>
						<fieldset style="width:100%;height:45;">
							<legend id='legend'><span class='legend'> CONDITION </span></legend>
							
							<INPUT class="textinput_d" TYPE="text" NAME="startdtime">
							<img src="./images/year_icon.gif" align="bottom" style="cursor:hand;" onclick="javascript:show_calendar('frm.startdtime')">
							<span style="font-size:20px">~</span>
							<INPUT class="textinput_d" TYPE="text" NAME="cmpltdtime">
							<img src="./images/year_icon.gif" align="bottom" style="cursor:hand;" onclick="javascript:show_calendar('frm.cmpltdtime')">
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;

							<INPUT class="textinput_s" TYPE="text" NAME="inputID" value="" onclick="this.value='';">
					
						</fieldset>
					</td>
					<td>
						<fieldset style="width:100%;height:45;">
							<legend id='legend'><span class='legend'> ACTION </span></legend>

							<INPUT id='act1' TYPE="radio" NAME="act" value="view" onclick="clickDateView(1)"><label for='act1'>VIEW</label>
							<INPUT id='act2' TYPE="radio" NAME="act" value="del"  onclick="clickDateView(2)"><label for='act2'>DELETE</label>
							<INPUT id='act3' TYPE="radio" NAME="act" value="suspend"  onclick="clickDateView(3)"><label for='act3'>suspend</label>
							<INPUT id='act4' TYPE="radio" NAME="act" value="resume"  onclick="clickDateView(4)"><label for='act4'>resume</label>
							<INPUT id='act5' TYPE="radio" NAME="act" value="close"  onclick="clickDateView(5)"><label for='act5'>close</label>

							<input id='check9' type='hidden' name='forceremove' value='true' checked><!-- label for='check9' title="forceremove">강제삭제여부</label-->
							&nbsp;&nbsp;&nbsp;&nbsp;
							
						</fieldset>
					</td>
					<td align="center">
						<input class="buttonSubmit" type="button" name="Start" value="" onClick="javascript:startAction();" />
					</td>
				</tr>
			</table>
		</td>
	</tr>
	<tr>	
		<td colspan="2">
			<fieldset style="width:100%;height:55;">
				<legend id='legend'><span class='legend'> Custom Query 
					<input id='customSQLCheck' type='checkbox' name='customSQLCheck'><label for='customSQLCheck' title="Use Custom Query">Use</label>
					</span></legend>
				<textarea name="customSQL" class="textarea_w"></textarea>
			</fieldset>
		</td>
	</tr>
	<tr>
		<td colspan="2">
			<fieldset style="width:100%;height:500;">
				<legend id='legend'><span class='legend'> RESULT </span></legend>
				<div align="left" valign="bottom"><br>
					<iframe src="" name="ifrm" width="98%" height="90%" frameborder="0"></iframe>
				</div>
			</fieldset>
		</td>
	</tr>
</table>
	
</form>
</BODY>
</HTML>
