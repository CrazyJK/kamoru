package kamoru.app.video.dao;

import java.util.List;

import kamoru.app.video.av.AVOpus;

public interface AVDao {

	List<AVOpus> getList(String studio, String opus, String title,
			String actress, boolean addCond, boolean existVideo,
			boolean existSubtitles, String listViewType, String sortMethod,
			boolean sortReverse, boolean useCacheData);

	
}
