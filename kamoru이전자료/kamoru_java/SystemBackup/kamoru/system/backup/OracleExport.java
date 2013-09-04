package kamoru.system.backup;

import kamoru.util.*;

public class OracleExport {

	static org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(OracleExport.class);

	String user;
	String pwd;
	String owner;
	String file;
	String log;
	
	/**
	 * EXP kamoru/kamoru OWNER=kamoru FILE=kamoru.dmp LOG=exp_kamoru.log
	 * @param user
	 * @param pwd
	 * @param owner
	 * @param file
	 * @param log
	 */
	public OracleExport(String user, String pwd, String owner, String file, String log){
		this.user 	= user;
		this.pwd 	= pwd;
		this.owner	= owner;
		this.file	= file;
		this.log	= log;
	}
	
	public void export(){
		String command = "EXP " 
					   + user + "/" + pwd 
					   + " OWNER=" + owner 
					   + " FILE=" + file
					   + " LOG=" + log;
		RuntimeUtil.execute(command);
	}
	
}
