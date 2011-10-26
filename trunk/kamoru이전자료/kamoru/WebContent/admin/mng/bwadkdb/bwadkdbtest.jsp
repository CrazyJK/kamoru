<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>

<%@ page import="com.hs.frmwk.db.*" %>
<%
int loopCount = request.getParameter("loop") != null ? Integer.parseInt(request.getParameter("loop")) : 10;
int threadCount = request.getParameter("thread") != null ? Integer.parseInt(request.getParameter("thread")) : 10;

for(int c=0 ; c < loopCount ; c++) {

	for(int i=0 ; i<threadCount ; i++) {
		new BWHelperThread(i).start();
	}

	try {
		Thread.sleep(1000);
	} catch(Exception e){
		System.out.println("Thread sleep exception ");				
		e.printStackTrace();
	}
}
System.out.format("\t\t\t TotalCount %d, successCount %d, failCount %d\n", totalCnt, successCnt, failCnt);

%>
<%!
public static int totalCnt = 0;

public static int successCnt = 0;
public static int failCnt = 0;

class BWHelperThread extends Thread {
	int no;
	
	public BWHelperThread(int no) {
		this.no = no;
	}
	
	public void run() {
		totalCnt++;
		executeHelper();
	}
	
	//synchronized
	public void executeHelper() {
		long st = System.nanoTime();
		
		//System.out.println("No." + no + " Start");
		BWDBHelper helper = null;
		try {
			helper = BWDBHelperFactory.getBWDBHelper("kamoruOracle", "No." + no);
			helper.setPreparedSQL("SELECT * FROM rlvntdata WHERE procid = 1001 AND rlvntdataseq = 1");
			helper.executeQuery();
			String tmp;
			while(helper.next()) {
				tmp = helper.getRSString("value");
			}			
			successCnt++;
			//System.out.println("No." + no + " completed");
		} catch(Exception e) {
			failCnt++;
			//System.out.println("No." + no + " Error :" + e.getMessage());
			//e.printStackTrace();
			String errmsg = e.getMessage();
			System.out.println("No." + no + " catch exception :");
			if(errmsg.indexOf("Connection reset") > -1) {
				System.out.println("No." + no + " connection reset");				
				e.printStackTrace();
			}else {
				System.out.println("No." + no + " else exception");				
				e.printStackTrace();
			}
		} finally {
			if(helper != null)
				try { helper.close(); } catch(Exception e) {
					System.out.print("No." + no + " finally exception :");
					e.printStackTrace();
				}
		}
		//System.out.println("No." + no + " End Elapsed Time:" + (System.nanoTime() - st)/1000000 + "ms");		
	}
}
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
<title>Total cnt : <%= totalCnt %></title>
</head>
<body>
<table border="1">
	<tr>
		<td><%= "successCount : " + successCnt %></td>
		<td><%= "failCount: " + failCnt %></td>
		<td><%= "Total cnt : " + totalCnt %></td>
	</tr>
</table>
</body>
</html>