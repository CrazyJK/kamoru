package kamoru.bg.net.client;

import java.io.*;
import java.net.*;
import java.util.*;

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
		readMessage();
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
	
	private void readMessage() throws IOException { 
		BufferedReader br = null;
		try {
			ClassLoader cl = Thread.currentThread().getContextClassLoader();
	        if( cl == null ) cl = ClassLoader.getSystemClassLoader();

	        br = new BufferedReader(new InputStreamReader(cl.getResourceAsStream(msgFile)));
	        //br = new BufferedReader(new FileReader(new File(msgFile)));
			String line = null;
			while((line = br.readLine()) != null) {
				message += line + "\n";
			}
			message = message.trim();
		} catch (IOException e) {
			throw new IOException(msgFile + " 파일을 읽을 수 없습니다.");
		} finally {
			if(br != null)
				try {br.close();} catch (IOException e) {}
		}
	}
	
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
		OutputStream os = null;
		OutputStreamWriter writer = null;
		try {
			socket = new Socket(ip, port);
			os = socket.getOutputStream();
			writer = new OutputStreamWriter(os);
			writer.write(message, 0, message.length());
			writer.flush();
			System.out.println("[" + ip + ":" + port + "] message[" + message + "] 전송 완료");
		} catch (IOException e) {
			throw new IOException("메시지 전송에 실패 하였습니다.", e);
		} finally {
			try {
				socket.close();
				writer.close();
				os.close();
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