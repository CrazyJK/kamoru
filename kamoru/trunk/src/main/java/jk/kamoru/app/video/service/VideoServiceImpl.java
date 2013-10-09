package jk.kamoru.app.video.service;

import java.io.File;
import java.io.IOException;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
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
import jk.kamoru.app.video.domain.ActressSort;
import jk.kamoru.app.video.domain.Sort;
import jk.kamoru.app.video.domain.Studio;
import jk.kamoru.app.video.domain.StudioSort;
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

		if(query == null || query.trim().length() == 0)
			return foundMapList;
		
		try {
			if(isChanged || historyFile == null) {
				historyList = FileUtils.readLines(getHistoryFile(), VideoCore.FILE_ENCODING);
				isChanged = false;
				logger.debug("read history.log size={}", historyList.size());
			}
			if(query == null || query.trim().length() == 0)
				return foundMapList;

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
			logger.debug("q={} foundLength={}", query, foundMapList.size());
			Collections.sort(foundMapList, new Comparator<Map<String, String>>(){

				@Override
				public int compare(Map<String, String> o1, Map<String, String> o2) {
					String thisStr = o1.get("date");
					String compStr = o2.get("date");

					String[] s = {thisStr, compStr};
					Arrays.sort(s);
					return s[0].equals(thisStr) ? 1 : -1;
				}
				
			});
			return foundMapList;
		} 
		catch (IOException e) {
			logger.error("history file read error", e);
			throw new VideoException("history file read error", e);
		}
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
		Collections.sort(foundMapList, new Comparator<Map<String, String>>() {

			@Override
			public int compare(Map<String, String> o1, Map<String, String> o2) {
				String thisStr = o1.get("opus");
				String compStr = o2.get("opus");

				String[] s = {thisStr, compStr};
				Arrays.sort(s);
				return s[0].equals(thisStr) ? 1 : -1;
			}});
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
	public List<Actress> getActressListInVideoList(List<Video> videoList) {
		logger.trace("size : {}", videoList.size());
		Map<String, Actress> actressMap = new TreeMap<String, Actress>();
		for(Video video : videoList) {
			for (Actress actress : video.getActressList()) {
				Actress actressInMap = actressMap.get(actress.getName());
				if (actressInMap == null) {
					actressInMap = videoDao.getActress(actress.getName());
					actressInMap.emptyVideo();
//					actressInMap = new Actress(actress.getName());
//					actressInMap.setMainBasePath(mainBasePath);
				}
				actressInMap.putVideo(video);
				actressMap.put(actress.getName(), actressInMap);
			}
		}
		List<Actress> list = new ArrayList<Actress>(actressMap.values());
		Collections.sort(list);
		logger.debug("found studio list size {}", list.size());
		return list;
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
	public List<Studio> getStudioListInVideoList(List<Video> videoList) {
		logger.trace("size : {}", videoList.size());
		Map<String, Studio> studioMap = new TreeMap<String, Studio>();
		for(Video video : videoList) {
			String studioName = video.getStudio().getName();
			Studio studio = studioMap.get(studioName);
			if (studio == null) {
				studio = videoDao.getStudio(studioName);
				studio.emptyVideo();
//				studio = new Studio(studioName);
//				studio.setMainBasePath(mainBasePath);
			}
			studio.putVideo(video);
			studioMap.put(studioName, studio);
		}
		List<Studio> list = new ArrayList<Studio>(studioMap.values());
		Collections.sort(list);
		logger.debug("found studio list size {}", list.size());
		return list;
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
				files = VideoUtils.toFileListToSimpleString(video.getFileAll());
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
			FileUtils.writeStringToFile(getHistoryFile(), historymsg, VideoCore.FILE_ENCODING, true);
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
						: true)
				&& (search.getSelectedStudio() == null ? true : search.getSelectedStudio().contains(video.getStudio().getName()))
				&& (search.getSelectedActress() == null ? true : VideoUtils.containsActress(video, search.getSelectedActress()))
				) 
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
	public Map<String, Long[]> groupByPath() {
		logger.trace("groupByPath");
		/*
		Map<String, String> map = new TreeMap<String, String>();
		long total = 0;
		for (String path : basePath) {
			File dir = new File(path);
			if (dir.exists()) {
				long size = FileUtils.sizeOfDirectory(dir);
				total += size;
				map.put(path, String.valueOf((int)(size / FileUtils.ONE_GB)) + " GB");
			}
			else {
				map.put(path, path + " does not exist");
			}
		}
		map.put("Total", String.valueOf((int)(total / FileUtils.ONE_GB)) + " GB");
		logger.debug("video group by path - {}", map);
		return map;
		*/
		Map<String, Long[]> pathMap = new TreeMap<String, Long[]>();
		Long[] total = new Long[]{0l, 0l};
		for (Video video : videoDao.getVideoList()) {
			String path = video.getDelegatePath();
			long length = video.length();
			Long[] data = pathMap.get(path);
			if (data == null) {
				data = new Long[]{0l, 0l};
			}
			total[0] += 1;
			total[1] += length;
			data[0] += 1;
			data[1] += length; 
			pathMap.put(path, data);
		}
		pathMap.put("Total", total);
		return pathMap;
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

	@Override
	public void arrangeVideo(String opus) {
		logger.trace(opus);
		videoDao.arrangeVideo(opus);
	}

	@Override
	public void moveVideo(String opus, String path) {
		logger.trace("{} move to {}", opus, path);
		videoDao.moveVideo(opus, path);
	}

	@Override
	public void reload() {
		logger.trace("reload");
		videoDao.reload();
	}

	@Override
	public void saveStudioInfo(String studio, Map<String, String> params) {
		logger.trace("name={}, params={}", studio, params);
		VideoUtils.saveFileFromMap(new File(mainBasePath, studio + VideoCore.EXT_STUDIO), params);
		videoDao.getStudio(studio).reloadInfo();
	}
	
	@Override
	public List<Actress> getActressList(final ActressSort sort) {
		logger.trace("sort={}", sort);
		List<Actress> list = videoDao.getActressList();
//		List<Actress> list = getActressListOfVideoes(videoDao.getVideoList());
		Collections.sort(list, new Comparator<Actress>(){

			@Override
			public int compare(Actress o1, Actress o2) {
				switch (sort) {
				case NAME:
					return StringUtils.compateTo(o1.getName(), o2.getName());
				case BIRTH:
					return StringUtils.compateTo(o2.getBirth(), o1.getBirth());
				case BODY:
					return StringUtils.compateTo(o2.getBodySize(), o1.getBodySize());
				case HEIGHT:
					return StringUtils.compateTo(o2.getHeight(), o1.getHeight());
				case DEBUT:
					return StringUtils.compateTo(o2.getDebut(), o1.getDebut());
				case VIDEO:
					return o2.getVideoList().size() - o1.getVideoList().size();
				default:
					return StringUtils.compateTo(o1.getName(), o2.getName());
				}
			}
		});
		return list;
	}

	@Override
	public List<Studio> getStudioList(final StudioSort sort) {
		logger.trace("sort={}", sort);
		List<Studio> list = videoDao.getStudioList();
//		List<Studio> list = getStudioListOfVideoes(videoDao.getVideoList());
		Collections.sort(list, new Comparator<Studio>(){

			@Override
			public int compare(Studio o1, Studio o2) {
				switch (sort) {
				case NAME:
					return StringUtils.compateTo(o1.getName(), o2.getName());
				case HOMEPAGE:
					return StringUtils.compateTo(o2.getHomepage(), o1.getHomepage());
				case COMPANY:
					return StringUtils.compateTo(o2.getCompanyName(), o1.getCompanyName());
				case VIDEO:
					return o2.getVideoList().size() - o1.getVideoList().size();
				default:
					return StringUtils.compateTo(o1.getName(), o2.getName());
				}
			}
		});
		return list;
	}

}