<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
%><%@ page import="java.util.*, java.io.*, kamoru.app.video.av.*, kamoru.frmwk.util.ServletUtils,org.apache.commons.io.FileUtils" 
%><%
AVCollectionCtrl ctrl = new AVCollectionCtrl();

String selectedOpus = ServletUtils.getParameter(request, "opus");

File imageFile = null;
if(ctrl.listBGImageName.equals(selectedOpus)) {
	imageFile = ctrl.getListBGImageFile();
}
else {
	List<AVOpus> list = (List<AVOpus>)session.getAttribute("avlist");
	
	for(AVOpus av : list) {
		if(selectedOpus.equals(av.getOpus())) {
			imageFile = av.getCoverImageFile();
		}
	}
}

byte[] b = FileUtils.readFileToByteArray(imageFile);
/* 
byte[] b = new byte[(int)imageFile.length()];
FileInputStream fis = null;
try {
	fis = new FileInputStream(imageFile);
	fis.read(b);
} catch (Exception e) {
	e.printStackTrace();
} finally {
	if(fis != null) try {fis.close();}catch(IOException e){}
} */

out = pageContext.pushBody();
//response.setContentType("image/" + img.substring(img.lastIndexOf(".")+1));
response.setContentType(getServletContext().getMimeType(imageFile.getName()));
if(!ctrl.listBGImageName.equals(selectedOpus)) {
	response.setDateHeader("Expires", new Date().getTime() + 86400*1000l);
	response.setHeader("Cache-Control", "max-age=" + 86400);
}
response.getOutputStream().write(b);
response.getOutputStream().flush();
response.getOutputStream().close();%>