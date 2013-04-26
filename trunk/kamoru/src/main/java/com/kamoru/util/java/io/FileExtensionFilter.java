package com.kamoru.util.java.io;

import java.io.File;
import java.io.FilenameFilter;

public class FileExtensionFilter implements FilenameFilter {

	String[] extension = null;
	
	/**
	 * @param extension if extension is null, return all file
	 */
	public FileExtensionFilter(String[] extension) {
		this.extension = extension;
	}

	@Override
	public boolean accept(File dir, String name) {
		File file = new File(dir.getAbsolutePath(), name);
		boolean rtn = false;
		if(file.isDirectory()) {
			rtn = true;
		}
		else {
			if(extension != null) {
				name = name.toLowerCase();
				for(int i=0; i<extension.length; i++) {
					if(name.endsWith(extension[i])) {
						rtn = true;
						break;
					}
				}
			}
			else {
				rtn = true;
			}
		}
		return rtn;
	}

}
