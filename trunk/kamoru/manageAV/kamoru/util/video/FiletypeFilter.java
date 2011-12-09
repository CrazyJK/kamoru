package kamoru.util.video;

import java.io.File;
import java.io.FilenameFilter;

public class FiletypeFilter implements FilenameFilter {

	String[] filters = null;
	public FiletypeFilter(String filter){
		if(filter == null || "*".equals(filter)){
			filters = null;
		}else{
			filters = filter.toLowerCase().split(" ");
		}
	}
	public boolean accept(File dir, String name){
		dir = new File(dir.getAbsolutePath(), name);
		boolean rtn = false;
		if(dir.isDirectory()){
			rtn = true;
		}else{
			if(filters != null){
				name = name.toLowerCase();
				for(int i=0; i<filters.length; i++){
					if(name.endsWith(filters[i])){
						rtn = true;
						break;
					}
				}
			}else{
				rtn = true;
			}
		}
		return rtn;
	}

}
