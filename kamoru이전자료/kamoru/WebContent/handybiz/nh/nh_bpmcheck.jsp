<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%> 
<%@ page import="java.io.*, java.util.*, com.hs.frmwk.db.*, java.sql.*" %>
<%!
final String dataPath = "F:\\data\\workspace\\kamoru\\WebContent\\handybiz\\nh\\txt\\";
//final String dataPath = "/data01/bpmg/bpmcheck/";

final int cnt_1_bizflow = 17;
final int cnt_2_bizflow = 15;

String[] bpmDiskNames = new String[]{"bpm","data"};
final int bpmDiskMaxLimit = 70;

String[] wasNames = new String[]{"JEUS"};
String[][] qhandlerNames = new String[][]{
		{"DEADLINE",    "flowhandler_deadline.out"},
		{"AM",	    "qhandler_am.out"},         
		{"AR",	    "qhandler_ar.out"}   
};

String[] wasDiskNames = new String[]{"JEUS","data","WEBTOB"};
final int wasDiskMaxLimit = 70;

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
String nh_bpm_date_1 = getText("nh_bpm_date_1.txt");
String nh_bpm_date_2 = getText("nh_bpm_date_2.txt");

String nh_bpm_ps_1 = getText("nh_bpm_ps_1.txt");
String nh_bpm_ps_2 = getText("nh_bpm_ps_2.txt");

String nh_bpm_df_1 = getText("nh_bpm_df_1.txt");
String nh_bpm_df_2 = getText("nh_bpm_df_2.txt");

String nh_bpm_runhistory_1 = getText("nh_bpm_runhistory_1.txt");
String nh_bpm_runhistory_2 = getText("nh_bpm_runhistory_2.txt");

String nh_bpm_vmstat_1 = getText("nh_bpm_vmstat_1.txt");
String nh_bpm_vmstat_2 = getText("nh_bpm_vmstat_2.txt");

// WAS
String nh_was_date_1 = getText("nh_was_date_1.txt");
String nh_was_date_2 = getText("nh_was_date_2.txt");
String nh_was_date_3 = getText("nh_was_date_3.txt");
String nh_was_date_4 = getText("nh_was_date_4.txt");

String nh_was_ps_1 = getText("nh_was_ps_1.txt");
String nh_was_ps_2 = getText("nh_was_ps_2.txt");
String nh_was_ps_3 = getText("nh_was_ps_3.txt");
String nh_was_ps_4 = getText("nh_was_ps_4.txt");

String nh_was_df_1 = getText("nh_was_df_1.txt");
String nh_was_df_2 = getText("nh_was_df_2.txt");
String nh_was_df_3 = getText("nh_was_df_3.txt");
String nh_was_df_4 = getText("nh_was_df_4.txt");

String nh_was_qh_1 = getText("nh_was_qh_1.txt");
String nh_was_qh_2 = getText("nh_was_qh_2.txt");
String nh_was_qh_3 = getText("nh_was_qh_3.txt");
String nh_was_qh_4 = getText("nh_was_qh_4.txt");

String nh_was_vmstat_1 = getText("nh_was_vmstat_1.txt");
String nh_was_vmstat_2 = getText("nh_was_vmstat_2.txt");
String nh_was_vmstat_3 = getText("nh_was_vmstat_3.txt");
String nh_was_vmstat_4 = getText("nh_was_vmstat_4.txt");

