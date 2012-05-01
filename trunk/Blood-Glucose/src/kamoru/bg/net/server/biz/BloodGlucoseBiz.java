package kamoru.bg.net.server.biz;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.security.Key;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import kamoru.frmwk.db.ibatis.SqlMapFactory;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;

public class BloodGlucoseBiz extends Thread {

	final static byte[] KEYbyte = new byte[]{
		(byte)0x06, (byte)0xa9, (byte)0x21, (byte)0x40, (byte)0x36, (byte)0xb8, (byte)0xa1, (byte)0x5b, 
		(byte)0x51, (byte)0x2e, (byte)0x03, (byte)0xd5, (byte)0x34, (byte)0x12, (byte)0x00, (byte)0x06, 
		(byte)0x44, (byte)0x3a, (byte)0xff, (byte)0xff, (byte)0xff, (byte)0xff, (byte)0xff, (byte)0xff, 
		(byte)0xff, (byte)0xff, (byte)0xff, (byte)0xff, (byte)0xff, (byte)0xff, (byte)0xff, (byte)0xff};
	final static byte[] IVbyte = new byte[]{
		(byte)0x06, (byte)0xa9, (byte)0x21, (byte)0x40, (byte)0x36, (byte)0xb8, (byte)0xa1, (byte)0x5b,
		(byte)0x51, (byte)0x2e, (byte)0x03, (byte)0xd5, (byte)0x34, (byte)0x12, (byte)0x00, (byte)0x06};
	
	Log logger = LogFactory.getLog(this.getClass());

	private Socket socket;
	
	private String hostAddress;
	private String hostName;
	private Date receivedDate;
	
	
	public BloodGlucoseBiz(Socket socket) {
		this.socket = socket;
	}
	
	@Override
	public void run() {
		logger.info("START");
		String message = new String();
		BufferedReader reader = null;
		BufferedWriter writer = null;
		try {
			InetAddress inetAddress = socket.getInetAddress();
			hostAddress = inetAddress.getHostAddress();
			hostName = inetAddress.getHostName();
			receivedDate = new Date();
			
			reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

			logger.info("connect reader/writer");
			
			// 1. READY?		
			int length = 0;
			char[] cbuf = new char[6];
			length = reader.read(cbuf);
			
			logger.info("message : " + String.valueOf(cbuf) + " - " + length);
			
			// check elapsed time 50 sec
			if("READY?".equals(String.valueOf(cbuf))) {
				writer.write("READY");
				writer.flush();
				logger.info("writer READY");
			} else {
				throw new Exception("약속된 응답이 아닙니다.");
			}
			
			cbuf = new char[96];
			length = reader.read(cbuf);

			logger.info("message : " + String.valueOf(cbuf));

			// check elapsed time 50 sec

			byte[] data = decryptMessage(String.valueOf(cbuf));
			
			for(int i = 0; i < 8; i++) {
				logger.info(data[i]);
			}
			
			insertDATA(data);
			
			writer.write("ACK");
			writer.flush();

			logger.info("writer ACK");

			logger.info("\n\thostAddress:[" + hostAddress + "]\n\thostName:[" + hostName + "]\n\treceivedDate:[" + receivedDate + "]\n\tmessage:[" + message + "]\n");
		} catch (IOException e) {
			logger.error("수신된 데이터를 읽는 도중 에러가 발생했습니다.", e);
		} catch (Exception e) {
			logger.error(e);
		} finally {
			try {
				reader.close();
			} catch (IOException e) {
				logger.error(e);
			}
			try {
				writer.close();
			} catch (IOException e) {
				logger.error(e);
			}
			try {
				socket.close();
			} catch (IOException e) {
				logger.error(e);
			}
		}

	}

	private void insertDATA(byte[] data) {
		SqlMapClient sqlMap = null;
		try {
			sqlMap = (SqlMapClient)SqlMapFactory.getSqlMap();
			sqlMap.startTransaction();
			// 기기번호, 연도, 월, 일, 시, 분, 초, 혈당값, receivedDate, seq, meter address, message
			// id, year, month, day, time, minute, second, bloodsugarvalue, regtdate
			HashMap map = new HashMap();
			map.put("id", 		IVbyte[0] + IVbyte[1] + IVbyte[2] + IVbyte[3] + IVbyte[4] + IVbyte[5] + IVbyte[6]);
			map.put("year",  	data[0]);
			map.put("month", 	data[1]);
			map.put("day", 		data[2]);
			map.put("time", 	data[3]);
			map.put("minute", 	data[4]);
			map.put("second", 	data[5]);
			map.put("bloodsugarvalue", data[6] + data[7]);
			map.put("regdata", receivedDate);
			
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

	private byte[] decryptMessage(String message) throws Exception {

		byte[] txtByte = Base64.decode(message);

		byte[] encryptByte = new byte[48];
		for(int id = 0, i = 0; i<txtByte.length; i++) {
			if(i > 15 && i < 64) 
				encryptByte[id++] = txtByte[i];
		}

		// secret key
		Key key = new SecretKeySpec(KEYbyte, "AES");
		// initial vector
		IvParameterSpec ivParameterSpec = new IvParameterSpec(IVbyte);
		// cipher
		Cipher cipher = Cipher.getInstance("AES/CBC/NOPadding");
		
		cipher.init(Cipher.DECRYPT_MODE, key, ivParameterSpec);
		byte[] txtDecrypt = cipher.doFinal(encryptByte);
	
		return txtDecrypt;
	}
	
}
