package kamoru.remote.sample;

import java.net.*;
import java.io.*;

public class DaytimeServer {
	public static final int DEFAULT_PORT = 13;

	
	
	public static void main(String[] args) throws IOException {
		if (args.length > 1)
			throw new IllegalArgumentException("Syntax: DaytimeServer []");
		// 사용자 입력 포트의 DatagramSocket 생성 입력값이 없을 경우 Default Port 13
		DatagramSocket socket = new DatagramSocket(args.length == 0 ? DEFAULT_PORT : Integer.parseInt(args[0]));
		DatagramPacket packet = new DatagramPacket(new byte[1], 1); // DatagramPacket
																	// 생성

		while (true) {
			socket.receive(packet); // 소켓으로부터 패킷을 받음
			System.out.println("Received from: " + packet.getAddress() + ":" + packet.getPort());

			String rerutnMsg = new java.util.Date().toString();
			
			byte[] outBuffer = rerutnMsg.getBytes("latin1"); // 패킷 정보를 표시
			packet.setData(outBuffer);
			packet.setLength(outBuffer.length);
			socket.send(packet);
		}
	}
}
