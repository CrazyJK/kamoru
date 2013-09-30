package jk.kamoru.app.video.domain;

import java.io.Serializable;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import jk.kamoru.app.video.VideoCore;

public class VideoSearch implements Serializable {

	private static final long serialVersionUID = VideoCore.SERIAL_VERSION_UID;

	String actress;
	boolean addCond;
	boolean existSubtitles;
	boolean existVideo;
	View listViewType = View.L;
	boolean neverPlay = false;
	boolean oldVideo = false;
	String opus;
	String searchText;
	List<String> selectedActress;
	List<String> selectedStudio;
	Sort sortMethod = Sort.M;
	boolean sortReverse = true;

	String studio;

	String title;
	boolean viewActressDiv = false;

	boolean viewStudioDiv = false;
	boolean zeroRank = true;
	
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
	public List<String> getSelectedActress() {
		return selectedActress;
	}

	public List<String> getSelectedStudio() {
		return selectedStudio;
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

	public boolean isOldVideo() {
		return oldVideo;
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

	public void setOldVideo(boolean oldVideo) {
		this.oldVideo = oldVideo;
	}

	public void setOpus(String opus) {
		this.opus = opus;
	}

	public void setSearchText(String searchText) {
		this.searchText = searchText;
	}

	public void setSelectedActress(List<String> selectedActress) {
		this.selectedActress = selectedActress;
	}

	public void setSelectedStudio(List<String> selectedStudio) {
		this.selectedStudio = selectedStudio;
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
	
	@Override
	public String toString() {
		return String
				.format("VideoSearch [actress=%s, addCond=%s, existSubtitles=%s, existVideo=%s, listViewType=%s, neverPlay=%s, oldVideo=%s, opus=%s, searchText=%s, selectedActress=%s, selectedStudio=%s, sortMethod=%s, sortReverse=%s, studio=%s, title=%s, viewActressDiv=%s, viewStudioDiv=%s, zeroRank=%s]",
						actress, addCond, existSubtitles, existVideo,
						listViewType, neverPlay, oldVideo, opus, searchText,
						selectedActress, selectedStudio, sortMethod,
						sortReverse, studio, title, viewActressDiv,
						viewStudioDiv, zeroRank);
	}


}
