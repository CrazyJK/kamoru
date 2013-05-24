package kamoru.test.base64;

import java.io.IOException;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
 * Base64 인코딩 디코딩 테스트
 * 
 * @author kdarkdev
 * 
 */
public class Base64Test {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		String text = "admin";
		System.out.println("원본 텍스트 : " + text);

		String encodeText = new BASE64Encoder().encode(text.getBytes());
		System.out.println("인코딩된 텍스트 : " + encodeText);

		String decodeText = new String(
				new BASE64Decoder().decodeBuffer(encodeText));
		System.out.println("디코딩된 텍스트 : " + decodeText);
	}
}