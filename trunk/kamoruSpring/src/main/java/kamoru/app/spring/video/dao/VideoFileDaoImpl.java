package kamoru.app.spring.video.dao;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import kamoru.app.spring.video.domain.Video;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * AV Collection controller<br>
 * data loading, searching, background-image setting
 * @author kamoru
 *
 */
@Component
public class VideoFileDaoImpl implements VideoDao {
	protected static final Log logger = LogFactory.getLog(VideoFileDaoImpl.class);

	private final String DEFAULT_SORTMETHOD = "O";

	@Autowired
	private VideoSource videoSource;

	/**
	 * returns a list that match the given conditions
	 * @param studio
	 * @param opus
	 * @param title
	 * @param actress
	 * @param addCond if true, existVideo and existSubtitles are activated
	 * @param existVideo
	 * @param existSubtitles
	 * @param sortMethod S:studio, O:opus, T:title, A:actress, M:modified
	 * @param sortReverse if true, reverse sort
	 * @param useCache if true, use cache data
	 * @return
	 */
	private List<Video> getAV(
			String[] selectedBasePathId, String studio, String opus, String title, String actress, 
			boolean addCond, boolean existVideo, boolean existSubtitles, 
			String sortMethod, boolean sortReverse, boolean useCache) {
		logger.info("getAV : params[selectedBasePathId:" + ArrayUtils.toString(selectedBasePathId) + ", studio:" + studio + ", opus:" + opus + ", title:" + title + ", actress:" + actress + ", addCond:" + addCond + ", existVideo:" + existVideo + ", existSubtitles:" + existSubtitles + ", sortMethod:" + sortMethod + ", sortReverse:" + sortReverse + ", useCache:" + useCache + "]");
		
		if(!useCache) {
			videoSource.reload();
		}
		
		List<Video> list = new ArrayList<Video>();
		for(Video video : videoSource.getList()) {
			if((studio  == null || studio.trim().length()  == 0 || studio.equalsIgnoreCase(video.getStudio())) 
			&& (opus    == null || opus.trim().length()    == 0 || opus.equalsIgnoreCase(video.getOpus()))
			&& (title   == null || title.trim().length()   == 0 || video.getTitle().toLowerCase().indexOf(title.toLowerCase()) > -1) 
			&& (actress == null || actress.trim().length() == 0 || video.containsActress(actress))
			&& (addCond ? (existVideo ? video.isExistVideoFileList() : !video.isExistVideoFileList()) && (existSubtitles ? video.isExistSubtitlesFileList() : !video.isExistSubtitlesFileList()) : true)
			) {
				video.setSortMethod(sortMethod);
				list.add(video);
			}
		}
		logger.info("getAV : found opus size " + list.size());
		if(sortReverse)
			Collections.sort(list, Collections.reverseOrder());
		else
			Collections.sort(list);
		return list;
	}
	
	@Override
	public Map<String, Integer> getStudioMap() {
		Map<String, Integer> studioMap = new TreeMap<String, Integer>();
		for(Video video : videoSource.getList()) {
			String studio = video.getStudio().toUpperCase();
			int count = 0;
			if(studioMap.containsKey(studio)) {
				count = studioMap.get(studio);
			}
			studioMap.put(studio, ++count);
		}
		return studioMap;
	}
	@Override
	public Map<String, Integer> getActressMap() {
		Map<String, Integer> actressMap = new TreeMap<String, Integer>();
		for(Video video : videoSource.getList()) {
			List<String> actressList = video.getActressList();
			for(String actress : actressList) {
				int count = 0;
				String reverseName = reverseName(actress);
				if(actressMap.containsKey(actress)) {
					count = actressMap.get(actress);
				} 
				else if(actressMap.containsKey(reverseName)) {
					count = actressMap.get(reverseName);
					actress = reverseName;
				}
				actressMap.put(actress, ++count);
			}
		}
		return actressMap;
	}
	private String reverseName(String name) {
		if(name == null) return null;
		String[] nameArr = StringUtils.split(name);
		ArrayUtils.reverse(nameArr);
		String retName = "";
		for(int i=0; i<nameArr.length; i++)
			retName += nameArr[i] + " ";
		return retName.trim();
	}

	@Override
	public List<Video> getVideoListByParams(Map<String, String> params) {
		String[] selectedBasePathId = null; 
		String studio = params.get("studio"); 
		String opus = params.get("opus");
		String title = params.get("title");
		String actress = params.get("actress");
		boolean addCond = BooleanUtils.toBoolean(params.get("addCond"));
		boolean existVideo = BooleanUtils.toBoolean(params.get("existVideo")); 
		boolean existSubtitles = BooleanUtils.toBoolean(params.get("existSubtitles"));
		String listViewType = params.get("listViewType");
		if(listViewType == null) {
			listViewType = "box";
			params.put("listViewType", listViewType); 
		}
		String sortMethod = params.get("sortMethod");
		if(sortMethod == null) {
			sortMethod = DEFAULT_SORTMETHOD;
			params.put("sortMethod", sortMethod);
		}
		boolean sortReverse = BooleanUtils.toBoolean(params.get("sortReverse")); 
		boolean useCacheData = BooleanUtils.toBoolean(params.get("useCacheData"));
		return getAV(selectedBasePathId, studio, opus, title, actress, addCond, existVideo, existSubtitles, sortMethod, sortReverse, useCacheData);
	}

	@Override
	public List<Video> getVideoListByActress(String actress) {
		return this.getAV(null, null, null, null, actress, false, false, false, null, false, false);
	}

	@Override
	public List<Video> getVideoListByStudio(String studio) {
		return this.getAV(null, studio, null, null, null, false, false, false, null, false, false);
	}

	@Override
	public List<Video> getVideoListByTitle(String title) {
		return this.getAV(null, null, null, title, null, false, false, false, null, false, false);
	}

	@Override
	public Video getVideo(String opus) {
		return videoSource.get(opus);
	}

	@Override
	public void deleteVideo(String opus) {
		getVideo(opus).deleteVideo();
	}

}

