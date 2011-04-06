package kamoru.test.serialization.socket;

import java.net.*;
import java.io.*;

public class JabberClient {

	static final int port = 8888;

	public static void main(String[] args) {
		MyObject o;

		try {
			InetAddress addr = InetAddress.getByName(null);
			System.out.println("[CLIENT] addr = " + addr);
			Socket socket = new Socket(addr, port);
			System.out.println("[CLIENT] socket = " + socket);
			
			ObjectInputStream fromServer = new ObjectInputStream(
					socket.getInputStream());
			DataOutputStream toServer = new DataOutputStream(
					socket.getOutputStream());

			int i = 0;

			while (true) {
				o = (MyObject) fromServer.readObject();
				System.out.println("[CLIENT] trying to send acknowledgement...");
				Thread.sleep(5000);
				toServer.writeInt(i++);
				System.out.println("[CLIENT] succeeded");
				System.out.println("[CLIENT] " + o);
			}
		} catch (EOFException f) {
			System.out.println("[CLIENT] detect EOF, exit");
			System.exit(0);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
