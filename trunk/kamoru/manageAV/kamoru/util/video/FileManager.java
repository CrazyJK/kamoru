package kamoru.util.video;
import java.util.*;
import java.io.*;
/**
 * @author  kamoru
 */
public class FileManager {

	private String path;
	private ArrayList<FileBean> list;
	private long totalSize;
	private int totalCount;
	
	public FileManager(String path, String filterStr){
		this.path = path;
		this.list = new ArrayList<FileBean>();

		File dir = new File(this.path);
		if(dir.isDirectory()) {
			
			String[] filenames = null;
			String[] extensions = null;
			long fromLastModified = 0l;
			long toLastModified = 0l;

			// name=xxx;extension=avi.mpg.wmv;days=30
			String[] filters = filterStr.split(":");
			for (String filterPair : filters) {
				System.out.println("filterPair : " + filterPair);
				String[] filter = filterPair.split("=");
				if(filter.length < 2) {
					continue;
				}
				if("name".equalsIgnoreCase(filter[0])) {
					filenames = filter[1].split(",");
					if(filenames != null)
						for(String s : filenames) {
							System.out.println("name :" + s);
						}
				} else if("extension".equalsIgnoreCase(filter[0])) {
					extensions = filter[1].split(",");
					if(extensions != null)
						for(String s : extensions) {
							System.out.println("ext : " + s);
						}
				} else if("fromDay".equalsIgnoreCase(filter[0])) {
					fromLastModified = System.currentTimeMillis() - Long.parseLong(filter[1])*24*60*60*1000l;
					System.out.println(fromLastModified);
				} else if("toDay".equalsIgnoreCase(filter[0])) {
					toLastModified = System.currentTimeMillis() - Long.parseLong(filter[1])*24*60*60*1000l;
					System.out.println(toLastModified);
				}
			}
			
			FileFilter filter = new FileFilterImpl(filenames, extensions, fromLastModified, toLastModified);
			
			listFile(dir, filter);
		}
		else {
			new RuntimeException("Not a directory : " + this.path);
		}
	}

	public ArrayList<FileBean> getList() {
		return this.list;
	}
	
	private void listFile(File dir, FileFilter filter) { 
		File[] files = dir.listFiles(filter);
		for(File f: files) {
			if(f.isDirectory())	{
				listFile(f, filter);
			}else{
				FileBean bean = new FileBean(f);
				list.add(bean);
				totalSize += bean.getSize();
				totalCount ++;
			}
		}
	}

	@SuppressWarnings("unchecked")
	private void sort(int sortMethod, boolean reverse){
		for(FileBean bean : list){
			bean.setSortMethod(sortMethod);
		}
		if(reverse){
			Comparator<FileBean> c = Collections.reverseOrder();
			Collections.sort(list, c);
		}else{
			Collections.sort(list);
		}
	}

	private void writeFile(String str, String path) {
		PrintWriter writer = null;
		try {
			writer = new PrintWriter(new FileWriter(new File(path)));
			writer.print(str);
			writer.flush();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if(writer != null) writer.close();
		}
		
	}

	public void sysout(){
		for(FileBean bean : list){
			System.out.format("%s %s%n", bean.getLastModified() , bean.toString()); 
		}
		System.out.format("Total count : %s, size : %s%n", totalCount, totalSize);
	}
	
