package jk.kamoru.test.serialization.socket;

import java.io.*;
import java.net.*;

public class JabberServer {

	static final int port = 8888;

	public static void main(String[] args) {
		try {
			MyObject o = new MyObject();
			ServerSocket s = new ServerSocket(port);
			System.out.println("[SERVER] Server Start\n\t" + s + "\n\tListens for a connection to be made ");
			
			Socket socket = s.accept();
			System.out.println("[SERVER] Connection accepted \n\t" + socket);

			ObjectOutputStream toClient = new ObjectOutputStream(
					socket.getOutputStream());
			DataInputStream fromClient = new DataInputStream(
					socket.getInputStream());

			while (o.count < 6) {
				o.setName();
				toClient.reset();
				toClient.writeObject(o);
				System.out.println("[SERVER] writing " + o);

				System.out.println("[SERVER] trying to received acknowledgement...");
				System.out.println("[SERVER] acknowledgement:" + fromClient.readInt());
				System.out.println("[SERVER] succeeded");
			}
			System.out.println("[SERVER] closing...");
			toClient.close();
			socket.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
