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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import kamoru.frmwk.util.FileUtils;

public class AVCollectionCtrl {
	protected static final Log logger = LogFactory.getLog(AVCollectionCtrl.class);

	private AVProp prop = AVProp.getInstance();
	private static Map<String, AVOpus> avData;
	private String[] basePathArray;
	
	public final String listImageName = "listImg.jpg";
	public final String unclassifiedStudio  = "_unclassified";
	public final String unclassifiedOpus    = "_unclassified";
	public final String unclassifiedActress = "_unclassified";
	public final String unknownActress		= "_unknownActress";
	
	public AVCollectionCtrl() {
		basePathArray = prop.basePath.split(";");
	}
	
	private void loadAVData() {
		avData = new HashMap<String, AVOpus>();
		List<File> list = new ArrayList<File>();
		for(String basePath : basePathArray) {
			Collection<File> found = org.apache.commons.io.FileUtils.listFiles(new File(basePath), null, true);
			list.addAll(found);
		}
		logger.debug("loadAVData : total file size : " + list.size());
		
		int unclassifiedNo = 0;
		for(Object o : list) {
			File f = (File)o;
			String filename = f.getName();
			
			if(listImageName.equals(filename)) {
				continue;
			}
			
			// parsing name. [레이블][품번][제목][배우]
			String[] data = filename.split("]");
			String studio 	= null;
			String opus 	= null;
			String title 	= null;
			String actress 	= null;
			String ext 		= filename.substring(filename.lastIndexOf(".")+1).trim().toLowerCase();
//				System.out.println(data.length);
			switch(data.length) {
			case 1:
			case 2:
				// 분류되지 않는 파일
				studio 	= unclassifiedStudio;
				opus 	= unclassifiedOpus + unclassifiedNo++;
				title 	= filename;
				actress = unclassifiedActress;
				break;
			case 3:
			case 4:
				studio 	= removeUnnecessaryCharacter(data[0]);
				opus 	= removeUnnecessaryCharacter(data[1]);
				title 	= removeUnnecessaryCharacter(data[2]);
				actress = unknownActress;
				break;
			default:
				studio 	= removeUnnecessaryCharacter(data[0]);
				opus 	= removeUnnecessaryCharacter(data[1]);
				title 	= removeUnnecessaryCharacter(data[2]);
				actress = removeUnnecessaryCharacter(data[3]);
			}
			
//				System.out.format("%s,%4s,%6s,%5s,%s - %s%n", studio, opus, actress, ext, data.length, title);
			
			AVOpus avopus = null;
			if(avData.containsKey(opus)) {
				avopus = (AVOpus)avData.get(opus);
			} else {
				avopus = new AVOpus();
				avopus.setStudio(studio);
				avopus.setOpus(opus);
				avopus.setActress(actress);
				avopus.setTitle(title);
			}
			
			if(prop.av_extensions.indexOf(ext) > -1) {
				avopus.setVideo(f.getAbsolutePath());
			} 
			else if(prop.cover_extensions.indexOf(ext) > -1) {
				avopus.setCover(f.getAbsolutePath());
			}
			else if(prop.subtitles_extensions.indexOf(ext) > -1) {
				avopus.setSubtitles(f.getAbsolutePath());
			}
			else if(prop.overview_extensions.indexOf(ext) > -1) {
				avopus.setOverview(f.getAbsolutePath());
			}
			avData.put(opus, avopus);
		}
		logger.debug("loadAVData : total opus size " + avData.size());
	}
	
	private String removeUnnecessaryCharacter(String str) {
		// 공백 제거, [ 빼고, 확장자 빼기
		str = str.trim();
		str = str.startsWith("[") ? str.substring(1) : str;
		int lastIndex = str.lastIndexOf(".");
		str = lastIndex > 0 ? (str.substring(lastIndex).length() < 5 ? str.substring(0, lastIndex) : str) : str;
		return str;
	}
	
	public List<AVOpus> getAV() {
		List<AVOpus> list = new ArrayList<AVOpus>();
		for(Object key : avData.keySet()) {
			AVOpus av = (AVOpus)avData.get(key);
			list.add(av);
		}
		Collections.sort(list);
		return list;
	}
	
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
			&& (actress == null || actress.trim().length() == 0 || av.getActress().toLowerCase().indexOf(actress.toLowerCase()) > -1)
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
	
	public Map<String, Integer> getStudios() {
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
	
	public Map<String, Integer> getActress() {
		Map<String, Integer> actressMap = new HashMap<String, Integer>();
		for(Object key : avData.keySet()) {
			AVOpus av = avData.get(key);
			List<String> actressList = av.getActressList();
			for(String actress : actressList) {
				Integer count = new Integer(0);
				if(actressMap.containsKey(actress)) {
					count = (Integer)actressMap.get(actress);
				}
				count += new Integer(1);
				actressMap.put(actress, count);
			}
		}
		Map<String, Integer> retMap = new TreeMap<String, Integer>(actressMap);
		return retMap;
	}
	
	private void setBackgroundImage() {
		try {
			List<?> list = FileUtils.getFileList(prop.backgroundImagePoolPath, new String[]{"jpg"}, null, true, null);
			// random select
			Random oRandom = new Random();
		    int index = oRandom.nextInt(list.size());
		    File src  = (File)list.get(index);
		    File dest = new File(basePathArray[0], listImageName);
		    FileUtils.copy(src, dest);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * @param args
	public static void main(String[] args) {
		AVCollectionCtrl ctrl = new AVCollectionCtrl();
		List<AVOpus> list = ctrl.getAV(null, "품번4", null, null, true);
		for(Object o : list) {
			AVOpus av = (AVOpus)o;
			System.out.format("\t레이블:%s][품번:%s][배우:%s][제목:%s%n",av.getStudio(), av.getOpus(), av.getActress(), av.getTitle());
			System.out.format("\t%s%n \t%s%n \t%s%n \t%s%n%n", av.getVideo(), av.getCover(), av.getSubtitles(), av.getOverview());
		}
	}
	 */
	
}

