package kamoru.test.serialization.customClass;

import java.io.*;

public class Write {

	public static void main(String atgs[]){
		try {
			FileOutputStream fos = new FileOutputStream("file.out");
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(new Test("testing", 37));
			oos.flush();
			fos.close();
		} catch (Throwable e) {
			System.err.println(e);
		}
	}
}
