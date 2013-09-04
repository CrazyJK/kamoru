package kamoru.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import kamoru.util.*;
public class RuntimeUtil 
{
	static org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(RuntimeUtil.class);

	public static String execute(String command)
	{
		StringBuffer sb = new StringBuffer();
		long currentTimeMillis = System.currentTimeMillis();
		logger.debug("Execute date " + DateUtil.getDate());
		Process process = null;
		try{
			logger.warn(command);

			process = Runtime.getRuntime().exec(command);
		
			String inLine = "";
			BufferedReader in = new BufferedReader(new InputStreamReader(process.getInputStream()));
			while ((inLine = in.readLine()) != null) 
			{
				sb.append(inLine);
				sb.append(System.getProperty("line.separator"));
				logger.debug(inLine);
			}
			in.close();
		} 
		catch(Exception e) 
		{
			sb.append(e.getMessage());
			logger.error(e.getMessage());
		}
		finally
		{
			if(process != null){ process.destroy();	}
			logger.debug("elapsed time " + (System.currentTimeMillis() - currentTimeMillis));
		}
		return sb.toString();
	}

}
