package com.kamoru.app.video.service;

import java.io.File;
import java.io.IOException;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.kamoru.app.video.VideoCore;
import com.kamoru.app.video.dao.VideoDao;
import com.kamoru.app.video.domain.Action;
import com.kamoru.app.video.domain.Actress;
import com.kamoru.app.video.domain.Studio;
import com.kamoru.app.video.domain.Video;
import com.kamoru.app.video.domain.VideoSearch;
import com.kamoru.app.video.util.VideoUtils;

@Service
public class VideoServiceImpl implements VideoService {
	protected static final Log logger = LogFactory.getLog(VideoServiceImpl.class);

	private byte[] defaultCoverFileBytes;
	
	@Value("#{videoProp['defaultCoverFilePath']}") 	private String defaultCoverFilePath;
	@Value("#{videoProp['editor']}") 				private String editor;
	@Value("#{videoProp['mainBasePath']}") 			private String mainBasePath;
	@Value("#{videoProp['player']}") 				private String player;
	@Value("#{videoProp['webp.mode']}") 			private boolean webpMode;

	@Autowired private VideoDao videoDao;

	private File historyFile;
	private List<String> historyList;
	
	private static boolean isChanged;

	public VideoServiceImpl() {
		isChanged = true;
	}
	
	@Override
	public void deleteVideo(String opus) {
		logger.info(opus);
		saveHistory(getVideo(opus), Action.DELETE);
		videoDao.deleteVideo(opus);
	}

	@Override
	public void editVideoSubtitles(String opus) {
		logger.info(opus);
		executeCommand(getVideo(opus), Action.SUBTITLES);
	}

	@Async
	private void executeCommand(Video video, Action action) {
		logger.info(video.getOpus() + " : " + action);
		String command = null;
		String[] argumentsArray = null;
		switch(action) {
			case PLAY:
				command = player;
				argumentsArray = video.getVideoFileListPathArray();
				break;
			case SUBTITLES:
				command = editor;
				argumentsArray = video.getSubtitlesFileListPathArray();
				break;
			default:
				throw new RuntimeException("Unknown Action");
		}
		if(argumentsArray == null)
			throw new RuntimeException("No arguments");
		
		String[] cmdArray = ArrayUtils.addAll(new String[]{command}, argumentsArray);
		try {
			Runtime.getRuntime().exec(cmdArray);
		} catch (IOException e) {
			logger.error("Error : executes command", e);
			throw new RuntimeException(e);
		}
	}

	@Override
	public List<Map<String, String>> findHistory(String query) {
		logger.info(query);
		List<Map<String, String>> foundMapList = new ArrayList<Map<String, String>>();

		if(query == null || query.trim().length() == 0)
			return foundMapList;
		
		try {
			if(isChanged || historyFile == null) {
				historyList = FileUtils.readLines(getHistoryFile(), VideoCore.FileEncoding);
				isChanged = false;
				logger.info("read history.log size=" + historyList.size());
			}
			for (String history : historyList) {
				if (StringUtils.indexOfIgnoreCase(history, query) > -1) {
					String[] hisStrings = StringUtils.split(history, ",", 4);
					int length = hisStrings.length;
					if (length > 0) {
						Map<String, String> map = new HashMap<String, String>();
						map.put("date", hisStrings[0].trim());
						if (length > 1)
							map.put("opus", hisStrings[1].trim());
						if (length > 2)
							map.put("act", hisStrings[2].trim());
						if (length > 3)
							map.put("desc", hisStrings[3].trim());
						foundMapList.add(map);
					}
				}
			}
		} 
		catch (IOException e) {
			logger.error("read error", e);
		}
		logger.info("q=" + query + " foundLength=" + foundMapList.size());
		return foundMapList;
	}

	@Override
	public List<Map<String, String>> findVideoList(String query) {
		logger.info(query);
		List<Map<String, String>> foundMapList = new ArrayList<Map<String, String>>();
		if(query == null || query.trim().length() == 0)
			return foundMapList;

		query = query.toLowerCase();
		for(Video video : videoDao.getVideoList()) {
			if(StringUtils.containsIgnoreCase(video.getOpus(), query)
					|| StringUtils.containsIgnoreCase(video.getStudio().getName(), query)
					|| StringUtils.containsIgnoreCase(video.getTitle(), query)
					|| StringUtils.containsIgnoreCase(video.getActress(), query)) {
				Map<String, String> map = new HashMap<String, String>();
				map.put("opus", video.getOpus());
				map.put("title", video.getTitle());
				map.put("studio", video.getStudio().getName());
				map.put("actress", video.getActress());
				map.put("existVideo", String.valueOf(video.isExistVideoFileList()));
				map.put("existCover", String.valueOf(video.isExistCoverFile()));
				map.put("existSubtitles", String.valueOf(video.isExistSubtitlesFileList()));
				foundMapList.add(map);
			} 
		}
		logger.info("q=" + query + " foundLength=" + foundMapList.size());
		return foundMapList;
	}

	@Override
	public Actress getActress(String actressName) {
		logger.info(actressName);
		return videoDao.getActress(actressName);
	}

	@Override
	public List<Actress> getActressList() {
		logger.info(new String());
		List<Actress> list = videoDao.getActressList();
		Collections.sort(list);
		return list;
	}

