package jk.kamoru.test.load;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class LoadTestForMCSD extends LoadTester {

	int threadSize;
	int runningTime;
	int maxSleepTime;



	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) {
		try {
			LoadTestForMCSD loadTest = new LoadTestForMCSD();
			loadTest.threadSize   = Integer.parseInt(args[0]);
			loadTest.runningTime  = Integer.parseInt(args[1]);
			loadTest.maxSleepTime = Integer.parseInt(args[2]);
			loadTest.start();
		} catch (Exception e) {
			System.out.println("First argument : Thread size, Second argement : Load time");
			System.err.println(e);
		}
	}

	@Override
	URL getLoginURL() {
		try {
			return new URL("http://123.212.190.111:8080/m/j_spring_security_check");
		} catch (MalformedURLException e) {
			throw new IllegalStateException(e);
		}
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
	List<URL> getLoadURLs() {
		try {
			return Arrays.asList(
					new URL("http://123.212.190.111:8080/m/home"),
					new URL("http://123.212.190.111:8080/m/rqst/home"),
					new URL("http://123.212.190.111:8080/m/rqst/list/todo"),
					new URL("http://123.212.190.111:8080/m/rqst/20311"),
					new URL("http://123.212.190.111:8080/m/rqst/list/run"),
					new URL("http://123.212.190.111:8080/m/faq/home"),
					new URL("http://123.212.190.111:8080/m/faq/6150"),
					new URL("http://123.212.190.111:8080/m/checkup/home"),
					new URL("http://123.212.190.111:8080/m/info/company/3433"),
					new URL("http://123.212.190.111:8080/m/info/install/24279"),
					new URL("http://123.212.190.111:8080/m/club/home"),
					new URL("http://123.212.190.111:8080/m/club/20130621"));
		} catch (MalformedURLException e) {
			throw new IllegalStateException(e);
		}
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
	List<LoadTester.LoginUser> getLoginUserList() {
		List<LoadTester.LoginUser> userList = new ArrayList<LoadTester.LoginUser>();
		userList.add(new LoginUser("남종관", "1"));
		userList.add(new LoginUser("배태영", "1"));
		userList.add(new LoginUser("장응주", "1"));
		userList.add(new LoginUser("서대영", "1"));
		userList.add(new LoginUser("현혁", "1"));
		return userList;
	}

	@Override
	long getSleepTimeMillis() {
		return (long)maxSleepTime * 1000;
	}

	@Override
	boolean isRandomSleep() {
		// TODO Auto-generated method stub
		return false;
	}

}	
