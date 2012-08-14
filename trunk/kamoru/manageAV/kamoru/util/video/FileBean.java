package kamoru.util.video;

import java.net.URI;
import java.nio.channels.FileChannel;
import java.util.Arrays;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @author  kamoru
 */
public class FileBean implements Comparable {

	public static final int SORT_NAME = 0;
	public static final int SORT_PATH = 1;
	public static final int SORT_SIZE = 2;
	public static final int SORT_LASTMODIFIED = 3;
	
	/**
	 * @uml.property  name="file"
	 */
	private File file;
	/**
	 * @uml.property  name="name"
	 */
	private String name;
	/**
	 * @uml.property  name="path"
	 */
	private String path;
	/**
	 * @uml.property  name="size"
	 */
	private long size;
	/**
	 * @uml.property  name="uri"
	 */
	private URI uri;
	/**
	 * @uml.property  name="lastModified"
	 */
	private long lastModified;
	/**
	 * @uml.property  name="sortMethod"
	 */
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
		return getLastModifiedDate() + " " + path + File.separator + name + " (" + getSizeConvert() + ")";
	}
	
	/**
	 * @return
	 * @uml.property  name="file"
	 */
	public File getFile() {
		return file;
	}

	/**
	 * @param file
	 * @uml.property  name="file"
	 */
	public void setFile(File file) {
		this.file = file;
	}

	/**
	 * @return
	 * @uml.property  name="name"
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 * @uml.property  name="name"
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return
	 * @uml.property  name="path"
	 */
	public String getPath() {
		return path;
	}

	/**
	 * @param path
	 * @uml.property  name="path"
	 */
	public void setPath(String path) {
		this.path = path;
	}

	/**
	 * @return
	 * @uml.property  name="size"
	 */
	public long getSize() {
		return size;
	}

	/**
	 * @param size
	 * @uml.property  name="size"
	 */
	public void setSize(long size) {
		this.size = size;
	}

	/**
	 * @return
	 * @uml.property  name="uri"
	 */
	public URI getUri() {
		return uri;
	}

	/**
	 * @param uri
	 * @uml.property  name="uri"
	 */
	public void setUri(URI uri) {
		this.uri = uri;
	}

	/**
	 * @return
	 * @uml.property  name="lastModified"
	 */
	public long getLastModified() {
		return lastModified;
	}

	/**
	 * @param lastModified
	 * @uml.property  name="lastModified"
	 */
	public void setLastModified(long lastModified) {
		this.lastModified = lastModified;
	}

	/**
	 * @return
	 * @uml.property  name="sortMethod"
	 */
	public int getSortMethod() {
		return sortMethod;
	}

	/**
	 * @param sortMethod
	 * @uml.property  name="sortMethod"
	 */
	public void setSortMethod(int sortMethod) {
		this.sortMethod = sortMethod;
	}

	@Override
	public int compareTo(Object obj) {
		FileBean comparer = (FileBean)obj;
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
		return new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date(lastModified));
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

	public String getFullName() {
		return this.path + System.getProperty("file.separator") + this.name;
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
