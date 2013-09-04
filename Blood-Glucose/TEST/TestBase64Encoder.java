import java.io.IOException;

import sun.misc.*;

public class TestBase64Encoder {
	public static void main(String[] args) {
		
		BASE64Encoder encoder = new BASE64Encoder();
		BASE64Decoder decoder = new BASE64Decoder();

		String txtPlain = "베이스64 인코딩, 디코딩 테스트입니다.";
		String txtCipher = "";
		System.out.println("Source String : " + txtPlain);

		txtCipher = encoder.encode(txtPlain.getBytes());
		System.out.println("Encode Base64 : " + txtCipher);

		String msg = "BqkhQDa4oVtRLgPVNBIABrZ757I2j2x1Ii5vsao4IAZC4EZ3O8fwqGtCFPAuHjbtRg20mV+Hi8lYMpNCgeUABgAAAVZCR01U";

		try {
			txtPlain = new String(decoder.decodeBuffer(txtCipher));
			System.out.println(toHexString(decoder.decodeBuffer(msg)));
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
		System.out.println("Decode Base64 : " + txtPlain);
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
	
}
