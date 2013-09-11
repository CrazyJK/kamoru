package jk.kamoru;

public class KamoruException extends RuntimeException {

	private static final long serialVersionUID = KAMORU.SERIAL_VERSION_UID;

	public KamoruException() {
		super();
	}

	public KamoruException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public KamoruException(String message, Throwable cause) {
		super(message, cause);
	}

	public KamoruException(String message) {
		super(message);
	}

	public KamoruException(Throwable cause) {
		super(cause);
	}

	
}
