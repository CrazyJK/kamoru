package com.kamoru.util.java.io;

import java.io.File;

public class 파일명_일괄변경 {

	private String basepath;
	
	public void setBasepath(String basepath) {
		this.basepath = basepath;
	}
	
	public void 일괄변경() {
		File[] files = new File(basepath).listFiles();
		int count = 0;
		for(File f : files) {
			count++;
			
			String parent = f.getParent();
			String folder = parent.substring(parent.lastIndexOf("/") + 1, parent.length());
			String fullname = f.getName();
			int lastIndex = fullname.lastIndexOf(".");
			String name = fullname.substring(0, lastIndex);
			String ext = fullname.substring(lastIndex + 1);
			
			String newName = folder + addZero(count, 4);

			System.out.format("[%s]-[%s][%s][%s][%s]%n", parent, folder, name, newName, ext);

			File newFile = new File(parent, newName + "." + ext.toLowerCase());
			
			f.renameTo(newFile);
		}
	}
	
	private String addZero(int number, int digit) {
		String s = String.valueOf(number);
		while(s.length() < digit) {
			s = "0" + s;
		}
		return s;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		파일명_일괄변경 이미지일관변경 = new 파일명_일괄변경();
		이미지일관변경.setBasepath("/home/kamoru/DaumCloud/MyPictures/Entertainer");
		이미지일관변경.일괄변경();
	}

}
