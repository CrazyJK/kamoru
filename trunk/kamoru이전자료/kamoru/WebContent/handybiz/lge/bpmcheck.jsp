<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%> 
<%@ page import="java.io.*, java.util.*, com.hs.frmwk.db.*, java.sql.*" %>
<%!
final String dataPath = "F:\\data\\workspace\\kamoru\\WebContent\\lge\\";
//final String dataPath = "/data01/bpmg/bpmcheck/";

final int cnt_1Q_bizflowd = 12;
final int cnt_1Q_bizflowg = 8;
final int cnt_2Q_bizflowg = 10;
String[] bpmDiskNames = new String[]{"home","BPMNAS1","usr"};
final int bpmDiskMaxLimit = 70;

String[] bpmdNames = new String[]{"9310"};
String[] bpmg1Names = new String[]{"9330", "9340", "9350", "9360"};
String[] bpmg2Names = new String[]{"9330", "9340", "9350", "9360"};
String[][] qhandlerNames = new String[][]{
		{"DEADLINE",    "flowhandler_deadline.out"},
		{"AM",	    "qhandler_am.out"},         
		{"AR",	    "qhandler_ar.out"},         
		{"ATP",	    "qhandler_atp.out"},        
		{"CM",	    "qhandler_cm.out"},         
		{"EICSP",	    "qhandler_eicsp.out"},      
		{"GERP",	    "qhandler_gerp.out"},       
		{"GLOBAL",	    "qhandler_global.out"},     
		{"HL",	    "qhandler_hl.out"},         
		{"MAIC",	    "qhandler_maic.out"},       
		{"MAP",	    "qhandler_map.out"},        
		{"MAPIC",	    "qhandler_mapic.out"},      
		{"MCH",	    "qhandler_mch.out"},        
		{"MF",	    "qhandler_mf.out"},         
		{"MGCM",	    "qhandler_mgcm.out"},       
		{"MGRTN",	    "qhandler_mgrtn.out"},      
		{"MJP",	    "qhandler_mjp.out"},        
		{"MMSB",	    "qhandler_mmsb.out"},       
		{"MMU",	    "qhandler_mmu.out"},        
		{"MRU",	    "qhandler_mru.out"},        
		{"MUS",	    "qhandler_mus.out"},        
		{"NPI",	    "qhandler_npi.out"},        
		{"QINTERFACE",  "qhandler_qinterface.out"}, 
		{"SM",	    "qhandler_sm.out"},         
		{"SQLAGENT",    "qhandler_sqlagent_1.out"}, 
		{"SS",	    "qhandler_ss.out"},         
		{"QHandlerSSO", "qhandler_sso.out"},        
		{"SVC",	    "qhandler_svc.out"},        
		{"TM",	    "qhandler_tm.out"},         
		{"UK",	    "qhandler_uk.out"},         
		{"XAC",	    "qhandler_xac.out"},        
		{"XFM",	    "qhandler_xfm.out"},        
		{"XSVC",	    "qhandler_xsvc.out"},       
		{"XSVC2",	    "qhandler_xsvc2.out"}       
};

String[] wasDiskNames = new String[]{"home","array02","data01","log001", "log002"};
final int wasDiskMaxLimit = 70;

