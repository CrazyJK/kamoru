package jk.kamoru.app.video.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import jk.kamoru.app.video.VideoCore;

@Component
@Scope("prototype")
public class Studio implements Serializable, Comparable<Object>{

	private static final long serialVersionUID = VideoCore.SERIAL_VERSION_UID;

	private String name;
	private String homepage;
	private String companyName;
	private List<Video> videoList;
	private List<Actress> actressList;
	
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
	public String getHomepage() {
		return homepage;
	}
	public String getCompanyName() {
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
	public void setHomepage(String homepage) {
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
	public int compareTo(Object o) {
		Studio comp = (Studio)o;
		String thisStr = this.getName().toLowerCase();
		String compStr = comp.getName().toLowerCase();
		String[] s = {thisStr, compStr};
		Arrays.sort(s);
		return s[0].equals(thisStr) ? -1 : 1;
	}
	
}
