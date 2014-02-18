package jk.kamoru.test.load;

import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public abstract class LoadTester {

	URL loginURL;
	String loginData;
	List<URL> loadURLs;

	abstract String getLoginUrl();

	abstract String getLoginDataFormat();

	abstract List<String[]> getUserList();

	abstract String getEncoding();

	abstract String[] getLoadUrls();

	abstract int getThreadSize();

	abstract long getRunningTimeMillis();

	abstract long getMaxSleepTimeMillis();
	
	public void start() throws Exception {
		loginURL = new URL(getLoginUrl());
		loadURLs = new ArrayList<URL>();
		for (String url : getLoadUrls()) {
			loadURLs.add(new URL(url));
		}
	
		ExecutorService threadPool = Executors.newFixedThreadPool(getThreadSize());
		
		System.out.println("No, Thread, URL, Elapsed time, Content length, Error");
		
		for (int i = 0; i < getThreadSize(); i++) {
			String[] userinfo = getUserList().get(i % getUserList().size());
			loginData = String.format(getLoginDataFormat(),
					URLEncoder.encode(userinfo[0], getEncoding()),
					URLEncoder.encode(userinfo[1], getEncoding()));
			
			Load load = new Load(userinfo[0], loginURL, loginData, loadURLs, getRunningTimeMillis(), getMaxSleepTimeMillis());
			threadPool.execute(load);
		}
		threadPool.shutdown();
	}

}
