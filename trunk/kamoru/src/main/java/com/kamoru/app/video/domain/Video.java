package com.kamoru.app.video.domain;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import com.kamoru.app.video.VideoCore;
import com.kamoru.app.video.VideoException;
import com.kamoru.app.video.util.VideoUtils;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * AV Bean class<br>
 * include studio, opus, title, overview info and video, cover, subtitles, log file<br>
 * action play, random play, editing subtitles and overview  
 * @author kamoru
 *
 */
@Component
@Scope("prototype")
public class Video implements Comparable<Object>, Serializable {

	private static final long serialVersionUID = VideoCore.Serial_Version_UID;
	
	private static final Log logger = LogFactory.getLog(Video.class);

	private final Sort DEFAULT_SORTMETHOD = Sort.M;

	// TITLE
	private Studio studio;
	private String opus;
	private String title;
	private List<Actress> actressList;
	private String etcInfo;
	
	private List<File> videoFileList;
	private List<File> subtitlesFileList;
	private File coverFile;
	private File coverWebpFile;
	private byte[] coverByteArray;
	private byte[] coverWebpByteArray;
	private List<File> etcFileList;
	
	// INFO json properties
	private File infoFile; // json file
	private int rank; // ranking score
	private String overview; // overview text
	private List<String> historyList; // history list

	private Integer playCount;
	
	private Sort sortMethod = DEFAULT_SORTMETHOD;


	public Video() {
		videoFileList 		= new ArrayList<File>();
		subtitlesFileList 	= new ArrayList<File>();
		etcFileList 		= new ArrayList<File>();
		
		actressList = new ArrayList<Actress>();
		historyList = new ArrayList<String>();

		playCount 	= 0;
		rank 		= 0;
		overview 	= "";
	}
	
	@Override
	public int compareTo(Object o) {
		Video comp = (Video)o;
		String thisStr = null;
		String compStr = null;
		if (sortMethod == Sort.S) {
			thisStr = this.getStudio().getName();
			compStr = comp.getStudio().getName();
		} else if (sortMethod == Sort.O) {
			thisStr = this.getOpus();
			compStr = comp.getOpus();
		} else if (sortMethod == Sort.T) {
			thisStr = this.getTitle();
			compStr = comp.getTitle();
		} else if (sortMethod == Sort.A) {
			thisStr = this.getActress();
			compStr = comp.getActress();
		} else if (sortMethod == Sort.M) {
			thisStr = String.valueOf(
					this.isExistVideoFileList() ? this.getVideoFileList().get(0).lastModified() : 
						(this.isExistCoverFile() ? this.getCoverFile().lastModified() : 0));
			compStr = String.valueOf(
					comp.isExistVideoFileList() ? comp.getVideoFileList().get(0).lastModified() : 
						(comp.isExistCoverFile() ? comp.getCoverFile().lastModified() : 0));
		} else if (sortMethod == Sort.P) {
			return this.getPlayCount() - comp.getPlayCount();
		} else if (sortMethod == Sort.R) {
			return this.getRank() - comp.getRank();
		}
		String[] s = {thisStr, compStr};
//		logger.info(ArrayUtils.toString(s));
		Arrays.sort(s);
		return s[0].equals(thisStr) ? -1 : 1;
	}

	/**
	 * actress 이름이 있는지 확인
	 * @param actressName
	 * @return
	 */
	public boolean containsActress(String actressName) {
		for(Actress actress : actressList)
			if(actress.contains(actressName))
				return true;
		return false;
	}

	/**
	 * 전체 배우 이름을 컴마(,)로 구분한 문자로 반환
	 * @return
	 */
	public String getActress() {
		List<String> list = new ArrayList<String>();
		for(Actress actress : actressList) {
			list.add(actress.getName());
		}
		return VideoUtils.toListToSimpleString(list);
	}
	public List<Actress> getActressList() {
		Collections.sort(actressList);
		return actressList;
	}
	/**
	 * cover file의 byte[] 반환. null이면 다시 읽기 시도한다.
	 * @return
	 */
	public byte[] getCoverByteArray() {
		if(this.coverByteArray == null)
			this.coverByteArray = VideoUtils.readFileToByteArray(coverFile);
		return this.coverByteArray;
	}

