import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;

public class TestCodecBase64 {
	public static void main(String[] args) {
		String txtPlain = "베이스64 인코딩, 디코딩 테스트입니다.";
		String txtCipher = "";
		System.out.println("Source String : " + txtPlain);

		txtCipher = Base64.encode(txtPlain.getBytes());
		System.out.println("Encode Base64 : " + txtCipher);

		txtPlain = new String(Base64.decode(txtCipher));
		System.out.println("Decode Base64 : " + txtPlain);

		String sample = "BqkhQDa4oVtRLgPVNBIABrZ757I2j2x1Ii5vsao4IAZC4EZ3O8fwqGtCFPAuHjbtRg20mV+Hi8lYMpNCgeUABgAAAVZCR01U";
		
		byte[] decodeTxt = Base64.decode(sample);
		System.out.println("Decode Base64 : " + new String(decodeTxt));
		System.out.println("Decode Base64 : " + toHexString(decodeTxt));

	}

	public static String toHexString(byte[] bytes) {
		if (bytes == null) {
			return null;
		}
		
		StringBuffer result = new StringBuffer();
		for (byte b : bytes) {
			result.append(Integer.toString((b & 0xF0) >> 4, 16));
			result.append(Integer.toString(b & 0x0F, 16));
		}
		return result.toString();
	}

}
