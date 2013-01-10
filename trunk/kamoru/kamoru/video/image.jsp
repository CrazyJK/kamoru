<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
%><%@ page import="java.util.*, java.io.*, kamoru.app.video.av.*, kamoru.frmwk.util.ServletUtils" 
%><%! public static AVProp prop = new AVProp(); %><%
String selectedOpus = ServletUtils.getParameter(request, "opus");

String img = null;
if("listImg".equals(selectedOpus)) {
	img = prop.basePath + "/listImg.jpg"; 
}
else {
	List<AVOpus> list = (List<AVOpus>)session.getAttribute("avlist");
	
	for(AVOpus av : list) {
		if(selectedOpus.equals(av.getOpus())) {
			img = av.getCover();
		}
	}
}

img = img == null ? prop.noImagePath : img;
File imageFile = new File(img);
byte[] b = new byte[(int)imageFile.length()];
FileInputStream fis = null;
try {
	fis = new FileInputStream(imageFile);
	fis.read(b);
} catch (Exception e) {
	e.printStackTrace();
} finally {
	if(fis != null) try {fis.close();}catch(IOException e){}
}

out = pageContext.pushBody();
response.setContentType("image/" + img.substring(img.lastIndexOf(".")+1));
response.getOutputStream().write(b);
response.getOutputStream().flush();
response.getOutputStream().close();

%>