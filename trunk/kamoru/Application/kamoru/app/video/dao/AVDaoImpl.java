package kamoru.app.video.dao;

import java.util.List;

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
		List<AVOpus> list = ctrl.getAV(studio, opus, title, actress, addCond, existVideo, existSubtitles, sortMethod, sortReverse, useCacheData);
		return list;
	}

}