	@Override
	public List<Actress> getActressListOfVideoes(List<Video> videoList) {
		logger.info("size : " + videoList.size());
		List<Actress> actressList = new ArrayList<Actress>();
		for(Video video : videoList) {
			for(Actress actress : video.getActressList()) {
				if(!actressList.contains(actress))
					actressList.add(actress);
			}
		}
		Collections.sort(actressList);
		return actressList;
	}

	@Override
	public byte[] getDefaultCoverFileByteArray() {
		logger.info(new String());
		if(defaultCoverFileBytes == null)
			try {
				defaultCoverFileBytes = FileUtils.readFileToByteArray(new File(defaultCoverFilePath));
			} catch (IOException e) {
				logger.error(e);
			}
		return defaultCoverFileBytes;
	}
	
	private File getHistoryFile() {
		if(historyFile == null)
			historyFile = new File(mainBasePath, "history.log");
		return historyFile;
	}

	@Override
	public Studio getStudio(String studioName) {
		logger.info(studioName);
		return videoDao.getStudio(studioName);
	}

	@Override
	public List<Studio> getStudioList() {
		logger.info(new String());
		List<Studio> list = videoDao.getStudioList(); 
		Collections.sort(list);
		return list;
	}
	
	@Override
	public List<Studio> getStudioListOfVideoes(List<Video> videoList) {
		logger.info("size : " + videoList.size());
		List<Studio> studioList = new ArrayList<Studio>();
		for(Video video : videoList) {
			if(!studioList.contains(video.getStudio()))
				studioList.add(video.getStudio());
		}
		Collections.sort(studioList);
		return studioList;
	}

	@Override
	public Video getVideo(String opus) {
		logger.info(opus);
		return videoDao.getVideo(opus);
	}

	@Override
	public byte[] getVideoCoverByteArray(String opus, boolean isChrome) {
		logger.info(opus);
		if(webpMode && isChrome)
			return videoDao.getVideo(opus).getCoverWebpByteArray();
		else 
			return videoDao.getVideo(opus).getCoverByteArray();
	}

	@Override
	public File getVideoCoverFile(String opus, boolean isChrome) {
		logger.info(opus);
		if(webpMode && isChrome)
			return videoDao.getVideo(opus).getCoverWebpFile();
		else
			return videoDao.getVideo(opus).getCoverFile();
	}

	@Override
	public List<Video> getVideoList() {
		logger.info(new String());
		List<Video> list = videoDao.getVideoList(); 
		Collections.sort(list);
		return list;
	}

	@Override
	public void playVideo(String opus) {
		logger.info(opus);
		executeCommand(videoDao.getVideo(opus), Action.PLAY);
		videoDao.getVideo(opus).increasePlayCount();
		saveHistory(videoDao.getVideo(opus), Action.PLAY);
	}

	@Override
	public void rankVideo(String opus, int rank) {
		logger.info(opus + " : " + rank);
		videoDao.getVideo(opus).setRank(rank);
	}

	private void saveHistory(Video video, Action action) {
		logger.info(video.getOpus() + " : " + action);
		String files = null; 
		String currDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
	
		switch(action) {
			case PLAY :
				files = video.getVideoFileListPath();
				break;
			case OVERVIEW :
				files = video.getInfoFile().getName();
				break;
			case COVER :
				files = video.getCoverFilePath();
				break;
			case SUBTITLES :
				files = video.getSubtitlesFileListPath();
				break;
			case DELETE :
				files = video.toString();
				break;
			default:
				throw new IllegalStateException("Undefined Action : " + action.toString());
		}
		String historymsg = MessageFormat.format("{0}, {1}, {2},\"{3}\"{4}", 
				currDate, video.getOpus(), action, files, System.getProperty("line.separator"));
		
		logger.debug("save history - " + historymsg);
		if(action != Action.DELETE)
			video.addHistory(historymsg);
		try {
			FileUtils.writeStringToFile(getHistoryFile(), historymsg, VideoCore.FileEncoding, true);
			isChanged = true;
		} catch (IOException e) {
			logger.error(historymsg, e);
		}
	}

	@Override
	public void saveVideoOverview(String opus, String overViewText) {
		logger.info(opus + " : " + overViewText);
		videoDao.getVideo(opus).saveOverView(overViewText);
	}

	@Override
	public List<Video> searchVideo(VideoSearch search) {
		logger.info(search);
		List<Video> list = new ArrayList<Video>();
		for (Video video : videoDao.getVideoList()) {
			if ((VideoUtils.equals(video.getStudio().getName(), search.getSearchText()) 
					|| VideoUtils.equals(video.getOpus(), search.getSearchText()) 
					|| VideoUtils.containsName(video.getTitle(), search.getSearchText()) 
					|| VideoUtils.containsActress(video, search.getSearchText())) 
				&& (search.isNeverPlay() ? video.getPlayCount() == 0 : true)
				&& (search.isZeroRank()  ? video.getRank() == 0 : true)
				&& (search.isAddCond()   
						? ((search.isExistVideo() ? video.isExistVideoFileList() : !video.isExistVideoFileList())
							&& (search.isExistSubtitles() ? video.isExistSubtitlesFileList() : !video.isExistSubtitlesFileList())) 
						: true)) {
				video.setSortMethod(search.getSortMethod());
				list.add(video);
			}
		}
		if (search.isSortReverse())
			Collections.sort(list, Collections.reverseOrder());
		else
			Collections.sort(list);
		logger.info("found video length : " + list.size());
		return list;
	}
}

