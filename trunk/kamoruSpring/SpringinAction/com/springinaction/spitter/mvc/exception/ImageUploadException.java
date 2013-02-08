package com.springinaction.spitter.mvc.exception;

public class ImageUploadException extends RuntimeException {

	public ImageUploadException(String message) {
		super(message);
	}

	public ImageUploadException() {
		super();
	}

	public ImageUploadException(String message, Throwable cause) {
		super(message, cause);
	}

	public ImageUploadException(Throwable cause) {
		super(cause);
	}

}
