package kamoru.system.backup;

import java.io.*;

import kamoru.util.RuntimeUtil;

public class FileCopy {

	static org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(FileCopy.class);

	String source_folder;
	String destination_folder;
	boolean copies_files_changed_on;
	
	public FileCopy(){
		
	}
	public FileCopy(String source, String destination){
		new FileCopy(source, destination, false);
	}
	public FileCopy(String source, String destination, boolean changed){
		source_folder = source;
		destination_folder = destination;
		copies_files_changed_on = changed;
	}
	
	public void setSourceFolder(String source){
		source_folder = source;
	}
	public void setDestinationFolder(String destination){
		destination_folder = destination;
	}
	public void setCopiesFilesChangedOn(boolean changed){
		copies_files_changed_on = changed;
	}
	public void setFolder(String source, String destination){
		setFolder(source, destination, false);
	}
	public void setFolder(String source, String destination, boolean changed){
		source_folder = source;
		destination_folder = destination;
		copies_files_changed_on = changed;
	}
	
	public void startCopy() throws FileNotFoundException
	{
		File sourceFolder = new File(source_folder);
		if(!sourceFolder.exists()){
			throw new FileNotFoundException(source_folder);
		}
		xcopy();
	}
	
	public void xcopy()
	{
		String sourcefilepath = source_folder;
		String destinationfilepath = destination_folder;
		String command = "XCOPY ";
		command += "\"" + sourcefilepath + "\" "; 
		command += "\"" + destinationfilepath + "\" ";
		command += "/E /H /K /Y /S /I ";
		if(copies_files_changed_on){
			command += "/D ";
		}
		if(BackupProperties.display_only.equalsIgnoreCase("true")){
			command += "/L ";
		}
		RuntimeUtil.execute(command);	
	}
			
	public static void main(String[] args) throws Exception {
		FileCopy fc = new FileCopy("F:/data/handy","E:/handy",true);
		fc.startCopy();
	}

}