	public File getCoverFile() {
		return coverFile;
	}
	
	public String getCoverFilePath() {
		if(isExistCoverFile())
			return getCoverFile().getAbsolutePath();
		return null;
	}
	
	/**
	 * webp형식의 cover file의 byte[] 반환. null이면 다시 한번 읽기 시도한다.
	 * @return
	 */
	public byte[] getCoverWebpByteArray() {
		if(this.coverWebpByteArray == null)
			this.coverWebpByteArray = VideoUtils.readFileToByteArray(coverWebpFile);
		return this.coverWebpByteArray;
	}
	public File getCoverWebpFile() {
		return coverWebpFile;
	}
	public String getCoverWebpFilePath() {
		if (this.isExistCoverWebpFile())
			return this.getCoverWebpFile().getAbsolutePath();
		return "";
	}
	/**
	 * video 대표 파일
	 * @return
	 */
	private File getDelegateFile() {
		if(this.isExistVideoFileList()) {
			return this.getVideoFileList().get(0);
		} else if(this.isExistCoverFile()) {
			return this.getCoverFile();
		} else if(this.isExistSubtitlesFileList()) {
			return this.getSubtitlesFileList().get(0);
		} else if(this.isExistEtcFileList()) {
			return this.getEtcFileList().get(0);
		} else if (this.infoFile != null) {
			return this.infoFile;
		} else {
			throw new VideoException("No delegate video file : " + this.getOpus());
		}
	}
	public List<File> getEtcFileList() {
		return etcFileList;
	}
	public String getEtcFileListPath() {
		if(isExistEtcFileList())
			return VideoUtils.arrayToString(getEtcFileList());
		return null;
	}
	public String getEtcInfo() {
		return etcInfo;
	}
	/**
	 * 모든 파일 list. null도 포함 되어 있을수 있음
	 * @return
	 */
	public List<File> getFileAll() {
		List<File> list = new ArrayList<File>();
		list.addAll(getVideoFileList());
		list.addAll(getSubtitlesFileList());
		list.addAll(getEtcFileList());
		list.add(this.coverFile);
		list.add(this.coverWebpFile);
		list.add(this.infoFile);
		return list;
	}
	
	/**
	 * video 대표 파일 이름
	 * @return
	 */
	private String getFilename() {
		return this.getDelegateFile().getName();
	}
	/**
	 * video 대표 파일 이름. 확장자를 뺀 대표이름
	 * @return
	 */
	private String getNameWithoutSuffix() {
		return VideoUtils.getName(getFilename());
	}
	public String getOpus() {
		return opus;
	}
	public String getOverviewText() {
		return this.overview;
	}

	/**
	 * video 대표 폴더 경로. video > cover > overview > subtitles > etc 순으로 찾는다.
	 * @return
	 */
	private String getDelegatePath() {
		return this.getDelegateFile().getParent();
	}
	public Integer getPlayCount() {
		return playCount;
	}

	// getter & setter method
	
	public Studio getStudio() {
		return studio;
	}

	public List<File> getSubtitlesFileList() {
		return subtitlesFileList;
	}

	public String getSubtitlesFileListPath() {
		if(isExistSubtitlesFileList())
			return VideoUtils.arrayToString(getSubtitlesFileList());
		return null;
	}

	public String[] getSubtitlesFileListPathArray() {
		if(isExistSubtitlesFileList()) {
			String[] filePathes = new String[getSubtitlesFileList().size()];
			for(int i=0; i<filePathes.length; i++)
				filePathes[i] = getSubtitlesFileList().get(i).getAbsolutePath();
			return filePathes;
		}
		return null;
	}
	
