package jk.kamoru.test.load;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class CrazyImageLoadTester extends LoadTester {

	int threadSize;
	int runningTime;
	int sleepTime;

	@Override
	URL getLoginURL() {
		return getURL("http://jk.kamoru:8080/crazy/j_spring_security_check");
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
		List<URL> loadURLs = new ArrayList<URL>();
		for (int i = 0; i < 21; i++)
			loadURLs.add(getURL(String.format("http://jk.kamoru:8080/crazy/image/%s", i)));
		return loadURLs;
	}

	@Override
	int getThreadSize() {
		return this.threadSize;
	}

	@Override
	long getRunningTimeMillis() {
		return (long) this.runningTime * 1000;
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
		return (long) sleepTime * 1000;
	}

	@Override
	boolean isRandomSleep() {
		return true;
	}

	/**
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		if (args != null && args.length > 2) {
			int threadSize = Integer.parseInt(args[0]);
			int runningTime = Integer.parseInt(args[1]);
			int sleepTime = Integer.parseInt(args[2]);

			CrazyImageLoadTester loadTest = new CrazyImageLoadTester();
			loadTest.threadSize = threadSize;
			loadTest.runningTime = runningTime;
			loadTest.sleepTime = sleepTime;
			loadTest.start();
		} else {
			System.out.println("First argument : Thread size, Second argement : Load time(sec), Third argument : Max sleep time(sec)");
			CrazyImageLoadTester loadTest = new CrazyImageLoadTester();
			loadTest.threadSize = 1;
			loadTest.runningTime = 1;
			loadTest.sleepTime = 1;
			loadTest.start();
		}
	}

}
