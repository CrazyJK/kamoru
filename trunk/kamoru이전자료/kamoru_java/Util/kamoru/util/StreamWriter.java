package kamoru.util;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import org.apache.log4j.Logger;

public class StreamWriter extends HttpServlet {

	static Logger logger;
	public static final int MAX_BUF_LEN = 4096;
	private static final String RELATIVE_PATH;
	private static final String ALLOW_PATH_PATTERN[];

	static {
		Logger.getLogger(kamoru.util.StreamWriter.class);
		RELATIVE_PATH = ".." + File.separator;
		ALLOW_PATH_PATTERN = new String[4];
		ALLOW_PATH_PATTERN[0] = File.separator + "hwserver" + File.separator
				+ "ctmp";
		ALLOW_PATH_PATTERN[1] = File.separator + "storages";
		String tmpdir = System.getProperty("java.io.tmpdir");
		try {
			ALLOW_PATH_PATTERN[2] = (new File(tmpdir)).getCanonicalPath();
		} catch (IOException e) {
			ALLOW_PATH_PATTERN[2] = tmpdir;
		}
		ALLOW_PATH_PATTERN[2] = "temp";
	}

	public StreamWriter() {
	}

	private static boolean isValidPath(File file) throws IOException {
		if (!file.exists())
			return false;
		String path = file.getPath();
		if (path.startsWith(RELATIVE_PATH))
			return false;
		for (int i = 0; i < ALLOW_PATH_PATTERN.length; i++)
			if (path.indexOf(ALLOW_PATH_PATTERN[i]) > -1)
				return true;

		return false;
	}

	private static void writeStream(File file, OutputStream out) throws IOException {
		try {
			BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));
			byte bytes[] = new byte[4096];
//			for (int readLen = bis.read(bytes, 0, 4096); -1 != readLen; readLen = bis.read(bytes, 0, 4096)) {
			int readLen = 0;
			while((readLen = bis.read(bytes, 0, 4096)) != -1) {	
				if (logger.isDebugEnabled())
					System.out.print("." + readLen);
				out.write(bytes, 0, readLen);
				out.flush();
			}
			bis.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
		
		String fileName = req.getParameter("file"); //"C:\\DOCUME~1\\ADMINI~1\\LOCALS~1\\Temp\\hwserver\\ctmp\\256\\_hwsa1_1b38_37b0_8c88.png"

		try {
			fileName = new String(fileName.getBytes("8859_1"),"UTF-8");
		} catch (Exception e) {
		}
		
		String displayFileName = req.getParameter("dispfile"); //"IE8 교차 사이트 스크립팅 화면 캡쳐.png"
		String hmimetype = req.getParameter("hmimetype"); // ""
		
		File file = new File(fileName);
		if (isValidPath(file)) {			
			String mimeType = getServletConfig().getServletContext().getMimeType(displayFileName);
			if (null == mimeType || "".equals(mimeType))
				mimeType = "application/octet-stream";
			else if (mimeType.equals("text/plain") && !displayFileName.endsWith(".txt"))
				mimeType = "application/octet-stream";
			
			if (!"".equals(hmimetype) && null != hmimetype)
				mimeType = hmimetype;
			res.setContentType(mimeType);
			
			res.setHeader("Content-Disposition", "attachment;filename=" + getVaildFileName(displayFileName));
			
			ServletOutputStream out = res.getOutputStream();
			writeStream(file, out);
			out.close();
			

		} else if (logger.isInfoEnabled())
			logger.error("[StreamWriter] INVALID ACCESS : " + file.getCanonicalPath());
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res)
			throws IOException, ServletException {
		doGet(req, res);
	}

	private static String getVaildFileName(String fileName) {
		StringBuffer sbName = new StringBuffer();
		try {
			char chs[] = fileName.toCharArray();
			int len = chs.length;
			for (int i = 0; i != len; i++) {
				char ch = chs[i];
				switch (chs[i]) {
				case 34: // '"'
				case 37: // '%'
				case 42: // '*'
				case 47: // '/'
				case 58: // ':'
				case 60: // '<'
				case 62: // '>'
				case 63: // '?'
				case 92: // '\\'
				case 124: // '|'
					sbName.append('_');
					break;

				default:
					sbName.append(ch);
					break;
				}
			}

		} catch (Exception e) {
		}
		return sbName.toString();
	}
}
