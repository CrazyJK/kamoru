package kamoru.app.video.av;

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

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * AV Collection controller<br>
 * data loading, searching, background-image setting
 * @author kamoru
 *
 */
public class AVCollectionCtrl {
	protected static final Log logger = LogFactory.getLog(AVCollectionCtrl.class);

	private AVProp prop = AVProp.getInstance();
	public static Map<String, AVOpus> avData;
	private String[] basePathArray;
	
	public final String listBGImageName = "listBGImg.jpg";
	public final String historyName   	= "history.log";
	public final String unclassifiedStudio  = "_unclassified";
	public final String unclassifiedOpus    = "_unclassified";
	public final String unclassifiedActress = "_unclassified";
	public final String unknownActress		= "_unknownActress";
	public final String noTitle				= "NoTitle";
	
	/**
	 * AV Control Unit
	 */
	public AVCollectionCtrl() {
		basePathArray = prop.basePath.split(";");
	}
	
	/**
	 * load all av in basePath(av.PCname.properties)
	 */
	private void loadAVData() {
		avData = new HashMap<String, AVOpus>();
		List<File> list = new ArrayList<File>();
		for(String basePath : basePathArray) {
			Collection<File> found = FileUtils.listFiles(new File(basePath), null, true);
			list.addAll(found);
		}
		logger.debug("loadAVData : total file size : " + list.size());
		
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
			
			AVOpus avopus = null;
			if(avData.containsKey(opus)) {
				avopus = (AVOpus)avData.get(opus);
			} else {
				avopus = new AVOpus();
				avopus.setStudio(studio);
				avopus.setOpus(opus);
				avopus.setTitle(title);
				avopus.setActress(actress);
			}
			
			if(prop.video_extensions.indexOf(extname) > -1) {
				avopus.setVideo(absolutePath);
			} 
			else if(prop.cover_extensions.indexOf(extname) > -1) {
				avopus.setCover(absolutePath);
			}
			else if(prop.subtitles_extensions.indexOf(extname) > -1) {
				avopus.setSubtitles(absolutePath);
			}
			else if(prop.overview_extensions.indexOf(extname) > -1) {
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
		logger.debug("loadAVData : total opus size " + avData.size());
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
	public List<AVOpus> getAV(String studio, String opus, String title, String actress, 
			boolean addCond, boolean existVideo, boolean existSubtitles, String sortMethod, boolean sortReverse, boolean useCache) {
		logger.debug("getAV : params{studio:" + studio + ", opus:" + opus + ", title:" + title + ", actress:" + actress + ", addCond:" + addCond + ", existVideo:" + existVideo + ", existSubtitles:" + existSubtitles + ", sortMethod:" + sortMethod + ", sortReverse:" + sortReverse + ", useCache:" + useCache + "}");
		if(!useCache || avData == null) {
			logger.debug("getAV : loadAVData()");
			loadAVData();
		}
		setBackgroundImage();
		
		List<AVOpus> list = new ArrayList<AVOpus>();
		for(Object key : avData.keySet()) {
//			System.out.println("key=" + key);
			AVOpus av = (AVOpus)avData.get(key);
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
		logger.debug("getAV : found opus size " + list.size());
		if(sortReverse){
			Collections.sort(list, Collections.reverseOrder());
		} else {
			Collections.sort(list);
		}
		return list;
	}
	
	/**
	 * Map generic means &lt;Studio-name, opus-count&gt;
	 * @return studio map
	 */
	public Map<String, Integer> getStudioMap() {
		Map<String, Integer> studioMap = new HashMap<String, Integer>();
		for(Object key : avData.keySet()) {
			AVOpus av = avData.get(key);
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
			AVOpus av = avData.get(key);
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
			String[] bgImgPoolPath = prop.backgroundImagePoolPath.split(";");
			List<File> list = new ArrayList<File>();
			for(String bgImgPath : bgImgPoolPath) {
				list.addAll(FileUtils.listFiles(new File(bgImgPath), new String[]{"jpg"}, true));
			}
			// random select
			Random oRandom = new Random();
		    int index = oRandom.nextInt(list.size());
		    File src  = (File)list.get(index);
		    File dest = new File(basePathArray[0], listBGImageName);
		    FileUtils.copyFile(src, dest);
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
}

