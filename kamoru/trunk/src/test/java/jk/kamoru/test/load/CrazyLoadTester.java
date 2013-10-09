package jk.kamoru.test.load;

import java.util.ArrayList;
import java.util.List;


public class CrazyLoadTester extends LoadTester {

	final String loginUrl = "http://jk.kamoru:8080/crazy/j_spring_security_check";
	final String loginDataFormat = "j_username=%s&j_password=%s";
	final String username = "name";
	final String password = "crazyjk";
	final String encoding = "UTF-8";
	final String[] loadUrls = new String[]{
			"http://jk.kamoru:8080/crazy/video",
			"http://jk.kamoru:8080/crazy/video/list",
			"http://jk.kamoru:8080/crazy/video/actress",
			"http://jk.kamoru:8080/crazy/video/studio",
			"http://jk.kamoru:8080/crazy/image",
			"http://jk.kamoru:8080/crazy/video/briefing",
			"http://jk.kamoru:8080/crazy/video/SNIS-010",
			"http://jk.kamoru:8080/crazy/video/actress/Yui%20Hatano",
			"http://jk.kamoru:8080/crazy/video/studio/MAXING",
			"http://jk.kamoru:8080/crazy/video/search.json?q=a"};
	int threadSize;
	int runningTime;

	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		if (args != null && args.length > 1) {
			int threadSize 	= Integer.parseInt(args[0]);
			int runningTime = Integer.parseInt(args[1]);
			
			CrazyLoadTester loadTest = new CrazyLoadTester();
			loadTest.setThreadSize(threadSize);
			loadTest.setRunningTime(runningTime);
			loadTest.start();
		}
		else {
			System.out.println("First argument : Thread size, Second argement : Load time(sec)");
		}
	}

	@Override
	String getLoginUrl() {
		return this.loginUrl;
	}

	@Override
	String getLoginDataFormat() {
		return this.loginDataFormat;
	}

	@Override
	String getEncoding() {
		return this.encoding;
	}

	@Override
	String[] getLoadUrls() {
		return this.loadUrls;
	}

	@Override
	int getThreadSize() {
		return this.threadSize;
	}

	@Override
	long getRunningTimeMillis() {
		return (long)this.runningTime * 1000;
	}

	public void setThreadSize(int threadSize) {
		this.threadSize = threadSize;
	}

	public void setRunningTime(int runningTime) {
		this.runningTime = runningTime;
	}

	@Override
	List<String[]> getUserList() {
		List<String[]> userList = new ArrayList<String[]>();
		userList.add(new String[]{"user1", "crazyjk"});
		userList.add(new String[]{"user2", "crazyjk"});
		userList.add(new String[]{"user3", "crazyjk"});
		userList.add(new String[]{"user4", "crazyjk"});
		userList.add(new String[]{"user5", "crazyjk"});
		return userList;
	}

}
