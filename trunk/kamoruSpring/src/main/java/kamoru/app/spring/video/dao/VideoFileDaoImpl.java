package kamoru.app.spring.video.dao;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;

import javax.annotation.PostConstruct;

import kamoru.app.spring.video.domain.Video;
import kamoru.app.spring.video.util.AVUtils;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

/**
 * AV Collection controller<br>
 * data loading, searching, background-image setting
 * @author kamoru
 *
 */
@Component
public class VideoFileDaoImpl implements VideoDao {
	protected static final Log logger = LogFactory.getLog(VideoFileDaoImpl.class);

	public Map<String, Video> avData;
	private List<Video> list; 
	private static String[] basePathArray;
	private String[] selectedBasePathArray;
	Map<String, String> history;
	
	public final String listBGImageName = "listBGImg.jpg";
	public final String historyName   	= "history.log";
	public final String unclassifiedStudio  = "_unclassified";
	public final String unclassifiedOpus    = "_unclassified";
	public final String unclassifiedActress = "_unclassified";
	public final String unknownActress		= "_unknownActress";
	public final String noTitle				= "NoTitle";
	private final String DEFAULT_SORTMETHOD = "O";
	private File currBGImageFile;
	
	@Value("#{avConfig['basePath']}") String basePath;
	@Value("#{avConfig['video_extensions']}") String video_extensions;
	@Value("#{avConfig['cover_extensions']}") String cover_extensions;
	@Value("#{avConfig['subtitles_extensions']}") String subtitles_extensions;
	@Value("#{avConfig['overview_extensions']}") String overview_extensions;
	@Value("#{avConfig['backgroundImagePoolPath']}") String backgroundImagePoolPath;
	
	@Value("#{avConfig['editor']}") String editor;
	@Value("#{avConfig['player']}") String player;
	@Value("#{avConfig['noImagePath']}") String noImagePath;

	/**
	 * AV Control Unit
	 */
	public VideoFileDaoImpl() {
//		basePathArray = basePath.split(";");
		logger.debug("AVCollectionCtrl create!");
	}
	
	@PostConstruct
	public void init() {
		logger.debug("PostConstruct");
		basePathArray = basePath.split(";");
		history = new HashMap<String, String>();
	}
	
	public String[] getBasePathArray() {
		return basePathArray;
	}


	public void setBasePathArray(String[] basePathArray) {
		this.basePathArray = basePathArray;
	}


