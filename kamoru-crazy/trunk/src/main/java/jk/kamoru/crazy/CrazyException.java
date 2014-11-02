package jk.kamoru.crazy;

import jk.kamoru.KamoruException;

public class CrazyException extends KamoruException {

	private static final long serialVersionUID = CRAZY.SERIAL_VERSION_UID;
	
	public CrazyException(String message, Throwable cause) {
		super(message, cause);
	}

	public CrazyException(String message) {
		super(message);
	}

	public CrazyException(Throwable cause) {
		super(cause);
	}

	@Override
	public String getKind() {
		return "Crazy";
	}

}
