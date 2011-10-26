<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%> 
<%@ page import="java.io.*, java.util.*" %>
<%!
final String scriptPath = "/data1/lgexbpm/script/";

String[] wasNames = new String[]{"7820", "7830"};
String[] wasdfNames = new String[]{"home","data1"};
String[] qNames = new String[]{"ATP","CM","MF","TM","SS","HL","GLOBAL","SM","NPI","SVC","MGCM","MMSB","XAC","AR","MAIC","XSVC","XSVC2","EICSP","XFM","MRU","MAPIC","MUS","MCH","MGRTN","DEADLINE","AM","SQLAGENT","qinterface.jar","qinterface5.jar","qinterface2.jar","qinterface_ss.jar","qinterface_ipt.jar","qinterface3.jar","qinterface4.jar"};
%>    
<%  
/*bpm check*/
String bpmCheck = runCommand(scriptPath + "getBPMStateByFTP.sh");
System.out.println(bpmCheck);
String bpmPS	= getBPMPS("bpmps_165.243.166.106.txt");
String bpmDF	= getBPMDF("df_165.243.166.106.txt");

/*WAS ps*/
String wasPS = runCommand(scriptPath + "ps_was.sh");
String wasDF = runCommand("df -k");

/*QHandler*/
String qhandlerPS = runCommand(scriptPath + "ps_qhandler.sh");


%>    
       
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title> BPM Server Health Check list </title>
<link rel="stylesheet" type="text/css" href="/bizflow/themes/default/bizflow_ko.css">
<style>
pre.console {border: 1px dotted #525D76; font-size: 8pt; background:WhiteSmoke;}
</style>
</head>
<body>
last updated : <%= new java.util.Date().toString() %><hr>
<h2> 개발 서버 상태 </h2>

<table width="1200">
	<tr valign="top">
		<td width="50%">
			<!-- BPM Server Info Start -->
			<table border="1" cellpadding="0" cellspacing="0" width="100%">
				<thead>
					<tr>
						<th rowspan="2">BPM</th>
						<th colspan="1">LGEABPM0 156.147.36.223</th>
					</tr>
					<tr>
						<th>계정 : bizflow2 (법인)</th>
					</tr>
				</thead>
				<tbody>
					<tr>
						<td>BPM Server</td>
						<td><pre class="console"><%= bpmPS %></pre></td>
					</tr>
					<tr>
						<td>Disk</td>
						<td colspan="1"><pre class="console"><%= bpmDF %></pre></td>
					</tr>
					<tr>
						<td>Load averages</td>
						<td colspan="1">top <br> 
							<pre class="console"></pre>
						</td>
					</tr>
				</tbody>
			</table>
			<!-- BPM Server Info End -->
		</td>
		<td width="50%">
			<!-- WAS Server Info Start -->
			<table border="1" cellpadding="0" cellspacing="0" width="100%">
				<colgroup><col width="100"><col></colgroup>
				<thead>
					<tr>
						<th rowspan="2">WAS</th>
						<th colspan="1">L03TPMASE0Q 156.147.36.223 개발서버</th>
					</tr>
					<tr>
						<th>Account : lgexbpm</th>
					</tr>
				</thead>
				<tbody>
					<tr>
						<td>WebLogic</td>
						<td><pre class="console"><%= runningCheck(wasPS, wasNames) %></pre></td>
					</tr>
					<tr>
						<td>Disk</td>
						<td colspan="1"><pre class="console"><%= convertDisk(wasDF, wasdfNames) %></pre></td>
					</tr>
					<tr>
						<td>qhandler</td>
						<td><pre class="console"><%= runningCheck(qhandlerPS, qNames) %></pre></td>
					</tr>
				</tbody>
			</table>
			<!-- WAS Server Info End -->
		</td>
	</tr>
</table>
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
public String convertBPMPS(String stdout){
	String[] stdouts = stdout.split(System.getProperty("line.separator"));
	if(stdouts.length == 12){
		return stdouts.length + " process. OK.";
	}else{
		return stdouts.length + " process. NG.";
	}
}
public String convertDisk(String stdout, String[] disks){
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
				ret += splitList.get(6) + " : " + checkMaxP(splitList.get(3).toString(), 80) + " used. \n" ;
			}
		}
	}
	return ret;
}
public String checkMaxP(String str, int max){
	String num = str.substring(0, str.length()-1);
	int i = Integer.parseInt(num);
	if(i<max){
		return str;
	}else{
		return "<font color=red>" + str + "</font>";
	}
}

public String convertWASPS(String stdout){
	String ret = "";
	String[] stdouts = stdout.split(System.getProperty("line.separator"));
	for(int i=0 ; i<stdouts.length ; i++){
		String[] strArr = stdouts[i].split(" ");
		for(int j=0 ; j<strArr.length ; j++){
			if(strArr[j].indexOf("-D7") > -1){
				ret += strArr[j] + " is running. ";
			}
		}
	}
	return ret;
}
public String runningCheck(String stdout, String[] names){
	String ret = "";
	//String[] stdouts = stdout.split(System.getProperty("line.separator"));
	for(int i=0 ; i<names.length ; i++)
	{
		ret += (i+1) + ". " + names[i];
		//for(int j=0 ; j<stdouts.length ; j++)
		//{
			if(stdout.indexOf(names[i]) > -1)
			{
				ret += " is running. \n";
			}else{
				ret += " <font color=red> not exist. \n";	
			}
		//}
	}
	return ret;
}

public String getBPMPS(String file) throws Exception{
	BufferedReader reader = new BufferedReader(new FileReader(scriptPath + file));
	String ret = "";
	String line = null;
	while((line = reader.readLine()) != null){
		ret += line + "\n";	
	}
	reader.close();
	return ret;
}

public String getBPMDF(String file) throws Exception{
	BufferedReader reader = new BufferedReader(new FileReader(scriptPath + file));
	String ret = "";
	String line = null;
	while((line = reader.readLine()) != null){
		ret += line + "\n";	
	}
	reader.close();
	return ret;
}

%>