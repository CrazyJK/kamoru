package jk.kamoru.app.video;

import jk.kamoru.KamoruException;

public class VideoException extends KamoruException {

	private static final long serialVersionUID = VideoCore.SERIAL_VERSION_UID;

	public VideoException() {
		super();
	}

	public VideoException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public VideoException(String message, Throwable cause) {
		super(message, cause);
	}

	public VideoException(String message) {
		super(message);
	}

	public VideoException(Throwable cause) {
		super(cause);
	}
	
}
