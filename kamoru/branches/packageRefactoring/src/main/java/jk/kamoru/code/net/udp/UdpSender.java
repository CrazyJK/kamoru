package jk.kamoru.code.net.udp;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class UdpSender {

	public UdpSender() {
	}

	public static void main(String args[]) throws Exception {
		
		String _ip   = "127.0.0.1";
		String _port = "7213";
		String _msg  = "asdadsad";
		
		int port = Integer.parseInt(_port);
		InetAddress dest_addr = InetAddress.getByName(_ip);
		String message = "UDP send to " + dest_addr.getHostAddress() + ":" + port + " " + _msg;
		System.out.println("msg=" + message);

		byte msg[] = message.getBytes();
		DatagramPacket p = new DatagramPacket(msg, msg.length, dest_addr, port);
		DatagramSocket s = new DatagramSocket();
		s.send(p);
		s.close();
		System.out.println("sent to " + dest_addr.getHostAddress() + ":" + port);
	}
}
