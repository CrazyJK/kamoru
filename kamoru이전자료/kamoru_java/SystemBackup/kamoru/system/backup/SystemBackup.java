package kamoru.system.backup;
import java.io.File;
import java.util.*;
import kamoru.util.*;
public class SystemBackup {

	static org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(SystemBackup.class);
	
	ArrayList sourceList = new ArrayList();
	String destination = null;
	
	String user;
	String pwd;
	ArrayList ownerList;
	String folder;
	
	public SystemBackup() throws Exception{
		initiate();
	}
	
	public void initiate() throws Exception{
		
		//프로퍼티 로드
		BackupProperties bp = BackupProperties.createInstance();
		destination = bp.getProperty("DESTINATION");
		sourceList  = bp.getProperties("SOURCE");
		
		user 		= bp.getProperty("Oracle.user");
		pwd 		= bp.getProperty("Oracle.pwd");
		ownerList 	= bp.getProperties("Oracle.owner");
		folder 		= bp.getProperty("Oracle.folder");

	}
	
	public void startBackup() throws Exception{
		String source = null;
		String destinationfolder = null;
		
		logger.debug(""+sourceList.size());
		
		for(int i=0 ; i<sourceList.size() ; i++){
			source = (String)sourceList.get(i);
			destinationfolder = destination + "/" + new File(source).getName();

			logger.debug(source + "][" + destinationfolder);
			
			FileCopy fc = new FileCopy(source, destinationfolder, true);
			fc.startCopy();
			println(fc.toString());
		}
		
		String owner = null;
		String file  = null;
		File ff		 = null;

		ff = new File(folder);
		if(!ff.exists()){
			ff.mkdirs();
		}
		if(!ff.exists() || !ff.isDirectory()){
			throw new Exception(ff.getAbsolutePath() + " is wrong!");
		}

		for(int i=0; i<ownerList.size(); i++){
			owner = (String)ownerList.get(i);
			file = ff.getAbsolutePath() + "/EXP_" + owner + "_" + DateUtil.getDate("yyyy-MM-dd_HH-mm-ss");
			OracleExport oe = new OracleExport(user, pwd, owner, file + ".dmp", file + ".log");
			oe.export();
			println(oe.toString());
		}
	}
	
	public void println(String str){
		System.out.println(str);
	}
	
	public static void main(String[] args) throws Exception{
		LogUtil lu = new LogUtil();
		lu.log4jLoad();
		
		SystemBackup sb = new SystemBackup();
		sb.startBackup();

	}

}
