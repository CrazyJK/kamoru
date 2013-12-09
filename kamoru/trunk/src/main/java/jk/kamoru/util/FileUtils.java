package jk.kamoru.util;

import java.io.File;

import org.springframework.util.Assert;


/**
 * commons.io.FileUtils 상속하고 필요한 기능 추가
 * @author kamoru
 *
 */
public class FileUtils extends org.apache.commons.io.FileUtils {
	
	public static final String EXTENSION_SEPARATOR = ".";

	
	/**
	 * 입력된 경로의 모든 파일명을 상위폴더명+일련번호로 변경
	 * @param basepath
	 */
	public static void renameToFoldername(File basepath) {
		Assert.isTrue(basepath.isDirectory(), "It is not directory! ");
		File[] files = basepath.listFiles();
		int digit = String.valueOf(files.length).length();
		int count = 0;
		for(File f : files) {
			String parent = f.getParent();
			String folder = parent.substring(parent.lastIndexOf(System.getProperty("file.separator")) + 1, parent.length());
			String fullname = f.getName();
			int lastIndex = fullname.lastIndexOf(".");
			String ext = fullname.substring(lastIndex + 1);
			String newName = folder + StringUtils.addZero(++count, digit);
			
			f.renameTo(new File(parent, newName + "." + ext.toLowerCase()));
		}
	}
	
	public static void renameToMelon(File basepath) {
		Assert.isTrue(basepath.isDirectory());
		for (File file : FileUtils.listFiles(basepath, null, false)) {
			String name = file.getName();
			if (name.length() > 4)
				file.renameTo(new File(file.getParentFile(), name.substring(4)));
		}
	}

	/**
	 * 확장자를 뺀 파일 이름
	 * @param file
	 * @return
	 */
	public static String getNameExceptExtension(File file) {
		Assert.isTrue(file.isFile(), "It is not file! - " + file.getAbsolutePath());
		return StringUtils.substringBeforeLast(file.getName(), EXTENSION_SEPARATOR);
	}

	/**
	 * 파일의 확장자
	 * @param file
	 * @return
	 */
	public static String getExtension(File file) {
		Assert.isTrue(file.isFile(), "It is not file! - " + file.getAbsolutePath());
		return StringUtils.substringAfterLast(file.getName(), EXTENSION_SEPARATOR);
	}

}
