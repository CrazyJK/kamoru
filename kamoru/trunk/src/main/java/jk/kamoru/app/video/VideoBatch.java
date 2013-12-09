package jk.kamoru.app.video;

import java.io.File;
import java.util.Collections;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

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
		 * 2. play count > 1
		 * 3. 자막 없음.
		 * 4. 여배우 : 정보가 없고, 비디오 개수가 5개 미만
		 */
		logger.info("batch : delete video automatically");
		int count = 0;
		int rankTotal = 0;
		int playTotal = 0;
		int subTotal = 0;
		int actressTotal = 0;
		long deletedTotal = 0l;
		for (Video video : videoService.getVideoList()) {
			if (video.getRank() <= 1) {
				rankTotal++;
				if (video.getPlayCount() > 1) {
					playTotal++;
					if (!video.isExistSubtitlesFileList()) {
						subTotal++;
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
							actressTotal++;
							count++;
							deletedTotal += video.getLength();
							logger.info("Candidate to deletion - {}.{} rank:{}, play:{}, path:{} size:{}", count, video.getFullname(), video.getRank(), video.getPlayCount(), video.getDelegatePath(), video.getLength());
							videoService.deleteVideo(video.getOpus());
						}
					}
				}
			}
		}
		if (count > 0)
			logger.info("    Total deleted size {}, rank {}, play {}, subtitles {}, actress {}", deletedTotal / FileUtils.ONE_GB, rankTotal, playTotal, subTotal, actressTotal);
		
		/*
		 * 종합 순위
		 * 대상 비디오
		 * 	- play count is over 0
		 * 	- subtitles is not exist
		 * 점수 배정
		 * 	- rank 			: 1
		 * 	- play count	: 1
		 * 	- actress video	: 1		
		 * 
		 */
		logger.info("batch : video point");
		int rankRatio = 2;
		int playRatio = 1;
		int actressVideoRatio = 1;
		int videoTotalCount = 0;
		long videoTotalSize = 0l;
		Map<Integer, Video> pointMap = new TreeMap<Integer, Video>(Collections.reverseOrder());
		for (Video video : videoService.getVideoList()) {
			if (video.getPlayCount() > 0 || !video.isExistSubtitlesFileList()) {
				int rankPoint = video.getRank() * rankRatio;
				int playPoint = video.getPlayCount() * playRatio;
				int actressVideoPoint = 0;
				for (Actress actress : video.getActressList()) {
					actressVideoPoint += actress.getVideoList().size() * actressVideoRatio;
				}
				int totalPoint = rankPoint + playPoint + actressVideoPoint;
				pointMap.put(totalPoint, video);
				videoTotalCount++;
				videoTotalSize += video.getLength();
			}
		}
		logger.info("    Total {}video, {}GB", videoTotalCount, videoTotalSize / FileUtils.ONE_GB);
		int index = 0;
		long maxVideoSize = 700 * FileUtils.ONE_GB;
		long totalVideoSize = 0l;
		for (Entry<Integer, Video> entry : pointMap.entrySet()) {
			int point = entry.getKey();
			Video video = entry.getValue();
			
			totalVideoSize += video.getLength();
			
			logger.info("{} = {} > {} : {}", point, totalVideoSize / FileUtils.ONE_GB, maxVideoSize / FileUtils.ONE_GB, video.getFullname());
			
			if (totalVideoSize > maxVideoSize) {
				logger.info("{}. point={} - {}, {}, {}, {}", ++index, point, video.getFullname(), video.getRank(), video.getPlayCount(), point - video.getRank() - video.getPlayCount());
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
