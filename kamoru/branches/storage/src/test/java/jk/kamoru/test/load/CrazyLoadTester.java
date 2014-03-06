package jk.kamoru.test.load;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
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
	int maxSleepTime;

	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		if (args != null && args.length > 1) {
			int threadSize 	= Integer.parseInt(args[0]);
			int runningTime = Integer.parseInt(args[1]);
			int maxSleepTime = Integer.parseInt(args[2]);

			CrazyLoadTester loadTest = new CrazyLoadTester();
			loadTest.setThreadSize(threadSize);
			loadTest.setRunningTime(runningTime);
			loadTest.setMaxSleepTime(maxSleepTime);
			loadTest.start();
		}
		else {
			System.out.println("First argument : Thread size, Second argement : Load time(sec), Third argument : Max sleep time(sec)");
		}
	}

	@Override
	URL getLoginURL() {
		try {
			return new URL(loginUrl);
		} catch (MalformedURLException e) {
			throw new IllegalStateException(e);
		}
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
	List<URL> getLoadURLs() {
		try {
			return Arrays.asList(
					new URL("http://jk.kamoru:8080/crazy/video"),
					new URL("http://jk.kamoru:8080/crazy/video/list"),
					new URL("http://jk.kamoru:8080/crazy/video/actress"),
					new URL("http://jk.kamoru:8080/crazy/video/studio"),
					new URL("http://jk.kamoru:8080/crazy/image"),
					new URL("http://jk.kamoru:8080/crazy/video/briefing"),
					new URL("http://jk.kamoru:8080/crazy/video/SNIS-010"),
					new URL("http://jk.kamoru:8080/crazy/video/actress/Yui%20Hatano"),
					new URL("http://jk.kamoru:8080/crazy/video/studio/MAXING"),
					new URL("http://jk.kamoru:8080/crazy/video/search.json?q=a"));
		} catch (MalformedURLException e) {
			throw new IllegalStateException(e);
		}
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
	List<LoadTester.LoginUser> getLoginUserList() {
		List<LoadTester.LoginUser> userList = new ArrayList<LoadTester.LoginUser>();
		userList.add(new LoginUser("user1", "crazyjk"));
		userList.add(new LoginUser("user2", "crazyjk"));
		userList.add(new LoginUser("user3", "crazyjk"));
		userList.add(new LoginUser("user4", "crazyjk"));
		userList.add(new LoginUser("user5", "crazyjk"));
		return userList;
	}

	@Override
	long getSleepTimeMillis() {
		return (long)maxSleepTime * 1000;
	}

	@Override
	boolean isRandomSleep() {
		return true;
	}

}
