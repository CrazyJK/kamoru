package jk.kamoru.util;

import java.io.File;

import org.junit.Assert;

public class WebpUtils {

	/**
	 * webp 변환
	 * @param cwebp cwebp 모듈 경로
	 * @param imageFile 원본 이미지 파일
	 * @param destPath 저장 폴더
	 */
	public static void convert(String cwebp, File imageFile, File destPath) {
		Assert.assertTrue("image is not file!", imageFile.isFile());
		Assert.assertTrue("destination path is not exist!", destPath.exists());
		
		String command = String.format("%s \"%s\" -q 80 -o \"%s\"", 
				cwebp,  
				imageFile.getAbsolutePath(), 
				new File(destPath, FileUtils.getNameExceptExtension(imageFile) + ".webp").getAbsolutePath());
		
		RuntimeUtils.exec(command);
	}

}
