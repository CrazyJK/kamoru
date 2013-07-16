package com.kamoru.app.video;

import com.kamoru.app.video.domain.Sort;

public class VideoCore {

	public static final long Serial_Version_UID = 588L; 
	
	public static final String FileEncoding = "UTF-8";
	
	public static final long WebCacheTime_sec = 86400*7l;

	public static final long WebCacheTime_Mili = WebCacheTime_sec*1000l;

	public static final Sort DEFAULT_SORTMETHOD = Sort.T;

}
