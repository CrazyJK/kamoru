package jk.kamoru.test.load;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import lombok.Cleanup;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Load extends Thread {

	static int callCount = 0;

	URL loginURL;
	String loginData;
	List<URL> loadURLs;
	long quitTime;
	long sleepTime;
	boolean randomSleep;
	String cookies = new String();
	
	public Load(String name, URL loginURL, String loginData, List<URL> loadURLs, 
			long runningTime, long sleepTime, boolean randomSleep) {
		this.setName(this.getName() + "-" + name);
		this.loginURL = loginURL;
		this.loginData = loginData;
		this.loadURLs = loadURLs; 
		this.quitTime = System.currentTimeMillis() + runningTime;
		this.sleepTime = sleepTime;
		this.randomSleep = randomSleep;
	}
	
	@Override
	public void run() {
		try {
			login();
		} catch (IOException e) {
			throw new IllegalStateException("Login fail", e);
		}
		while(System.currentTimeMillis() < quitTime) {
			load();
			sleep();
		}
	}

	private URL getLoadURL() {
		return loadURLs.get((int) (Math.random() * loadURLs.size()));
	}
	
	private void sleep() {
		try {
			if (randomSleep)
				sleep((long) (Math.random() * sleepTime));
			else 
				sleep(sleepTime);
		} catch (InterruptedException e) {
			log.error(e.getMessage());
		}
	}
	
	public void login() throws IOException {
		long startTime = System.currentTimeMillis();
		HttpURLConnection loginConn = (HttpURLConnection)loginURL.openConnection();
		loginConn.setDoOutput(true);
		loginConn.setInstanceFollowRedirects(false);
		@Cleanup OutputStreamWriter wr = new OutputStreamWriter(loginConn.getOutputStream());
		wr.write(loginData);
		wr.flush();

		Map<?, ?> m = loginConn.getHeaderFields();
		if (m.containsKey("Set-Cookie")) {
			Collection<?> c = (Collection<?>)m.get("Set-Cookie");
		    for (Iterator<?> i = c.iterator(); i.hasNext(); ) {
		    	cookies += (String)i.next() + ", ";
	    	}
		}
        long elapsedTime = System.currentTimeMillis() - startTime;
		log.info(String.format(LoadTester.OUTPUT_PATTERN, callCount, this.getName(), loginURL, elapsedTime, "", "", cookies));   
	}
	
	public void load() {
		++callCount;
		URL loadUrl = getLoadURL();
		try {
			long startTime = System.currentTimeMillis();
			
			HttpURLConnection loadConn = (HttpURLConnection)loadUrl.openConnection();
			loadConn.setRequestProperty("cookie", cookies);
			@Cleanup BufferedReader br = new BufferedReader(new InputStreamReader(loadConn.getInputStream()));
	        StringBuilder sb = new StringBuilder();
	        String buf = "";
	        while ((buf = br.readLine()) != null) {
	        	sb.append(buf);
	        }
	        
	        long elapsedTime = System.currentTimeMillis() - startTime;
            log.info(String.format(LoadTester.OUTPUT_PATTERN, callCount, this.getName(), loadUrl, elapsedTime, sb.length(), "", ""));
		} catch (IOException e) {
			log.info(String.format(LoadTester.OUTPUT_PATTERN, callCount, this.getName(), loadUrl, "", "", e.getMessage(), ""));
		}
	}

}	