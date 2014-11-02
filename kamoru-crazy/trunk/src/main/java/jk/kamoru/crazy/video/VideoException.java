package jk.kamoru.crazy.video;

import jk.kamoru.crazy.CrazyException;
import jk.kamoru.crazy.video.domain.Video;

/**video에서 발생하는 에러
 * @author kamoru
 */
public class VideoException extends CrazyException {

	private static final long serialVersionUID = VIDEO.SERIAL_VERSION_UID;

	private Video video;
	
	public VideoException(Video video, String message, Throwable cause) {
		super(String.format("[%s] %s", video.getOpus(), message), cause);
		this.video = video;
	}

	public VideoException(Video video, String message) {
		super(String.format("[%s] %s", video.getOpus(), message));
		this.video = video;
	}

	public VideoException(Video video, Throwable cause) {
		super(String.format("[%s]", video.getOpus()), cause);
		this.video = video;
	}

	public Video getVideo() {
		return video;
	}
}
