package kamoru.frmwk.struts.upload;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.struts.upload.FormFile;

public class UploadUtils {
	
    public static void doFileUpload(FormFile formFile, String path) throws FileNotFoundException, IOException {
        InputStream stream = formFile.getInputStream();
 
        String fileName = formFile.getFileName();
        OutputStream bos = new FileOutputStream(path+fileName);
 
        int bytesRead = 0;
        byte[] buffer = new byte[8192];
        while ((bytesRead = stream.read(buffer, 0, 8192)) != -1) {
            bos.write(buffer, 0, bytesRead);
        }
        bos.close();
        stream.close();
    }

}