	public String getTitle() {
		return title;
	}

	/**
	 * video의 대표 날자. video > cover > overview > subtitles > etc 순으로 찾는다.
	 * @return
	 */
	public String getVideoDate() {
		return DateFormatUtils.format(this.getDelegateFile().lastModified(), "yyyy-MM-dd");
	}

	public List<File> getVideoFileList() {
		return videoFileList;
	}

	// file path method
	public String getVideoFileListPath() {
		if(isExistVideoFileList()) 
			return VideoUtils.arrayToString(getVideoFileList()); 
		return null;
	}
	
	public String[] getVideoFileListPathArray() {
		if(isExistVideoFileList()) {
			String[] filePathes = new String[getVideoFileList().size()];
			for(int i=0; i<filePathes.length; i++)
				filePathes[i] = getVideoFileList().get(i).getAbsolutePath();
			return filePathes;
		}
		return null;
	}

	public void increasePlayCount() {
		this.playCount++;
	}
	
	public boolean isExistCoverFile() {
		return this.coverFile != null;
	}

	public boolean isExistCoverWebpFile() {
		return this.coverWebpFile != null;
	}

	public boolean isExistEtcFileList() {
		return this.etcFileList != null && this.etcFileList.size() > 0;
	}

	public boolean isExistSubtitlesFileList() {
		return this.subtitlesFileList != null && this.subtitlesFileList.size() > 0;
	}
	// isExist file method
	public boolean isExistVideoFileList() {
		return this.videoFileList != null && this.videoFileList.size() > 0;  
	}

	public boolean isExistOverview() {
		return this.overview != null && this.overview.trim().length() > 0;
	}
	
	/**
	 * actress를 추가한다. 기존actress가 발견되면 ref를 갱신.
	 * @param actress
	 */
	public void putActress(Actress actress) {
		boolean notFound = true;
		for(Actress actressInList : this.actressList) {
			if(actressInList.getName().equalsIgnoreCase(actress.getName())) {
				notFound = false;
				actressInList = actress;
				break;
			}
		}
		if(notFound)
			this.actressList.add(actress);
	}

	/**
	 * 모든 파일을 지운다.
	 */
	public void removeVideo() {
		for(File file : getFileAll())
			if(file != null && file.exists()) 
				if(FileUtils.deleteQuietly(file))
					logger.debug(file.getAbsoluteFile());
				else
					logger.info("delete fail : " + file.getAbsoluteFile());
	}

	public void saveOverView(String overViewText) {
		logger.debug(opus + " [" + overViewText + "]");
		this.overview = overViewText;
		this.saveInfo();
	}

	public void setActressList(List<Actress> actressList) {
		this.actressList = actressList;
	}

	/**
	 * cover 파일 저장. 파일이 존재하면 coverByteArray도 함께 저장한다.
	 * @param coverFile
	 */
	public void setCoverFile(File coverFile) {
		this.coverFile = coverFile;
		if(coverFile.exists())
			this.coverByteArray = VideoUtils.readFileToByteArray(coverFile);
	}

	/**
	 * webp형식의 cover file 저장. 파일이 존재하면 byte[]도 같이 저장한다.
	 * @param coverWebpFile
	 */
	public void setCoverWebpFile(File coverWebpFile) {
		this.coverWebpFile = coverWebpFile;
		if(coverWebpFile.exists())
			this.coverWebpByteArray = VideoUtils.readFileToByteArray(coverWebpFile);
	}

	public void setEtcFile(File file) {
		this.etcFileList.add(file);		
	}

	public void setEtcFileList(List<File> etcFileList) {
		this.etcFileList = etcFileList;
	}

	public void setEtcInfo(String etcInfo) {
		this.etcInfo = etcInfo;
	}

	public void setOpus(String opus) {
		this.opus = opus;
	}

	public void setPlayCount(Integer playCount) {
		this.playCount = playCount;
	}
	