	public void makeKPL(String dest) {
		sort(FileBean.SORT_LASTMODIFIED, false);
		StringBuilder sb = new StringBuilder();
		sb.append("[playlist]").append(System.getProperty("line.separator"));
		int i=0;
		for(FileBean bean : list) {
			i++;
			sb.append("File").append(i).append("=").append(bean.getFullName()).append(System.getProperty("line.separator"));
			sb.append("Title").append(i).append("=").append(bean.getName()).append(System.getProperty("line.separator"));
			sb.append("Length").append(i).append("=").append(bean.getSize()).append(System.getProperty("line.separator"));
			sb.append("Played").append(i).append("=0").append(System.getProperty("line.separator"));
		}
		sb.append("NumberOfEntries=").append(i).append(System.getProperty("line.separator"));
		sb.append("Version=2").append(System.getProperty("line.separator"));
		sb.append("CurrentIndex=1").append(System.getProperty("line.separator"));
				
		System.out.println(sb.toString());
		
		writeFile(sb.toString(), dest);
	}
	
	
	public String jsonText(){
		StringBuilder sb = new StringBuilder();
		sb.append("[");
		for(FileBean bean : list)
		{
			sb.append("{");
			sb.append("NAME : \"").append(bean.getName()).append("\", ");
			sb.append("SIZE : \"").append(bean.getSize()).append("\", ");
			sb.append("SIZECONVERT : \"").append(bean.getSizeConvert()).append("\", ");
			sb.append("PATH : \"").append(bean.getPath()).append("\", ");
			sb.append("URI  : \"").append(bean.getUri()).append("\", ");
			sb.append("MODIFIED : \"").append(bean.getLastModified()).append("\",");
			sb.append("MODIFIEDDATE : \"").append(bean.getLastModifiedDate()).append("\"");
			sb.append("},");
		}
		sb.substring(0, sb.length()-1);
		sb.append("]");
		return sb.toString();
	}
	
	public void moveFile(String dest) {
		for(FileBean bean : list) {
			System.out.println(bean.toString());
//			System.out.println("\tmove to " + bean.moveTo(dest));
		}
	}

	public void copyFile(String dest) {
		for(FileBean bean : list) {
			System.out.println(bean.toString());
//			System.out.println("\tcopy to " + bean.copyTo(dest));
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		try {
			String src = args[0];
			String flt = args[1];
			String cmd = args[2];

			if("out".equalsIgnoreCase(cmd)) {
				FileManager mf = new FileManager(src, flt);
				mf.sysout();
			} 
			else if("kpl".equalsIgnoreCase(cmd)) {
				String dst = args[3];
				FileManager mf = new FileManager(src, flt);
				mf.makeKPL(dst);
			} 
			else if("move".equalsIgnoreCase(cmd)) {
				String dst = args[3];
				FileManager mf = new FileManager(src, flt);
				mf.moveFile(dst);
			}
			else if("copy".equalsIgnoreCase(cmd)) {
				String dst = args[3];
				FileManager mf = new FileManager(src, flt);
				mf.copyFile(dst);
			}
		} catch(ArrayIndexOutOfBoundsException e) {
//			showUsage();
			e.printStackTrace();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	private static void showUsage() {
		System.out.println("Usage: ");
		System.out.println("java FileManager SOURCE CONDITION COMMAND");
		System.out.println();
		System.out.println("    SOURCE               - Source folder");
		System.out.println("    CONDITION");
		System.out.println("        name=:extension=mpg,wmv,mp4:fromDay=30:toDay=5 ");
		System.out.println("    COMMAND");
		System.out.println("        out              - Display list");
		System.out.println("        kpl  dest-file   - Make kpl file to dest");
		System.out.println("        move dest-folder - Move file to dest");
		System.out.println("        copy dest-folder - Copy file to dest");
		System.out.println("ex.");
		System.out.println("    java -classpath . kamoru.util.video.FileManager /home/kamoru/ETC/Download/ name=:extension=mpg,wmv,mp4:fromDay=30:toDay=5 out ");
		System.out.println("    java -classpath . kamoru.util.video.FileManager /home/kamoru/ETC/Download/ name=:extension=mpg,wmv,mp4:fromDay=30:toDay=5 kpl list.kpl ");
		System.out.println("    java -classpath . kamoru.util.video.FileManager /home/kamoru/ETC/Download/ name=:extension=mpg,wmv,mp4:fromDay=30:toDay=5 move /home/user/back ");
	}

}
