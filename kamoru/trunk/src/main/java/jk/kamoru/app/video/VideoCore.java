package jk.kamoru.app.video;

import jk.kamoru.app.video.domain.Sort;
import jk.kamoru.core.KAMORU;

public class VideoCore {

	public static final long Serial_Version_UID = KAMORU.Serial_Version_UID; 
	
	public static final String FileEncoding = "UTF-8";
	
	public static final long WebCacheTime_sec = 86400*7l;

	public static final long WebCacheTime_Mili = WebCacheTime_sec*1000l;

	public static final Sort DEFAULT_SORTMETHOD = Sort.T;

	public static final String EXT_ACTRESS = ".actress";

}
