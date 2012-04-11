package kamoru.util.crypto;

import java.security.*;
import javax.crypto.*;
import javax.crypto.spec.*;
import java.io.*;

class Encrypt {

	public static void main(String[] args) throws Exception {

		String message = "Message to Decode";

		KeyGenerator key = KeyGenerator.getInstance("AES");
		key.init(256);

		SecretKey s = key.generateKey();
		byte[] raw = s.getEncoded();

		SecretKeySpec sskey = new SecretKeySpec(raw, "AES");

		Cipher cipher = Cipher.getInstance("AES");

		cipher.init(Cipher.ENCRYPT_MODE, sskey);

		byte[] encrypted = cipher.doFinal(message.getBytes());
		System.out.println("encrypted string: " + (encrypted));

	}
}
