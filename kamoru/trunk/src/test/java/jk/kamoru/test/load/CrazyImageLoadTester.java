package jk.kamoru.test.load;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class CrazyImageLoadTester extends LoadTester {

	final String loginUrl = "http://jk.kamoru:8080/crazy/j_spring_security_check";
	final String loginDataFormat = "j_username=%s&j_password=%s";
	final String username = "name";
	final String password = "crazyjk";
	final String encoding = "UTF-8";
	final String loadImageUrlPattern = "http://jk.kamoru:8080/crazy/image/%s";
	final int maxImageIndex = 21;
	
	int threadSize;
	int runningTime;
	int maxSleepTime;

	
	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		if (args != null && args.length > 2) {
			int threadSize 	 = Integer.parseInt(args[0]);
			int runningTime  = Integer.parseInt(args[1]);
			int maxSleepTime = Integer.parseInt(args[2]);
			
			CrazyImageLoadTester loadTest = new CrazyImageLoadTester();
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
		String[] loadUrls = new String[21];
		for (int i=0; i<maxImageIndex; i++) {
			loadUrls[i] = String.format(loadImageUrlPattern, i); 
		}
		return loadUrls;
	}

	@Override
	int getThreadSize() {
		return this.threadSize;
	}

	@Override
	long getRunningTimeMillis() {
		return (long)this.runningTime * 1000;
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

	@Override
	long getMaxSleepTimeMillis() {
		return (long)maxSleepTime * 1000;
	}

}
