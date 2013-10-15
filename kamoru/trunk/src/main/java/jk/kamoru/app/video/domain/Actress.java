package jk.kamoru.app.video.domain;

import java.io.File;
import java.io.Serializable;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import jk.kamoru.app.video.VideoCore;
import jk.kamoru.app.video.util.VideoUtils;
import jk.kamoru.util.FileUtils;
import jk.kamoru.util.StringUtils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
public class Actress implements Serializable, Comparable<Actress> {

	private static final long serialVersionUID = VideoCore.SERIAL_VERSION_UID;

	@Value("#{videoProp['mainBasePath']}") 			
	private String mainBasePath;

	private String name;
	private String localName;
	private String birth;
	private String bodySize;
	private String debut;
	private String height;
	
	private List<Studio> studioList;
	private List<Video>   videoList;

	private boolean loaded;

	public Actress() {
		studioList = new ArrayList<Studio>();
		videoList = new ArrayList<Video>();
	}
	public Actress(String name) {
		this();
		this.name = name;
	}
	
	@Override
	public String toString() {
		return String
				.format("Actress [name=%s, birth=%s, bodySize=%s, debut=%s, height=%s, localName=%s]",
						name, birth, bodySize, debut, height, localName);
	}
	@Override
	public int compareTo(Actress comp) {
		return StringUtils.compareTo(this.getName(), comp.getName());
	}
	
	public boolean contains(String actressName) {
		return VideoUtils.equalsName(name, actressName);
	}
	
	public String getBirth() {
		loadInfo();
		return birth;
	}
	public String getBodySize() {
		loadInfo();
		return bodySize;
	}
	public String getDebut() {
		loadInfo();
		return debut;
	}
	public String getHeight() {
		loadInfo();
		return height;
	}
	public String getLocalName() {
		loadInfo();
		return localName;
	}
	public String getName() {
		return name;
	}
	public List<Studio> getStudioList() {
		return studioList;
	}
	public List<Video> getVideoList() {
		return videoList;
	}
	public List<URL> getWebImage() {
		return VideoUtils.getGoogleImage(this.getName());
	}
	
	private void loadInfo() {
		if (!loaded) {
			Map<String, String> info = VideoUtils.readFileToMap(new File(mainBasePath, name + FileUtils.EXTENSION_SEPARATOR + VideoCore.EXT_ACTRESS));
			this.localName = info.get("LOCALNAME");
			this.birth     = info.get("BIRTH");
			this.height    = info.get("HEIGHT");
			this.bodySize  = info.get("BODYSIZE");
			this.debut     = info.get("DEBUT");
			loaded = true;
		}
	}
	public void reloadInfo() {
		loaded = false;
		loadInfo();
	}
	public void addStudio(Studio studio) {
		if(!this.studioList.contains(studio))
			this.studioList.add(studio);
	}
	public void addVideo(Video video) {
		if(!this.videoList.contains(video))
			this.videoList.add(video);
	}
	public void setBirth(String birth) {
		this.birth = birth;
	}
	public void setBodySize(String bodySize) {
		this.bodySize = bodySize;
	}
	public void setDebut(String debut) {
		this.debut = debut;
	}
	public void setHeight(String height) {
		this.height = height;
	}
	public void setLocalName(String localName) {
		this.localName = localName;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setStudioList(List<Studio> studioList) {
		this.studioList = studioList;
	}
	
	public void setVideoList(List<Video> videoList) {
		this.videoList = videoList;
	}
	public void setMainBasePath(String mainBasePath) {
		this.mainBasePath = mainBasePath;
	}
	public void emptyVideo() {
		videoList.clear();
	}
	
}
