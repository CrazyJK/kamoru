package com.kamoru.util.java.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FileUtils {
	
	/**
	 * 
	 * @param path 검색할 폴더
	 * @param extension 파일 확장자 배열
	 * @param searchName 검색 파일명
	 * @param isSubSearch if true, 하위 폴더 검색
	 * @param beanClassName 파일을 담을 객체 이름. if null, use java.io.File
	 * @return beanClass List
	 * @throws IOException
	 */
	public static List getFileList(String path, String[] extension, String searchName, boolean isSubSearch, String beanClassName) throws IOException {
		if(path == null) {
			throw new IOException("input path is null");
		}
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
	
	/**
	 * 
	 * @param list getFileList에서 return된 list
	 * @param sortType 0:name, 1:path, 2:size, 3:last_modified 
	 * @param reverse if true, reverse order
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static List sort(List list, int sortType, boolean reverse){
		
		for(int i=0; i<list.size(); i++) {
			FileBean fb = (FileBean)list.get(i);
			fb.setSortMethod(sortType);
		}
		
		if(reverse){
			Collections.sort(list, Collections.reverseOrder());
		}else{
			Collections.sort(list);
		}
		return list;
	}

	/**
	 * 
	 * @param list getFileList에서 return된 list
	 * @return
	 */
	public static List getSameSizeFileList(List list) {
		List samelist = new ArrayList();
		for(int i=0; i<list.size(); i++) {
			FileBean fb1 = (FileBean)list.get(i);
			long size1 = fb1.getSize();
			boolean firstCompare = true;
			for(int j=i+1; j<list.size(); j++) {
				FileBean fb2 = (FileBean)list.get(j);
				long size2 = fb2.getSize(); 
				if(size1 == size2) {
					if(firstCompare) {
						samelist.add(fb1);
					}
					samelist.add(fb2);
					list.remove(fb2);
				}
			}
		}
		return samelist;
	}

	public static boolean copy(File src, File dest) {
		FileChannel fcin = null;
		FileChannel fcout = null;
		try {
			if(dest.isDirectory()) {
				File fParent = new File(dest.getParent());
				if(!fParent.exists()) {
					fParent.mkdir();
				}
				dest.createNewFile();
			}
			fcin =  new FileInputStream(src).getChannel();
			fcout = new FileOutputStream(dest).getChannel();
			fcin.transferTo(0, fcin.size(), fcout);
			return true;
		} catch(IOException e) {
			e.printStackTrace();
			return false;
		} finally {
			if(fcout != null) try{fcout.close();}catch(Exception e){}
			if(fcin  != null) try{fcin.close();}catch(Exception e){}
		}

	}
}
