package kamoru.util.crypto;

import java.security.*;
import java.security.spec.AlgorithmParameterSpec;

import javax.crypto.*;
import javax.crypto.spec.*;

import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;

import java.io.*;

class Encrypt {

	// 32 bytes => 256bit
	final static byte[] KEYbyte = new byte[]{
		(byte)0x06, (byte)0xa9, (byte)0x21, (byte)0x40, (byte)0x36, (byte)0xb8, (byte)0xa1, (byte)0x5b, 
		(byte)0x51, (byte)0x2e, (byte)0x03, (byte)0xd5, (byte)0x34, (byte)0x12, (byte)0x00, (byte)0x06, 
		(byte)0x44, (byte)0x3a, (byte)0xff, (byte)0xff, (byte)0xff, (byte)0xff, (byte)0xff, (byte)0xff, 
		(byte)0xff, (byte)0xff, (byte)0xff, (byte)0xff, (byte)0xff, (byte)0xff, (byte)0xff, (byte)0xff};
	final static byte[] IVbyte = new byte[]{
		(byte)0x06, (byte)0xa9, (byte)0x21, (byte)0x40, (byte)0x36, (byte)0xb8, (byte)0xa1, (byte)0x5b,
		(byte)0x51, (byte)0x2e, (byte)0x03, (byte)0xd5, (byte)0x34, (byte)0x12, (byte)0x00, (byte)0x06};
	
	public static void main(String[] args) throws Exception {

		// secret key
		Key key = new SecretKeySpec(KEYbyte, "AES");
		// initial vector
		IvParameterSpec ivParameterSpec = new IvParameterSpec(IVbyte);
		// cipher
		Cipher cipher = Cipher.getInstance("AES/CBC/NOPadding");

		//
		// Encrypt -> Encode -> Decode -> Decrypt
		// Start

//		String message = "You see there is only one constant. One universal. It is the only real truth. Causality Action, reaction. Cause and effect";
//		System.out.println("original string:         [" + message + "]");
//		System.out.println("original string size:    [" + message.length() + "]");
//		
//		// 1. Encryption
//		cipher.init(Cipher.ENCRYPT_MODE, key, ivParameterSpec);
//		byte[] encrypted = cipher.doFinal(message.getBytes());
//		System.out.println("1.encrypted hex:         [" + toHexString(encrypted) + "]");
//		System.out.println("1.encrypted size:        [" + encrypted.length + "]");
//
//		// 2. Base64 Encoding
//		String base64Encoded = Base64.encode(encrypted);
//		System.out.println("2.base64 encoded string: [" + base64Encoded + "]");
//		System.out.println("2.base64 encoded size:   [" + base64Encoded.length() + "]");
//
//		// 3. Base64 Decoding
//		byte[] base64Decode = Base64.decode(base64Encoded);
//		System.out.println("3.base64 decoded hex:    [" + toHexString(base64Decode) + "]");
//		System.out.println("3.base64 decoded size:   [" + base64Decode.length + "]");
//
//		// 4. Decryption
//		cipher.init(Cipher.DECRYPT_MODE, key, ivParameterSpec);
//		byte[] decrypted = cipher.doFinal(base64Decode);
//		System.out.println("4.decrypted hex:         [" + toHexString(decrypted) + "]");
//		System.out.println("4.decrypted size:        [" + decrypted.length + "]");
//		System.out.println("4.decrypted string:      [" + (new String(decrypted)) + "]");

		// End

		
		String txtBase64 = "BqkhQDa4oVtRLgPVNBIABrZ757I2j2x1Ii5vsao4IAZC4EZ3O8fwqGtCFPAuHjbtRg20mV+Hi8lYMpNCgeUABgAAAVZCR01U";
		System.out.println("txtBase64 size : " + txtBase64.length());
		
		
		byte[] txtByte = Base64.decode(txtBase64);
		System.out.println("decoded hex:[" + toHexString(txtByte) + "]");
		System.out.println("decoded hex:[" + txtByte.length + "]");
		
		byte[] encryptByte = new byte[48];

		for(int id = 0, i = 0; i<txtByte.length; i++) {
			if(i > 15 && i < 64) 
				encryptByte[id++] = txtByte[i];

		}
		System.out.println("decoded hex size:[" + toHexString(encryptByte) + "]");
		System.out.println("decoded hex size:[" + encryptByte.length + "]");

//		for(int i=0; i<encryptByte.length; i++) {
//			System.out.print(toHexString(encryptByte[i]) + " hex [" + encryptByte[i] + "] - ");
//			encryptByte[i] = encryptByte[i] < 0 ? (byte)convert(encryptByte[i]) : encryptByte[i];
//			System.out.println("convert hex :[" + encryptByte[i] + "]" + toHexString(encryptByte[i]));
//		}
		
		cipher.init(Cipher.DECRYPT_MODE, key, ivParameterSpec);
		byte[] txtDecrypt = cipher.doFinal(encryptByte);
		System.out.println("decrypted string:[" + toHexString(txtDecrypt) + "]");
//		System.out.println("decrypted string:[" + (new String(txtDecrypt)) + "]");
		
	}
	
	public static String toHexString(byte[] bytes) {
		if (bytes == null) {
			return null;
		}
		
		StringBuffer result = new StringBuffer();
		for (byte b : bytes) {
			result.append(Integer.toString((b & 0xF0) >> 4, 16));
			result.append(Integer.toString(b & 0x0F, 16));
			result.append(",");
		}
		return result.toString();
	}
	public static String toHexString(byte b) {
		StringBuffer result = new StringBuffer(3);
		result.append(Integer.toString((b & 0xF0) >> 4, 16));
		result.append(Integer.toString(b & 0x0F, 16));
		return result.toString();
	}
	public static int convert(byte b) {
//		return (int)b&0xff;
		return b + 128;
	}
}