/** data file list
LGEBPM1Q_bizflowd.txt
LGEBPM1Q_bizflowg.txt
LGEBPM1Q_date.txt
LGEBPM1Q_df.txt
LGEBPM1Q_vmstat.txt
LGEBPM1Q_runhistory.txt
LGEBPM2Q_bizflowg.txt
LGEBPM2Q_date.txt
LGEBPM2Q_df.txt
LGEBPM2Q_vmstat.txt
LGEBPM2Q_runhistory.txt
LGEWBPM1_bpmd.txt
LGEWBPM1_bpmg.txt
LGEWBPM1_date.txt
LGEWBPM1_df.txt
LGEWBPM1_vmstat.txt
LGEWBPM2_bpmg.txt
LGEWBPM2_date.txt
LGEWBPM2_df.txt
LGEWBPM2_qhandler.txt
LGEWBPM2_qhandler_log.txt
LGEWBPM2_vmstat.txt
*/
public String getText(String fname) throws Exception {
	StringBuffer retsb = new StringBuffer();
	try{
		BufferedReader reader = new BufferedReader(new FileReader(dataPath + fname));
		String inline;
		while((inline = reader.readLine()) != null) {
			if(inline.trim().length() == 0){
				continue;
			}
			retsb.append(inline);
			retsb.append(System.getProperty("line.separator"));
		}
	}catch(FileNotFoundException e){
		retsb.append(e.getMessage());
	}
	return retsb.toString();
}
%>    

<%  
String strRefreshTime = request.getParameter("refreshTime");
if (strRefreshTime == null) {
	strRefreshTime = "0";
}

// BPM
String LGEBPM1Q_date = getText("LGEBPM1Q_date.txt");
String LGEBPM2Q_date = getText("LGEBPM2Q_date.txt");

String LGEBPM1Q_bizflowd = getText("LGEBPM1Q_bizflowd.txt");
String LGEBPM1Q_bizflowg = getText("LGEBPM1Q_bizflowg.txt");
String LGEBPM2Q_bizflowg = getText("LGEBPM2Q_bizflowg.txt");

String LGEBPM1Q_df = getText("LGEBPM1Q_df.txt");
String LGEBPM2Q_df = getText("LGEBPM2Q_df.txt");

String LGEBPM1Q_runhistory = getText("LGEBPM1Q_runhistory.txt");
String LGEBPM2Q_runhistory = getText("LGEBPM2Q_runhistory.txt");

String LGEBPM1Q_vmstat = getText("LGEBPM1Q_vmstat.txt");
String LGEBPM2Q_vmstat = getText("LGEBPM2Q_vmstat.txt");

// WAS
String LGEWBPM1_date = getText("LGEWBPM1_date.txt");
String LGEWBPM2_date = getText("LGEWBPM2_date.txt");

String LGEWBPM1_bpmd = getText("LGEWBPM1_bpmd.txt");
String LGEWBPM1_bpmg = getText("LGEWBPM1_bpmg.txt");
String LGEWBPM2_bpmg = getText("LGEWBPM2_bpmg.txt");

String LGEWBPM1_df = getText("LGEWBPM1_df.txt");
String LGEWBPM2_df = getText("LGEWBPM2_df.txt");

String LGEWBPM2_qhandler = getText("LGEWBPM2_qhandler.txt");
String LGEWBPM2_qhandler_log = getText("LGEWBPM2_qhandler_log.txt");

