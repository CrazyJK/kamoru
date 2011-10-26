package kamoru.io;

import java.io.*;
/**
 * if 입력(thread갯수) = <font color="red">100</font>, <br>
 * { [ (각자 thread순번의 파일을 생성/쓰기/파일복사/복사된파일삭제 * 10회) * <font color="red">100개의 thread</font> ] * 10회} 
 * @author Administrator
 *
 */
public class IOTest {

	public static int totalCnt = 0;

	public int threadCount;
	
	public IOTest(String threadCount){
		this.threadCount = Integer.parseInt(threadCount);
	}
	
	public IOTest(int threadCount){
		this.threadCount = threadCount;
	}
	
	public void play(int idx) {
		try {
			CopyFileThread[] c = new CopyFileThread[threadCount];

			for (int i = 0; i < c.length; i++) {
				c[i] = new CopyFileThread(i + 1, idx);
			}

			for (int i = 0; i < c.length; i++) {
				c[i].start();
			}

			for (int i = 0; i < c.length; i++) {
				c[i].join();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String args[]) throws Exception {
		String args0 = null;
		if(args.length == 0){
			System.out.print("스레드 갯수를 입력하세요. ");
			BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
			args0 = br.readLine();
		}else{
			args0 = args[0];
		}

		IOTest t = new IOTest(args0);
		int i = 0;

		
		long tLog = System.currentTimeMillis();

		while (i < 10) {
			t.play(i);
			i++;
		}

		System.out.println("Elapsed time = "
				+ (System.currentTimeMillis() - tLog));
	}

}
