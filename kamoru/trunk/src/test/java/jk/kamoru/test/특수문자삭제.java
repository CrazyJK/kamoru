package jk.kamoru.test;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

public class 특수문자삭제 {

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
//		String str = FileUtils.readFileToString(new File("/home/kamoru/ETC/Download", "취지_컬럼_오류.gwmain.log"));
		String str = "nam";
		System.out.println(
				str
				.replaceAll("[\r\n]", "")
//				.replace((char)10,  (char)13).replace((char)13,  (char)0)
				.replace(System.getProperty("line.separator"), "")
				);
		for(byte b : str.getBytes()) 
			System.out.println(b);
	}

}
