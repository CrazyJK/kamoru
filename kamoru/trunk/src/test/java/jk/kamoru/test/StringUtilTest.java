package jk.kamoru.test;

import org.apache.commons.lang3.StringUtils;

public class StringUtilTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String str = "adad, sda ";

		System.out.println(
				StringUtils.isAlphaSpace(str)
				);
		System.out.println(
				str.matches(".*[ㄱ-ㅎㅏ-ㅣ가-힣]+.*"));
	}

}
