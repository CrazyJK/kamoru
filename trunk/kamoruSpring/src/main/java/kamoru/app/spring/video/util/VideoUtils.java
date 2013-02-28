package kamoru.app.spring.video.util;

import java.io.File;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.Collection;
import java.util.List;

import kamoru.app.spring.video.dao.VideoDaoFile;
import kamoru.app.spring.video.domain.Video;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

public class VideoUtils {

	static VideoDaoFile ctrl = new VideoDaoFile();
	
	public static void changeOldNameStyle(String path, String unclassifiedPath) {
		Collection<File> found = FileUtils.listFiles(new File(path), null, true);
		int classified = -1;
		for(File file : found) {
			String name		= file.getName();
			String filename = getFileName(file);
			String extname  = getFileExtension(file);
			
//			if(ctrl.listBGImageName.equals(name) || ctrl.historyName.equals(name))
//				continue;
			
			String[] filenamepart = StringUtils.split(filename, ']');
//			System.out.format("%s%n", ArrayUtils.toString(filenamepart));
			int partCount = filenamepart.length;
			String studio = "", opus = "", title = "NoTitle", actress = "UnKnown", date = "";
			switch(partCount) {
			case 5:
				date = removeUnnecessaryCharacter(filenamepart[4]);
			case 4:
				actress = removeUnnecessaryCharacter(filenamepart[3]);
			case 3:
				title = removeUnnecessaryCharacter(filenamepart[2]);
			case 2:
				opus = removeUnnecessaryCharacter(filenamepart[1]);
				String[] opuspart = StringUtils.splitByCharacterType(opus);
				if(opuspart != null && opuspart.length == 2) {
					opus = opuspart[0].toUpperCase() + "-" + opuspart[1];
				}
				studio = removeUnnecessaryCharacter(filenamepart[0]);
				classified = 0;
				break;
			case 1:
				classified = 1;
				break;
			default:
				classified = 2;
				break;
			}
			studio = "IDEAPOCKET".equals(studio) ? "IdeaPocket" : studio;
			if(title.startsWith("20")) {
				date = title.substring(0, 10);
				title = title.substring(10);
			}
			title = title.replaceAll(opus, "").trim();
			String uniqueName = "Unknown";
			if(title.startsWith(uniqueName)) {
				title = title.replaceAll(uniqueName, "").trim();
				actress = uniqueName;
			}
			if(classified == 0) {
				System.out.format("정리됨 : %s -> [%s][%s][%s][%s][%s].%s%n", name, studio, opus, title, actress, extname, date);
				String newName = MessageFormat.format("[{0}][{1}][{2}][{3}][{5}].{4}", studio, opus, title, actress, extname, date);
				try {
					if(!name.equals(newName)) {
						FileUtils.moveFile(file, new File(path, newName));
						System.out.format("    move : %s -> %s%n", name, newName);
					}
				} catch (IOException e) {
					System.out.format("Error : %s%n", e.getMessage());
				}
			}
			else if(classified == 1){
				System.out.format("정리안됨 : %s -> move to %s%n", file.getAbsoluteFile(), unclassifiedPath);
				try {
					FileUtils.moveFileToDirectory(file, new File(unclassifiedPath), true);
				} catch (IOException e) {
					System.out.format("Error : %s%n", e.getMessage());
				}
			}
			else if(classified == 2) {
				System.out.format("인자가 많음 : %s%n", file.getAbsolutePath());
			} 
			else {
				System.out.format("이건 뭥미 : %s%n", file.getAbsolutePath());
			}
		}
	}
	
	public static String getFileName(File file) {
		String filename = file.getName();
		int index = filename.lastIndexOf(".");
		return index < 0 ? filename : filename.substring(0, index); 
	}
	public static String getFileExtension(File file) {
		String filename = file.getName();
		int index = filename.lastIndexOf(".");
		return index < 0 ? "" : filename.substring(index + 1); 
	}
	public static String removeUnnecessaryCharacter(String str) {
		// 공백, [ 제거  
		str = str.trim();
		str = str.startsWith("[") ? str.substring(1) : str;
		return str.trim();
	}

	public static String arrayToString(Object array) {
		String toString = ArrayUtils.toString(array);
		return toString.substring(1, toString.length() - 1);
	}
	
	public static void main(String[] g) {
		VideoUtils.changeOldNameStyle("E:\\AV_JAP", "E:\\AV_JAP\\unclassified");
	}

	public static String getOpusArrayStyleString(List<Video> videoList) {
		StringBuilder sb = new StringBuilder();
		sb.append("[");
		for(int i=0, iEnd=videoList.size(); i<iEnd; i++) {
			if(i > 0)
				sb.append(",");
			sb.append("\"" + videoList.get(i).getOpus() + "\"");
		}
		sb.append("]");
		return sb.toString();
	}
}
