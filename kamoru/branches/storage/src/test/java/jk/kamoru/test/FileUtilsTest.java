package jk.kamoru.test;

import java.io.File;
import java.util.Collection;
import java.util.Iterator;

import org.apache.commons.io.*;

public class FileUtilsTest {

	public static void main(String[] args) {
//		Collection coll =  FileUtils.listFiles(new File("/home/kamoru/ETC/Download"), new String[]{"avi","mp3","pdf"}, true);
//		System.out.println(coll.size());
//		Iterator it = coll.iterator();
//		while(it.hasNext()) {
//			File file = (File)it.next();
//			System.out.println(file.getName());
//		}
		File[] files = FileUtils.convertFileCollectionToFileArray(FileUtils.listFiles(new File("/home/kamoru/ETC/Download"), new String[]{"avi","mp3","pdf"}, true));
		for(File file: files) {
			System.out.println(file.getName());
		}
	}
}