String LGEWBPM1_vmstat = getText("LGEWBPM1_vmstat.txt");
String LGEWBPM2_vmstat = getText("LGEWBPM2_vmstat.txt");
%>
       
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<% if(!strRefreshTime.equals("0")) { %>
<meta http-equiv="refresh" content="<%=strRefreshTime%>;url=bpmcheck.jsp?refreshTime=<%=strRefreshTime%>">
<% } %>
<title> BPM Server Health Check list </title>
<style>
button {border: 0px dotted #525D76; font: 9pt 굴림; background: WhiteSmoke; cursor:pointer; width:100%; text-align:right}	
pre.console {border: 1px dotted #525D76; font-size: 8pt; background:WhiteSmoke;}
th {background: Orange;}
tr {vertical-align:text-top;}
form	{display: inline;}
.OK	{color:blue}
.NG {color:red}
</style>
<script>
function viewDetail(objid){
	obj = document.getElementById(objid);
	dispValue = obj.style.display;
	if(dispValue == 'none'){
		obj.style.display = "";
	}else{
		obj.style.display = "none";
	}
}
function setTime(){
	if(!confirm("실행주기를 "+(parseInt(document.frm.refreshTime.value)/60)+" 분으로 변경 하시겠습니까?")) {
		document.frm.refreshTime.value = _time;
	 	return;
	}
	document.frm.submit();
}
</script>
</head>
<body>
<h1> LGEXBPM System Mornitoring - 운영 </h1>

<form name="frm">
화면 갱신 주기&nbsp;&nbsp;
	<select name="refreshTime" onChange="setTime()" style="text-align:right;">
	<% for (int i = 0; i <= 600; i=i+60) { %>
		<option value="<%= i %>" <%= i == Integer.parseInt(strRefreshTime)? "selected" : ""%> style="text-align:right;"><%=i/60%> m</option>
	<% } %>
	</select>&nbsp;&nbsp;
	LastUpdated : <%= new java.util.Date().toString() %>
	<a href="javascript:viewDetail('SET_VAR')">설정값 확인</a>
</form>

<!-- 설정 변수 확인 -->
<div id="SET_VAR" style="display:none">
<table border="1" cellpadding="0" cellspacing="0" width="1200">
	<tr>
		<th>변수명</th>
		<th>값</th>
		<th>설명</th>
	</tr>
	<tr>
		<td>dataPath</td>
		<td><%= dataPath %></td>
		<td>데이터가 있는 폴더</td>
	</tr>
	<tr>
		<td>cnt_1Q_bizflowd</td>
		<td><%= cnt_1Q_bizflowd %></td>
		<td>1Q서버의 국내BPM 프로세스 정상 갯수</td>
	</tr>
	<tr>
		<td>cnt_1Q_bizflowg</td>
		<td><%= cnt_1Q_bizflowg %></td>
		<td>1Q서버의 법인BPM 프로세스 정상 갯수</td>
	</tr>
	<tr>
		<td>cnt_2Q_bizflowg</td>
		<td><%= cnt_2Q_bizflowg %></td>
		<td>2Q서버의 법인BPM 프로세스 정상 갯수</td>
	</tr>
	<tr>
		<td>bpmDiskNames</td>
		<td><%= getArrayValues(bpmDiskNames) %></td>
		<td>BPM서버(1Q,2Q)에서 모니터링할 디스크 마운트명</td>
	</tr>
	<tr>
		<td>bpmDiskMaxLimit</td>
		<td><%= bpmDiskMaxLimit %></td>
		<td>디스크 사용량의 정상 최고값</td>
	</tr>
	<tr>
		<td>bpmdNames</td>
		<td><%= getArrayValues(bpmdNames) %></td>
		<td>국내 WAS의 포트</td>
	</tr>
	<tr>
		<td>bpmg1Names</td>
		<td><%= getArrayValues(bpmg1Names) %></td>
		<td>LGEWBPM1의 법인 포트</td>
	</tr>
	<tr>
		<td>bpmg2Names</td>
		<td><%= getArrayValues(bpmg2Names) %></td>
		<td>LGEWBPM2의 법인 포트</td>
	</tr>
	<tr>
		<td>qhandlerNames</td>
		<td><%= getArrayValues(qhandlerNames) %></td>
		<td>Qhandler를 구분하는 이름</td>
	</tr>
	<tr>
		<td>wasDiskNames</td>
		<td><%= getArrayValues(wasDiskNames) %></td>
		<td>WAS서버에서 모니터링할 디스크 마운트명</td>
	</tr>
	<tr>
		<td>wasDiskMaxLimit</td>
		<td><%= wasDiskMaxLimit %></td>
		<td>WAS서버 디스크 사용량의 정상 최고값</td>
	</tr>
</table>
</div>

<!-- BPM Server  -->
<table border="1" cellpadding="0" cellspacing="0" width="1200">
	<colgroup><col width="200"><col width="500"><col width="500"></colgroup>
	<thead>
		<tr>
			<th>BPM</th>
			<th>LGEBPM1Q </th>
			<th>LGEBPM2Q </th>
		</tr>
	</thead>
	<tbody>
		<tr>
			<td>vmstat</td>
			<td>  <%= displayBPMvmstat(LGEBPM1Q_vmstat) %>
				<button onclick="javascript:viewDetail('LGEBPM1Q_vmstat')">자세히보기/닫기</button>
				<pre id="LGEBPM1Q_vmstat" style="display:none;" class="console"><%= LGEBPM1Q_vmstat %></pre>		
			</td>
			<td> <%= displayBPMvmstat(LGEBPM2Q_vmstat) %> 
				<button onclick="javascript:viewDetail('LGEBPM2Q_vmstat')">자세히보기/닫기</button>
				<pre id="LGEBPM2Q_vmstat" style="display:none;" class="console"><%= LGEBPM2Q_vmstat %></pre>		
			</td>
		</tr>
		<tr>
			<td>BPM 국내 bizflowd</td>
			<td> <%= displayBPMPS(LGEBPM1Q_bizflowd, cnt_1Q_bizflowd) %> 
				<button onclick="javascript:viewDetail('LGEBPM1Q_bizflowd')">자세히보기/닫기</button>
				<pre id="LGEBPM1Q_bizflowd" style="display:none;" class="console"><%= LGEBPM1Q_bizflowd %></pre>
			</td>
			<td> 해당사항 없음 </td>
		</tr>
		<tr>
			<td>BPM 법인 bizflowg</td>
			<td> <%= displayBPMPS(LGEBPM1Q_bizflowg, cnt_1Q_bizflowg) %> 
			 	<button onclick="javascript:viewDetail('LGEBPM1Q_bizflowg')">자세히보기/닫기</button>
				<pre id="LGEBPM1Q_bizflowg" style="display:none;" class="console"><%= LGEBPM1Q_bizflowg %></pre>		
			</td>
			<td><%= displayBPMPS(LGEBPM2Q_bizflowg, cnt_2Q_bizflowg) %> 
			 	<button onclick="javascript:viewDetail('LGEBPM2Q_bizflowg')">자세히보기/닫기</button>
				<pre id="LGEBPM2Q_bizflowg" style="display:none;" class="console"><%= LGEBPM2Q_bizflowg %></pre>
			</td>
		</tr>
		<tr>
			<td>Disk</td>
			<td> <%= displayBPMDF(LGEBPM1Q_df, bpmDiskNames) %> 
				<button onclick="javascript:viewDetail('LGEBPM1Q_df')">자세히보기/닫기</button>
				<pre id="LGEBPM1Q_df" style="display:none;" class="console"><%= LGEBPM1Q_df %></pre>		
			</td>
			<td> <%= displayBPMDF(LGEBPM2Q_df, bpmDiskNames) %> 
				<button onclick="javascript:viewDetail('LGEBPM2Q_df')">자세히보기/닫기</button>
				<pre id="LGEBPM2Q_df" style="display:none;" class="console"><%= LGEBPM2Q_df %></pre>		
			</td>
		</tr>
		<tr>
			<td>RunHistory</td>
			<td>  <%= LGEBPM1Q_runhistory.replaceAll("\n", "<br>") %>
				<button onclick="javascript:viewDetail('LGEBPM1Q_runhistory')">자세히보기/닫기</button>
				<pre id="LGEBPM1Q_runhistory" style="display:none;" class="console"><%= LGEBPM1Q_runhistory %></pre>		
			</td>
			<td> <%= LGEBPM2Q_runhistory.replaceAll("\n", "<br>") %> 
				<button onclick="javascript:viewDetail('LGEBPM2Q_runhistory')">자세히보기/닫기</button>
				<pre id="LGEBPM2Q_runhistory" style="display:none;" class="console"><%= LGEBPM2Q_runhistory %></pre>		
			</td>
		</tr>
		<tr>
			<td>last updated</td>
			<td><%= LGEBPM1Q_date %></td>
			<td><%= LGEBPM2Q_date %></td>
		</tr>
	</tbody>
</table>

<br>

<!-- WAS Server  -->
<table border="1" cellpadding="0" cellspacing="0" width="1200">
	<colgroup><col width="200"><col width="500"><col width="500"></colgroup>
	<thead>
		<tr>
			<th>WAS</th>
			<th>LGEWBPM1</th>
			<th>LGEWBPM2</th>
		</tr>
	</thead>
	<tbody>
		<tr>
			<td>vmstat</td>
			<td>  <%= displayWASvmstat(LGEWBPM1_vmstat) %>
				<button onclick="javascript:viewDetail('LGEWBPM1_vmstat')">자세히보기/닫기</button>
				<pre id="LGEWBPM1_vmstat" style="display:none;" class="console"><%= LGEWBPM1_vmstat %></pre>		
			</td>
			<td> <%= displayWASvmstat2(LGEWBPM2_vmstat) %> 
				<button onclick="javascript:viewDetail('LGEWBPM2_vmstat')">자세히보기/닫기</button>
				<pre id="LGEWBPM2_vmstat" style="display:none;" class="console"><%= LGEWBPM2_vmstat %></pre>		
			</td>
		</tr>
		<tr>
			<td>WebLogic 국내 bpmd</td>
			<td><%= displayWASPS(LGEWBPM1_bpmd, bpmdNames) %>
				<button onclick="javascript:viewDetail('LGEWBPM1_bpmd')">자세히보기/닫기</button>
				<pre id="LGEWBPM1_bpmd" style="display:none;" class="console"><%= LGEWBPM1_bpmd %></pre>		
			</td>
			<td> 해당사항 없음 </td>
		</tr>
		<tr>
			<td>WebLogic 법인 bpmg</td>
			<td><%= displayWASPS(LGEWBPM1_bpmg, bpmg1Names) %>
				<button onclick="javascript:viewDetail('LGEWBPM1_bpmg')">자세히보기/닫기</button>
				<pre id="LGEWBPM1_bpmg" style="display:none;" class="console"><%= LGEWBPM1_bpmg %></pre>		
			</td>
			<td><%= displayWASPS(LGEWBPM2_bpmg, bpmg2Names) %>
				<button onclick="javascript:viewDetail('LGEWBPM2_bpmg')">자세히보기/닫기</button>
				<pre id="LGEWBPM2_bpmg" style="display:none;" class="console"><%= LGEWBPM2_bpmg %></pre>		
			</td>
		</tr>
		<tr>
			<td>Disk</td>
			<td> <%= displayWASDisk(LGEWBPM1_df, wasDiskNames) %>
				<button onclick="javascript:viewDetail('LGEWBPM1_df')">자세히보기/닫기</button>
				<pre id="LGEWBPM1_df" style="display:none;" class="console"><%= LGEWBPM1_df %></pre>		
			</td>
			<td><%= displayWASDisk(LGEWBPM2_df, wasDiskNames) %>
				<button onclick="javascript:viewDetail('LGEWBPM2_df')">자세히보기/닫기</button>
				<pre id="LGEWBPM2_df" style="display:none;" class="console"><%= LGEWBPM2_df %></pre>		
			</td>
		</tr>
		<tr>
			<td>Tablespace / Qhandler</td>
			<td> lgexbpm DB<br><%= getTablespaceInfo() %> </td>
			<td><%= displayQHandler(LGEWBPM2_qhandler, LGEWBPM2_qhandler_log,  qhandlerNames) %>
				<button onclick="javascript:viewDetail('LGEWBPM2_qhandler')">자세히보기/닫기</button>
				<pre id="LGEWBPM2_qhandler" style="display:none;" class="console"><%= LGEWBPM2_qhandler %></pre>		
			</td>
		</tr>
		<tr>
			<td>last updated</td>
			<td><%= LGEWBPM1_date %></td>
			<td><%= LGEWBPM2_date %></td>
		</tr>
	</tbody>
</table>
<script language="javaScript">
	var _time = document.frm.refreshTime.value;
</script>
</body>
</html>
<%!
public String runCommand(String commandLine){
	try {
		
		Runtime runtime = Runtime.getRuntime();
		Process process = runtime.exec(commandLine);
	
		StringBuffer stdout = new StringBuffer();
		String inLine = "";
		java.io.BufferedReader in = new java.io.BufferedReader(new java.io.InputStreamReader(process.getInputStream()));
	
		while ((inLine = in.readLine()) != null) {
			stdout.append(inLine);
			stdout.append(System.getProperty("line.separator"));
		}
	
		in.close();
		process.destroy();
		return stdout.toString();
	} catch(Exception e) {
		return e.getMessage();
	}
}
public String displayBPMPS(String stdout, int okCount){
	String[] stdouts = stdout.split(System.getProperty("line.separator"));
	if(stdouts.length == okCount){
		return "<span class='OK'>" + stdouts.length + "/" + okCount + " process. OK.</span>";
	}else{
		return "<span class='NG'>" + stdouts.length + "/" + okCount + " process. NoGood.</span>";
	}
}
public String displayWASDisk(String stdout, String[] disks){
	String ret = "";
	String[] stdouts = stdout.split(System.getProperty("line.separator"));
	for(int i=0 ; i<stdouts.length ; i++)
	{
		for(int j=0 ; j<disks.length ; j++)
		{
			if(stdouts[i].indexOf(disks[j]) > -1)
			{
				String[] line = stdouts[i].split(" ");
				java.util.ArrayList splitList = new java.util.ArrayList(); 
				for(int k=0 ; k<line.length ; k++) 
				{
					if(line[k].trim().length() != 0)
					{
						splitList.add(line[k]);
					}
				}
				ret += splitList.get(6) + " : " + checkMaxP(splitList.get(3).toString(), wasDiskMaxLimit) + " used. <br>\n" ;
			}
		}
	}
	return ret;
}
public String checkMaxP(String str, int max){
	String num = str;
	if(str.indexOf("%") > -1){
		num = str.substring(0, str.length()-1);	
	}
	
	int i = Integer.parseInt(num.trim());
	if(i<max){
		return str;
	}else{
		return "<font class='NG'>" + str + "</font>";
	}
}

public String displayWASPS(String stdout, String[] portNames){
	String ret = "";
	for(int i=0 ; i<portNames.length ; i++){
		ret += portNames[i];
		if(stdout.indexOf(portNames[i]) > -1){
			ret += " is running. <br>\n";
		}else{
			ret += " is <span class='NG'>not running.</span> <br>\n";
		}
	}
	return ret;
}
public String displayQHandler(String stdout, String log, String[][] names){
	String ret = "<table border=0 cellspacing=0 cellpadding=0>";
	//String[] stdouts = stdout.split(System.getProperty("line.separator"));
	String[] logArr = log.split(System.getProperty("line.separator"));
	for(int i=0 ; i<names.length ; i++)
	{
		ret += "<tr>";
		ret += "<td align=right>" + (i+1) + "&nbsp;</td>";
		ret += "<td>" + names[i][0];
		//for(int j=0 ; j<stdouts.length ; j++)
		//{
			if(stdout.indexOf(names[i][0]) > -1)
			{
				ret += " is running. </td>";
				for(int k=0 ; k<logArr.length ; k++){
					if(logArr[k].indexOf(names[i][1]) > -1){
						ret += "<td>";
						ret += logArr[k].substring(45, 58);
						ret += "</td>";
					}
				}
				ret += "</td>";
			}else{
				ret += " <span class='NG'> not exist.</span> </td>";	
			}
		//}
		ret += "</tr>";
	}
	ret += "</table>";
	return ret;
}

public String displayBPMDF(String stdout, String[] disks) throws Exception{
	String ret = "";
	String[] stdouts = stdout.split(System.getProperty("line.separator"));
	for(int i=0 ; i<stdouts.length ; i=i+4) {
		if(stdouts[i].trim().length() > 0){
			ret += stdouts[i].substring(0,10).trim() + " : "
				+  checkMaxP(stdouts[i+3].trim().substring(0,3).trim(), bpmDiskMaxLimit) + " % <br>\n";  
		}
	}
	
	String ret2 = "";
	String[] rets = ret.split("\n");
	for(int i=0 ; i<disks.length ; i++) {
		for(int j=0 ; j<rets.length ; j++) {
			if(rets[j].indexOf(disks[i]) > -1){
				ret2 += rets[j];
			}
		}
			
	}
	return ret2;
}

public String getArrayValues(String[] strs){
	if(strs == null){
		return "Null";
	}else{
		String ret = "";
		for(int i=0 ; i<strs.length ; i++){
			ret += strs[i] + " ";
		}
		return ret;
	}
}
public String getArrayValues(String[][] strs){
	if(strs == null){
		return "Null";
	}else{
		String ret = "";
		for(int i=0 ; i<strs.length ; i++){
			ret += strs[i][0] + " ";
		}
		return ret;
	}
}
public String displayBPMvmstat(String stdout){
	String ret = "";
	ArrayList stdList = splitBlank(stdout);
	for(int i=0 ; i<stdList.size() ; i++){
		ret += "[" + i + ":" + stdList.get(i) + "] ";
	}
	ret  = "CPU user: " + stdList.get(38) + " system: " + stdList.get(39) + " idle: " + stdList.get(40) + "<br>";
	ret += "Memory avm: " + stdList.get(26) + " free: " + stdList.get(27) + "<br>";
	ret += "Process r: " + stdList.get(23) + " b: " +  stdList.get(24) + " w: " +  stdList.get(25) + "<br>";
	return ret;
}
public String displayWASvmstat(String stdout){
	String ret = "";
	ArrayList stdList = splitBlank(stdout);
	for(int i=0 ; i<stdList.size() ; i++){
		ret += "[" + i + ":" + stdList.get(i) + "] ";
	}
	ret = "CPU user: " + stdList.get(43) + " system: " + stdList.get(44) + " idle: " + stdList.get(45) + "<br>";
	ret += "Memory avm: " + stdList.get(32) + " fre: " + stdList.get(33) + "<br>";
	ret += "Process r: " + stdList.get(30) + " b: " +  stdList.get(31) + "<br>";
	return ret;
}
public String displayWASvmstat2(String stdout){
	String ret = "";
	ArrayList stdList = splitBlank(stdout);
	for(int i=0 ; i<stdList.size() ; i++){
		ret += "[" + i + ":" + stdList.get(i) + "] ";
	}
	ret  = "CPU user: " + stdList.get(46) + " system: " + stdList.get(47) + " idle: " + stdList.get(48) + "<br>";
	ret += "Memory avm: " + stdList.get(35) + " fre: " + stdList.get(36) + "<br>";
	ret += "Process r: " + stdList.get(33) + " b: " +  stdList.get(34) + "<br>";
	return ret;
}

private ArrayList splitBlank(String str){
	ArrayList retList = new ArrayList();
	String[] strArr = str.split(" ");
	for(int i=0 ; i<strArr.length ; i++){
		if(strArr[i].trim().length() != 0){
			retList.add(strArr[i].trim());
		}
	}
	return retList;
}

public String getTablespaceInfo(){
	String sql = "SELECT                                                                      " 
		+ "     a.tablespace_name, ROUND(SUM(a.total)/1024/1024, 2) Total,                    "
		+ "     ROUND((SUM(a.total) - SUM(NVL(b.free, 0)))/1024/1024, 2) Used,                "
		+ "     ROUND(SUM(NVL(b.free, 0))/1024/1024,2) Free,                                  "
		+ "     ROUND((SUM(a.total) - SUM(NVL(b.free, 0))) / SUM(a.total) * 100 , 2) UsedP    "
		+ "FROM                                                                               "
		+ "     (                                                                             "
		+ "       SELECT d.tablespace_name, d.file_id, sum(d.bytes) total                     "
		+ "       FROM dba_data_files d                                                       "
		+ "       GROUP BY d.tablespace_name, d.file_id                                       "
		+ "      ) a,                                                                         "
		+ "     (                                                                             "
		+ "       SELECT f.file_id, sum(f.bytes) free                                         "
		+ "       FROM dba_free_space f                                                       "
		+ "       GROUP BY f.file_id                                                          "
		+ "     ) b                                                                           "
		+ "WHERE                                                                              "
		+ "     a.file_id = b.file_id(+)                                                      "
		+ "GROUP BY a.tablespace_name                                                         ";
	StringBuffer retsb = new StringBuffer("<table border=1 cellpadding=0 cellspacing=0>");
	retsb.append("<tr>");
	retsb.append("<th>TABLESPACE_NAME</th>");
	retsb.append("<th>Total(M)</th>");
	retsb.append("<th>Used(M)</th>");
	retsb.append("<th>Free(M)</th>");
	retsb.append("<th>Used(%)</th>");
	retsb.append("</tr>");
	try{
		ArrayList tsList = executeQuery(sql, null);
		for(int i=0 ; i<tsList.size() ; i++){
			HashMap record = (HashMap)tsList.get(i);
			retsb.append("<tr>");
			retsb.append("<td>");
			retsb.append(record.get("TABLESPACE_NAME"));
			retsb.append("</td>");
			retsb.append("<td align=right>");
			retsb.append(record.get("TOTAL"));
			retsb.append("</td>");
			retsb.append("<td align=right>");
			retsb.append(record.get("USED"));
			retsb.append("</td>");
			retsb.append("<td align=right>");
			retsb.append(record.get("FREE"));
			retsb.append("</td>");
			retsb.append("<td align=right>");
			retsb.append(record.get("USEDP"));
			retsb.append("</td>");
			retsb.append("</tr>");
		}
	}catch(Exception e){
		retsb.append("<tr>");
		retsb.append("<td colspan=5> Error! ");
		retsb.append(e.getMessage());
		retsb.append("</td>");
		retsb.append("</tr>");
	}
	retsb.append("</table>");
	return retsb.toString();
}
public ArrayList executeQuery(String sql, ArrayList param) throws Exception
{
	ArrayList recordList = null;
	BWDBHelper helper = null;
	try{
		recordList = new ArrayList();
		helper = BWDBHelperFactory.getBWDBHelper("bizflow");
		helper.setPreparedSQL(sql);
		if(param != null){
			for(int i=0 ; i<param.size() ; i++){
				helper.setString(i+1, param.get(i).toString());
			}
		}
		helper.executeQuery();

		//get header name
		ResultSetMetaData metadata = helper.getMetaData();
		int columnCnt = metadata.getColumnCount();
		String[] columnName = new String[columnCnt];
		for(int i=0 ; i<columnCnt ; i++){
			columnName[i] = metadata.getColumnName(i+1);
		}
		
		//get data
		while(helper.next()){
			HashMap record = new HashMap();
			for(int i=0 ; i<columnCnt ; i++){
				record.put(columnName[i], helper.getRSString(columnName[i]));
			}
			recordList.add(record);
		}	
		return recordList;
	}catch(Exception e){
		//e.printStackTrace();
		throw e;
	}finally{
		if(helper != null) 
			try{helper.close();}catch(Exception e){}
	}
}

%>