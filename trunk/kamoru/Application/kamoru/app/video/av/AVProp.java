package kamoru.app.video.av;

import java.io.InputStream;
import java.net.InetAddress;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * AV properties<br>
 * usage) private AVProp prop = AVProp.getInstance();<br>
 * properties file location is /resource/av.[hostname].properties
 * @author kamoru
 *
 */
public class AVProp {
	protected static final Log logger = LogFactory.getLog(AVProp.class);

	public String                  player = "\"C:\\Program Files (x86)\\The KMPlayer\\KMPlayer.exe\" ";
	public String                  editor = "\"C:\\Program Files (x86)\\The KMPlayer\\KMPlayer.exe\" ";
	public String             noImagePath = "/home/kamoru/DaumCloud/MyPictures/삽질금지.jpg";
	public String                basePath = "/home/kamoru/ETC/collection";
	public String        video_extensions = "avi,mpg,wmv,mp4";
	public String        cover_extensions = "jpg,jpeg,gif";
	public String    subtitles_extensions = "smi,srt";
	public String     overview_extensions = "txt,html";
	public String backgroundImagePoolPath = "/home/kamoru/DaumCloud/MyPictures";
	
	private String propertiesPath;

	private static AVProp prop;
	
	private AVProp() {
		loadProperties();
	}
	
	public static AVProp getInstance() {
		if(prop == null)
			prop = new AVProp();
		return prop;
	}
	
	private void loadProperties() {
		Properties prop = new Properties();
		try {
			propertiesPath = "/resources/av." + InetAddress.getLocalHost().getHostName() + ".properties";
			logger.debug("AV properties load... " + propertiesPath);
			
			InputStream in = getClass().getResourceAsStream(propertiesPath);
			prop.load(in);
			                 player = prop.getProperty("player", 					player).trim();
			                 editor = prop.getProperty("editor", 					editor).trim();
			            noImagePath = prop.getProperty("noImagePath", 				noImagePath).trim(); 
			               basePath = prop.getProperty("basePath", 					basePath).trim(); 
			       video_extensions = prop.getProperty("video_extensions", 			video_extensions).trim().toLowerCase(); 
			       cover_extensions = prop.getProperty("cover_extensions", 			cover_extensions).trim().toLowerCase(); 
			   subtitles_extensions = prop.getProperty("subtitles_extensions", 		subtitles_extensions).trim().toLowerCase(); 
				overview_extensions = prop.getProperty("overview_extensions",  		overview_extensions).trim().toLowerCase();
			backgroundImagePoolPath = prop.getProperty("backgroundImagePoolPath",  	backgroundImagePoolPath).trim();
			in.close();
			
			logger.debug("\tplayer : " + player);
			logger.debug("\teditor : " + editor);
			logger.debug("\tnoImagePath : " + noImagePath);
			logger.debug("\tbasePath : " + basePath);
			logger.debug("\tvideo_extensions : " + video_extensions);
			logger.debug("\tcover_extensions : " + cover_extensions);
			logger.debug("\tsubtitles_extensions : " + subtitles_extensions);
			logger.debug("\toverview_extensions : " + overview_extensions);
			logger.debug("\tbackgroundImagePoolPath : " + backgroundImagePoolPath);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
