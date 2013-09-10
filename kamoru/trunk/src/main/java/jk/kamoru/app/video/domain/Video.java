package jk.kamoru.app.video.domain;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.io.FileExistsException;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import jk.kamoru.app.video.VideoCore;
import jk.kamoru.app.video.VideoException;
import jk.kamoru.app.video.util.VideoUtils;
import jk.kamoru.util.ArrayUtils;
import jk.kamoru.util.FileUtils;
import jk.kamoru.util.StringUtils;

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

	private static final Logger logger = LoggerFactory.getLogger(Video.class);
	
	private static final long serialVersionUID = VideoCore.Serial_Version_UID;

	private static Sort sortMethod = VideoCore.DEFAULT_SORTMETHOD;
	
	@Value("#{videoProp['server.base.url']}") private String baseurl;
	
	private List<Actress> actressList;
	private File coverFile;
	private File coverWebpFile;
	private List<File> etcFileList;
	private String etcInfo;
	private List<String> historyList; // history list
	private File infoFile; // json file
	private String opus;
	private String overview; // overview text
	private Integer playCount;
	private int rank; // ranking score
	private Studio studio;
	private List<File> subtitlesFileList;
	private String title;
	private List<File> videoFileList;

	private String releaseDate;


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
	
	/**
	 * Action history를 info 파일에 저장. 
	 * @param historymsg
	 */
	public void addHistory(String historymsg) {
		logger.trace(historymsg);
		historyList.add(historymsg);
		this.saveInfo();
	}

	/**
	 * 비디오 파일이 있으면, 나머지 파일을 같은 위치에 모은다.
	 */
	public void arrange() {
		logger.trace(opus);
		if (this.isExistVideoFileList()) {
			move(this.getDelegatePath());
		}
	}

	@Override
	public int compareTo(Object o) {
		Video comp = (Video)o;
		String thisStr = "";
		String compStr = "";
		
		switch(sortMethod) {
		case S:
			thisStr = this.getStudio().getName();
			compStr = comp.getStudio().getName();
			break;
		case O:
			thisStr = this.getOpus();
			compStr = comp.getOpus();
			break;
		case T:
			thisStr = this.getTitle();
			compStr = comp.getTitle();
			break;
		case A:
			thisStr = this.getActress();
			compStr = comp.getActress();
			break;
		case M:
			thisStr = String.valueOf(this.getDelegateFile() != null ? this.getDelegateFile().lastModified() : 0l);
			compStr = String.valueOf(comp.getDelegateFile() != null ? comp.getDelegateFile().lastModified() : 0l);
			break;
		case P:
			return this.getPlayCount() - comp.getPlayCount();
		case R:
			return this.getRank() - comp.getRank();
		default:
			break;
		
		}
		String[] s = {thisStr, compStr};
		logger.trace("{} : {}", this.opus, ArrayUtils.toString(s));
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
			if (VideoUtils.equalsName(actress.getName(), actressName))
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

	/**
	 * Actress 목록. 이름순 정렬
	 * @return
	 */
	public List<Actress> getActressList() {
		Collections.sort(actressList);
		return actressList;
	}
	
	/**
	 * cover file의 byte[] 반환
	 * @return 없거나 에러이면 null 반환
	 */
	public byte[] getCoverByteArray() {
		return VideoUtils.readFileToByteArray(coverFile);
	}
	
	/**
	 * 커버 파일 반환
	 * @return
	 */
	public File getCoverFile() {
		return coverFile;
	}

	/**
	 * cover 파일 절대 경로
	 * @return 없으면 공백
	 */
	public String getCoverFilePath() {
		if(isExistCoverFile())
			return getCoverFile().getAbsolutePath();
		return "";
	}
	
	/**
	 * webp형식의 cover file의 byte[] 반환
	 * @return 없거나 에러이면 null 반환
	 */
	public byte[] getCoverWebpByteArray() {
		return VideoUtils.readFileToByteArray(coverWebpFile);
	}

	/**
	 * WebP cover 파일 반환
	 * @return
	 */
	public File getCoverWebpFile() {
		return coverWebpFile;
	}
	
	/**
	 * WebP cover 파일 절대 경로
	 * @return 없으면 공백
	 */
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
		if (this.isExistVideoFileList()) 
			return this.getVideoFileList().get(0);
		else if (this.isExistCoverFile()) 
			return this.getCoverFile();
		else if (this.isExistSubtitlesFileList()) 
			return this.getSubtitlesFileList().get(0);
		else if (this.isExistEtcFileList()) 
			return this.getEtcFileList().get(0);
		else if (this.infoFile != null) 
			return this.infoFile;
		else if (this.coverWebpFile != null)
			return this.coverWebpFile;
		else 
			throw new VideoException("No delegate video file : " + this.opus + " " + this.toString());
	}
	
	/**
	 * video 대표 파일 이름
	 * @return
	 */
	private String getDelegateFilename() {
		return this.getDelegateFile().getName();
	}

	/**
	 * video 대표 파일 이름. 확장자를 뺀 대표이름
	 * @return
	 */
	private String getDelegateFilenameWithoutSuffix() {
		return VideoUtils.getName(getDelegateFilename());
	}
	
	/**
	 * video 대표 폴더 경로. video > cover > overview > subtitles > etc 순으로 찾는다.
	 * @return
	 */
	private String getDelegatePath() {
		return this.getDelegateFile().getParent();
	}
	
	/**
	 * 기타 파일 
	 * @return
	 */
	public List<File> getEtcFileList() {
		return etcFileList;
	}
	
	/**
	 * 기타 파일 경로
	 * @return
	 */
	public String getEtcFileListPath() {
		if(isExistEtcFileList())
			return VideoUtils.arrayToString(getEtcFileList());
		return "";
	}
	
	/**
	 * 기타 정보. 보통 날자
	 */
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
	 * 히스토리 내용 반환. 건별 줄바꿈함
	 * @return
	 */
	public String getHistoryText() {
		StringBuilder sb = new StringBuilder();
		for (String his : historyList) {
			sb.append(his).append(System.getProperty("line.separator"));
		}
		return sb.toString();
	}

	/**
	 * info 파일 반환. 없으면 대표경로에 만듬.
	 * @return
	 */
	public File getInfoFile() {
		if(this.infoFile == null) 
			this.infoFile = new File(this.getDelegatePath(), this.getDelegateFilenameWithoutSuffix() + ".info");
		return infoFile;
	}

	/**
	 * info 파일 경로
	 * @return
	 */
	public String getInfoFilePath() {
		if (infoFile != null)
			return this.infoFile.getAbsolutePath();
		return "";
	}

	/**
	 * @return 품번
	 */
	public String getOpus() {
		return opus;
	}

	/**
	 * overview
	 * @return
	 */
	public String getOverviewText() {
		return overview;
	}

	/**
	 * play count
	 * @return
	 */
	public Integer getPlayCount() {
		return playCount;
	}

	/**
	 * rank point
	 * @return
	 */
	public int getRank() {
		return rank;
	}

	/**
	 * studio
	 * @return
	 */
	public Studio getStudio() {
		return studio;
	}

	/**
	 * subtitles file list
	 * @return
	 */
	public List<File> getSubtitlesFileList() {
		return subtitlesFileList;
	}

	/**
	 * subtitles file list path
	 * @return
	 */
	public String getSubtitlesFileListPath() {
		if(isExistSubtitlesFileList())
			return VideoUtils.arrayToString(getSubtitlesFileList());
		return "";
	}

	/**
	 * 자막 파일 위치를 배열로 반환, 외부 에디터로 자막 수정시 사용 
	 * @return
	 */
	public String[] getSubtitlesFileListPathArray() {
		if(isExistSubtitlesFileList()) {
			String[] filePathes = new String[getSubtitlesFileList().size()];
			for(int i=0; i<filePathes.length; i++)
				filePathes[i] = getSubtitlesFileList().get(i).getAbsolutePath();
			return filePathes;
		}
		return null;
	}
	
	/**
	 * title
	 * @return
	 */
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

	/**
	 * video file list
	 * @return
	 */
	public List<File> getVideoFileList() {
		return videoFileList;
	}

	/**
	 * video file list path
	 * @return
	 */
	public String getVideoFileListPath() {
		if(isExistVideoFileList()) 
			return VideoUtils.arrayToString(getVideoFileList()); 
		return "";
	}
	
	/**
	 * 비디오 파일 목록 배열. 플레이어 구동시 사용
	 * @return
	 */
	public String[] getVideoFileListPathArray() {
		if(isExistVideoFileList()) {
			String[] filePathes = new String[getVideoFileList().size()];
			for(int i=0; i<filePathes.length; i++)
				filePathes[i] = getVideoFileList().get(i).getAbsolutePath();
			return filePathes;
		}
		return null;
	}

	public URL getVideoURL() {
		if (videoFileList == null || videoFileList.size() == 0) {
			return null;
		}
		else {
			File vfile = videoFileList.get(0);
			String pname = vfile.getParentFile().getName();
			
			try {
				return new URL(baseurl + "/" + pname + "/" + vfile.getName());
			} catch (MalformedURLException e) {
				logger.error(e.getMessage(), e);
			}
			return null;
		}
	}
	
	/**
	 * play count 증가
	 */
	public void increasePlayCount() {
		this.playCount++;
	}

	/**
	 * 커버 파일이 존재하는지
	 * @return
	 */
	public boolean isExistCoverFile() {
		return this.coverFile != null;
	}

	/**
	 * WebP 커버 파일이 존재하는지
	 * @return
	 */
	public boolean isExistCoverWebpFile() {
		return this.coverWebpFile != null;
	}

	/**
	 * 기타 파일이 존재하는지
	 * @return
	 */
	public boolean isExistEtcFileList() {
		return this.etcFileList != null && this.etcFileList.size() > 0;
	}

	/**
	 * overview가 있는지
	 * @return
	 */
	public boolean isExistOverview() {
		return this.overview != null && this.overview.trim().length() > 0;
	}

	/**
	 * 자막 파일이 존재하는지
	 * @return
	 */
	public boolean isExistSubtitlesFileList() {
		return this.subtitlesFileList != null && this.subtitlesFileList.size() > 0;
	}

	/**
	 * 비디오 파일이 존재하는지
	 * @return
	 */
	public boolean isExistVideoFileList() {
		return this.videoFileList != null && this.videoFileList.size() > 0;  
	}

	/**
	 * destDir 폴더로 전체 파일 이동
	 * @param destDir
	 */
	public void move(String destDir) {
		File destFile = new File(destDir);
		if (!destFile.exists()) 
			throw new VideoException(destDir + " is not exist!");
		for (File file : getFileAll()) {
			if (file != null && file.exists() && !file.getParent().equals(destDir)) {
				if (destFile.getFreeSpace() < file.length()) {
					logger.warn("destination is small. size=" + destFile.getFreeSpace());
					break;
				}
				try {
					FileUtils.moveFileToDirectory(file, destFile, false);
					logger.info("move file from {} to {}", file.getAbsolutePath(), destDir);
				} catch (FileExistsException fe) {
					logger.warn("File exist, then delete ");
					FileUtils.deleteQuietly(file);
				} catch (IOException e) {
					logger.error("Fail move file", e);
				}
			}
		}
	}

	/**
	 * actress를 추가한다. 기존actress가 발견되면 ref를 갱신.
	 * @param actress
	 */
	public void putActress(Actress actress) {
		boolean notFound = true;
		for (Actress actressInList : this.actressList) {
			if (actressInList.contains(actress.getName())) {
				notFound = false;
				actressInList = actress;
				break;
			}
		}
		if (notFound)
			this.actressList.add(actress);
	}

	/**
	 * 모든 파일을 지운다.
	 */
	public void removeVideo() {
		for(File file : getFileAll())
			if(file != null && file.exists()) 
				if(FileUtils.deleteQuietly(file))
					logger.info(file.getAbsolutePath());
				else
					logger.error("delete fail : {}", file.getAbsolutePath());
	}

	/**
	 * info 내용 저장
	 */
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
			logger.info("{} {}", opus, root);
			FileUtils.writeStringToFile(file, root.toString(), VideoCore.FileEncoding);
		} catch (IOException e) {
			logger.error("info save error", e);
		}
	}

	/**
	 * overview 내용 저장
	 * @param overViewText
	 */
	public void saveOverView(String overViewText) {
		logger.trace("{} [{}]", opus, overViewText);
		this.overview = overViewText;
		this.saveInfo();
	}
	
	/**
	 * actress list
	 * @param actressList
	 */
	public void setActressList(List<Actress> actressList) {
		this.actressList = actressList;
	}

	/**
	 * cover file
	 * @param coverFile
	 */
	public void setCoverFile(File coverFile) {
		this.coverFile = coverFile;
	}

	/**
	 * webp cover file
	 * @param coverWebpFile
	 */
	public void setCoverWebpFile(File coverWebpFile) {
		this.coverWebpFile = coverWebpFile;
	}

	/**
	 * etc file
	 * @param file
	 */
	public void setEtcFile(File file) {
		this.etcFileList.add(file);		
	}
	
	/**
	 * etc file list
	 * @param etcFileList
	 */
	public void setEtcFileList(List<File> etcFileList) {
		this.etcFileList = etcFileList;
	}
	
	/**
	 * etc info
	 * @param etcInfo
	 */
	public void setEtcInfo(String etcInfo) {
		this.etcInfo = etcInfo;
	}

	/**
	 * info file. 파일 분석해서 필요 데이터(rank, overview, history, playcount) 설정
	 * @param info file
	 */
	public void setInfoFile(File file) {
		this.infoFile = file;
		
		JSONObject json = null;
		try {
			json = JSONObject.fromObject(FileUtils.readFileToString(infoFile, VideoCore.FileEncoding));
		} catch (IOException e1) {
			logger.error("info read error", e1);
			return;
		}
		JSONObject infoData = json.getJSONObject("info");

		String opus = infoData.getString("opus");
		if (!this.opus.equalsIgnoreCase(opus)) 
			throw new VideoException("invalid info file. " + this.opus + " != " + opus);
		
		String rank = infoData.getString("rank");
		this.rank = NumberUtils.toInt(rank, 0);
		
		this.overview = infoData.getString("overview");

		JSONArray hisArray = infoData.getJSONArray("history");
		this.playCount = 0;
		for (int i=0, e=hisArray.size(); i<e; i++){
			String line = hisArray.getString(i);
			this.historyList.add(line);
			String[] linePart = StringUtils.split(line, ",");
			if (linePart.length > 2 && linePart[2].trim().equalsIgnoreCase(Action.PLAY.toString())) 
				this.playCount++;
		}
	}

	/**
	 * opus
	 * @param opus
	 */
	public void setOpus(String opus) {
		this.opus = opus;
	}

	/**
	 * play count
	 * @param playCount
	 */
	public void setPlayCount(Integer playCount) {
		this.playCount = playCount;
	}

	/**
	 * rank. info 파일에 저장
	 * @param rank
	 */
	public void setRank(int rank) {
		logger.trace("{} rank is {}", opus, rank);
		this.rank = rank;
		this.saveInfo();
	}

	/**
	 * sort method. 정렬 방식 
	 * @param sortMethod
	 */
	@SuppressWarnings("static-access")
	public void setSortMethod(Sort sortMethod) {
		this.sortMethod = sortMethod;
	}

	/**
	 * studio
	 * @param studio
	 */
	public void setStudio(Studio studio) {
		this.studio = studio;
	}

	/**
	 * add subtitles file
	 * @param file
	 */
	public void setSubtitlesFile(File file) {
		this.subtitlesFileList.add(file);		
	}
	
	/**
	 * subtitles file list
	 * @param subtitlesFileList
	 */
	public void setSubtitlesFileList(List<File> subtitlesFileList) {
		this.subtitlesFileList = subtitlesFileList;
	}
	
	/**
	 * title
	 * @param title
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * add video file
	 * @param file
	 */
	public void setVideoFile(File file) {
		this.videoFileList.add(file);
	}

	/**
	 * video file list
	 * @param videoFileList
	 */
	public void setVideoFileList(List<File> videoFileList) {
		this.videoFileList = videoFileList;
	}
/*
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
*/
	
	
	
	public String getReleaseDate() {
		return releaseDate;
	}

	@Override
	public String toString() {
		return String
				.format("Video [opus=%s, actressList=%s, coverFile=%s, coverWebpFile=%s, etcFileList=%s, etcInfo=%s, historyList=%s, infoFile=%s, overview=%s, playCount=%s, rank=%s, studio=%s, subtitlesFileList=%s, title=%s, videoFileList=%s, releaseDate=%s]",
						opus, actressList, coverFile, coverWebpFile, etcFileList,
						etcInfo, historyList, infoFile, overview,
						playCount, rank, studio, subtitlesFileList, title,
						videoFileList, releaseDate);
	}

	public void setReleaseDate(String releaseDate) {
		this.releaseDate = releaseDate;
	}
}
