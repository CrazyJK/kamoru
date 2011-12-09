package kamoru.util.video;

import java.net.URI;
import java.nio.channels.FileChannel;
import java.util.Arrays;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileBean implements Comparable {

	public static final int SORT_NAME = 0;
	public static final int SORT_PATH = 1;
	public static final int SORT_SIZE = 2;
	public static final int SORT_LASTMODIFIED = 3;
	
	private File file;
	private String name;
	private String path;
	private long size;
	private URI uri;
	private long lastModified;
	private int sortMethod;

	public FileBean(File f) {
		this.file = f;
		this.name = f.getName();
		this.path = f.getParent();
		this.size = f.length();
		this.uri  = f.toURI();
		this.lastModified = f.lastModified();
	}
	
	public String toString() {
		return path + File.separator + "==" + name + " (" + getSizeConvert() + ") - " + getLastModifiedDate();
	}
	
	public int getSortMethod() {
		return sortMethod;
	}
	public void setSortMethod(int sortMethod) {
		this.sortMethod = sortMethod;
	}
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public long getSize() {
		return size;
	}

	public void setSize(long size) {
		this.size = size;
	}

	public URI getUri() {
		return uri;
	}

	public void setUri(URI uri) {
		this.uri = uri;
	}

	public long getLastModified() {
		return lastModified;
	}

	public void setLastModified(long lastModified) {
		this.lastModified = lastModified;
	}

	@Override
	public int compareTo(Object arg0) {
		FileBean comparer = (FileBean) arg0;
		switch(sortMethod){
		case SORT_NAME:
			String[] s = {this.name, comparer.name};
			Arrays.sort(s);
			return s[0].equals(this.name) ? -1 : 1; 
		case SORT_PATH:
			String[] p = {this.path, comparer.path};
			Arrays.sort(p);
			return p[0].equals(this.path) ? -1 : 1; 
		case SORT_SIZE:
			return (int) (this.size - comparer.size);
		case SORT_LASTMODIFIED:
			return (int) (this.lastModified - comparer.lastModified);
		default:
			return 0;
		}
	}
	
	public String getLastModifiedDate() {
		return new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm").format(new java.util.Date(lastModified));
	}
	
	public String getSizeConvert() {
		if(size < 1000l){
			return size + "B";
		}else if(size < 1000000l){
			return Math.round(size/1000l) + "KB";
		}else if(size < 1000000000l){
			return Math.round(size/1000000l) + "MB";
		}else if(size < 1000000000000l){
			return Math.round(size/1000000000l) + "GB";
		}else if(size < 1000000000000000l){
			return Math.round(size/1000000000000l) + "TB";
		}else{
			return String.valueOf(size);
		}
	}
	
	public boolean moveTo(String path) {
		File dir = new File(path);
		dir.mkdirs();
		
		return file.renameTo(new File(path, name));
	}
	
	public boolean copyTo(String path) {
		File dest = new File(path, name);
		return copy(file, dest);
	}
	
	private boolean copy(File fOrg, File fTarget){
		try{
			FileInputStream inputStream = new FileInputStream(fOrg);
			if (!fTarget.isFile())
			{
				File fParent = new File (fTarget.getParent());
				if (!fParent.exists())
				{
					fParent.mkdir();
				}
				fTarget.createNewFile();
			}
			FileOutputStream outputStream = new FileOutputStream(fTarget);
			FileChannel fcin =  inputStream.getChannel();
			FileChannel fcout = outputStream.getChannel();
			long size = fcin.size();
			fcin.transferTo(0, size, fcout);
			fcout.close();
			fcin.close();
			outputStream.close();
			inputStream.close();
			return true;
		}catch(IOException e){
			return false;
		}
	}
}
