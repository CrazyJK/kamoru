package com.kamoru.app.video;

public class VideoException extends RuntimeException {

	private static final long serialVersionUID = VideoCore.Serial_Version_UID;

	public VideoException(String msg) {
		super(msg);
	}
	
	public VideoException(String msg, Throwable e) {
		super(msg, e);
	}
	
}
