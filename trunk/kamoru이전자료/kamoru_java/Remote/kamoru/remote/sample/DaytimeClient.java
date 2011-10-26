package kamoru.remote.sample;

import java.net.*;
import java.io.*;

public class DaytimeClient {
	public static void main(String[] args) throws IOException {
		if ((args.length != 1))
			throw new IllegalArgumentException("Syntax: DaytimeClient [:]");

		int idx = args[0].indexOf(":");
		int port = (idx > -1) ? Integer.parseInt(args[0].substring(idx + 1)) : DaytimeServer.DEFAULT_PORT;
		String hostName = (idx > -1) ? args[0].substring(0, idx) : args[0];
		InetAddress host = InetAddress.getByName(hostName);

		DatagramSocket socket = new DatagramSocket();
		socket.setSoTimeout(5000);
		DatagramPacket packet = new DatagramPacket(new byte[256], 1, host, port);
		socket.send(packet);
		packet.setLength(packet.getData().length);
		socket.receive(packet);
		socket.close();

		byte[] data = packet.getData();
		int length = packet.getLength();
		System.out.println(new String(data, 0, length, "latin1"));
	}
}
