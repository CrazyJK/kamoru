package kamoru.app.video.av;

import java.io.File;
import java.io.FileReader;
import java.util.Properties;

public class AVProp {

	public static String player = "\"C:\\Program Files (x86)\\The KMPlayer\\KMPlayer.exe\" ";
	public static String noImagePath = "/home/kamoru/DaumCloud/MyPictures/삽질금지.jpg";
	
	public static String basePath = "/home/kamoru/ETC/collection";
//	public static String basePath = "E:\\AV_JAP";
	public static String av_extensions = "avi,mpg,wmv,mp4";
	public static String cover_extensions = "jpg,jpeg,gif";
	public static String subtitles_extensions = "smi,srt";
	public static String overview_extensions = "txt,html";
	public static String backgroundImagePath = "E:\\DaumCloud\\MyPictures\\Girls";
	
	static {
		Properties prop = new Properties();
		File f = new File("av.properties");
		System.out.println("AV properties load..." + f.getAbsolutePath());
		try {
			prop.load(new FileReader(f));
			player = prop.getProperty("player", player);
			noImagePath = prop.getProperty("noImagePath", noImagePath); 
			basePath = prop.getProperty("basePath", basePath); 
			av_extensions = prop.getProperty("av_extensions", av_extensions); 
			cover_extensions = prop.getProperty("", cover_extensions); 
			subtitles_extensions = prop.getProperty("subtitles_extensions", subtitles_extensions); 
			overview_extensions = prop.getProperty("overview_extensions", overview_extensions);
			backgroundImagePath = prop.getProperty("backgroundImagePath", backgroundImagePath);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
