package kamoru.frmwk.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {

	public static final String DEFAULT_DATE_PATTERN = "yyyy-MM-dd";
	public static final String DEFAULT_TIME_PATTERN = "HH:mm:ss";
	public static final String DEFAULT_FULL_PATTERN = "yyyy-MM-dd HH:mm:ss";
	
	public static String getCurrentDateString() {
		return new SimpleDateFormat(DEFAULT_FULL_PATTERN).format(new Date());
	}
	
	public static String getDateString(String pattern) {
		return new SimpleDateFormat(pattern).format(new Date());
	}
	
	
}
