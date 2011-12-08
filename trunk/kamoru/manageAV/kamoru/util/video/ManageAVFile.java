package kamoru.util.video;
import java.util.*;
import java.io.*;
public class ManageAVFile {

	public String path;
	public String filter;
	public ArrayList<HashMap<String, String>> list;
	public ManageAVFile(String path, String filter){
		this.path = path;
		this.filter = filter;
		this.list = new ArrayList<HashMap<String, String>>();

		getFilelist(list, path, filter);
	}

	public void moveOldFile() throws Exception{
		
	}
	

	public void getFilelist(ArrayList<HashMap<String, String>> list, String dir, String filter)
	{
		String[] filterArr = filter.split(" ");
		
		ArrayList<String> subDirList = new ArrayList<String>();
		File[] files = new File(dir).listFiles();
		for(File f: files)
		{
			if(f.isDirectory())
			{
				subDirList.add(f.getAbsolutePath());
			}
			else
			{
				String name = f.getName().toLowerCase();
				for(int i=0; i<filterArr.length; i++)
				{
					if(name.endsWith(filterArr[i]))
					{
						list.add(getFileInfo(f));
					}
				}
			}
		}
		
		for(String subPath: subDirList)
		{
			getFilelist(list, subPath, filter);
		}
	}

	HashMap<String, String> getFileInfo(File f)
	{
		String path = f.getParent().replace('\\','/');
		
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("NAME", f.getName());
		map.put("SIZE", String.valueOf(f.length()));
		map.put("PATH", path);
		map.put("URI",  String.valueOf(f.toURI()));
		map.put("MODIFIED", String.valueOf(f.lastModified()));
		return map;
	}

	public void display()
	{
		if(list.size() > 0)
		{
			for(HashMap<String, String> file : list)
			{
				System.out.format("%s : %S : %S : %S : %S%n", 
						file.get("NAME"), file.get("SIZE"), file.get("PATH"), file.get("URI"), file.get("MODIFIED"));
			}
		}
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String path   = args != null && args.length > 0 ? args[0] : "/home/kamoru/ETC";
		String filter = args != null && args.length > 1 ? args[1] : "mp3";
		ManageAVFile mf = new ManageAVFile(path, filter);
		mf.display();
	}

}
