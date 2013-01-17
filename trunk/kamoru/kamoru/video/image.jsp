<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
%><%@ page import="java.util.*, java.io.*, kamoru.app.video.av.*, kamoru.frmwk.util.ServletUtils" 
%><%! public AVProp prop = AVProp.getInstance(); %><%
String selectedOpus = ServletUtils.getParameter(request, "opus");
AVCollectionCtrl ctrl = new AVCollectionCtrl();

String img = null;
if(ctrl.listImageName.equals(selectedOpus)) {
	img = prop.basePath.split(";")[0] + "/" + ctrl.listImageName; 
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