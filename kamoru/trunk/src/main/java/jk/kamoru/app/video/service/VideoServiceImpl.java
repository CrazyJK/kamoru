package jk.kamoru.app.video.service;

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
import java.util.TreeMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import jk.kamoru.app.video.VideoCore;
import jk.kamoru.app.video.VideoException;
import jk.kamoru.app.video.dao.VideoDao;
import jk.kamoru.app.video.domain.Action;
import jk.kamoru.app.video.domain.Actress;
import jk.kamoru.app.video.domain.Sort;
import jk.kamoru.app.video.domain.Studio;
import jk.kamoru.app.video.domain.Video;
import jk.kamoru.app.video.domain.VideoSearch;
import jk.kamoru.app.video.util.VideoUtils;
import jk.kamoru.util.ArrayUtils;
import jk.kamoru.util.FileUtils;
import jk.kamoru.util.RuntimeUtils;
import jk.kamoru.util.StringUtils;

@Service
public class VideoServiceImpl implements VideoService {
	protected static final Logger logger = LoggerFactory.getLogger(VideoServiceImpl.class);

	private byte[] defaultCoverFileBytes;
	
	@Value("#{videoProp['defaultCoverFilePath']}") 	private String defaultCoverFilePath;
	@Value("#{videoProp['editor']}") 				private String editor;
	@Value("#{videoProp['mainBasePath']}") 			private String mainBasePath;
	@Value("#{videoProp['player']}") 				private String player;
	@Value("#{videoProp['webp.mode']}") 			private boolean webpMode;

	@Value("#{videoProp['basePath']}") 				private String[] basePath;
	
	@Autowired private VideoDao videoDao;

	private File historyFile;
	private List<String> historyList;
	
	private static boolean isChanged;

	public VideoServiceImpl() {
		isChanged = true;
	}
	
	@Override
	public void deleteVideo(String opus) {
		logger.trace(opus);
		saveHistory(getVideo(opus), Action.DELETE);
		videoDao.deleteVideo(opus);
	}

	@Override
	public void editVideoSubtitles(String opus) {
		logger.trace(opus);
		executeCommand(getVideo(opus), Action.SUBTITLES);
	}

	@Async
	private void executeCommand(Video video, Action action) {
		logger.trace("{} : {}", video.getOpus(), action);
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
				throw new VideoException("Unknown Action");
		}
		if(argumentsArray == null)
			throw new RuntimeException("No arguments");
		