	/**
	 * load all av in basePath(av.PCname.properties)
	 */
	private void loadAVData() {
		avData = new HashMap<String, Video>();
		List<File> list = new ArrayList<File>();
		for(String basePath : selectedBasePathArray) {
			File baseDir = new File(basePath);
			if(!baseDir.isDirectory()) {
				continue;
			}
			Collection<File> found = FileUtils.listFiles(new File(basePath), null, true);
			list.addAll(found);
		}
		logger.info("loadAVData : total file size : " + list.size());
		
		int unclassifiedNo = 0;
		for(File file : list) {
			String name 		= file.getName();
			String filename 	= AVUtils.getFileName(file);
			String extname 		= AVUtils.getFileExtension(file);
			String absolutePath = file.getAbsolutePath();

			if(listBGImageName.equals(name) || historyName.equals(name)) {
				continue;
			}
			
			// parsing name. [레이블][품번][제목][배우]
			String[] data = filename.split("]");
			String studio 	= null;
			String opus 	= null;
			String title 	= noTitle;
			String actress 	= unknownActress;
//				System.out.println(data.length);
			switch(data.length) {
			case 4:
				actress = AVUtils.removeUnnecessaryCharacter(data[3]);
			case 3:
				title 	= AVUtils.removeUnnecessaryCharacter(data[2]);
			case 2:
				opus 	= AVUtils.removeUnnecessaryCharacter(data[1]);
				studio 	= AVUtils.removeUnnecessaryCharacter(data[0]);
				break;
			case 1:
				// 분류되지 않는 파일
				studio 	= unclassifiedStudio;
				opus 	= unclassifiedOpus + unclassifiedNo++;
				title 	= filename;
				actress = unclassifiedActress;
				break;
			default:
				studio 	= AVUtils.removeUnnecessaryCharacter(data[0]);
				opus 	= AVUtils.removeUnnecessaryCharacter(data[1]);
				title 	= AVUtils.removeUnnecessaryCharacter(data[2]);
				for(int i=4; i<data.length; i++) {
					title += " " + AVUtils.removeUnnecessaryCharacter(data[i]);
				}
				title	= AVUtils.removeUnnecessaryCharacter(title);
				actress = AVUtils.removeUnnecessaryCharacter(data[3]);
			}
			
//				System.out.format("%s,%4s,%6s,%5s,%s - %s%n", studio, opus, actress, ext, data.length, title);
			
			Video avopus = null;
			if(avData.containsKey(opus)) {
				avopus = (Video)avData.get(opus);
			} else {
				avopus = new Video(this.basePath, this.editor, this.player, this.noImagePath);
				avopus.setStudio(studio);
				avopus.setOpus(opus);
				avopus.setTitle(title);
				avopus.setActress(actress);
			}
			
			if(video_extensions.indexOf(extname) > -1) {
				avopus.setVideo(absolutePath);
			} 
			else if(cover_extensions.indexOf(extname) > -1) {
				avopus.setCover(absolutePath);
			}
			else if(subtitles_extensions.indexOf(extname) > -1) {
				avopus.setSubtitles(absolutePath);
			}
			else if(overview_extensions.indexOf(extname) > -1) {
				avopus.setOverview(absolutePath);
			}
			else if("log".indexOf(extname) > -1) {
				avopus.setHistory(absolutePath);
			} 
			else {
				avopus.setEtc(absolutePath);
			}
			avData.put(opus, avopus);
		}
		logger.info("loadAVData : total opus size " + avData.size());
	}
	
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
	public List<Video> getAV(String[] selectedBasePathId, String studio, String opus, String title, String actress, 
			boolean addCond, boolean existVideo, boolean existSubtitles, String sortMethod, boolean sortReverse, boolean useCache) {
		logger.info("getAV : params[selectedBasePathId:" + ArrayUtils.toString(selectedBasePathId) + ", studio:" + studio + ", opus:" + opus + ", title:" + title + ", actress:" + actress + ", addCond:" + addCond + ", existVideo:" + existVideo + ", existSubtitles:" + existSubtitles + ", sortMethod:" + sortMethod + ", sortReverse:" + sortReverse + ", useCache:" + useCache + "]");
		
//		basePathArray = basePath.split(";");

		if(selectedBasePathId != null && selectedBasePathId.length > 0) {
			useCache = false;
			selectedBasePathArray = new String[selectedBasePathId.length];
			for(int i=0; i<selectedBasePathId.length; i++) {
				selectedBasePathArray[i] = this.basePathArray[Integer.parseInt(selectedBasePathId[i])];
			}
		}
		else {
			selectedBasePathArray = basePathArray;
		}
		
		if(!useCache || avData == null) {
			logger.info("getAV : loadAVData()");
			loadAVData();
		}

		setBackgroundImage();
		
		List<Video> list = new ArrayList<Video>();
		for(Object key : avData.keySet()) {
//			System.out.println("key=" + key);
			Video av = (Video)avData.get(key);
			if((studio  == null || studio.trim().length()  == 0 || studio.equalsIgnoreCase(av.getStudio())) 
			&& (opus    == null || opus.trim().length()    == 0 || opus.equalsIgnoreCase(av.getOpus()))
			&& (title   == null || title.trim().length()   == 0 || av.getTitle().toLowerCase().indexOf(title.toLowerCase()) > -1) 
			&& (actress == null || actress.trim().length() == 0 || av.containsActress(actress))
			&& (addCond ? (existVideo ? av.existVideo() : !av.existVideo()) && (existSubtitles ? av.existSubtitles() : !av.existSubtitles()) : true)
			) {
				av.setSortMethod(sortMethod);
				list.add(av);
			}
		}
		logger.info("getAV : found opus size " + list.size());
		if(sortReverse){
			Collections.sort(list, Collections.reverseOrder());
		} else {
			Collections.sort(list);
		}
		this.list = list;
		return list;
	}
	
	/**
	 * Map generic means &lt;Studio-name, opus-count&gt;
	 * @return studio map
	 */
	public Map<String, Integer> getStudioMap() {
		Map<String, Integer> studioMap = new HashMap<String, Integer>();
		for(Object key : avData.keySet()) {
			Video av = avData.get(key);
			String studio = av.getStudio().toUpperCase();
			Integer count = new Integer(0);
			if(studioMap.containsKey(studio)) {
				count = (Integer)studioMap.get(studio);
			}
			count += new Integer(1);
			studioMap.put(studio, count);
		}
		Map<String, Integer> retMap = new TreeMap<String, Integer>(studioMap);
		return retMap;
	}
	
