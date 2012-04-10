package kamoru.bg.net.server;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import kamoru.bg.net.server.biz.BloodGlucoseBiz;

public class BloodGlucoseServer {

	Log logger = LogFactory.getLog(this.getClass());

	final String iniFile = "blood-glucose.ini";
	
	int port;
	
	public BloodGlucoseServer() throws Exception {
		setServerInfo();
	}

	private void setServerInfo() throws Exception {
		Properties props = new Properties();
		try {
			ClassLoader cl = Thread.currentThread().getContextClassLoader();
	        if( cl == null ) cl = ClassLoader.getSystemClassLoader();

			props.load(cl.getResourceAsStream(iniFile));
			port = Integer.valueOf(props.getProperty("serverport")).intValue();
		} catch (FileNotFoundException e) {
			throw new FileNotFoundException(iniFile + " 파일을 찾을 수 없습니다.");
		} catch (IOException e) {
			throw new IOException(iniFile + " 파일을 읽을 수 없습니다.");
		}
	}

	
	public void start() throws IOException {
		
		ServerSocket serverSocket = null;
		try {
			serverSocket = new ServerSocket(port);
			logger.info("Blood Glucose Server start");
		} catch (IOException e) {
			throw new IOException("서버 시작에 실패하였습니다.", e);
		}
		while(true) {
			try {
				Socket socket = serverSocket.accept(); 
				BloodGlucoseBiz bloodGlucoseBiz = new BloodGlucoseBiz(socket);
				bloodGlucoseBiz.start();
				logger.info("recevied data");
			} catch (IOException e) {
				throw new IOException("서버 구동이 중지 되었습니다.", e);
			}
		}
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			BloodGlucoseServer server = new BloodGlucoseServer();
			server.start();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
