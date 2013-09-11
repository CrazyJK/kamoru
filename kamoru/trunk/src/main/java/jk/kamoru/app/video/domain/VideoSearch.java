package jk.kamoru.app.video.domain;

import java.io.Serializable;
import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;

import jk.kamoru.app.video.VideoCore;

public class VideoSearch implements Serializable {

	private static final long serialVersionUID = VideoCore.SERIAL_VERSION_UID;

	String actress;
	boolean addCond;
	boolean existSubtitles;
	boolean existVideo;
	View listViewType = View.V;
	boolean neverPlay = false;
	boolean zeroRank = true;
	boolean oldVideo = false;
	String opus;
	String searchText;
	Sort sortMethod = Sort.M;
	boolean sortReverse = true;
	String studio;

	String title;

	boolean viewActressDiv = false;
	boolean viewStudioDiv = false;

	public String getActress() {
		return actress;
	}
	public View getListViewType() {
		return listViewType;
	}

	public String getOpus() {
		return opus;
	}

	public String getSearchText() {
		return searchText;
	}

	public Sort getSortMethod() {
		return sortMethod;
	}

	public String getStudio() {
		return studio;
	}

	public String getTitle() {
		return title;
	}

	public boolean isAddCond() {
		return addCond;
	}

	public boolean isExistSubtitles() {
		return existSubtitles;
	}

	public boolean isExistVideo() {
		return existVideo;
	}

	public boolean isNeverPlay() {
		return neverPlay;
	}

	public boolean isSortReverse() {
		return sortReverse;
	}

	public boolean isViewActressDiv() {
		return viewActressDiv;
	}

	public boolean isViewStudioDiv() {
		return viewStudioDiv;
	}

	public boolean isZeroRank() {
		return zeroRank;
	}

	public void setActress(String actress) {
		this.actress = actress;
	}

	public void setAddCond(boolean addCond) {
		this.addCond = addCond;
	}

	public void setExistSubtitles(boolean existSubtitles) {
		this.existSubtitles = existSubtitles;
	}

	public void setExistVideo(boolean existVideo) {
		this.existVideo = existVideo;
	}

	public void setListViewType(View listViewType) {
		this.listViewType = listViewType;
	}

	public void setNeverPlay(boolean neverPlay) {
		this.neverPlay = neverPlay;
	}

	public void setOpus(String opus) {
		this.opus = opus;
	}

	public void setSearchText(String searchText) {
		this.searchText = searchText;
	}

	public void setSortMethod(Sort sortMethod) {
		this.sortMethod = sortMethod;
	}

	public void setSortReverse(boolean sortReverse) {
		this.sortReverse = sortReverse;
	}

	public void setStudio(String studio) {
		this.studio = studio;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setViewActressDiv(boolean viewActressDiv) {
		this.viewActressDiv = viewActressDiv;
	}

	public void setViewStudioDiv(boolean viewStudioDiv) {
		this.viewStudioDiv = viewStudioDiv;
	}

	public void setZeroRank(boolean zeroRank) {
		this.zeroRank = zeroRank;
	}

	public boolean isOldVideo() {
		return oldVideo;
	}
	
	public void setOldVideo(boolean oldVideo) {
		this.oldVideo = oldVideo;
	}
	
	@SuppressWarnings("rawtypes")
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
	
	@Override
	public String toString() {
		return String
				.format("VideoSearch [actress=%s, addCond=%s, existSubtitles=%s, existVideo=%s, listViewType=%s, neverPlay=%s, opus=%s, searchText=%s, sortMethod=%s, sortReverse=%s, studio=%s, title=%s, viewActressDiv=%s, viewStudioDiv=%s, zeroRank=%s]",
						actress, addCond, existSubtitles, existVideo,
						listViewType, neverPlay, opus, searchText, sortMethod,
						sortReverse, studio, title, viewActressDiv,
						viewStudioDiv, zeroRank);
	}


}
