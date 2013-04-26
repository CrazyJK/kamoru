package com.kamoru.tools.webp;

import java.io.File;
import java.io.IOException;
import java.util.Collection;

import org.apache.commons.io.FileUtils;

public class WebpConverter {

	private static final String cwebp = "/home/kamoru/DevTools/libwebp-0.3.0-linux-x86-32/cwebp"; 
	private static final String imgPath = "/home/kamoru/DaumCloud/MyPictures/Entertainer";
	private static final String[] imgExt = {"jpg", "jpng", "png", "JPG", "JPNG", "PNG"};
	private static final String destPath = "/home/kamoru/Enter/";
	/**
	 * @param args
	 * @throws IOException 
	 * @throws InterruptedException 
	 */
	public static void main(String[] args) throws IOException, InterruptedException {
		Collection<File> found = FileUtils.listFiles(new File(imgPath), imgExt, false);
		for(File file : found) {
			String fullname = file.getAbsolutePath();
			String name = getFileName(file);
			
			System.out.println(file.getAbsolutePath());
			System.out.println(file.getParent());
			
			String command = cwebp + " " + fullname + " -q 80 -o " + destPath + name + ".webp";
			System.out.println(command);
			Runtime.getRuntime().exec(command);
			Thread.sleep(1000);
		}
	}

	private static String getFileName(File file) {
		String name = file.getName();
		int idx = name.lastIndexOf(".");
		return idx < 0 ? name : name.substring(0, idx);
	}

}
