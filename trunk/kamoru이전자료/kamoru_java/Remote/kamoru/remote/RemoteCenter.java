package kamoru.remote;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.io.*;

import kamoru.util.LogUtil;

public class RemoteCenter {

	static org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(RemoteCenter.class);
	
	private String hostName;
	private int port;
	private String charsetName = "MS949";

	public RemoteCenter(String hostName, int port){
		this.hostName = hostName;
		this.port = port;
		logger.info("RemoteCenter init");
	}
	
	public String command(String command){
		String ret = null;
		try{
			InetAddress host = InetAddress.getByName(hostName);
	
			DatagramSocket socket = new DatagramSocket();
			socket.setSoTimeout(5000);
			logger.info("init socket");
			
			byte[] cmd = command.getBytes(charsetName);
			DatagramPacket packet = new DatagramPacket(cmd, cmd.length, host, port);
			logger.info("init packet. host:" + hostName + ":" + port);

			logger.debug(""+packet.getData().length);

			socket.send(packet);
			logger.info("send packet. command=" + command);

			
			packet.setData(new byte[1024]);
			packet.setLength(1024);


			socket.receive(packet);
			logger.debug(""+packet.getData().length);
			byte[] data = packet.getData();
			int length = packet.getLength();
			ret = new String(data, 0, length, charsetName);
			logger.info("receive packet. ret=" + ret);

			socket.close();
	
		}catch(IOException ioe){
			logger.error(ioe);
			ret = ioe.getMessage();
		}
		return ret;
	}
	
	public static void main(String[] args) throws Exception{
		LogUtil lu = new LogUtil();
		lu.log4jLoad();
		
		RemoteCenter ra = new RemoteCenter(args[0], Integer.parseInt(args[1]));
		System.out.println(ra.command(args[2]));
	}
}
