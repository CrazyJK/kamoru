package kamoru.util.video;
import java.util.*;
import java.io.*;
public class FileManager {

	public String path;
	public String filter;
	public ArrayList<FileBean> list;
	public long totalSize;
	public int totalCount;
	
	public FileManager(String path, String filter){
		this.path = path;
		this.filter = filter;
		this.list = new ArrayList<FileBean>();

		listFile(path);
	}

	public ArrayList<FileBean> getList() {
		return list;
	}
	
	public void listFile(String dir) { 
//		ArrayList<String> subDirList = new ArrayList<String>();
		File[] files = new File(dir).listFiles(new FiletypeFilter(filter));
		for(File f: files){
			if(f.isDirectory())	{
//				subDirList.add(f.getAbsolutePath());
				listFile(f.getAbsolutePath());
			}else{
				FileBean bean = new FileBean(f);
				list.add(bean);
				totalSize += bean.getSize();
				totalCount ++;
			}
		}
		
//		for(String subPath: subDirList){
//			getFilelist(subPath);
//		}
	}

	public void sort(int sortMethod, boolean reverse){
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
	
	public void display(){
		for(FileBean bean : list){
			System.out.format("%s%n", bean.toString()); 
		}
		System.out.format("Total count : %s, size : %s%n", totalCount, totalSize);
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
	
	@SuppressWarnings("unchecked")
	public void moveOldFile(String dest) {
		Collections.sort(list);
		
		long tenPerSize = (long) (totalSize * 0.1d);
		long fileSize = 0;
		int fileCount = 0;
		for(FileBean bean : list){
			fileSize += bean.getSize();
			if(fileSize < tenPerSize) {
				fileCount++;
				System.out.println(bean.toString());
//				System.out.println("\tmove to " + bean.moveTo(dest));
				System.out.println("\tcopy to " + bean.copyTo(dest));
			}else{
				break;
			}
		}
		System.out.format("Select count : %s, size : %s - Total count : %s, size : %s%n", fileCount, fileSize, totalCount, totalSize);
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String path   = args != null && args.length > 0 ? args[0] : "/home/kamoru/ETC";
		String filter = args != null && args.length > 1 ? args[1] : "*";
		String moveToDest = args != null && args.length > 2 ? args[2] : "/home/kamoru/old";
		FileManager mf = new FileManager(path, filter);
		mf.display();
		//mf.moveOldFile(moveToDest);
	}

}
