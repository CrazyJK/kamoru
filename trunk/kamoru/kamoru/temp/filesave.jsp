<%@ page language="java" contentType="text/html; charset=euc-kr"
import="java.sql.*,java.io.File,java.io.*,
		java.util.Date,java.util.*,
		java.net.*,java.text.*"
		session="false" %>

<%
String FROM = "";
String FILENAME = "";
String XML = "";
response.setHeader("Cache-Control","no-cache");
HashMap hashMap=null;
ObjectInputStream objin = null;

Object obj = null;
out.clearBuffer();
try {
    objin = new ObjectInputStream(request.getInputStream());
    hashMap= (HashMap)objin.readObject();

    FROM = (String)hashMap.get("FROM");
    FILENAME =(String)hashMap.get("FILE");
    XML = (String)hashMap.get("XML");
}
catch(Exception  e)
{
    out.println(e);
}
finally
{
    if(objin!=null)
        objin.close();
}


//FileInputStream fis = null;
//BufferedInputStream bis = null;
FileOutputStream fos = null;
OutputStreamWriter osw = null;
BufferedWriter bw = null;
PrintWriter pw = null;
//FileWriter fwSnapshot = null;
System.out.println("XML == " + XML);

//if(null == FROM || "".equals(FROM)) FROM = "GWNotify";
//if(null == FILENAME || "".equals(FILENAME)) FILENAME = System.currentTimeMillis()+".notify";

try {
//    for (int i = 0; i < serversCount; i++) {

        String messagePath = null;


//        if(null != configFilePath){
//            fis = new FileInputStream(configFilePath);
//            bis = new BufferedInputStream(fis);
//            parser.parse(bis);
//            Element configRoot = parser.getDocumentElement();

//			File watcherFilePath = new File(configFilePath, "../../queue/"+FROM);

//            if (null != fis) try { fis.close(); } catch (Exception ce) { }
//            if (null != bis) try { bis.close(); } catch (Exception ce) { }

            File f = new File (messagePath+"/"+FILENAME);

            fos = new FileOutputStream(f);
            osw = new OutputStreamWriter(fos, "UTF-8");
            bw = new BufferedWriter(osw);
            pw = new PrintWriter(bw);

            pw.println(XML);
//
//            File fSnap = new File (messagePath+"/_"+FILENAME);
//            fwSnapshot = new FileWriter(fSnap);

			pw.close();
			bw.close();
			osw.close();
			fos.close();
//        }
    //}
} catch (Exception e) {
    out.println(e.toString());
} finally {
//    if (null != fis) try { fis.close(); } catch (Exception ce) { }
//    if (null != bis) try { bis.close(); } catch (Exception ce) { }
    if (pw != null) try { pw.close(); } catch (Exception ce) { }
    if (bw != null) try { bw.close(); } catch (Exception ce) { }
    if (osw != null) try { osw.close(); } catch (Exception ce) { }
    if (fos != null) try { fos.close(); } catch (Exception ce) { }
//    if (fwSnapshot != null) try { fwSnapshot.close(); } catch (Exception ce) { }
}

%>

<html>
<head><title></title>
<script language="javascript">
<!--
if(opener) {
    self.close();
}
-->
</script>
</head>
<body>
<body>
</html>