package kamoru.app.video.av;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import kamoru.frmwk.util.FileUtils;

public class AVCollectionCtrl {

	public String basePath = "/home/kamoru/ETC/collection";
	public String av_extensions = "avi,mpg,wmv,mp4";
	public String cover_extensions = "jpg,jpeg";
	public String subtitles_extensions = "smi,srt";
	public String overview_extensions = "txt,html";
	
	
	public AVCollectionCtrl() {
		
	}
	
	public Map getAVData() {
		Map map = new HashMap();
		try {
			List list = FileUtils.getFileList(basePath, null, null, true, null);
			System.out.println("size " + list.size());
			
			int unclassifiedNo = 0;
			for(Object o : list) {
				File f = (File)o;
				String filename = f.getName();
				
				// parsing name. [레이블][품번][제목][배우]
				String[] data = filename.split("]");
				String label 	= null;
				String opus 	= null;
				String title 	= null;
				String actress 	= null;
				String ext = filename.substring(filename.lastIndexOf(".")+1);
				boolean canBeClassified = true;
//				System.out.println(data.length);
				switch(data.length) {
				case 1:
				case 2:
					// 분류되지 않는 파일
					label 	= "unclassified";
					opus 	= "unclassified" + unclassifiedNo++;
					title 	= filename;
					actress = "unclassified";
					break;
				case 3:
				case 4:
					label 	= removeFirstCharacter(data[0]);
					opus 	= removeFirstCharacter(data[1]);
					title 	= removeFirstCharacter(data[2]);
					actress = "NoName";
					break;
				default:
					label 	= removeFirstCharacter(data[0]);
					opus 	= removeFirstCharacter(data[1]);
					title 	= removeFirstCharacter(data[2]);
					actress = removeFirstCharacter(data[3]);
				}
				
//					System.out.format("%s,%4s,%6s,%5s,%s - %s%n", label, opus, actress, ext, data.length, title);
				
				AVOpus avopus = null;
				if(map.containsKey(opus)) {
					avopus = (AVOpus)map.get(opus);
				} else {
					avopus = new AVOpus();
					avopus.setLabel(label);
					avopus.setOpus(opus);
					avopus.setActress(actress);
					avopus.setTitle(title);
				}
				
				if(av_extensions.indexOf(ext) > -1) {
					avopus.setAvFilename(f.getAbsolutePath());
				} 
				else if(cover_extensions.indexOf(ext) > -1) {
					avopus.setAvCover(f.getAbsolutePath());
				}
				else if(subtitles_extensions.indexOf(ext) > -1) {
					avopus.setAvSubtutles(f.getAbsolutePath());
				}
				else if(overview_extensions.indexOf(ext) > -1) {
					avopus.setAvOverview(f.getAbsolutePath());
				}
				map.put(opus, avopus);
				
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return map;
	}
	
	private String removeFirstCharacter(String str) {
		return str == null || str.trim().length() == 0 ? "" : (str.startsWith("[") ? str.substring(1) : str);
	}
	
	public List getAV(Map map, String label, String opus, String title, String actress, boolean existSubtitles) {
		List list = new ArrayList();
		for(Object key : map.keySet()) {
			System.out.println("key=" + key);
			AVOpus av = (AVOpus)map.get(key);
			if((label != null && label.equalsIgnoreCase(av.getLabel())) 
					|| (opus != null && opus.equalsIgnoreCase(av.getOpus()))
					|| (title != null && av.getTitle().toLowerCase().indexOf(title.toLowerCase()) > -1) 
					|| (actress != null && av.getActress().toLowerCase().indexOf(actress.toLowerCase()) > -1)
					|| (existSubtitles && av.getAvSubtutles() != null)) {
				list.add(av);
			}
		}		
		return list;
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		AVCollectionCtrl ctrl = new AVCollectionCtrl();
		Map map = ctrl.getAVData();
//		for(Object key : map.keySet()) {
//			System.out.println("key=" + key);
//			AVOpus av = (AVOpus)map.get(key);
//			System.out.format("\t레이블:%s][품번:%s][배우:%s][제목:%s%n",av.getLabel(), av.getOpus(), av.getActress(), av.getTitle());
//			System.out.format("\t%s%n \t%s%n \t%s%n \t%s%n", av.getAvFilename(), av.getAvCover(), av.getAvSubtutles(), av.getAvOverview());
//		}
		List list = ctrl.getAV(map, null, "품번4", null, null, true);
		for(Object o : list) {
			AVOpus av = (AVOpus)o;
			System.out.format("\t레이블:%s][품번:%s][배우:%s][제목:%s%n",av.getLabel(), av.getOpus(), av.getActress(), av.getTitle());
			System.out.format("\t%s%n \t%s%n \t%s%n \t%s%n%n", av.getAvFilename(), av.getAvCover(), av.getAvSubtutles(), av.getAvOverview());
		}
	}
	
}

class AVOpus {
	protected String label;
	protected String opus;
	protected String actress;
	protected String title;
	
	protected String avFilename;
	protected String avSubtutles;
	protected String avCover;
	protected String avOverview;
	
	public AVOpus() {
		
	}
	
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public String getOpus() {
		return opus;
	}
	public void setOpus(String opus) {
		this.opus = opus;
	}
	public String getActress() {
		return actress;
	}
	public void setActress(String actress) {
		this.actress = actress;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getAvFilename() {
		return avFilename;
	}
	public void setAvFilename(String avFilename) {
		this.avFilename = avFilename;
	}
	public String getAvSubtutles() {
		return avSubtutles;
	}
	public void setAvSubtutles(String avSubtutles) {
		this.avSubtutles = avSubtutles;
	}
	public String getAvCover() {
		return avCover;
	}
	public void setAvCover(String avCover) {
		this.avCover = avCover;
	}
	public String getAvOverview() {
		return avOverview;
	}
	public void setAvOverview(String avOverview) {
		this.avOverview = avOverview;
	}
	
	
	
}
