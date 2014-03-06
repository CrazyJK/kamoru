package jk.kamoru.test;
import java.io.*;
public class ZeroFileCreate {

	
	public static void main(String[] args) throws Exception{
		FileWriter fw = new FileWriter(new File("/home/kamoru/ZeroFile.txt"));
		fw.flush();
		fw.close();
		fw = new FileWriter("");
	}
}
