package kamoru.bg.net.client;

import java.io.*;
import java.net.*;
import java.util.*;

import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;

public class BloodGlucoseClient {

	final String iniFile = "blood-glucose.ini";
	final String msgFile = "blood-glucose.msg";
	
	/**
	 *  서버 IP,Port 정보.
	 *  blood-glucose.ini에서 읽어 온다.
	 */
	String ip; 
	int port;
	
	/**
	 *  서버에 전송할 내용.
	 *  blood-glucose.msg 파일을 읽어 온다.
	 */
	String message = new String();
	
	/**
	 *  생성자. 
	 *  서버 IP / Port 정보, 보낼 메시지 확인.
	 */
	public BloodGlucoseClient() throws Exception{
		setServerInfo();
//		readMessage();
		encryptMessage();
	}

	private void setServerInfo() throws Exception {
		Properties props = new Properties();
		try {
			ClassLoader cl = Thread.currentThread().getContextClassLoader();
	        if( cl == null ) cl = ClassLoader.getSystemClassLoader();

			props.load(cl.getResourceAsStream(iniFile));
			ip = String.valueOf(props.get("serverip"));
			port = Integer.valueOf(props.getProperty("serverport")).intValue();
		} catch (FileNotFoundException e) {
			throw new FileNotFoundException(iniFile + " 파일을 찾을 수 없습니다.");
		} catch (IOException e) {
			throw new IOException(iniFile + " 파일을 읽을 수 없습니다.");
		}
	}
	
//	private void readMessage() throws IOException { 
//		BufferedReader br = null;
//		try {
//			ClassLoader cl = Thread.currentThread().getContextClassLoader();
//	        if( cl == null ) cl = ClassLoader.getSystemClassLoader();
//
//	        br = new BufferedReader(new InputStreamReader(cl.getResourceAsStream(msgFile)));
//	        //br = new BufferedReader(new FileReader(new File(msgFile)));
//			String line = null;
//			while((line = br.readLine()) != null) {
//				message += line + "\n";
//			}
//			message = message.trim();
//		} catch (IOException e) {
//			throw new IOException(msgFile + " 파일을 읽을 수 없습니다.");
//		} finally {
//			if(br != null)
//				try {br.close();} catch (IOException e) {}
//		}
//	}
	
	/**
	 *  보낼 메시지를 암호화 한다.
	 */
	private void encryptMessage() throws Exception {
		
	}
	
	/**
	 *  서버에 암호화된 메시지를 전송한다.
	 * @throws IOException
	 */
	public void startSocket() throws IOException {


		Socket socket = null;
		BufferedReader reader = null;
		BufferedWriter writer = null;
		try {
			socket = new Socket(ip, port);
			writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
			reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			
			System.out.println("start");
			
			writer.write("READY?");
			writer.flush();

			System.out.println("write READY?");

			// READY
			char[] cbuf = new char[5];
			int length = reader.read(cbuf);
			
			System.out.println(cbuf);

			writer.write("BqkhQDa4oVtRLgPVNBIABrZ757I2j2x1Ii5vsao4IAZC4EZ3O8fwqGtCFPAuHjbtRg20mV+Hi8lYMpNCgeUABgAAAVZCR01U");
			writer.flush();

			System.out.println("write message");
			
			char[] cbuf1 = new char[3];
			int length1= reader.read(cbuf1);

			System.out.println(cbuf1);
			
			System.out.println("[" + ip + ":" + port + "] message[" + message + "] 전송 완료");
		} catch (IOException e) {
			throw new IOException("메시지 전송에 실패 하였습니다.", e);
		} finally {
			try {
				socket.close();
				writer.close();
				reader.close();
			} catch (IOException e) {
				throw new IOException("소켓 닫기에 실패했습니다", e);
			}
		}
	}

	public static void main(String args[]) throws Exception {

		BloodGlucoseClient c = new BloodGlucoseClient();
		c.startSocket();

	}

}