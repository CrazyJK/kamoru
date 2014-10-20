package jk.kamoru.crazy.video;

import jk.kamoru.KamoruException;

/**video에서 발생하는 에러
 * @author kamoru
 */
public class VideoException extends KamoruException {

	private static final long serialVersionUID = VideoCore.SERIAL_VERSION_UID;

	public VideoException() {
		super();
	}
/*	since JDK 1.7
	public VideoException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
*/
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
