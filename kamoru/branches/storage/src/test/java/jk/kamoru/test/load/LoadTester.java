package jk.kamoru.test.load;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class LoadTester {

	/** No, Thread, URL, Elapsed time, Content length, Error, Desc */
	static final String OUTPUT_PATTERN = "%s, %s, %s, %s, %s, %s, %s";
	
	abstract URL getLoginURL();

	abstract String getLoginDataFormat();

	abstract List<LoginUser> getLoginUserList();

	abstract String getEncoding();

	abstract List<URL> getLoadURLs();

	abstract int getThreadSize();

	abstract long getRunningTimeMillis();

	abstract long getSleepTimeMillis();
	
	abstract boolean isRandomSleep();
	
	public final void start() throws Exception {
		log.info("No, Thread, URL, Elapsed time, Content length, Error, Desc");
		
		ExecutorService threadPool = Executors.newFixedThreadPool(getThreadSize());
		for (int i = 0; i < getThreadSize(); i++) {
			LoginUser loginUser = getLoginUserList().get(i % getLoginUserList().size());
			String loginData = String.format(getLoginDataFormat(), loginUser.getId(), loginUser.getPassword());
			
			threadPool.execute(new Load(loginUser.getId(), getLoginURL(), loginData, getLoadURLs(), getRunningTimeMillis(), getSleepTimeMillis(), isRandomSleep()));
		}
		threadPool.shutdown();
	}

	public URL getURL(String url) {
		try {
			return new URL(url);
		} catch (MalformedURLException e) {
			throw new IllegalStateException(e);
		}
	}
	
	@Data
	static class LoginUser {
		private final static String UTF8 = "UTF-8";
		
		private String id;
		private String password;
		private String encoding;
		
		protected LoginUser(String id, String password) {
			this(id, password, UTF8);
		}
		
		protected LoginUser(String id, String password, String encoding) {
			this.id = id;
			this.password = password;
			this.encoding = encoding;
		}

		protected String getId() {
			try {
				return URLEncoder.encode(id, encoding);
			} catch (UnsupportedEncodingException e) {
				return id;
			}
		}

		protected String getPassword() {
			try {
				return URLEncoder.encode(password, encoding);
			} catch (UnsupportedEncodingException e) {
				return password;
			}
		}
	}
}
