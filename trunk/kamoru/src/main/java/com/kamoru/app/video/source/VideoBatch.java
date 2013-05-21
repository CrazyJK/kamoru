package com.kamoru.app.video.source;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.kamoru.app.video.domain.Video;
import com.kamoru.app.video.source.VideoSource;

@Component
public class VideoBatch {

	private static final Logger logger = LoggerFactory.getLogger(VideoBatch.class);

	@Autowired VideoSource videoSource;

	@Value("#{videoProp['moveWatchedVideo']}") 
	private boolean moveWatchedVideo;
	
	@Value("#{videoProp['watchedVideoPath']}") 
	private String WATCHED_PATH;

	@Scheduled(cron="0 */5 * * * *")
	public void batchVideoSource() {
		logger.info("execute batch. reload");
		videoSource.reload();
		
		if(moveWatchedVideo) {
			logger.info("video move Start");
			int count = 0;
			for(Video video : videoSource.getVideoMap().values()) {
				if(video.getPlayCount() > 0 && video.getVideoFileListPath().startsWith("E")) {
					if(video.getVideoFileListPath().indexOf(WATCHED_PATH) < 0) {
						if(count++ >= 20) {
							break;
						}
						logger.info(count + " : " + video.getOpus() + " : " + video.toString());
						video.move(WATCHED_PATH );
						logger.info("move completed");
						try {
							Thread.sleep(3000l);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				}
			}
			logger.info("video move End");
		}
	}
	
}
