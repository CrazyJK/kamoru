package jk.kamoru.app.video;

import java.io.File;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import jk.kamoru.app.video.domain.Video;
import jk.kamoru.app.video.service.VideoService;
import jk.kamoru.util.FileUtils;

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
	@Value("#{prop['maximumGBSizeOfEntireVideo']}")  	private int 	MAXIMUM_GB_SIZE_OF_ENTIRE_VIDEO;
	@Value("#{prop['deleteLowerScoreVideo']}") 			private boolean DELETE_LOWER_SCORE_VIDEO;
	
	/** 최소 공간 사이즈 */
	private final long MIN_FREE_SPAC = 10 * FileUtils.ONE_GB;
	private final long SLEEP_TIME = 10 * 1000;

	@Scheduled(cron="0 */5 * * * *")
	public void batchVideoSource() {
		
		logger.info("BATCH START");
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
		 */
		
		/*
		 * 종합 순위
		 * 점수 배정
		 * 	- rank 			: rankRatio
		 * 	- play count	: playRatio
		 * 	- actress video	: actressRatio		
		 *  - subtitles     : subtitlesRatio
		 */
		logger.info("BATCH : delete lower score video [{}]", DELETE_LOWER_SCORE_VIDEO);
		if (DELETE_LOWER_SCORE_VIDEO) {
			long maximumSizeOfEntireVideo = MAXIMUM_GB_SIZE_OF_ENTIRE_VIDEO * FileUtils.ONE_GB;
			long sumSizeOfTotalVideo  = 0l;
			long sumSizeOfDeleteVideo = 0l;
			int  countOfTotalVideo    = 0;
			int  countOfDeleteVideo   = 0;
			
			List<Video> list = videoService.getVideoList();
			Collections.sort(list, new Comparator<Video>(){
				@Override
				public int compare(Video o1, Video o2) {
					return o2.getScore() - o1.getScore();
				}});
			int minAliveScore = 0;
			for (Video video : list) {
				int score = video.getScore();
				sumSizeOfTotalVideo += video.getLength();
				countOfTotalVideo++;
				
				if (sumSizeOfTotalVideo > maximumSizeOfEntireVideo) {
					sumSizeOfDeleteVideo += video.getLength();
					countOfDeleteVideo++;
					
					logger.info("    {}/{}. score[{}] = rankScore[{}] + playScore[{}] + actressScore[{}] + subtitlesScore[{}]; {}", 
							countOfDeleteVideo,
							countOfTotalVideo,
							score, 
							video.getRankScore(), 
							video.getPlayScore(), 
							video.getActressScore(),
							video.getSubtitlesScore(),
							video.getFullname());
	
					videoService.deleteVideo(video.getOpus());
				}
				else {
					minAliveScore = score;
				}
			}
	//		ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
	//	    attr.getRequest().getSession(true).setAttribute("MIN_SCORE", minAliveScore);
			if (countOfDeleteVideo > 0)
				logger.info("    Total deleted {} video, {} GB", countOfDeleteVideo, sumSizeOfDeleteVideo / FileUtils.ONE_GB);
			logger.info("    Current minimum score is {} ", minAliveScore);
		}
		
		logger.info("BATCH : delete garbage file");
		for (Video video : videoService.getVideoList()) {
			if (!video.isExistVideoFileList() 
					&& !video.isExistCoverFile()
					&& !video.isExistCoverWebpFile() 
					&& !video.isExistSubtitlesFileList()) {
				logger.info("    delete garbage file - {}", video);
				videoService.deleteVideo(video.getOpus());
			}
		}
		
		logger.info("BATCH : arrange to same folder");
		for (Video video : videoService.getVideoList()) {
			logger.trace("    arrange video {}", video.getOpus());
			videoService.arrangeVideo(video.getOpus());
		}
		
		logger.info("BATCH : move watched video [{}] to {}", MOVE_WATCHED_VIDEO, WATCHED_PATH);
		int maximumCountOfMoveVideo = 5;
		if (MOVE_WATCHED_VIDEO) {
			int countOfMoveVideo = 0;
			for (Video video : videoService.getVideoList()) {
				if (video.getPlayCount() > 0
						&& video.getVideoFileListPath().indexOf(WATCHED_PATH) < 0
						&& countOfMoveVideo++ < maximumCountOfMoveVideo
						&& new File(WATCHED_PATH).getFreeSpace() > MIN_FREE_SPAC) {
					logger.info("    move video {} : {}", countOfMoveVideo, video.getFullname());
					
					videoService.moveVideo(video.getOpus(), WATCHED_PATH);

					if (countOfMoveVideo < maximumCountOfMoveVideo)
						try {
							Thread.sleep(SLEEP_TIME);
						} catch (InterruptedException e) {
							logger.error("sleep error", e);
						}
				}
			}
		}

		logger.info("BATCH : reload");
		videoService.reload();
		
		long elapsedTime = System.currentTimeMillis() - startTime;
		logger.info("BATCH END. Elapsed time : {} ms", elapsedTime);
	}
	
}
