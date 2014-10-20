package jk.kamoru.crazy.image;

import jk.kamoru.KAMORU;
import jk.kamoru.KamoruException;

public class ImageException extends KamoruException {

	private static final long serialVersionUID = KAMORU.SERIAL_VERSION_UID;

	public ImageException() {
		super();
	}
/*	since JDK 1.7
	public ImageException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
*/
	public ImageException(String message, Throwable cause) {
		super(message, cause);
	}

	public ImageException(String message) {
		super(message);
	}

	public ImageException(Throwable cause) {
		super(cause);
	}
	
}
