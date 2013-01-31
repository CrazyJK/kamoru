package kamoru.app.video.dao;

import java.util.List;
import java.util.Map;

import kamoru.app.video.av.AVCollectionCtrl;
import kamoru.app.video.av.AVOpus;

public class AVDaoImpl implements AVDao {

	private AVCollectionCtrl ctrl;
	
	@Override
	public List<AVOpus> getList(String studio, String opus, String title,
			String actress, boolean addCond, boolean existVideo,
			boolean existSubtitles, String listViewType, String sortMethod,
			boolean sortReverse, boolean useCacheData) {
		ctrl = new AVCollectionCtrl();
		sortMethod = sortMethod == null ? "O" : sortMethod;
		List<AVOpus> list = ctrl.getAV(null, studio, opus, title, actress, addCond, existVideo, existSubtitles, sortMethod, sortReverse, useCacheData);
		return list;
	}

	@Override
	public Map<String, Integer> getStudioMap() {
		return ctrl.getStudioMap();
	}

	@Override
	public Map<String, Integer> getActressMap() {
		return ctrl.getActressMap();
	}

}
