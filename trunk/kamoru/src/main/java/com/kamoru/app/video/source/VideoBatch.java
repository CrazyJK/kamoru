package com.kamoru.app.video.source;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.kamoru.app.video.source.VideoSource;

@Component
public class VideoBatch {

	private static final Logger logger = LoggerFactory.getLogger(VideoBatch.class);

	@Autowired VideoSource videoSource;

	@Scheduled(cron="0 */5 * * * *")
	public void batchVideoSource() {
		logger.info("execute batch");
		videoSource.reload();
	}

}
