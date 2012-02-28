package kamoru.frmwk.util;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FileUtils {
	
	public static List getFileList(String path, String[] extension, String searchName, boolean isSubSearch, String beanClassName) throws IOException {
		File dir = new File(path);
		if(!dir.exists()) {
			throw new IOException("The path[" + path + "] does not exist!");
		}
		else {
			if(!dir.isDirectory()) {
				throw new IOException("The path[" + path + "] is not a directory!");
			}
			else {
				FilenameFilter filter = new FileExtensionFilter(extension);
				List list = new ArrayList();
				return getFileList(list, path, filter, searchName, isSubSearch, beanClassName);
			}
		}
	}
	
	private static List getFileList(List list, String path, FilenameFilter filter, String searchName, boolean isSubSearch, String beanClassName) throws RuntimeException {
		File[] files = new File(path).listFiles(filter);
		for(File f: files) {
			if(f.isDirectory() && isSubSearch) {
				getFileList(list, f.getAbsolutePath(), filter, searchName, isSubSearch, beanClassName);
			}
			else {
				if(searchName == null || searchName.trim().length() == 0 
						|| f.getName().toLowerCase().indexOf(searchName.toLowerCase()) > -1) {

					if(beanClassName == null || beanClassName.trim().length() ==0) {
						list.add(f);
					}
					else {
						Class clazz = null;
						try {
							clazz = Class.forName(beanClassName);
						} catch (ClassNotFoundException e) {
							throw new RuntimeException(e);
						}
						Class[] parameterTypes = {File.class};
						Constructor cons = null;
						try {
							cons = clazz.getConstructor(parameterTypes);
						} catch (NoSuchMethodException e) {
							throw new RuntimeException(e);
						}
						Object[] args = {f};
						Object instance = null;
						try {
							instance = cons.newInstance(args);
						} catch (InstantiationException e) {
							throw new RuntimeException(e);
						} catch (IllegalAccessException e) {
							throw new RuntimeException(e);
						} catch (InvocationTargetException e) {
							throw new RuntimeException(e);
						}
						list.add(instance);
					}
				}
			}
		}
		return list;
	}
	
	public static List sort(List list, int sortMethod, boolean reverse){
		if(reverse){
			Collections.sort(list, Collections.reverseOrder());
		}else{
			Collections.sort(list);
		}
		return list;
	}

}
