package jk.kamoru.app.video.domain;

import java.io.File;
import java.io.Serializable;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import jk.kamoru.KamoruException;
import jk.kamoru.app.video.VideoCore;
import jk.kamoru.app.video.util.VideoUtils;
import jk.kamoru.util.StringUtils;

@Component
@Scope("prototype")
public class Studio implements Serializable, Comparable<Studio> {

	private static final long serialVersionUID = VideoCore.SERIAL_VERSION_UID;

	private String name;
	private URL homepage;
	private String companyName;
	private List<Video> videoList;
	private List<Actress> actressList;

	private boolean loaded;
	@Value("#{videoProp['mainBasePath']}") 			
	private String mainBasePath;
	
	public Studio() {
		videoList = new ArrayList<Video>();
		actressList = new ArrayList<Actress>();
	}

	public Studio(String name) {
		this();
		this.name = name;
	}

	
	@Override
	public String toString() {
		return String.format("Studio [name=%s, homepage=%s, companyName=%s]",
				name, homepage, companyName);
	}

	public void putVideo(Video video) {
		if(!videoList.contains(video))
			this.videoList.add(video);		
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
	
	public String getName() {
		return name;
	}
	public URL getHomepage() {
		loadInfo();
		return homepage;
	}
	public String getCompanyName() {
		loadInfo();
		return companyName;
	}
	public List<Actress> getActressList() {
		return actressList;
	}
	public List<Video> getVideoList() {
		return videoList;
	}

	public void setName(String name) {
		this.name = name;
	}
	public void setHomepage(URL homepage) {
		this.homepage = homepage;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public void setActressList(List<Actress> actressList) {
		this.actressList = actressList;
	}
	public void setVideoList(List<Video> videoList) {
		this.videoList = videoList;
	}

	@Override
	public int compareTo(Studio comp) {
		return StringUtils.compateTo(this.getName().toLowerCase(), comp.getName().toLowerCase());
	}

	private void loadInfo() {
		if (!loaded) {
			Map<String, String> info = VideoUtils.readFileToMap(new File(mainBasePath, name + VideoCore.EXT_STUDIO));
			try {
				this.homepage = new URL(info.get("HOMEPAGE"));
			} catch (MalformedURLException e) {
				// do nothing!
			}
			this.companyName     = info.get("COMPANYNAME");
			loaded = true;
		}
	}
	public void reloadInfo() {
		loaded = false;
	}

	public void setMainBasePath(String mainBasePath) {
		this.mainBasePath = mainBasePath;
	}

	public void emptyVideo() {
		videoList.clear();
	}
	
}