	/**
	 * Map generic means &lt;actress-name, opus-count&gt;
	 * @return actress map
	 */
	public Map<String, Integer> getActressMap() {
		Map<String, Integer> actressMap = new HashMap<String, Integer>();
		for(Object key : avData.keySet()) {
			Video av = avData.get(key);
			List<String> actressList = av.getActressList();
			for(String actress : actressList) {
				Integer count = new Integer(0);
				String reverseName = reverseName(actress);
				if(actressMap.containsKey(actress)) {
					count = (Integer)actressMap.get(actress);
				} 
				else if(actressMap.containsKey(reverseName)) {
					count = (Integer)actressMap.get(reverseName);
					actress = reverseName;
				}
				count += new Integer(1);
				actressMap.put(actress, count);
			}
		}
		Map<String, Integer> retMap = new TreeMap<String, Integer>(actressMap);
		return retMap;
	}
	private String reverseName(String name) {
		if(name == null) return null;
		String[] nameArr = StringUtils.split(name);
		String retName = "";
		for(int i=nameArr.length; i>0; i--) {
			retName += nameArr[i-1] + " ";
		}
		return retName.trim();
	}

	/**
	 * background-image file copy from backgroundImagePoolPath to basePath 'listBGImg.jpg' 
	 */
	private void setBackgroundImage() {
		try {
			String[] bgImgPoolPath = backgroundImagePoolPath.split(";");
			List<File> list = new ArrayList<File>();
			for(String bgImgPath : bgImgPoolPath) {
				list.addAll(FileUtils.listFiles(new File(bgImgPath), new String[]{"jpg"}, true));
			}
			// random select
			Random oRandom = new Random();
		    int index = oRandom.nextInt(list.size());
		    File src  = list.get(index);
		    currBGImageFile = new File(basePathArray[0], listBGImageName);
		    FileUtils.copyFile(src, currBGImageFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 
	 * @return background-image file
	 */
	public File getListBGImageFile() {
		return new File(basePathArray[0], this.listBGImageName);
	}

	public File getImageFile(String selectedOpus, Model model) {
		File imageFile = null;
		if(listBGImageName.equals(selectedOpus) || selectedOpus.trim().length() == 0) {
			imageFile = getListBGImageFile();
			if(model != null)
				model.addAttribute("isBGImage", true);
		}
		else {
			for(Object key : avData.keySet()) {
				if(selectedOpus.equals(key)) {
					Video av = avData.get(key);
					imageFile = av.getCoverImageFile();
					if(model != null)
						model.addAttribute("isBGImage", false);
				}
			}
		}		
		return imageFile;
	}

	public File getImageFile(String opus) {
		return getImageFile(opus, null);
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
			params.put("listViewType ", listViewType); 
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
		if(avData == null) {
			loadAVData();
		}
		return avData.get(opus);
	}

	@Override
	public void saveOverview(String opus, String overViewTxt) {
		getVideo(opus).saveOverViewTxt(overViewTxt);
	}

	@Override
	public void deleteVideo(String opus) {
		getVideo(opus).deleteOpus();
		avData.remove(opus);
	}

	@Override
	public void playVideo(String opus) {
		getVideo(opus).playVideo();
		history.put(opus, "play");
	}

	@Override
	public void editSubtitles(String opus) {
		getVideo(opus).editSubtitles();
	}

	@Override
	public void playRandomVideo() {
		int listSize = list.size();
		int loopCount = 0;    
		Random random = new Random();
		Video av = list.get(random.nextInt(listSize));

	    while(history.containsKey(av.getOpus())) {
	    	if(loopCount++ > 100) {
	    		logger.warn("100번 뽑아도 안나온다. 다음 기회에");
	    		break;
	    	}
	    	av = list.get(random.nextInt(listSize));
	    }
		av.playVideo();
		history.put(av.getOpus(), "play");
	}

	@Override
	public File getBGImageFile(String curr) {
		if(curr == null || curr.trim().length() == 0) {
			this.setBackgroundImage();
		}
		logger.debug(currBGImageFile.getAbsolutePath());
		return currBGImageFile;
	}


}
