package com.kamoru.app.video.source;

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.kamoru.app.video.dao.VideoDao;
import com.kamoru.app.video.domain.Video;

@Component
public class VideoBatch {

	private static final Logger logger = LoggerFactory.getLogger(VideoBatch.class);

	@Autowired VideoDao videoDao;

	@Value("#{videoProp['moveWatchedVideo']}") private boolean MOVE_WATCHED_VIDEO;
	@Value("#{videoProp['watchedVideoPath']}") private String WATCHED_PATH;
	/** 최소 공간 사이즈 5GB */
	private final long MIN_FREE_SPAC = 1024*1024*1024*5l;
	private final long SLEEP_TIME = 3000l;
	
	@Value("#{videoProp['removeLowerRankVideo']}") private boolean REMOVE_LOWER_RANK_VIDEO;
	@Value("#{videoProp['lowerRankVideoBaselineScore']}")  private int LOWER_RANK_VIDEO_BASELINE_SCORE;


	@Scheduled(cron="0 */5 * * * *")
	public void batchVideoSource() {

		int count = 0;
		for (Video video : videoDao.getVideoList()) {
			
			// remove lower rank video periodically
			if (REMOVE_LOWER_RANK_VIDEO) {
				if (video.getRank() < LOWER_RANK_VIDEO_BASELINE_SCORE) {
					logger.info("remove lower rank video " + video.getOpus() + ":" + video.getRank() + ":" + video.getTitle());
					videoDao.deleteVideo(video.getOpus());
				}
			}

			// Garbage file delete
			if (!video.isExistVideoFileList() && !video.isExistCoverFile()
					&& !video.isExistCoverWebpFile() && !video.isExistSubtitlesFileList()) {
				videoDao.deleteVideo(video.getOpus());
			}

			// video file move to same folder
			video.arrange();

			// watched video move
			if (MOVE_WATCHED_VIDEO) {
				File dir = new File(WATCHED_PATH);
				if (dir.getFreeSpace() > MIN_FREE_SPAC) {
					if (video.getPlayCount() > 0) {// && video.getVideoFileListPath().startsWith("E")) {
						if (video.getVideoFileListPath().indexOf(WATCHED_PATH) < 0) {
							if (count++ < 10) {
								logger.info("video move " + count + " : " + video.getOpus() + " : " + video.toString());
								video.move(WATCHED_PATH);
								logger.info("move completed");
								try {
									Thread.sleep(SLEEP_TIME);
								} catch (InterruptedException e) {
									logger.error("sleep error", e);
								}
							}
						}
					}
				}
			}

		}
		

		logger.info("execute batch. reload");
		videoDao.reload();

	}
	
}