	public void setSortMethod(Sort sortMethod) {
		this.sortMethod = sortMethod;
	}

	public void setStudio(Studio studio) {
		this.studio = studio;
	}

	public void setSubtitlesFile(File file) {
		this.subtitlesFileList.add(file);		
	}
	
	public void setSubtitlesFileList(List<File> subtitlesFileList) {
		this.subtitlesFileList = subtitlesFileList;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	public void setVideoFile(File file) {
		this.videoFileList.add(file);
	}

	public void setVideoFileList(List<File> videoFileList) {
		this.videoFileList = videoFileList;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("video : ").append(this.getVideoFileListPath()).append(",");
		sb.append("subtitles : ").append(this.getSubtitlesFileListPath()).append(",");
		sb.append("cover : ").append(this.getCoverFilePath()).append(",");
		sb.append("info : ").append(this.getInfoFilePath()).append(",");
		sb.append("etc : ").append(this.getEtcFileListPath());
		return sb.toString();
	}

	public void move(String WATCHED_PATH) {
		for(File file : getFileAll())
			if(file != null && file.exists())
				try {
					FileUtils.moveFileToDirectory(file, new File(WATCHED_PATH), true);
				} catch (IOException e) {
					logger.error(e);
					e.printStackTrace();
				}
	}

	public void setRank(int rank) {
		logger.info(this.getOpus() + " rank is " + rank);
		this.rank = rank;
		this.saveInfo();
	}
	
	public int getRank() {
		return rank;
	}

	public void setInfoFile(File file) {
		this.infoFile = file;
		
		JSONObject json = null;
		try {
			json = JSONObject.fromObject(FileUtils.readFileToString(infoFile, VideoCore.FileEncoding));
		} catch (IOException e1) {
			logger.error(e1);
			e1.printStackTrace();
		}
		JSONObject infoData = json.getJSONObject("info");

		String opus = infoData.getString("opus");
		if (!this.opus.equalsIgnoreCase(opus)) 
			throw new VideoException("invalid info file");
		String rank = infoData.getString("rank");
		this.rank = NumberUtils.toInt(rank, 0);
		
		this.overview = infoData.getString("overview");

		JSONArray hisArray = infoData.getJSONArray("history");
		this.playCount = 0;
		for(int i=0, e=hisArray.size(); i<e; i++){
			String line = hisArray.getString(i);
			historyList.add(line);
			String[] linePart = StringUtils.split(line, ",");
			if(linePart.length > 2 && linePart[2].trim().equalsIgnoreCase(Action.PLAY.toString())) {
				this.playCount++;
			}
		}

	}

	public File getInfoFile() {
		if(this.infoFile == null) {
			this.infoFile = new File(this.getDelegatePath(), this.getNameWithoutSuffix() + ".info");
		}
		return infoFile;
	}
	public String getInfoFilePath() {
		if (infoFile != null)
			return this.infoFile.getAbsolutePath();
		return "";
	}
	private void saveInfo() {
		JSONObject info = new JSONObject();
		info.put("opus", this.opus);
		info.put("rank", this.rank);
		info.put("overview",  this.overview);

		JSONArray his = new JSONArray();
		his.addAll(historyList);
		
		info.put("history", his);
		JSONObject root = new JSONObject();
		root.put("info", info);
		
		File file = this.getInfoFile();
		try {
			FileUtils.writeStringToFile(file, root.toString(), VideoCore.FileEncoding);
		} catch (IOException e) {
			logger.error("info save error", e);
		}
		logger.info(opus + " " + root.toString());
	}

	public void addHistory(String historymsg) {
		historyList.add(historymsg);
		this.saveInfo();
	}
	
	public String getHistoryText() {
		StringBuilder sb = new StringBuilder();
		for (String his : historyList) {
			sb.append(his).append(System.getProperty("line.separator"));
		}
		return sb.toString();
	}
}
