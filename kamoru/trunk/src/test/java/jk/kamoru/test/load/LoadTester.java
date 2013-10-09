package jk.kamoru.test.load;

import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

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

	public void start() throws Exception {
		this.loginURL = new URL(getLoginUrl());
		this.loadURLs = new ArrayList<URL>();
		for (String url : getLoadUrls()) {
			this.loadURLs.add(new URL(url));
		}
		System.out.println("No, Thread, URL, Elapsed time, Content length, Error");
		for (int i = 0; i < getThreadSize(); i++) {
			String[] userinfo = getUserList().get(i % getUserList().size());
			this.loginData = String.format(getLoginDataFormat(),
					URLEncoder.encode(userinfo[0], getEncoding()),
					URLEncoder.encode(userinfo[1], getEncoding()));
			Load load = new Load(userinfo[0], loginURL, loginData, loadURLs, getRunningTimeMillis());
			load.start();
		}
//		System.out.format("Load test end. Total call %s", Load.callCount);
	}

}
