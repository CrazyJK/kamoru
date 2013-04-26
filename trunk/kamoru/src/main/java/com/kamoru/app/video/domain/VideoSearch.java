package com.kamoru.app.video.domain;

import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;

public class VideoSearch {

	String studio; 
	String opus;
	String title;
	String actress;
	boolean addCond;
	boolean existVideo; 
	boolean existSubtitles;
	String listViewType = "box";
	String sortMethod = "M";
	boolean sortReverse = true;
	
	boolean viewStudioDiv = false;
	boolean viewActressDiv = false;
	
	public String getStudio() {
		return studio;
	}
	public String getOpus() {
		return opus;
	}
	public String getTitle() {
		return title;
	}
	public String getActress() {
		return actress;
	}
	public boolean isAddCond() {
		return addCond;
	}
	public boolean isExistVideo() {
		return existVideo;
	}
	public boolean isExistSubtitles() {
		return existSubtitles;
	}
	public String getListViewType() {
		return listViewType;
	}
	public String getSortMethod() {
		return sortMethod;
	}
	public boolean isSortReverse() {
		return sortReverse;
	}
	public boolean isViewStudioDiv() {
		return viewStudioDiv;
	}
	public boolean isViewActressDiv() {
		return viewActressDiv;
	}
	public void setStudio(String studio) {
		this.studio = studio;
	}
	public void setOpus(String opus) {
		this.opus = opus;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public void setActress(String actress) {
		this.actress = actress;
	}
	public void setAddCond(boolean addCond) {
		this.addCond = addCond;
	}
	public void setExistVideo(boolean existVideo) {
		this.existVideo = existVideo;
	}
	public void setExistSubtitles(boolean existSubtitles) {
		this.existSubtitles = existSubtitles;
	}
	public void setListViewType(String listViewType) {
		this.listViewType = listViewType;
	}
	public void setSortMethod(String sortMethod) {
		this.sortMethod = sortMethod;
	}
	public void setSortReverse(boolean sortReverse) {
		this.sortReverse = sortReverse;
	}
	public void setViewStudioDiv(boolean viewStudioDiv) {
		this.viewStudioDiv = viewStudioDiv;
	}
	public void setViewActressDiv(boolean viewActressDiv) {
		this.viewActressDiv = viewActressDiv;
	}

	
}
