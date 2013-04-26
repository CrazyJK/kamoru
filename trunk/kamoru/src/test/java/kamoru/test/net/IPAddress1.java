package kamoru.test.net;

import java.net.*;

public class IPAddress1 {

	public static void main(String[] args) {

		InetAddress ip = null;

		try {

			ip = InetAddress.getByName("java.pukyung.co.kr");
			System.out.println("호스트 이름: " + ip.getHostName());
			System.out.println("호스트 IP 주소: " + ip.getHostAddress());

			System.out.println("로컬호스트 IP 주소: " +
					InetAddress.getLocalHost().getHostAddress());

			System.out.println("로컬호스트 주소: " +
					InetAddress.getLocalHost());

		} catch (UnknownHostException ue) {
			System.out.println(ue);
		}
	}
}