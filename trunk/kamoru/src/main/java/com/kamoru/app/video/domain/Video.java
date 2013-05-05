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

//	private String studio;
	private String opus;
	private String title;
//	private List<String> actressList;
	private String etcInfo;
	
	private List<File> videoFileList;
	private List<File> subtitlesFileList;
	private File coverFile;
	private File coverWebpFile;
	private byte[] coverByteArray;
	private File overviewFile;
	private File historyFile;
	private List<File> etcFileList;
	
	private Integer playCount;
	
	private final String DEFAULT_SORTMETHOD = "O";
	private String sortMethod = DEFAULT_SORTMETHOD;
	
	private Studio studio;
	private List<Actress> actressList;

	private byte[] coverWebpByteArray;
	
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
	public void putActress(Actress actress) {
		boolean found = false;
		for(Actress actressInList : this.actressList) {
			if(actressInList.getName().equalsIgnoreCase(actress.getName())) {
				found = true;
				actressInList = actress;
			}
		}
		if(!found)
			this.actressList.add(actress);
	}

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
		if("S".equals(sortMethod)) {
			thisStr = this.getStudio().getName();
			compStr = comp.getStudio().getName();
		} else if("O".equals(sortMethod)) {
			thisStr = this.getOpus();
			compStr = comp.getOpus();
		} else if("T".equals(sortMethod)) {
			thisStr = this.getTitle();
			compStr = comp.getTitle();
		} else if("A".equals(sortMethod)) {
			thisStr = this.getActress();
			compStr = comp.getActress();
		} else if("M".equals(sortMethod)) {
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
		return getVideoFileList() != null && getVideoFileList().size() > 0;  
	}
	public boolean isExistSubtitlesFileList() {
		return getSubtitlesFileList() != null && getSubtitlesFileList().size() > 0;
	}
	public boolean isExistCoverFile() {
		return getCoverFile() != null;
	}
	public boolean isExistCoverWebpFile() {
		return getCoverWebpFile() != null;
	}
	public boolean isExistOverviewFile() {
		return getOverviewFile() != null; 
	}
	public boolean isExistHistoryFile() {
		return getHistoryFile() != null;
	}
	public boolean isExistEtcFileList() {
		return getEtcFileList() != null && getEtcFileList().size() > 0;
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

	public void setCoverWebpFile(File coverWebpFile) {
		this.coverWebpFile = coverWebpFile;
		if(coverWebpFile.exists())
			this.coverWebpByteArray = readFileToByteArray(coverWebpFile);
	}

	public byte[] getCoverWebpByteArray() {
		if(this.coverWebpByteArray == null)
			this.coverWebpByteArray = readFileToByteArray(coverWebpFile);
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

	public byte[] getCoverByteArray() {
		if(this.coverByteArray == null)
			this.coverByteArray = readFileToByteArray(coverFile);
		return this.coverByteArray;
	}
	
	public File getCoverFile() {
		return coverFile;
	}

	public void setCoverFile(File coverFile) {
		this.coverFile = coverFile;
		if(coverFile.exists())
			this.coverByteArray = readFileToByteArray(coverFile);
	}
	
	private byte[] readFileToByteArray(File file) {
		if(file == null)
			return null;
		try {
			return FileUtils.readFileToByteArray(file);
		} catch (IOException e) {
			logger.error(e);
			return null;
		}
	}
	
	public File getOverviewFile() {
		return overviewFile;
	}

	public void setOverviewFile(File overviewFile) {
		this.overviewFile = overviewFile;
	}

	public File getHistoryFile() {
		return historyFile;
	}

	public void setHistoryFile(File historyFile) {
		this.historyFile = historyFile;
		try {
			List<String> lineList = FileUtils.readLines(historyFile, VideoCore.FileEncoding);
			for(String line : lineList) {
				String[] linePart = StringUtils.split(line, ",");
				if(linePart.length > 2 && linePart[2].trim().equalsIgnoreCase(Action.PLAY.toString())) {
					this.playCount++;
				}
			}
		} catch (IOException e) {
			logger.error(e);
		}
		if(this.playCount == 0)
			logger.info(this.getOpus() + " never play.");
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

	public void setSortMethod(String sortMethod) {
		this.sortMethod = sortMethod;
	}

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
		if(isExistOverviewFile()) {
			try {
				return FileUtils.readFileToString(getOverviewFile(), VideoCore.FileEncoding);
			}catch(IOException ioe){
				return "Error:" + ioe.getMessage();
			}
		} else {
			return "";
		}
	}
	
	public String getHistoryText() {
		if(isExistHistoryFile()) {
			try {
				return FileUtils.readFileToString(getHistoryFile(), VideoCore.FileEncoding);
			}catch(IOException ioe){
				return "Error:" + ioe.getMessage();
			}
		} else {
			return "";
		}
	}

	public void removeVideo() {
		for(File file : getFileAll())
			FileUtils.deleteQuietly(file);
	}

	public String getVideoDate() {
		if(this.isExistVideoFileList()) {
			return DateFormatUtils.format(this.videoFileList.get(0).lastModified(), "yyyy-MM-dd");
		} 
		else if(this.isExistCoverFile()) {
			return DateFormatUtils.format(this.coverFile.lastModified(), "yyyy-MM-dd");
		}
		else {
			return "";
		}
	}
}
