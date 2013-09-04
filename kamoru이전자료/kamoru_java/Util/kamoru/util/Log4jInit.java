package kamoru.util;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.FileNotFoundException;
import java.util.Enumeration;
import java.util.ArrayList;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.PropertyConfigurator;
import org.apache.log4j.Logger;
import org.apache.log4j.Level;
import org.apache.log4j.Appender;
import org.apache.log4j.spi.LoggerRepository;

/**
 * Servlet implementation class Log4jInit
 */
public class Log4jInit extends HttpServlet {
	private static final long serialVersionUID = 1L;

	static Logger rootLogger = Logger.getRootLogger();
	final String[] levels = new String[]{
			"TRACE","DEBUG","WARN","INFO","ERROR","FATAL"
	};
	
	public ServletConfig config = null;
	public String filepath = null;
	
	public void init(ServletConfig config) {
		this.config = config;
		log4jLoad();
	}

	protected void service(HttpServletRequest request, HttpServletResponse response) throws IOException{
		PrintWriter out = response.getWriter();
		response.setContentType("text/html; charset=UTF-8");
		
		String loggername = request.getParameter("loggername");
		String loggerlevel   = request.getParameter("loggerlevel");
		String reload 		 = request.getParameter("reload");
		String msg = "";
		if(reload != null && reload.equalsIgnoreCase("true")){
			if(log4jLoad()){
				msg = "<h4>Log4j reload by default</h4>";
			}else{
				msg = "<h4 style='color:red;'>Log4j reload fail. Check the stdout log.</h4>";
			}	
		}

		ArrayList loggerList = new ArrayList();
		ArrayList loggerInfoList = new ArrayList();
		Logger _logger = null;
		String _loggername = null;
		String _loggerlevel = null;
		String _loggerappender = null;

		loggerList.add(rootLogger);
		LoggerRepository lr = rootLogger.getLoggerRepository();
		for(Enumeration enumeration = lr.getCurrentLoggers();enumeration.hasMoreElements();){
			loggerList.add((Logger)enumeration.nextElement());
		}
		
		for(int i=0 ; i<loggerList.size() ; i++)
		{
			_logger = (Logger)loggerList.get(i);

			_loggername = _logger.getName();

			// set new Log level
			if(loggername != null && loggername.equals(_loggername) && loggerlevel != null && loggerlevel.trim().length() > 0){
				_logger.setLevel(Level.toLevel(loggerlevel));
			}
			_loggerlevel = _logger.getLevel() == null ? "" : _logger.getLevel().toString();
			
			_loggerappender = "";
			for(Enumeration enumAppend = _logger.getAllAppenders(); enumAppend.hasMoreElements();){
				Appender appender = (Appender)enumAppend.nextElement();
				if(appender != null){
					_loggerappender += "[" + appender.getName() + "]";
				}
			}
			loggerInfoList.add(new String[]{	_loggername, _loggerlevel, _loggerappender});
		}
		

		out.write("");
		out.write("<!DOCTYPE html PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\" \"http://www.w3.org/TR/html4/loose.dtd\">");
		out.write("");
		out.write("<html>");
		out.write("<head>");
		out.write("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">");
		out.write("<title>log4j control</title>");
		out.write("<style>");
		out.write("iframe {border: 1pt dotted gray;}");
		out.write("</style>");
		out.write("<script>");
		out.write("function changeLoggerLevel(loggerName){");
		out.write("loggername = document.getElementById(\"loggername\");");
		out.write("loggerlevel = document.getElementById(\"loggerlevel\");");
		out.write("select_loggerLevel = document.getElementById(loggerName + \"_loggerLevel\");");
		out.write("");
		out.write("loggername.value= loggerName;");
		out.write("loggerlevel.value = select_loggerLevel.value;");
		out.write("document.forms[0].submit();");
		out.write("}");
		out.write("</script>");
		out.write("</head>");
		out.write("<body>");
		out.write("<form action=\"\" method=\"get\" name=\"log4jForm\" id=\"log4jForm\">");
		out.write("<input type=\"hidden\" name=\"loggername\" />");
		out.write("<input type=\"hidden\" name=\"loggerlevel\" />");
		out.write("</form>");
		out.write("<h3>log4j control page</h3>");
		out.write("<fieldset>");
		out.write("<legend>Current Logger List &nbsp; <a href=\"log4jinit\">refresh</a></legend>");
		out.write("<table border=\"0\">");
		out.write("<thead>");
		out.write("<tr>");
		out.write("<th>Logger Name</th>");
		out.write("<th>Current Level</th>");
		out.write("<th>Change Level</th>");
		out.write("<th>Current Appender</th>");
		out.write("</tr>");
		out.write("</thead>");
		out.write("<tbody>");
		out.write("");
		for(int i=0 ; i<loggerInfoList.size() ; i++){ 
						String[] loggerInfo = (String[])loggerInfoList.get(i); 
		out.write("");
		out.write("<tr>");
		out.write("<td>");
		out.print( loggerInfo[0] );
		out.write("</td>");
		out.write("<td>");
		out.print( loggerInfo[1] );
		out.write("</td>");
		out.write("<td>");
		out.write("<select name=\"");
		out.print( loggerInfo[0] );
		out.write("_loggerLevel\" onchange=\"changeLoggerLevel('");
		out.print( loggerInfo[0] );
		out.write("')\">");
		out.write("<optgroup label=\"to Level\">");
		out.write("<option value=\"\">--</option>");
			for(int j=0 ; j<levels.length ; j++){ 
		out.write("");
		out.write("<option value=\"");
		out.print( levels[j] );
		out.write('"');
		out.write(' ');
		out.print( loggerInfo[1].equals(levels[j]) ? "selected" : "" );
		out.write('>');
		out.print( levels[j] );
		out.write("</option>");
		out.write("");
			} 
		out.write("</optgroup>");
		out.write("");
		out.write("</select>");
		out.write("</td>");
		out.write("<td>");
		out.print( loggerInfo[2] );
		out.write("</td>");
		out.write("</tr>");
		out.write("");
		} 
		out.write("");
		out.write("</tbody>");
		out.write("</table>");
		out.write("<p>For log4j default set, click <a href=\"log4jinit?reload=true\" >here</a>" + msg + "</p>");
		out.write("Current configuration file location is <code>" + filepath + "</code>");
		out.write("</fieldset>");
		out.write("</body>");
		out.write("</html>");

		out.close();
	}

	public boolean log4jLoad(){
		try
		{
			String file = config.getInitParameter("log4j-init-file");
			if(file == null){
				throw new Exception("servlet init-param <param-name>log4j-init-file</param-name> not exist. check web.xml");
			}else{
				filepath = config.getServletContext().getRealPath(file);
				File f = new File(filepath);
				if(!f.exists()){
					throw new FileNotFoundException("log4j configuration file not found. [" + filepath + "]");
				}
				PropertyConfigurator.configure(filepath);
				System.out.println("Log4j Init.");
				return true;
			}
		}catch(Exception e){
			System.out.println("ERROR: Log4j init fail. \n\t" + e.getMessage());
			return false;
		}
	}
}
