package com.kamoru.app.video.domain;

import java.io.Serializable;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.kamoru.app.video.util.VideoUtils;

@Component
@Scope("prototype")
public class Actress implements Serializable, Comparable<Object> {

	private static final long serialVersionUID = -3241999343906740614L;

	private String name;
	private Date birth;
	private int height;
	private String bodySize;
	private Date debut;
	private List<Studio> studioList;
	private List<Video> videoList;
	
	public Actress() {
		studioList = new ArrayList<Studio>();
		videoList = new ArrayList<Video>();
	}
	public Actress(String name) {
		this();
		this.name = name;
	}
	
	public void putStudio(Studio studio) {
		if(!this.studioList.contains(studio))
			this.studioList.add(studio);
	}
	public void putVideo(Video video) {
		if(!this.videoList.contains(video))
			this.videoList.add(video);
	}
	
	public String getName() {
		return name;
	}
	public Date getBirth() {
		return birth;
	}
	public int getHeight() {
		return height;
	}
	public String getBodySize() {
		return bodySize;
	}
	public Date getDebut() {
		return debut;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setBirth(Date birth) {
		this.birth = birth;
	}
	public void setHeight(int height) {
		this.height = height;
	}
	public void setBodySize(String bodySize) {
		this.bodySize = bodySize;
	}
	public void setDebut(Date debut) {
		this.debut = debut;
	}
	
	public List<Studio> getStudioList() {
		return studioList;
	}
	public List<Video> getVideoList() {
		return videoList;
	}
	public void setStudioList(List<Studio> studioList) {
		this.studioList = studioList;
	}
	public void setVideoList(List<Video> videoList) {
		this.videoList = videoList;
	}
	@Override
	public int compareTo(Object o) {
		Actress comp = (Actress)o;
		String thisStr = this.getName();
		String compStr = comp.getName();
		String[] s = {thisStr, compStr};
		Arrays.sort(s);
		return s[0].equals(thisStr) ? -1 : 1;
	}
	public boolean contains(String actressName) {
		return this.name.toLowerCase().indexOf(actressName.toLowerCase()) > -1;
	}
	
	public List<URL> getWebImage() {
		return VideoUtils.getGoogleImage(this.getName());
	}
	
}
