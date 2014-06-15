package jk.kamoru.test;

import java.io.File;

import jk.kamoru.util.FileUtils;

public class test2 {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// thread 10, user 5
		int threadSize = 2;
		int userSize = 4;
		int index = 0;
		for (int i=0; i<threadSize; i++) { 

			System.out.format("%2s : %2s,  %2s,  %2s,  %2s%n", 
					i,
					index++,
					threadSize % userSize,
					i % threadSize,
					i % userSize
					);
		}
		File f = new File("E:\\Watched_video");
		System.out.println(f.length());
		System.out.println(f.getTotalSpace());
		System.out.println(f.getUsableSpace());
		System.out.println(FileUtils.sizeOfDirectory(f));
		System.out.println(FileUtils.sizeOfDirectory(f) / FileUtils.ONE_GB);
		
		
	}

}
