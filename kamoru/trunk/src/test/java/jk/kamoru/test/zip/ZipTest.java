package jk.kamoru.test.zip;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import jk.kamoru.util.ZipUtils;

public class ZipTest {

	File file1 = new File("E:\\Girls\\gnom", "29675_잘빠진 각선미-2.jpeg");
	File file2 = new File("E:\\Girls\\gnom", "29676_만지고 싶은 가슴-2.jpeg");
	File file3 = new File("E:\\Girls\\gnom", "29677_야 외 노 출-2.jpeg");

	public void zip() throws IOException {
		final int BUFFER_SIZE = 1024 * 2;

		ZipOutputStream zipOut = new ZipOutputStream(new FileOutputStream(
				new File("E:\\Girls\\gnom", "test.zip")));

		ZipEntry zipEntry1 = new ZipEntry(file1.getName());
		zipOut.putNextEntry(zipEntry1);
		BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file1));
		byte[] buffer = new byte[BUFFER_SIZE];
        int cnt = 0;
        while ((cnt = bis.read(buffer, 0, BUFFER_SIZE)) != -1) {
        	zipOut.write(buffer, 0, cnt);
        }
        bis.close();
		zipOut.closeEntry();

		ZipEntry zipEntry2 = new ZipEntry(file2.getName());
		zipOut.putNextEntry(zipEntry2);
		BufferedInputStream bis2 = new BufferedInputStream(new FileInputStream(file2));
		buffer = new byte[BUFFER_SIZE];
        cnt = 0;
        while ((cnt = bis2.read(buffer, 0, BUFFER_SIZE)) != -1) {
        	zipOut.write(buffer, 0, cnt);
        }
        bis2.close();
        zipOut.closeEntry();
		
		ZipEntry zipEntry3 = new ZipEntry(file3.getName());
		zipOut.putNextEntry(zipEntry3);
		BufferedInputStream bis3 = new BufferedInputStream(new FileInputStream(file3));
		buffer = new byte[BUFFER_SIZE];
        cnt = 0;
        while ((cnt = bis3.read(buffer, 0, BUFFER_SIZE)) != -1) {
        	zipOut.write(buffer, 0, cnt);
        }
        bis3.close();
		zipOut.closeEntry();

		zipOut.close();
	}

	public void unzip() throws ZipException, IOException {
		ZipFile zipFile = new ZipFile(new File("E:\\Girls\\gnom", "test.zip"));
		System.out.format("%s %s%n", zipFile.getName(), zipFile.size());
		Enumeration<? extends ZipEntry> entries = zipFile.entries();
		while (entries.hasMoreElements()) {
			ZipEntry entry = entries.nextElement();
			System.out.format("%s %s%n", entry.getName(), entry.getSize());

		}
		zipFile.close();
	}

	public void unzip2() throws IOException {
		ZipInputStream zin = new ZipInputStream(new FileInputStream(new File("E:\\Girls\\gnom", "test.zip")));
		ZipEntry entry = null;
		while ((entry = zin.getNextEntry()) != null) {
			System.out.format("%s [%s]%n", entry.getName(), entry.getSize());
		}
		zin.close();
	}
	
	public void test() {
		File zipFile = new File("E:\\Girls\\gnom", "ziputils2.zip");
		File baseDir = new File("E:\\Girls");
		List<File> fileList = Arrays.asList(file1, file2, file3);
		ZipUtils.zip(zipFile, baseDir, fileList);
	}
	
	/**
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		ZipTest zipTest = new ZipTest();
//		zipTest.zip();
//		zipTest.unzip();
//		zipTest.unzip2();
		zipTest.test();
	}

}
