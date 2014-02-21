package jk.kamoru.core.sleep;

import jk.kamoru.KAMORU;

public class SleepException extends RuntimeException {

	private static final long serialVersionUID = KAMORU.SERIAL_VERSION_UID;

	public SleepException() {
		super();
	}

	public SleepException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public SleepException(String message, Throwable cause) {
		super(message, cause);
	}

	public SleepException(String message) {
		super(message);
	}

	public SleepException(Throwable cause) {
		super(cause);
	}

	
}
