package jk.kamoru.app.video;

import java.io.File;

import jk.kamoru.app.video.domain.Actress;
import jk.kamoru.app.video.domain.Video;
import jk.kamoru.app.video.service.VideoService;
import jk.kamoru.util.FileUtils;

import org.apache.commons.lang.StringUtils;
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

	@Value("#{prop['moveWatchedVideo']}") 				private boolean MOVE_WATCHED_VIDEO;
	@Value("#{prop['watchedVideoPath']}") 				private String 	WATCHED_PATH;
	@Value("#{prop['removeLowerRankVideo']}") 			private boolean REMOVE_LOWER_RANK_VIDEO;
	@Value("#{prop['lowerRankVideoBaselineScore']}")  	private int 	LOWER_RANK_VIDEO_BASELINE_SCORE;

	/** 최소 공간 사이즈 */
	private final long MIN_FREE_SPAC = 10 * FileUtils.ONE_GB;
	private final long SLEEP_TIME = 30 * 1000;

	@Scheduled(cron="0 */5 * * * *")
	public void batchVideoSource() {
		logger.info("batch START");
		long startTime = System.currentTimeMillis();

		logger.info("batch : remove lower rank video [{}] < {}", REMOVE_LOWER_RANK_VIDEO, LOWER_RANK_VIDEO_BASELINE_SCORE);
		if (REMOVE_LOWER_RANK_VIDEO) {
			for (Video video : videoService.getVideoList()) {
				if (video.getRank() < LOWER_RANK_VIDEO_BASELINE_SCORE) {
					logger.info("remove lower rank video {} : {} : {}", video.getOpus(), video.getRank(), video.getTitle());
					videoService.deleteVideo(video.getOpus());
				}
			}
		}

		/*
		 * 비디오 삭제 조건
		 * 1. rank <= 1
		 * 2. play count > 2
		 * 3. 자막 없음.
		 * 4. 여배우 : 정보가 없고, 비디오 개수가 5개 미만에 모두 만족
		 */
		logger.info("batch : delete video automatically");
		int count = 0;
		for (Video video : videoService.getVideoList()) {
			if (video.getRank() <= 1) {
				if (video.getPlayCount() > 2) {
					if (!video.isExistSubtitlesFileList()) {
						boolean delete = true;
						for (Actress actress : video.getActressList()) {
							if (actress.getVideoList().size() > 5 
									|| !StringUtils.isEmpty(actress.getBirth()) 
									|| !StringUtils.isEmpty(actress.getBodySize())) {
									delete = false; 
									break;
							}
						}
						if (delete) {
							count++;
							logger.info("Candidate to deletion - {}.{}", count, video.getFullname());
//							videoService.deleteVideo(video.getOpus());
						}
					}
				}
			}
		}
		
		logger.info("batch : delete garbage file");
		for (Video video : videoService.getVideoList()) {
			if (!video.isExistVideoFileList() 
					&& !video.isExistCoverFile()
					&& !video.isExistCoverWebpFile() 
					&& !video.isExistSubtitlesFileList()) {
				logger.info("delete garbage file - {}", video);
				videoService.deleteVideo(video.getOpus());
			}
		}
		
		logger.info("batch : arrange to same folder");
		for (Video video : videoService.getVideoList()) {
			logger.trace("arrange video {}", video.getOpus());
			videoService.arrangeVideo(video.getOpus());
		}
		
		logger.info("batch : move watched video [{}] to {}", MOVE_WATCHED_VIDEO, WATCHED_PATH);
		if (MOVE_WATCHED_VIDEO) {
			count = 0;
			for (Video video : videoService.getVideoList()) {
				if (video.getPlayCount() > 0
						&& video.getVideoFileListPath().indexOf(WATCHED_PATH) < 0
						&& count++ < 5
						&& new File(WATCHED_PATH).getFreeSpace() > MIN_FREE_SPAC) {
					logger.info("move video {} : {}", count, video);
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
