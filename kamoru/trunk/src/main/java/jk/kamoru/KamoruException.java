package jk.kamoru;

/**나의 어플리케이션에서 발생하는 에러
 * @author kamoru
 *
 */
public class KamoruException extends RuntimeException {

	private static final long serialVersionUID = KAMORU.SERIAL_VERSION_UID;

	public KamoruException() {
		super();
	}
/*	sinse JDK 1.7
	public KamoruException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
*/
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
