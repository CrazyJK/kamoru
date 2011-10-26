package kamoru.util;

import java.io.File;
import java.io.FileNotFoundException;

import org.apache.log4j.PropertyConfigurator;

public class LogUtil {

	String file;
	
	public LogUtil(){
		this.file = System.getProperty("log4j.configuration");
	}
	
	public LogUtil(String file){
		this.file = file;
	}
	
	public boolean log4jLoad(){
		try
		{
			//String file = config.getInitParameter("log4j-init-file");
			if(file == null){
				throw new Exception("log4j-init-file not exist");
			}else{
				File f = new File(file);
				if(!f.exists()){
					throw new FileNotFoundException("log4j configuration file not found. [" + file + "]");
				}
				PropertyConfigurator.configure(file);
				System.out.println("Log4j Init.");
				return true;
			}
		}catch(Exception e){
			System.out.println("ERROR: Log4j init fail. \n\t" + e.getMessage());
			return false;
		}
	}

}
