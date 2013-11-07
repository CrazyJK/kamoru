package jk.kamoru.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class ZipUtils {

	public static void zip(File zipFile, File baseDir, List<File> fileList) {
		ZipOutputStream out = null;
		try {
			out = new ZipOutputStream(new FileOutputStream(zipFile));
			String entryName = null;
			
			if (baseDir != null) {
				if (!baseDir.isDirectory())
					throw new IllegalArgumentException("");
				
			}
			for (File file : fileList) {
				if (baseDir != null) {
					if (file.getAbsolutePath().startsWith(baseDir.getAbsolutePath()))
						entryName = StringUtils.removeStart(file.getAbsolutePath(), baseDir.getAbsolutePath());
					else
						entryName = file.getName();
				}
				else {
					entryName = file.getName();
				}
				ZipEntry entry = new ZipEntry(entryName);
				try {
					out.putNextEntry(entry);
					out.write(FileUtils.readFileToByteArray(file));
					out.closeEntry();
				} catch (IOException e) {
					continue;
				}
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (out != null)
				try {
					out.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
		
	}
}
