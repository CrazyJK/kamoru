package jk.kamoru.app.video.source;

import java.io.File;

import jk.kamoru.app.video.domain.Video;
import jk.kamoru.app.video.service.VideoService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class VideoBatch {

	private static final Logger logger = LoggerFactory.getLogger(VideoBatch.class);

	@Autowired VideoService videoService;

	@Value("#{videoProp['moveWatchedVideo']}") private boolean MOVE_WATCHED_VIDEO;
	@Value("#{videoProp['watchedVideoPath']}") private String WATCHED_PATH;
	/** 최소 공간 사이즈 */
	private final long MIN_FREE_SPAC = 1024*1024*1024*10l;
	private final long SLEEP_TIME = 3000l;
	
	@Value("#{videoProp['removeLowerRankVideo']}") private boolean REMOVE_LOWER_RANK_VIDEO;
	@Value("#{videoProp['lowerRankVideoBaselineScore']}")  private int LOWER_RANK_VIDEO_BASELINE_SCORE;


	@Scheduled(cron="0 */5 * * * *")
	public void batchVideoSource() {
		logger.info("batch START");
		long startTime = System.currentTimeMillis();

		if (REMOVE_LOWER_RANK_VIDEO) {
			logger.info("batch : remove lower rank video periodically");
			for (Video video : videoService.getVideoList()) {
				if (video.getRank() < LOWER_RANK_VIDEO_BASELINE_SCORE) {
					logger.debug("remove lower rank video {} : {} : {}", video.getOpus(), video.getRank(), video.getTitle());
					videoService.deleteVideo(video.getOpus());
				}
			}
		}
		
		logger.info("batch : delete garbage file");
		for (Video video : videoService.getVideoList()) {
			if (!video.isExistVideoFileList() 
					&& !video.isExistCoverFile()
					&& !video.isExistCoverWebpFile() 
					&& !video.isExistSubtitlesFileList()) {
				logger.debug("delete garbage file - {}", video);
				videoService.deleteVideo(video.getOpus());
			}
		}
		
		logger.info("batch : move video file to same folder");
		for (Video video : videoService.getVideoList()) {
			logger.trace("arrange video {}", video.getOpus());
			videoService.arrangeVideo(video.getOpus());
		}
		
		if (MOVE_WATCHED_VIDEO) {
			logger.info("batch : move watched video");
			int count = 0;
			for (Video video : videoService.getVideoList()) {
				if (video.getPlayCount() > 0
						&& video.getVideoFileListPath().indexOf(WATCHED_PATH) < 0
						&& count++ < 10
						&& new File(WATCHED_PATH).getFreeSpace() > MIN_FREE_SPAC) {
					logger.debug("move video {} : {}", count, video);
					videoService.moveVideo(video.getOpus(), WATCHED_PATH);
					try {
						Thread.sleep(SLEEP_TIME);
					} catch (InterruptedException e) {
						logger.error("sleep error", e);
					}
				}
			}
		}

		logger.info("batch : reload");
		videoService.reload();
		
		long elapsedTime = System.currentTimeMillis() - startTime;
		logger.info("batch END. Elapsed time : {} ms", elapsedTime);
	}
	
}
