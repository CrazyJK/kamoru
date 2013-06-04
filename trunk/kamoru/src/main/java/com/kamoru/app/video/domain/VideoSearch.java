package com.kamoru.app.video.domain;

import java.io.Serializable;
import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;

import com.kamoru.app.video.VideoCore;

public class VideoSearch implements Serializable {

	private static final long serialVersionUID = VideoCore.Serial_Version_UID;

	String studio;
	String opus;
	String title;
	String actress;
	String searchText;
	boolean addCond;
	boolean existVideo;
	boolean existSubtitles;
	boolean neverPlay = true;
	View listViewType = View.B;
	Sort sortMethod = Sort.M;
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

	public void setSortReverse(boolean sortReverse) {
		this.sortReverse = sortReverse;
	}

	public void setViewStudioDiv(boolean viewStudioDiv) {
		this.viewStudioDiv = viewStudioDiv;
	}

	public void setViewActressDiv(boolean viewActressDiv) {
		this.viewActressDiv = viewActressDiv;
	}

	public boolean isNeverPlay() {
		return neverPlay;
	}

	public void setNeverPlay(boolean neverPlay) {
		this.neverPlay = neverPlay;
	}

	public Sort getSortMethod() {
		return sortMethod;
	}

	public void setSortMethod(Sort sortMethod) {
		this.sortMethod = sortMethod;
	}

	public View getListViewType() {
		return listViewType;
	}

	public void setListViewType(View listViewType) {
		this.listViewType = listViewType;
	}

	public String getSearchText() {
		return searchText;
	}

	public void setSearchText(String searchText) {
		this.searchText = searchText;
	}

	@Override
	public String toString() {
		return String
				.format("VideoSearch [studio=%s, opus=%s, title=%s, actress=%s, searchText=%s, addCond=%s, existVideo=%s, existSubtitles=%s, neverPlay=%s, listViewType=%s, sortMethod=%s, sortReverse=%s, viewStudioDiv=%s, viewActressDiv=%s]",
						studio, opus, title, actress, searchText, addCond,
						existVideo, existSubtitles, neverPlay, listViewType,
						sortMethod, sortReverse, viewStudioDiv, viewActressDiv);
	}

	String getParam(HttpServletRequest request) {
		StringBuffer sb = new StringBuffer();
		Enumeration enu = request.getParameterNames();
		while (enu.hasMoreElements()) {
			String name = (String) enu.nextElement();
			String value = request.getParameter(name);
			sb.append("&").append(name).append("=").append(value);
		}
		return sb.toString();
	}

}
