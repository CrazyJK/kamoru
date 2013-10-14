package jk.kamoru.app.video;

import jk.kamoru.KAMORU;
import jk.kamoru.app.video.domain.Sort;

public class VideoCore {

	public static final long SERIAL_VERSION_UID = KAMORU.SERIAL_VERSION_UID; 
	
	public static final String FILE_ENCODING = "UTF-8";
	
	public static final long WEBCACHETIME_SEC = 86400*7l;

	public static final long WEBCACHETIME_MILI = WEBCACHETIME_SEC*1000l;

	public static final Sort DEFAULT_SORTMETHOD = Sort.T;

	public static final String HISTORY_LOG = "history.log";

	public static final String EXT_ACTRESS = "actress";

	public static final String EXT_STUDIO = "studio";

	public static final String EXT_INFO = "info";

	public static final String EXT_WEBP = "webp";

}
