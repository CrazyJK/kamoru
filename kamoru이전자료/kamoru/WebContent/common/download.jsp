<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="java.io.*,org.apache.log4j.Logger" %>
<%
String fileName = request.getParameter("file");
String displayFileName = request.getParameter("dispfile");

try {
	fileName = new String(fileName.getBytes("8859_1"),"UTF-8");
	displayFileName = new String(displayFileName.getBytes("8859_1"),"UTF-8");
//	displayFileName = java.net.URLEncoder.encode(displayFileName, "UTF-8");	
} catch (Exception e) {
}

String hmimetype = request.getParameter("hmimetype");


System.out.println("param file      " + fileName);
System.out.println("param dispfile  " + displayFileName);
System.out.println("param hmimetype " + hmimetype);

File file = new File(fileName);
if (isValidPath(file)) {			
	String mimeType = getServletConfig().getServletContext().getMimeType(displayFileName);
	if (null == mimeType || "".equals(mimeType))
		mimeType = "application/octet-stream";
	else if (mimeType.equals("text/plain") && !displayFileName.endsWith(".txt"))
		mimeType = "application/octet-stream";
	
	if (!"".equals(hmimetype) && null != hmimetype)
		mimeType = hmimetype;
	response.setContentType(mimeType);
	System.out.println("mimeType=" + mimeType);
	
	response.setHeader("Content-Disposition", "attachment;filename=" + getVaildFileName(displayFileName));
	System.out.println("attachment;filename=" + getVaildFileName(displayFileName));
	
	response.setHeader("Content-Length",String.valueOf(file.length()));
	
	ServletOutputStream output = response.getOutputStream();
	writeStream(file, output);
	output.close();
} else {
	out.println("INVALID ACCESS : " + file.getCanonicalPath());
}
%>
<%!
public static final int MAX_BUF_LEN = 4096;
private static final String RELATIVE_PATH;
private static final String ALLOW_PATH_PATTERN[];

static {
	RELATIVE_PATH = ".." + File.separator;
	ALLOW_PATH_PATTERN = new String[4];
	ALLOW_PATH_PATTERN[0] = File.separator + "hwserver" + File.separator + "ctmp";
	ALLOW_PATH_PATTERN[1] = File.separator + "storages";
	String tmpdir = System.getProperty("java.io.tmpdir");
	System.out.println("java.io.tmpdir=" + tmpdir);
	try {
		ALLOW_PATH_PATTERN[2] = (new File(tmpdir)).getCanonicalPath();
	} catch (IOException e) {
		ALLOW_PATH_PATTERN[2] = tmpdir;
	}
	ALLOW_PATH_PATTERN[3] = "temp";
}

public static boolean isValidPath(File file) throws IOException {
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

public static void writeStream(File file, OutputStream out) throws IOException {
	try {
		BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));
		byte bytes[] = new byte[MAX_BUF_LEN];
//		for (int readLen = bis.read(bytes, 0, 4096); -1 != readLen; readLen = bis.read(bytes, 0, 4096)) {
		int readLen = 0;
		while((readLen = bis.read(bytes, 0, MAX_BUF_LEN)) != -1) {	
			out.write(bytes, 0, readLen);
			out.flush();
		}
		bis.close();
	} catch (Exception e) {
		System.out.println("writeStream Error=" + e.getMessage());	
//		e.printStackTrace();
	}
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
		e.printStackTrace();
	}
	System.out.println("VaildFileName=" + fileName);
	return sbName.toString();
}
%>