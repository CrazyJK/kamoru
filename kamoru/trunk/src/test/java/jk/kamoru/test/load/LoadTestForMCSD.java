package jk.kamoru.test.load;

import java.util.ArrayList;
import java.util.List;


public class LoadTestForMCSD extends LoadTester {

	String[] loadUrls = new String[]{
			"http://123.212.190.111:8080/m/home",
			"http://123.212.190.111:8080/m/rqst/home",
			"http://123.212.190.111:8080/m/rqst/list/todo",
			"http://123.212.190.111:8080/m/rqst/20311",
			"http://123.212.190.111:8080/m/rqst/list/run",
			"http://123.212.190.111:8080/m/faq/home",
			"http://123.212.190.111:8080/m/faq/6150",
			"http://123.212.190.111:8080/m/checkup/home",
			"http://123.212.190.111:8080/m/info/company/3433",
			"http://123.212.190.111:8080/m/info/install/24279",
			"http://123.212.190.111:8080/m/club/home",
			"http://123.212.190.111:8080/m/club/20130621"};

	int threadSize;
	long runningTime;



	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) {
		try {
			LoadTestForMCSD loadTest = new LoadTestForMCSD();
			loadTest.threadSize = Integer.parseInt(args[0]);
			loadTest.runningTime = Integer.parseInt(args[1]);
			loadTest.start();
		} catch (Exception e) {
			System.out.println("First argument : Thread size, Second argement : Load time");
			System.err.println(e);
		}
	}

	@Override
	String getLoginUrl() {
		return "http://123.212.190.111:8080/m/j_spring_security_check";
	}

	@Override
	String getLoginDataFormat() {
		return "j_username=%s&j_password=%s";
	}

	@Override
	String getEncoding() {
		return "UTF-8";
	}

	@Override
	String[] getLoadUrls() {
		return loadUrls;
	}

	@Override
	int getThreadSize() {
		return threadSize;
	}

	@Override
	long getRunningTimeMillis() {
		return runningTime * 1000l;
	}

	@Override
	List<String[]> getUserList() {
		List<String[]> userList = new ArrayList<String[]>();
		userList.add(new String[]{"남종관", "1"});
		userList.add(new String[]{"배태영", "1"});
		userList.add(new String[]{"정필종", "1"});
		userList.add(new String[]{"장응주", "1"});
		userList.add(new String[]{"조상욱", "1"});
		return userList;
	}

}	
