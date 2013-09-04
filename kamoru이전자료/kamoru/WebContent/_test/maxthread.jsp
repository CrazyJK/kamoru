<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<%!
class DummyThread extends Thread {
	public void run() {
		try {
			Thread.sleep(100000);
		} catch(Exception e) {
			System.out.println(e.getMessage());
		}
	}
}
%>
<%
// 16,213 created and javax.servlet.ServletException: java.lang.OutOfMemoryError: unable to create new native thread


int cnt = 0;
while(true) {
	new DummyThread().start();
	cnt ++;
	if(cnt%100==0) System.out.format("%d thread created.\n", cnt);
}
%>