%>
       
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<% if(!strRefreshTime.equals("0")) { %>
<meta http-equiv="refresh" content="<%=strRefreshTime%>;url=bpmcheck.jsp?refreshTime=<%=strRefreshTime%>">
<% } %>
<title> BPM Server Health Check list </title>
<style>
button {border: 0px dotted #525D76; font: 9pt 굴림; background: WhiteSmoke; cursor:pointer; width:100%; text-align:right; display:;}	
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
<pre>큐핸들러와 테이블스페이스 보이도록
서버에 적용할 스크립트 작성
</pre>
<h1> BPM System Mornitoring - 운영 </h1>

<form name="frm" style="display:">
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
<table border="1" cellpadding="0" cellspacing="0" width="1200" style="display:">
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
		<td>cnt_1_bizflow</td>
		<td><%= cnt_1_bizflow %></td>
		<td>1서버의 BPM 프로세스 정상 갯수</td>
	</tr>
	<tr>
		<td>cnt_2_bizflow</td>
		<td><%= cnt_2_bizflow %></td>
		<td>2서버의 BPM 프로세스 정상 갯수</td>
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
		<td>wasNames</td>
		<td><%= getArrayValues(wasNames) %></td>
		<td>WAS의 구별 이름</td>
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
			<th> BPM 1 </th>
			<th> BPM 2 </th>
		</tr>
	</thead>
	<tbody>
		<tr>
			<td>vmstat</td>
			<td>  <%= displaySUNvmstat(nh_bpm_vmstat_1) %>
				<button onclick="javascript:viewDetail('nh_bpm_vmstat_1')">자세히보기/닫기</button>
				<pre id="nh_bpm_vmstat_1" style="display:none;" class="console"><%= nh_bpm_vmstat_1 %></pre>		
			</td>
			<td> <%= displaySUNvmstat(nh_bpm_vmstat_2) %> 
				<button onclick="javascript:viewDetail('nh_bpm_vmstat_2')">자세히보기/닫기</button>
				<pre id="nh_bpm_vmstat_2" style="display:none;" class="console"><%= nh_bpm_vmstat_2 %></pre>		
			</td>
		</tr>
		<tr>
			<td>BPM </td>
			<td> <%= displayBPMPS(nh_bpm_ps_1, cnt_1_bizflow) %> 
			 	<button onclick="javascript:viewDetail('nh_bpm_ps_1')">자세히보기/닫기</button>
				<pre id="nh_bpm_ps_1" style="display:none;" class="console"><%= nh_bpm_ps_1 %></pre>		
			</td>
			<td><%= displayBPMPS(nh_bpm_ps_2, cnt_2_bizflow) %> 
			 	<button onclick="javascript:viewDetail('nh_bpm_ps_2')">자세히보기/닫기</button>
				<pre id="nh_bpm_ps_2" style="display:none;" class="console"><%= nh_bpm_ps_2 %></pre>
			</td>
		</tr>
		<tr>
			<td>Disk</td>
			<td> <%= displaySUNDisk(nh_bpm_df_1, bpmDiskNames) %> 
				<button onclick="javascript:viewDetail('nh_bpm_df_1')">자세히보기/닫기</button>
				<pre id="nh_bpm_df_1" style="display:none;" class="console"><%= nh_bpm_df_1 %></pre>		
			</td>
			<td> <%= displaySUNDisk(nh_bpm_df_2, bpmDiskNames) %> 
				<button onclick="javascript:viewDetail('nh_bpm_df_2')">자세히보기/닫기</button>
				<pre id="nh_bpm_df_2" style="display:none;" class="console"><%= nh_bpm_df_2 %></pre>		
			</td>
		</tr> 
		<tr>
			<td>RunHistory</td>
			<td>  <%= nh_bpm_runhistory_1.replaceAll("\n", "<br>") %>
				<button onclick="javascript:viewDetail('nh_bpm_runhistory_1')">자세히보기/닫기</button>
				<pre id="nh_bpm_runhistory_1" style="display:none;" class="console"><%= nh_bpm_runhistory_1 %></pre>		
			</td>
			<td> <%= nh_bpm_runhistory_2.replaceAll("\n", "<br>") %> 
				<button onclick="javascript:viewDetail('nh_bpm_runhistory_2')">자세히보기/닫기</button>
				<pre id="nh_bpm_runhistory_2" style="display:none;" class="console"><%= nh_bpm_runhistory_2 %></pre>		
			</td>
		</tr>
		<tr>
			<td>last updated</td>
			<td><%= nh_bpm_date_1 %></td>
			<td><%= nh_bpm_date_2 %></td>
		</tr>
	</tbody>
</table>

<br>

<!-- WAS Server  -->
<table border="1" cellpadding="0" cellspacing="0" width="1200">
	<colgroup><col width="200"><col width="250"><col width="250"><col width="250"><col width="250"></colgroup>
	<thead>
		<tr>
			<th>WAS</th>
			<th>WAS 1</th>
			<th>WAS 2</th>
			<th>WAS 3</th>
			<th>WAS 4</th>
		</tr>
	</thead>
	<tbody>
		<tr>
			<td>vmstat</td>
			<td>  <%= displaySUNvmstat(nh_was_vmstat_1) %>
				<button onclick="javascript:viewDetail('nh_was_vmstat_1')">자세히보기/닫기</button>
				<pre id="nh_was_vmstat_1" style="display:none;" class="console"><%= nh_was_vmstat_1 %></pre>		
			</td>
			<td> <%= displaySUNvmstat(nh_was_vmstat_2) %> 
				<button onclick="javascript:viewDetail('nh_was_vmstat_2')">자세히보기/닫기</button>
				<pre id="nh_was_vmstat_2" style="display:none;" class="console"><%= nh_was_vmstat_2 %></pre>		
			</td>
			<td> <%= displaySUNvmstat(nh_was_vmstat_3) %> 
				<button onclick="javascript:viewDetail('nh_was_vmstat_3')">자세히보기/닫기</button>
				<pre id="nh_was_vmstat_3" style="display:none;" class="console"><%= nh_was_vmstat_3 %></pre>		
			</td>
			<td> <%= displaySUNvmstat(nh_was_vmstat_4) %> 
				<button onclick="javascript:viewDetail('nh_was_vmstat_4')">자세히보기/닫기</button>
				<pre id="nh_was_vmstat_4" style="display:none;" class="console"><%= nh_was_vmstat_4 %></pre>		
			</td>
		</tr>
		<tr>
			<td>WAS</td>
			<td><%= displayWASPS(nh_was_ps_1, wasNames) %>
				<button onclick="javascript:viewDetail('nh_was_ps_1')">자세히보기/닫기</button>
				<pre id="nh_was_ps_1" style="display:none;" class="console"><%= nh_was_ps_1 %></pre>		
			</td>
			<td><%= displayWASPS(nh_was_ps_2, wasNames) %>
				<button onclick="javascript:viewDetail('nh_was_ps_2')">자세히보기/닫기</button>
				<pre id="nh_was_ps_2" style="display:none;" class="console"><%= nh_was_ps_2 %></pre>		
			</td>
			<td><%= displayWASPS(nh_was_ps_3, wasNames) %>
				<button onclick="javascript:viewDetail('nh_was_ps_3')">자세히보기/닫기</button>
				<pre id="nh_was_ps_3" style="display:none;" class="console"><%= nh_was_ps_3 %></pre>		
			</td>
			<td><%= displayWASPS(nh_was_ps_4, wasNames) %>
				<button onclick="javascript:viewDetail('nh_was_ps_4')">자세히보기/닫기</button>
				<pre id="nh_was_ps_4" style="display:none;" class="console"><%= nh_was_ps_4 %></pre>		
			</td>
		</tr>
		<tr>
			<td>Disk</td>
			<td> <%= displaySUNDisk(nh_was_df_1, wasDiskNames) %>
				<button onclick="javascript:viewDetail('nh_was_df_1')">자세히보기/닫기</button>
				<pre id="nh_was_df_1" style="display:none;" class="console"><%= nh_was_df_1 %></pre>		
			</td>
			<td> <%= displaySUNDisk(nh_was_df_2, wasDiskNames) %>
				<button onclick="javascript:viewDetail('nh_was_df_2')">자세히보기/닫기</button>
				<pre id="nh_was_df_2" style="display:none;" class="console"><%= nh_was_df_2 %></pre>		
			</td>
			<td> <%= displaySUNDisk(nh_was_df_3, wasDiskNames) %>
				<button onclick="javascript:viewDetail('nh_was_df_3')">자세히보기/닫기</button>
				<pre id="nh_was_df_3" style="display:none;" class="console"><%= nh_was_df_3 %></pre>		
			</td>
			<td> <%= displaySUNDisk(nh_was_df_4, wasDiskNames) %>
				<button onclick="javascript:viewDetail('nh_was_df_4')">자세히보기/닫기</button>
				<pre id="nh_was_df_4" style="display:none;" class="console"><%= nh_was_df_4 %></pre>		
			</td>
		</tr>
<%--		
		<tr style="display:none">
			<td>Tablespace / Qhandler</td>
			<td>  DB<br><%= getTablespaceInfo() %> </td>
			<td><%= displayQHandler(LGEWBPM2_qhandler, LGEWBPM2_qhandler_log,  qhandlerNames) %>
				<button onclick="javascript:viewDetail('LGEWBPM2_qhandler')">자세히보기/닫기</button>
				<pre id="LGEWBPM2_qhandler" style="display:none;" class="console"><%= LGEWBPM2_qhandler %></pre>		
			</td>
		</tr>
--%>		
		<tr>
			<td>last updated</td>
			<td><%= nh_was_date_1 %></td>
			<td><%= nh_was_date_2 %></td>
			<td><%= nh_was_date_3 %></td>
			<td><%= nh_was_date_4 %></td>
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
public String displaySUNDisk(String stdout, String[] disks){
	String ret = "";
	String[] stdouts = stdout.split(System.getProperty("line.separator"));
	for(int i=0 ; i<stdouts.length ; i++)
	{
		for(int j=0 ; j<disks.length ; j++)
		{
			if(stdouts[i].indexOf(disks[j]) > -1)
			{
				ArrayList lineList = splitBlank(stdouts[i]);
				ret += lineList.get(5) + " : " + checkMaxP(lineList.get(4).toString(), wasDiskMaxLimit) + " used. <br>\n" ;
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
public String displaySUNvmstat(String stdout){
	String ret = "";
	ArrayList stdList = splitBlank(stdout);
	for(int i=0 ; i<stdList.size() ; i++){
		ret += "[" + i + ":" + stdList.get(i) + "] ";
	}
	ret  = "CPU user: " + stdList.get(47) + " system: " + stdList.get(48) + " idle: " + stdList.get(49) + "<br>";
	ret += "Memory swap: " + stdList.get(31) + " free: " + stdList.get(32) + "<br>";
	ret += "Process r: " + stdList.get(28) + " b: " +  stdList.get(29) + " w: " +  stdList.get(30) + "<br>";
	return ret;
}
public String displayWASvmstat(String stdout){
	String ret = "";
	ArrayList stdList = splitBlank(stdout);
	for(int i=0 ; i<stdList.size() ; i++){
		ret += "[" + i + ":" + stdList.get(i) + "] ";
	}
	ret  = "CPU user: " + stdList.get(47) + " system: " + stdList.get(48) + " idle: " + stdList.get(49) + "<br>";
	ret += "Memory swap: " + stdList.get(31) + " free: " + stdList.get(32) + "<br>";
	ret += "Process r: " + stdList.get(28) + " b: " +  stdList.get(29) + " w: " +  stdList.get(30) + "<br>";
	return ret;
}
public String displayWASvmstat2(String stdout){
	String ret = "";
	ArrayList stdList = splitBlank(stdout);
	for(int i=0 ; i<stdList.size() ; i++){
		ret += "[" + i + ":" + stdList.get(i) + "] ";
	}
	ret  = "CPU user: " + stdList.get(47) + " system: " + stdList.get(48) + " idle: " + stdList.get(49) + "<br>";
	ret += "Memory swap: " + stdList.get(31) + " free: " + stdList.get(32) + "<br>";
	ret += "Process r: " + stdList.get(28) + " b: " +  stdList.get(29) + "<br>";
	return ret;
}

private ArrayList splitBlank(String str){
	//System.out.println(str);
	ArrayList retList = new ArrayList();
	String[] strArr = str.split(" ");
	int cnt = 0;
	for(int i=0 ; i<strArr.length ; i++){
		if(strArr[i].trim().length() != 0){
			retList.add(strArr[i].trim());
			//System.out.println(cnt++ + ".[" + strArr[i].trim() + "]");
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