package kamoru.system.backup;
import java.util.*;
import java.io.*;
public class BackupProperties {

	private static String PROPERTIES_FILE = System.getProperty("backup_properties");
	
	/** ����� ���� �ʰ�, ������ ������ ǥ�ø� �մϴ� */
	public static String display_only = System.getProperty("display_only");
	
	/** ���� ����/���� ex. F:\data\handy */
	public static String source_folder = System.getProperty("source_folder");
	
	/** ��� ���� ex. E:\handy */
	public static String destination_folder = System.getProperty("destination_folder");
	
	/** ��� ���Ϻ��� ���ο� ���� ���ϸ� �����մϴ�. */
	public static String copies_files_changed_on = System.getProperty("copies_files_changed_on");
	
	public static String LINE = System.getProperty("line.separator");

	public Properties prop;
	
	private static BackupProperties bp = null;
	
	private BackupProperties() throws Exception{
		load();
	}
	
	public static synchronized BackupProperties createInstance() throws Exception{
		if ( bp == null ) {
			bp = new BackupProperties();
		}
		return bp;
	}

	public void load() throws Exception{
		prop = new Properties();
		prop.load(new FileInputStream(PROPERTIES_FILE));
		System.out.println(PROPERTIES_FILE + " loaded");
	}

	public String getProperty(String key){
		return prop.getProperty(key);
	}
	
	public String getProperty(String key, String defaultValue){
		return prop.getProperty(key, defaultValue);
	}

	public ArrayList getProperties(String key){
		ArrayList retList = new ArrayList();
		int cnt = Integer.parseInt(prop.getProperty(key + ".COUNT"));
		for(int i=0 ; i<cnt ; i++){
			retList.add(prop.getProperty(key + "." + (i+1)));
		}
		return retList;
	}
	
	public String toString(){
		return "source_folder=" + source_folder + LINE
			+  "destination_folder=" + destination_folder + LINE
			+  "copies_files_changed_on=" + copies_files_changed_on + LINE;
	}
}
