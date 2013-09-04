package jk.kamoru.test;

import java.io.IOException;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;

public class HttpClientTest {

//	private static final String url = "http://123.212.190.148/handyGW/MailCallMulti2.jsp";
//	private static final String url = "http://123.212.190.111:6759/addon/getRequest.jsp";
	private static final String url = "http://123.212.190.111:6759/addon/MailCallMulti2.jsp";
	/**
	 * @param args
	 * @throws IOException 
	 * @throws HttpException 
	 */
	public static void main(String[] args) throws HttpException, IOException {
		HttpClient client = new HttpClient();
		
		HttpMethod method = new PostMethod(url);
		
		method.setRequestHeader("Content-Type","application/x-www-form-urlencoded; charset=UTF-8");
		
		NameValuePair sid = new NameValuePair("SID", "2006017");
		NameValuePair rid = new NameValuePair("RID", "2006017");
		NameValuePair tit = new NameValuePair("TIT", "test 제목 2");
		NameValuePair body = new NameValuePair("BODY", "본문내용");
		
		method.setQueryString(new NameValuePair[]{sid, rid, tit, body});
		
		int status = client.executeMethod(method);
		
		if(status == HttpStatus.SC_OK) {
			System.out.println("OK");
		}
		System.out.println(method.getStatusText());
		System.out.println(method.getResponseBodyAsString());
	}

}
