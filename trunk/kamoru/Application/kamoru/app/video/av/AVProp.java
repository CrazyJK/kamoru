package kamoru.app.video.av;

import java.io.InputStream;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class AVProp {
	protected static final Log logger = LogFactory.getLog(AVProp.class);

	public static String                  player = "\"C:\\Program Files (x86)\\The KMPlayer\\KMPlayer.exe\" ";
	public static String             noImagePath = "/home/kamoru/DaumCloud/MyPictures/삽질금지.jpg";
	public static String                basePath = "/home/kamoru/ETC/collection";
	public static String           av_extensions = "avi,mpg,wmv,mp4";
	public static String        cover_extensions = "jpg,jpeg,gif";
	public static String    subtitles_extensions = "smi,srt";
	public static String     overview_extensions = "txt,html";
	public static String backgroundImagePoolPath = "/home/kamoru/DaumCloud/MyPictures";
	
	private static boolean isLoad = false;
	private static final String propertiesPath = "/resources/av." + System.getProperty("os.name") + ".properties";

	public AVProp() {
		if(!isLoad) {
			isLoad = loadProperties();
		}
	}
	
	private boolean loadProperties() {
		logger.debug("AV properties load... " + propertiesPath);
		Properties prop = new Properties();
		try {
			InputStream in = getClass().getResourceAsStream(propertiesPath);
			prop.load(in);
			                 player = prop.getProperty("player", 					player);
			            noImagePath = prop.getProperty("noImagePath", 				noImagePath); 
			               basePath = prop.getProperty("basePath", 					basePath); 
			          av_extensions = prop.getProperty("av_extensions", 			av_extensions); 
			       cover_extensions = prop.getProperty("cover_extensions", 			cover_extensions); 
			   subtitles_extensions = prop.getProperty("subtitles_extensions", 		subtitles_extensions); 
				overview_extensions = prop.getProperty("overview_extensions",  		overview_extensions);
			backgroundImagePoolPath = prop.getProperty("backgroundImagePoolPath",  	backgroundImagePoolPath);
			in.close();
			
			logger.debug("\tplayer : " + player);
			logger.debug("\tnoImagePath : " + noImagePath);
			logger.debug("\tbasePath : " + basePath);
			logger.debug("\tav_extensions : " + av_extensions);
			logger.debug("\tcover_extensions : " + cover_extensions);
			logger.debug("\tsubtitles_extensions : " + subtitles_extensions);
			logger.debug("\toverview_extensions : " + overview_extensions);
			logger.debug("\tbackgroundImagePoolPath : " + backgroundImagePoolPath);
			
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
}
