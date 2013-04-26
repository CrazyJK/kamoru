package com.kamoru.app.image.source;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;

import com.kamoru.app.image.source.ImageSource;

public class ImageBatch {

	private static final Logger logger = LoggerFactory.getLogger(ImageBatch.class);

	@Autowired ImageSource imageSource;
	
	@Scheduled(cron="0 */5 * * * *")
	public void batchImageSource() {
		logger.info("execute batch");
		imageSource.reload();
	}

}
