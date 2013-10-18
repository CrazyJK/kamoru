package jk.kamoru.test.util;

import java.io.File;

import jk.kamoru.util.FileUtils;

public class FileUtilsTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		File basepath = new File("/home/kamoru/ETC/Download/Melon-10-11");
		FileUtils.renameToMelon(basepath);

	}

}
