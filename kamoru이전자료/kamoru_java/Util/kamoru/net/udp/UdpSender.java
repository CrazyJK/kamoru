package kamoru.net.udp;
import java.io.*;
import java.net.*;

public class UdpSender {

	public UdpSender() {
	}

	public static void main(String args[]) throws Exception {
		int port = Integer.parseInt(args[1]);
		InetAddress dest_addr = InetAddress.getByName(args[0]);
		String message = "UDP send to " + dest_addr.getHostAddress() + ":" + port;
		if(args.length == 3){
			BufferedReader reader = new BufferedReader(new FileReader(args[2]));
			String line = null;
			message = "";
			while((line = reader.readLine()) != null){
				message += line;
			}
		}
		System.out.println("msg=" + message);
		byte msg[] = message.getBytes();
		DatagramPacket p = new DatagramPacket(msg, msg.length, dest_addr, port);
		DatagramSocket s = new DatagramSocket();
		s.send(p);
		s.close();
		System.out.println("sent to " + dest_addr.getHostAddress() + ":" + port);
	}
}
