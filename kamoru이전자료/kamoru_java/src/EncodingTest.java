
import java.io.*;

public class EncodingTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception {
		try
		{
			String encoding = System.getProperty("ExtGear.encoding", "utf-8");
			System.out.println("ExtGear.encoding is [" + encoding + "]");
		File file = new File((new StringBuilder()).append("r:").append(File.separator).append("2_0112345CL.msg").toString());
		FileInputStream fileInputStream = new FileInputStream(file);
		InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream, " ");
		System.out.println("UTF-8");
		InputStreamReader inputStreamReader2 = new InputStreamReader(fileInputStream);
		System.out.println("Non");
		}
		catch(Exception e){
			System.out.println("[" + e.getMessage() + "]");
			e.printStackTrace();
			
		}
	}

}
