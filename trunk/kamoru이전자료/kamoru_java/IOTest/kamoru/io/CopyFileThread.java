package kamoru.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.PrintStream;

public class CopyFileThread extends Thread {
	int num = 0;
	int idx = 0;
	
	public CopyFileThread(int i, int idx) {
		num = i;
		this.idx = idx;
	}

	public void run() {
		try {
			FileOutputStream out; // declare a file output object
			PrintStream p; // declare a print stream object
			int cnt = 0;

			while (cnt < 10) {
				cnt++;

				// Create a new file output stream
				// connected to "myfile.txt"
				out = new FileOutputStream(Integer.toString(num));

				// Connect print stream to the output stream
				p = new PrintStream(out);
				p.println("This is written to a file");
				p.close();

				File f1 = new File(Integer.toString(num));
				File f2 = new File(Integer.toString(num) + ".copy");
				copyFile(f1, f2);
				f2.delete();
				System.out.print(".");
				// Thread.sleep(1);
				System.out.print("*");
				IOTest.totalCnt++;

				//System.out.println("totalCnt=" + IOTester.totalCnt + " idx=" + idx + " cnt=" + cnt + " num=" + num);

			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void copyFile(File in, File out) throws Exception {
		FileInputStream fis = new FileInputStream(in);
		FileOutputStream fos = new FileOutputStream(out);
		byte[] buf = new byte[1024];
		int i = 0;

		while ((i = fis.read(buf)) != -1) {
			fos.write(buf, 0, i);
		}
		fis.close();
		fos.close();
	}
}
