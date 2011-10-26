package kamoru.remote;

import java.net.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.io.*;

import kamoru.util.*;

public class RemoteAgent {

	static org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(RemoteAgent.class);

	private int port;
	
	private String charsetName = "MS949";
	
	public RemoteAgent(int port){
		this.port = port;
		logger.info("RemoteAgent init");
	}
	
	public boolean startup(){
		DatagramPacket packet = null;
		DatagramSocket socket = null;
		byte msg[] = new byte[1024];
		try{
			packet = new DatagramPacket(msg, msg.length);
			socket = new DatagramSocket(port);
			logger.info("RemoteAgent start. port:" + port);

			while (true) {
				logger.info("stand by...");
				socket.receive(packet);
	            String message = new String(packet.getData(), 0, packet.getLength());       
	            logger.info("Received from: " + packet.getAddress() + ":" + packet.getPort() + "[" + message + "]");

	            
				
				String returnMsg = RuntimeUtil.execute(message);
				
				byte[] outBuffer = returnMsg.getBytes(charsetName); // 패킷 정보를 표시
				packet.setData(outBuffer);
				packet.setLength(outBuffer.length);
				socket.send(packet);
	            logger.info("Send to: " + packet.getAddress() + ":" + packet.getPort() + "[" + returnMsg + "]");
			}
		}catch(SocketException se){
			se.printStackTrace();
			logger.error(se);
		}catch(IOException ioe){
			ioe.printStackTrace();
			logger.error(ioe);
		}
		return true;
	}
	
	public static void main(String[] args) throws Exception{
		LogUtil lu = new LogUtil();
		lu.log4jLoad();
		
		RemoteAgent ra = new RemoteAgent(Integer.parseInt(args[0]));
		ra.startup();
	}
}
