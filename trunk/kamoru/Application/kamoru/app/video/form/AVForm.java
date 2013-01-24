package kamoru.app.video.form;

import java.util.List;
import java.util.Map;

import kamoru.app.video.av.AVOpus;

import org.apache.struts.action.ActionForm;

public class AVForm extends ActionForm {

	private static final long serialVersionUID = 1L;
	private String studio;
	private String opus;
	private String title;
	private String actress;
	private boolean addCond;
	private boolean existVideo;
	private boolean existSubtitles;
	private boolean viewStudioDiv;
	private boolean viewActressDiv;
	private String listViewType = "box";
	private String sortMethod = "O";
	private boolean sortReverse;
	private boolean useCacheData;
	
	private String listBGImageName = "listBGImg.jpg";
	
	private List<AVOpus> avlist;
	private Map<String, Integer> studioMap;
	private Map<String, Integer> actressMap;

	// getter & setter
	public String getListBGImageName() {
		return listBGImageName;
	}
	public String getStudio() {
		return studio;
	}
	public void setStudio(String studio) {
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
	public String getActress() {
		return actress;
	}
	public void setActress(String actress) {
		this.actress = actress;
	}
	public boolean isAddCond() {
		return addCond;
	}
	public void setAddCond(boolean addCond) {
		this.addCond = addCond;
	}
	public boolean isExistVideo() {
		return existVideo;
	}
	public void setExistVideo(boolean existVideo) {
		this.existVideo = existVideo;
	}
	public boolean isExistSubtitles() {
		return existSubtitles;
	}
	public void setExistSubtitles(boolean existSubtitles) {
		this.existSubtitles = existSubtitles;
	}
	public boolean isViewStudioDiv() {
		return viewStudioDiv;
	}
	public void setViewStudioDiv(boolean viewStudioDiv) {
		this.viewStudioDiv = viewStudioDiv;
	}
	public boolean isViewActressDiv() {
		return viewActressDiv;
	}
	public void setViewActressDiv(boolean viewActressDiv) {
		this.viewActressDiv = viewActressDiv;
	}
	public String getListViewType() {
		return listViewType;
	}
	public void setListViewType(String listViewType) {
		this.listViewType = listViewType;
	}
	public String getSortMethod() {
		return sortMethod;
	}
	public void setSortMethod(String sortMethod) {
		this.sortMethod = sortMethod;
	}
	public boolean isSortReverse() {
		return sortReverse;
	}
	public void setSortReverse(boolean sortReverse) {
		this.sortReverse = sortReverse;
	}
	public boolean isUseCacheData() {
		return useCacheData;
	}
	public void setUseCacheData(boolean useCacheData) {
		this.useCacheData = useCacheData;
	}
	public List<AVOpus> getAvlist() {
		return avlist;
	}
	public void setAvlist(List<AVOpus> avlist) {
		this.avlist = avlist;
	}
	public Map<String, Integer> getStudioMap() {
		return studioMap;
	}
	public void setStudioMap(Map<String, Integer> studioMap) {
		this.studioMap = studioMap;
	}
	public Map<String, Integer> getActressMap() {
		return actressMap;
	}
	public void setActressMap(Map<String, Integer> actressMap) {
		this.actressMap = actressMap;
	}
	
}
