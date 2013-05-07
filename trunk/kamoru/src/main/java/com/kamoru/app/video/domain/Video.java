package com.kamoru.app.video.domain;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import com.kamoru.app.video.VideoCore;
import com.kamoru.app.video.VideoException;
import com.kamoru.app.video.util.VideoUtils;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
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
	
	protected static final Log logger = LogFactory.getLog(Video.class);

	private final Sort DEFAULT_SORTMETHOD = Sort.M;

	private Studio studio;
	private String opus;
	private List<Actress> actressList;
	private String title;
	private String etcInfo;
	
	private List<File> videoFileList;
	private List<File> subtitlesFileList;
	private File coverFile;
	private File coverWebpFile;
	private byte[] coverByteArray;
	private byte[] coverWebpByteArray;
	private File overviewFile;
	private File historyFile;
	private List<File> etcFileList;
	
	private Integer playCount;
	
	private Sort sortMethod = DEFAULT_SORTMETHOD;
	
	
	public Video() {
		videoFileList = new ArrayList<File>();
		subtitlesFileList = new ArrayList<File>();
		etcFileList = new ArrayList<File>();
		actressList = new ArrayList<Actress>();
		playCount = 0;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("video : ").append(this.getVideoFileListPath()).append(",");
		sb.append("subtitles : ").append(this.getSubtitlesFileListPath()).append(",");
		sb.append("cover : ").append(this.getCoverFilePath()).append(",");
		sb.append("overview : ").append(this.getOverviewFilePath()).append(",");
		sb.append("history : ").append(this.getHistoryFilePath()).append(",");
		sb.append("etc : ").append(this.getEtcFileListPath());
		return sb.toString();
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
	public void setActressList(List<Actress> actressList) {
		this.actressList = actressList;
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
		}
		String[] s = {thisStr, compStr};
//		logger.info(ArrayUtils.toString(s));
		Arrays.sort(s);
		return s[0].equals(thisStr) ? -1 : 1;
	}
	
	// isExist file method
	public boolean isExistVideoFileList() {
		return this.videoFileList != null && this.videoFileList.size() > 0;  
	}
	public boolean isExistSubtitlesFileList() {
		return this.subtitlesFileList != null && this.subtitlesFileList.size() > 0;
	}
	public boolean isExistCoverFile() {
		return this.coverFile != null;
	}
	public boolean isExistCoverWebpFile() {
		return this.coverWebpFile != null;
	}
	public boolean isExistOverviewFile() {
		return this.overviewFile != null; 
	}
	public boolean isExistHistoryFile() {
		return this.historyFile != null;
	}
	public boolean isExistEtcFileList() {
		return this.etcFileList != null && this.etcFileList.size() > 0;
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
	public String getCoverFilePath() {
		if(isExistCoverFile())
			return getCoverFile().getAbsolutePath();
		return null;
	}
	public String getOverviewFilePath() {
		if(isExistOverviewFile())
			return getOverviewFile().getAbsolutePath();
		return null;
	}
	public String getHistoryFilePath() {
		if(isExistHistoryFile())
			return getHistoryFile().getAbsolutePath();
		return null;
	}
	public String getEtcFileListPath() {
		if(isExistEtcFileList())
			return VideoUtils.arrayToString(getEtcFileList());
		return null;
	}
	public Integer getPlayCount() {
		return playCount;
	}

	public void setPlayCount(Integer playCount) {
		this.playCount = playCount;
	}
	public void increasePlayCount() {
		this.playCount++;
	}

	// getter & setter method
	
	public List<File> getVideoFileList() {
		return videoFileList;
	}

	public File getCoverWebpFile() {
		return coverWebpFile;
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

	/**
	 * webp형식의 cover file의 byte[] 반환. null이면 다시 한번 읽기 시도한다.
	 * @return
	 */
	public byte[] getCoverWebpByteArray() {
		if(this.coverWebpByteArray == null)
			this.coverWebpByteArray = VideoUtils.readFileToByteArray(coverWebpFile);
		return this.coverWebpByteArray;
	}
	
	public void setVideoFileList(List<File> videoFileList) {
		this.videoFileList = videoFileList;
	}

	public List<File> getSubtitlesFileList() {
		return subtitlesFileList;
	}

	public void setSubtitlesFileList(List<File> subtitlesFileList) {
		this.subtitlesFileList = subtitlesFileList;
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
	 * overview file. 없으면 대표이름으로 만들어서 반환
	 * @return
	 */
	public File getOverviewFile() {
		if(overviewFile == null)
			overviewFile = new File(getPath(), getNameWithoutSuffix() + ".txt");
		return overviewFile;
	}

	public void setOverviewFile(File overviewFile) {
		this.overviewFile = overviewFile;
	}

	/**
	 * history file. 없을경우 대표이름으로 만들어서 반환
	 * @return
	 */
	public File getHistoryFile() {
		if(historyFile == null)
			historyFile = new File(getPath(), getNameWithoutSuffix() + ".log");
		return historyFile;
	}

	/**
	 * history 파일 설정. 파일이 존재하면 읽어서 playCount도 설정
	 * @param historyFile
	 */
	public void setHistoryFile(File historyFile) {
		this.historyFile = historyFile;
		try {
			if(!historyFile.exists())
				return;
			for(String line : FileUtils.readLines(historyFile, VideoCore.FileEncoding)) {
				String[] linePart = StringUtils.split(line, ",");
				if(linePart.length > 2 && linePart[2].trim().equalsIgnoreCase(Action.PLAY.toString())) {
					this.playCount++;
				}
			}
		} catch (IOException e) {
			logger.error(e);
		}
	}

	public List<File> getEtcFileList() {
		return etcFileList;
	}

	public void setEtcFileList(List<File> etcFileList) {
		this.etcFileList = etcFileList;
	}
	public Studio getStudio() {
		return studio;
	}

	public void setStudio(Studio studio) {
		this.studio = studio;
	}

	public String getOpus() {
		return opus;
	}

	public void setOpus(String opus) {
		this.opus = opus;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getEtcInfo() {
		return etcInfo;
	}

	public void setEtcInfo(String etcInfo) {
		this.etcInfo = etcInfo;
	}

	public void setVideoFile(File file) {
		this.videoFileList.add(file);
	}

	public void setSubtitlesFile(File file) {
		this.subtitlesFileList.add(file);		
	}

	public void setEtcFile(File file) {
		this.etcFileList.add(file);		
	}

	public void setSortMethod(Sort sortMethod) {
		this.sortMethod = sortMethod;
	}

	/**
	 * 모든 파일 list. null도 포함 되어 있을수 있음
	 * @return
	 */
	public List<File> getFileAll() {
		List<File> list = new ArrayList<File>();
		list.addAll(getVideoFileList());
		list.addAll(getSubtitlesFileList());
		list.add(getCoverFile());
		list.add(getHistoryFile());
		list.add(getOverviewFile());
		list.addAll(getEtcFileList());
		return list;
	}
	
	public String getOverviewText() {
		return VideoUtils.readFileToString(getOverviewFile());
	}
	
	public String getHistoryText() {
		return VideoUtils.readFileToString(getHistoryFile());
	}

	/**
	 * 모든 파일을 지운다.
	 */
	public void removeVideo() {
		for(File file : getFileAll())
			if(file != null) 
				if(FileUtils.deleteQuietly(file))
					logger.debug(file.getAbsoluteFile());
				else
					logger.info("delete fail : " + file.getAbsoluteFile());
	}

	/**
	 * video의 대표 날자. video > cover > overview > subtitles > etc 순으로 찾는다.
	 * @return
	 */
	public String getVideoDate() {
		return DateFormatUtils.format(this.getDelegateFile().lastModified(), "yyyy-MM-dd");
	}
	
	/**
	 * video 대표 폴더 경로. video > cover > overview > subtitles > etc 순으로 찾는다.
	 * @return
	 */
	private String getPath() {
		return this.getDelegateFile().getParent();
	}
	
	/**
	 * video 대표 파일 이름. 확장자를 뺀 대표이름
	 * @return
	 */
	private String getNameWithoutSuffix() {
		return VideoUtils.getName(getFilename());
	}
	/**
	 * video 대표 파일 이름
	 * @return
	 */
	private String getFilename() {
		return this.getDelegateFile().getName();
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
		} else if(this.isExistOverviewFile()) {
			return this.getOverviewFile();
		} else if(this.isExistSubtitlesFileList()) {
			return this.getSubtitlesFileList().get(0);
		} else if(this.isExistEtcFileList()) {
			return this.getEtcFileList().get(0);
		} else if(this.isExistHistoryFile()) {
			return this.getHistoryFile();
		} else {
			throw new VideoException("No delegate video file : " + this.getOpus());
		}
	}

	public void saveOverView(String overViewText) {
		logger.debug(opus + " [" + overViewText + "]");
		try {
			FileUtils.writeStringToFile(getOverviewFile(), overViewText, VideoCore.FileEncoding);
		} catch (IOException e) {
			logger.error("save overview error", e);
			throw new VideoException("save overview error", e);
		}
	}
	
}
