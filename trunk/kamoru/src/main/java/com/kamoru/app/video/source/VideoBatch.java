package com.kamoru.app.video.source;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.kamoru.app.video.VideoCore;
import com.kamoru.app.video.domain.Video;
import com.kamoru.app.video.source.VideoSource;

@Component
public class VideoBatch {

	private static final Logger logger = LoggerFactory.getLogger(VideoBatch.class);

	@Autowired VideoSource videoSource;

	@Value("#{videoProp['moveWatchedVideo']}") private boolean MOVE_WATCHED_VIDEO;
	@Value("#{videoProp['watchedVideoPath']}") private String WATCHED_PATH;
	
	
	@Value("#{videoProp['removeLowerRankVideo']}") private boolean REMOVE_LOWER_RANK_VIDEO;
	@Value("#{videoProp['lowerRankVideoBaselineScore']}")  private int LOWER_RANK_VIDEO_BASELINE_SCORE;

	private static boolean first = true;

	@Scheduled(cron="0 */5 * * * *")
	public void batchVideoSource() {

		// remove lower rank video periodically
		if (REMOVE_LOWER_RANK_VIDEO) {
			logger.info("remove lower rank video START");
			for (Video video : videoSource.getVideoMap().values()) {
				if (video.getRank() < LOWER_RANK_VIDEO_BASELINE_SCORE) {
					logger.info(video.getOpus() + ":" + video.getRank() + ":" + video.getTitle());
					video.removeVideo();
				}
			}
			logger.info("remove lower rank video END");
		}

		// watched video move
		if (MOVE_WATCHED_VIDEO) {
			logger.info("video move Start");
			int count = 0;
			for (Video video : videoSource.getVideoMap().values()) {
				if (video.getPlayCount() > 0) { // && video.getVideoFileListPath().startsWith("E")) {
					if (video.getVideoFileListPath().indexOf(WATCHED_PATH) < 0) {
						if (count++ >= 20) {
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
		
		for (Video video : videoSource.getVideoMap().values()) {
			if (!video.isExistVideoFileList() && !video.isExistCoverFile()
					&& !video.isExistCoverWebpFile() && !video.isExistSubtitlesFileList()) {
				video.removeVideo();
			}
		}

		logger.info("execute batch. reload");
		videoSource.reload();

	}
	
}