		String[] cmdArray = ArrayUtils.addAll(new String[]{command}, argumentsArray);
		logger.debug("exec command - {} {}", command, argumentsArray);
		RuntimeUtils.exec(cmdArray);
		
	}

	@Override
	public List<Map<String, String>> findHistory(String query) {
		logger.trace(query);
		List<Map<String, String>> foundMapList = new ArrayList<Map<String, String>>();

//		if(query == null || query.trim().length() == 0)
//			return foundMapList;
		
		try {
			if(isChanged || historyFile == null) {
				historyList = FileUtils.readLines(getHistoryFile(), VideoCore.FileEncoding);
				isChanged = false;
				logger.debug("read history.log size={}", historyList.size());
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
			logger.error("history file read error", e);
		}
		logger.debug("q={} foundLength={}", query, foundMapList.size());
		return foundMapList;
	}

	@Override
	public List<Map<String, String>> findVideoList(String query) {
		logger.trace(query);
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
		logger.debug("q={} foundLength={}", query, foundMapList.size());
		return foundMapList;
	}

	@Override
	public Actress getActress(String actressName) {
		logger.trace(actressName);
		return videoDao.getActress(actressName);
	}

	@Override
	public List<Actress> getActressList() {
		logger.trace("");
		List<Actress> list = videoDao.getActressList();
		Collections.sort(list);
		return list;
	}

	@Override
	public List<Actress> getActressListOfVideoes(List<Video> videoList) {
		logger.trace("size : {}", videoList.size());
		List<Actress> actressList = new ArrayList<Actress>();
		for(Video video : videoList) {
			for(Actress actress : video.getActressList()) {
				if(!actressList.contains(actress))
					actressList.add(actress);
			}
		}
		Collections.sort(actressList);
		logger.debug("found actress list size {}", actressList.size());
		return actressList;
	}

	@Override
	public byte[] getDefaultCoverFileByteArray() {
		logger.trace("getDefaultCoverFileByteArray");
		if(defaultCoverFileBytes == null)
			try {
				defaultCoverFileBytes = FileUtils.readFileToByteArray(new File(defaultCoverFilePath));
			} catch (IOException e) {
				logger.error("cover file byte array read fail", e);
			}
		return defaultCoverFileBytes;
	}
	
	private File getHistoryFile() {
		if(historyFile == null)
			historyFile = new File(mainBasePath, "history.log");
		logger.debug("history file is {}", historyFile.getAbsolutePath());
		return historyFile;
	}

	@Override
	public Studio getStudio(String studioName) {
		logger.trace(studioName);
		return videoDao.getStudio(studioName);
	}

	@Override
	public List<Studio> getStudioList() {
		logger.trace("getStudioList");
		List<Studio> list = videoDao.getStudioList(); 
		Collections.sort(list);
		return list;
	}
	
	@Override
	public List<Studio> getStudioListOfVideoes(List<Video> videoList) {
		logger.trace("size : {}", videoList.size());
		List<Studio> studioList = new ArrayList<Studio>();
		for(Video video : videoList) {
			if(!studioList.contains(video.getStudio()))
				studioList.add(video.getStudio());
		}
		Collections.sort(studioList);
		logger.debug("found studio list size {}", studioList.size());
		return studioList;
	}

	@Override
	public Video getVideo(String opus) {
		logger.trace(opus);
		return videoDao.getVideo(opus);
	}

	@Override
	public byte[] getVideoCoverByteArray(String opus, boolean isChrome) {
		logger.trace(opus);
		if(webpMode && isChrome)
			return videoDao.getVideo(opus).getCoverWebpByteArray();
		else 
			return videoDao.getVideo(opus).getCoverByteArray();
	}

	@Override
	public File getVideoCoverFile(String opus, boolean isChrome) {
		logger.trace(opus);
		if(webpMode && isChrome)
			return videoDao.getVideo(opus).getCoverWebpFile();
		else
			return videoDao.getVideo(opus).getCoverFile();
	}

	@Override
	public List<Video> getVideoList() {
		logger.trace("getVideoList");
		List<Video> list = videoDao.getVideoList(); 
		Collections.sort(list);
		return list;
	}

	@Override
	public void playVideo(String opus) {
		logger.trace(opus);
		executeCommand(videoDao.getVideo(opus), Action.PLAY);
		videoDao.getVideo(opus).increasePlayCount();
		saveHistory(videoDao.getVideo(opus), Action.PLAY);
	}

	@Override
	public void rankVideo(String opus, int rank) {
		logger.trace("opus={} : rank={}", opus, rank);
		videoDao.getVideo(opus).setRank(rank);
	}

	private void saveHistory(Video video, Action action) {
		logger.trace("opus={} : action={}", video.getOpus(), action);
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
		
		logger.debug("save history - {}", historymsg);
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
		logger.trace("opus={} : text={}", opus, overViewText);
		videoDao.getVideo(opus).saveOverView(overViewText);
	}

	@Override
	public List<Video> searchVideo(VideoSearch search) {
		logger.trace("{}", search);
		if (search.isOldVideo()) {
			search.setSortMethod(Sort.M);
			search.setSortReverse(false);
		}
		List<Video> foundList = new ArrayList<Video>();
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
						: true)) 
			{
				video.setSortMethod(search.getSortMethod());
				foundList.add(video);
			}
		}
		if (search.isSortReverse())
			Collections.sort(foundList, Collections.reverseOrder());
		else
			Collections.sort(foundList);

		logger.debug("found video list size {}", foundList.size());

		if (search.isOldVideo() && foundList.size() > 9) {
			return foundList.subList(0, 10);
		}
		else {
			return foundList;
		}
	}
	
	@Override
	public Map<String, String> groupByPath() {
		logger.trace("groupByPath");
		Map<String, String> map = new TreeMap<String, String>();
		for (String path : basePath) {
			File dir = new File(path);
			if (dir.exists()) {
				int size = (int) (FileUtils.sizeOfDirectory(dir) / FileUtils.ONE_GB);
				map.put(path, String.valueOf(size) + " GB");
			}
			else {
				map.put(path, path + " does not exist");
			}
		}
		logger.debug("video group by path - {}", map);
		return map;
	}

	@Override
	public void saveActressInfo(String name, Map<String, String> params) {
		logger.trace("name={}, params={}", name, params);
		VideoUtils.saveFileFromMap(new File(mainBasePath, name + VideoCore.EXT_ACTRESS), params);
		videoDao.getActress(name).reloadInfo();
	}

	@Override
	public Map<String, List<Video>> groupByDate() {
		logger.trace("groupByDate");
		Map<String, List<Video>> map = new TreeMap<String, List<Video>>();
		for (Video video : videoDao.getVideoList()) {
			String yyyyMM = StringUtils.substringBeforeLast(video.getVideoDate(), "-");
			if (map.containsKey(yyyyMM)) {
				map.get(yyyyMM).add(video);
			}
			else {
				List<Video> videoList = new ArrayList<Video>();
				videoList.add(video);
				map.put(yyyyMM, videoList);
			}
		}
		logger.debug("video group by date - {}", map);
		return map;
	}

	@Override
	public Map<Integer, List<Video>> groupByRank() {
		logger.trace("groupByRank");
		Map<Integer, List<Video>> map = new TreeMap<Integer, List<Video>>();
		for (Video video : videoDao.getVideoList()) {
			Integer rank = video.getRank();
			if (map.containsKey(rank)) {
				map.get(rank).add(video);
			}
			else {
				List<Video> videoList = new ArrayList<Video>();
				videoList.add(video);
				map.put(rank, videoList);
			}
		}
		logger.debug("video group by rank - {}", map);
		return map;
	}

	@Override
	public Map<Integer, List<Video>> groupByPlay() {
		logger.trace("groupByPlay");
		Map<Integer, List<Video>> map = new TreeMap<Integer, List<Video>>();
		for (Video video : videoDao.getVideoList()) {
			Integer play = video.getPlayCount();
			if (map.containsKey(play)) {
				map.get(play).add(video);
			}
			else {
				List<Video> videoList = new ArrayList<Video>();
				videoList.add(video);
				map.put(play, videoList);
			}
		}
		logger.debug("video group by play - {}", map);
		return map;
	}

}