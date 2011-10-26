package kamoru.remote.sample;

import java.net.*;
import java.io.*;

public class DaytimeServer {
	public static final int DEFAULT_PORT = 13;

	
	
	public static void main(String[] args) throws IOException {
		if (args.length > 1)
			throw new IllegalArgumentException("Syntax: DaytimeServer []");
		// ����� �Է� ��Ʈ�� DatagramSocket ���� �Է°��� ���� ��� Default Port 13
		DatagramSocket socket = new DatagramSocket(args.length == 0 ? DEFAULT_PORT : Integer.parseInt(args[0]));
		DatagramPacket packet = new DatagramPacket(new byte[1], 1); // DatagramPacket
																	// ����

		while (true) {
			socket.receive(packet); // �������κ��� ��Ŷ�� ����
			System.out.println("Received from: " + packet.getAddress() + ":" + packet.getPort());

			String rerutnMsg = new java.util.Date().toString();
			
			byte[] outBuffer = rerutnMsg.getBytes("latin1"); // ��Ŷ ������ ǥ��
			packet.setData(outBuffer);
			packet.setLength(outBuffer.length);
			socket.send(packet);
		}
	}
}
