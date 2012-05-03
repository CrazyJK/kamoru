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
import java.util.List;
import java.util.ArrayList;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;




import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import kamoru.frmwk.db.ibatis.SqlMapFactory;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;

public class BloodGlucoseBiz extends Thread {

	private final byte[] KEYbyte = new byte[]{
		(byte)0x06, (byte)0xa9, (byte)0x21, (byte)0x40, (byte)0x36, (byte)0xb8, (byte)0xa1, (byte)0x5b, 
		(byte)0x51, (byte)0x2e, (byte)0x03, (byte)0xd5, (byte)0x34, (byte)0x12, (byte)0x00, (byte)0x06, 
		(byte)0x44, (byte)0x3a, (byte)0xff, (byte)0xff, (byte)0xff, (byte)0xff, (byte)0xff, (byte)0xff, 
		(byte)0xff, (byte)0xff, (byte)0xff, (byte)0xff, (byte)0xff, (byte)0xff, (byte)0xff, (byte)0xff};
	private final byte[] IVbyte = new byte[]{
		(byte)0x06, (byte)0xa9, (byte)0x21, (byte)0x40, (byte)0x36, (byte)0xb8, (byte)0xa1, (byte)0x5b,
		(byte)0x51, (byte)0x2e, (byte)0x03, (byte)0xd5, (byte)0x34, (byte)0x12, (byte)0x00, (byte)0x06};
	
	Log logger = LogFactory.getLog(this.getClass());

	private Socket 	socket;
	private String 	hostAddress;
	private String 	hostName;
	private Date 	receivedDate;
	private BufferedReader 	reader;
	private BufferedWriter 	writer;
	char[] cbuf;
	SqlMapClient sqlMap;

	/**
	 * jsp에서 SELECT 용도로 사용
	 */
	public BloodGlucoseBiz() {
		
	}
	
	/**
	 * Socket서버에서 소켓 처리용으로 사용
	 * @param socket
	 */
	public BloodGlucoseBiz(Socket socket) {
		this.socket = socket;
	}
	
	@Override
	public void run() {
		logger.info("START");
		try {
			InetAddress inetAddress = socket.getInetAddress();
			hostAddress  = inetAddress.getHostAddress();
			hostName 	 = inetAddress.getHostName();
			receivedDate = new Date();
			reader 		 = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			writer 		 = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

			logger.info("hostAddress:[" + hostAddress + "] hostName:[" + hostName + "] receivedDate:[" + receivedDate + "]");
			
			readREADYQ();
			sendREADY();
			readMessage();
			processAndSendACK();

		} catch (Exception e) {
			logger.error("수신된 데이터를 처리하는 도중 에러가 발생했습니다.", e);
		} finally {
			try { reader.close(); } catch (IOException e) {logger.error(e);}
			try { writer.close(); } catch (IOException e) {logger.error(e);}
			try { socket.close(); } catch (IOException e) {logger.error(e);}
		}

	}
	
	private void readREADYQ() throws Exception{
		cbuf = new char[6];
		int length = reader.read(cbuf);
		logger.info("1. received message : " + String.valueOf(cbuf) + " - " + length);
	}
	
	private void sendREADY() throws Exception {
		if("READY?".equals(String.valueOf(cbuf))) {
			Future<Object> future = Executors.newCachedThreadPool().submit(new Callable<Object>() {
				public Object call() {
					try {
						writer.write("READY");
						writer.flush();
						logger.info("2. send message : READY");
						return new Object();
					} catch (IOException e) {
						throw new RuntimeException(e);
					}
				}
			});
			try {
				future.get(50, TimeUnit.SECONDS);
			} catch (Exception e) {
				throw e;
			} finally {
				future.cancel(true);
			}
		} else {
			throw new Exception("약속된 응답이 아닙니다.");
		}
	}
	
	private void readMessage() throws IOException {
		cbuf = new char[96];
		int length = reader.read(cbuf);
		logger.info("3. received message : " + String.valueOf(cbuf));
	}
	
	private void processAndSendACK() throws Exception {
		try {
			sqlMap = (SqlMapClient)SqlMapFactory.getSqlMap();
			sqlMap.startTransaction();
			ExecutorService executor = Executors.newCachedThreadPool();
			Callable<Object> task = new Callable<Object>() {
				public Object call() {
					try {
						byte[] data = decryptMessage(String.valueOf(cbuf));

						HashMap map = new HashMap();
						map.put("id", 		toHexString(IVbyte[0]) + toHexString(IVbyte[1]) + toHexString(IVbyte[2]) + toHexString(IVbyte[3]) + toHexString(IVbyte[4]) + toHexString(IVbyte[5]) + toHexString(IVbyte[6]));
						map.put("year",  	data[0]);
						map.put("month", 	data[1]);
						map.put("day", 		data[2]);
						map.put("time", 	data[3]);
						map.put("minute", 	data[4]);
						map.put("second", 	data[5]);
						map.put("bloodsugarvalue", data[6] + data[7]);
						map.put("regtdate", receivedDate);
						
						sqlMap.insert("bg.insertBloodGlucoseData", map);
						
//						try {Thread.sleep(60*1000);} catch (InterruptedException e) {}
						
						return new Object();
					} catch (Exception e) {
						throw new RuntimeException(e);
					}
				}
			};
			Future<Object> future = executor.submit(task);
			try {
				Object result = future.get(50, TimeUnit.SECONDS);
			} catch (Exception e) {
				throw e;
			} finally {
				future.cancel(true);
			}
			
			sqlMap.commitTransaction();
			logger.info("data inserted");
			
			writer.write("ACK");
			writer.flush();
			logger.info("4. send message : ACK");
		} catch (Exception e) {
			writer.write("ERR");
			writer.flush();
			logger.info("4. send message : ERR");
			throw e;
		} finally {
			try {
				if(sqlMap != null)
					sqlMap.endTransaction();
			} catch (SQLException e) {
				throw new RuntimeException(e);
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
		Key key = new SecretKeySpec(KEYbyte, "AES");
		IvParameterSpec ivParameterSpec = new IvParameterSpec(IVbyte);
		Cipher cipher = Cipher.getInstance("AES/CBC/NOPadding");
		cipher.init(Cipher.DECRYPT_MODE, key, ivParameterSpec);
		byte[] txtDecrypt = cipher.doFinal(encryptByte);
		return txtDecrypt;
	}
    
	public static String toHexString(byte b) {
		StringBuffer result = new StringBuffer(3);
		result.append(Integer.toString((b & 0xF0) >> 4, 16));
		result.append(Integer.toString(b & 0x0F, 16));
		return result.toString();
	}
	
	public List getBloodSugarList(String strId) {
		SqlMapClient sqlMap = null;
		List list = new ArrayList();
		try {
			sqlMap = (SqlMapClient)SqlMapFactory.getSqlMap();
	  
			HashMap map = new HashMap();
			map.put("id", strId);
			list = sqlMap.queryForList("bg.getBloodClucoseList", map);  
			System.out.println("list size>>"+list.size());
		} catch (SQLException e) {
			logger.error("getBloodSugarList Error : ", e);
		}
		return list;
	}
    
	public List getBloodSugarIdList(){
		SqlMapClient sqlMap = null;
    	List list = new ArrayList();
    	try {
    		sqlMap = (SqlMapClient)SqlMapFactory.getSqlMap();
    		list = sqlMap.queryForList("bg.getBloodClucoseId", null);  
    		System.out.println("list size>>"+list.size());
    	} catch (SQLException e) {
    		logger.error("getBloodSugarIdList Error : ", e);
    	}
		return list;
    }
}
