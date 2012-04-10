package kamoru.bg.net.server.biz;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.Socket;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import kamoru.frmwk.db.ibatis.SqlMapFactory;

import com.ibatis.sqlmap.client.SqlMapClient;

public class BloodGlucoseBiz extends Thread {

	Log logger = LogFactory.getLog(this.getClass());

	private Socket socket;
	
	private String hostAddress;
	private String hostName;
	private Date receivedDate;
	private String message = new String();
	
	public BloodGlucoseBiz(Socket socket) {
		this.socket = socket;
	}
	
	@Override
	public void run() {
		BufferedReader reader = null;
		try {
			InetAddress inetAddress = socket.getInetAddress();
			hostAddress = inetAddress.getHostAddress();
			hostName = inetAddress.getHostName();
			receivedDate = new Date();
			reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			String line = null;
			while((line = reader.readLine()) != null) {
				message += line + "\n";
			}
			message = message.trim();
			logger.info("\n\thostAddress:[" + hostAddress + "]\n\thostName:[" + hostName + "]\n\treceivedDate:[" + receivedDate + "]\n\tmessage:[" + message + "]\n");
		} catch (IOException e) {
			logger.error("수신된 데이터를 읽는 도중 에러가 발생했습니다.", e);
		} finally {
			try {
				reader.close();
				socket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		try {
			decryptMessage();
			logger.debug("decrypted message : " + message);
		} catch (Exception e2) {
			logger.error("메시지 복호화에 실패 하였습니다.", e2);
		}

		
		SqlMapClient sqlMap = null;
		try {
			sqlMap = (SqlMapClient)SqlMapFactory.getSqlMap();
			sqlMap.startTransaction();
			
			HashMap map = new HashMap();
			map.put("hostAddress", hostAddress);
			map.put("hostName", hostName);
			map.put("receivedDate", receivedDate);
			map.put("message", message);
			
			sqlMap.insert("bg.insertBloodGlucoseData", map);
			
			sqlMap.commitTransaction();
			logger.info("data inserted");
		} catch (SQLException e) {
			logger.error("디비 저장에 실패 하였습니다.", e);
		} finally {
			try {
				sqlMap.endTransaction();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}

	}

	private void decryptMessage() throws Exception {
		
	}
	
}
