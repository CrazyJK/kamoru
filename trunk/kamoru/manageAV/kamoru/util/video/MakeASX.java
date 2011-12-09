package kamoru.util.video;
import java.util.*;
import java.io.*;
public class MakeASX {

	public String makeASXString(String path, String filter) throws Exception{
		StringBuilder sb = new StringBuilder();

		try
		{
			File dirF = new File(path);
			if(!dirF.exists() || !dirF.isDirectory())
			{
				throw new Exception(path + " is not a directory or no exists");
			}
			else
			{
				ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
				getFilelist(list, path, filter);

				if(list.size() == 0)
				{
					sb.append(path + " is empty");			
				}
				else
				{
					System.out.format("File size %d", list.size());
					sb.append("<asx version = \"3.0\" >\n");
					for(int i=0; i<list.size(); i++)
					{
						HashMap<String, String> fileinfo = (HashMap<String, String>)list.get(i);
						sb.append("\t<entry>\n");
						sb.append("\t\t<title>").append(fileinfo.get("NAME")).append("</title>\n");
						sb.append("\t\t<ref href = \"").append(fileinfo.get("PATH") ).append(System.getProperty("file.separator")).append(fileinfo.get("NAME")).append("\" />\n");
						sb.append("\t</entry>\n");
					}
					sb.append("</asx>\n");
				}
			}
		}
		catch(Exception e)
		{
			throw e;
		}
		return sb.toString();
	}
	
	void getFilelist(ArrayList<HashMap<String, String>> list, String dir, String filter) throws Exception
	{
		String[] filterArr = filter.split(" ");
		
		ArrayList<String> dirList = new ArrayList<String>();
		File[] files = new File(dir).listFiles();
		for(File f: files)
		{
			if(f.isDirectory())
			{
				dirList.add(f.getAbsolutePath());
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
		
		for(String path: dirList)
		{
			getFilelist(list, path, filter);
		}
	}

	HashMap<String, String> getFileInfo(File f) throws Exception
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
	
	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception {
		String basepath = "E:\\av";
		if(args != null && args.length > 0){
			basepath = args[0];
		}
		MakeASX make = new MakeASX();
		String str = make.makeASXString(basepath, VIDEO_FILTER);
		PrintWriter writer = new PrintWriter(new FileWriter(new File(DESKTOP_PATH + new File(basepath).getPath().substring(3) + ".asx")));
		writer.write(str);
		writer.flush();
		writer.close();
	}
	
	public static final String VIDEO_FILTER = "asf avi divx mkv mp4 mpg rmvb wmv";
	public static final String DESKTOP_PATH = "C:\\Users\\kAmOrU\\Desktop\\";

}
