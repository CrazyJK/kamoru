package jk.kamoru.test.load;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class Load extends Thread {

	static int callCount = 0;

	URL loginURL;
	String loginData;
	List<URL> loadURLs;
	long quitTime;
	String cookies;
	long maxSleepTime;
	
	public Load() {
		cookies = new String();
	}

	public Load(String name, URL loginURL, String loginData, List<URL> loadURLs, long runningTime, long maxSleepTime) {
		this();
		this.setName(this.getName() + "-" + name);
		this.loginURL = loginURL;
		this.loginData = loginData;
		this.loadURLs = loadURLs; 
		this.quitTime = System.currentTimeMillis() + runningTime;
		this.maxSleepTime = maxSleepTime;
	}
	
	public void run() {
		login();
		while(System.currentTimeMillis() < quitTime) {
			call();
			sleep();
		}
	}

	private URL getLoadURL() {
		return loadURLs.get((int) (Math.random() * loadURLs.size()));
	}
	
	private void sleep() {
		try {
			sleep((long) (Math.random() * maxSleepTime));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	@SuppressWarnings("rawtypes")
	public void login() {
		OutputStreamWriter wr = null;
		try {
			HttpURLConnection loginConn = (HttpURLConnection)loginURL.openConnection();
			loginConn.setDoOutput(true);
			loginConn.setInstanceFollowRedirects(false);
			wr = new OutputStreamWriter(loginConn.getOutputStream());
			wr.write(loginData);
			wr.flush();
	
			Map m = loginConn.getHeaderFields();
			if (m.containsKey("Set-Cookie")) {
				Collection c =(Collection)m.get("Set-Cookie");
			    for (Iterator i = c.iterator(); i.hasNext(); ) {
			    	cookies += (String)i.next() + ", ";
		    	}
			}
//			System.out.println("Login. cookies=" + cookies);   
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (wr != null)
				try {
					wr.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
	}
	
	public void call() {
		String uri = null;
		OutputStreamWriter wr = null;
		BufferedReader br = null;
		try {
			long startTime = System.currentTimeMillis();
			
			URL loadUrl = getLoadURL();
			uri = loadUrl.toURI().toString();
			
			HttpURLConnection loadConn = (HttpURLConnection)loadUrl.openConnection();
			loadConn.setRequestProperty("cookie", cookies);
			br = new BufferedReader(new InputStreamReader(loadConn.getInputStream()));
	        StringBuilder sb = new StringBuilder();
	        String buf = "";
	        while ((buf = br.readLine()) != null) {
	        	sb.append(buf);
	        }
	        long elapsedTime = System.currentTimeMillis() - startTime;
            System.out.println(
            		String.format("%4s, %-10s, %-50s, %7s, %s", 
            				++callCount, this.getName(), uri, elapsedTime, sb.toString().length()));
		} catch (IOException e) {
			System.out.println(String.format("%4s, %-10s, %-50s, , , %s", ++callCount, this.getName(), uri, e.getMessage()));
		} catch (URISyntaxException e) {
			System.out.println(String.format("%4s, %-10s, %-50s, , , %s", ++callCount, this.getName(), uri, e.getMessage()));
		} finally {
			if (wr != null)
				try {
					wr.close();
				} catch (IOException e) {
					System.out.println(String.format("%4s, %-10s, %-50s, , , %s", ++callCount, this.getName(), uri, e.getMessage()));
				}
			if (br != null)
				try {
					br.close();
				} catch (IOException e) {
					System.out.println(String.format("%4s, %-10s, %-50s, , , %s", ++callCount, this.getName(), uri, e.getMessage()));
				}
		}
	}
}	