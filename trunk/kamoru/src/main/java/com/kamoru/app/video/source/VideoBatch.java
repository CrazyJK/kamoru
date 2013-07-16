package com.kamoru.app.video.source;

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

			// watched video move
			if (MOVE_WATCHED_VIDEO) {
				if (video.getPlayCount() > 0) { // && video.getVideoFileListPath().startsWith("E")) {
					if (video.getVideoFileListPath().indexOf(WATCHED_PATH) < 0) {
						if (count++ < 10) {
							logger.info("video move " + count + " : " + video.getOpus() + " : " + video.toString());
							video.move(WATCHED_PATH );
							logger.info("move completed");
							try {
								Thread.sleep(3000l);
							} catch (InterruptedException e) {
								logger.error("", e);
							}
						}
					}
				}
			}

			// video file move to same folder
			video.arrange();
		}
		

		logger.info("execute batch. reload");
		videoDao.reload();

	}
	
}
