/*
 * �ۼ���: Ȳ��
 * �ۼ���: 2008.07.31
 * �̸�: TimeoutInterrupt.java
 * ����: TimeOut ó�� ������
 * �����̷� : 2008.07.31 [Ȳ��] [���ʻ���]
 */
package thread;

public class TimeoutInterrupt extends Thread {

	/**
	 * @param args
	 */
	private long starttime = 0L;
	private Thread processDaemon = null;
	private int timeOut = 0;
	private Object[] returnObject = null;
	private String successYN = null;
	
	public TimeoutInterrupt() {
		
	}
	
	public TimeoutInterrupt(long starttime, Object targetObj, String method, Object[] argument, String timeout) {
		this.starttime = starttime;
		
		ProcessThread processThread = new ProcessThread(this, targetObj, method, argument);
		processDaemon = new Thread(processThread);
		
		String sTimeOut = timeout; // �⺻ 60�� 
			
		try {
			timeOut = Integer.parseInt(sTimeOut);
		} catch (Exception e) {
			timeOut = 10000;
		}
		
		returnObject = new Object[2];
		returnObject[0] = null;
		successYN = "Y";
	}
	
	public void run() {
		long currentTime = 0L;
		
		System.out.println("ThreadName : " + getName() + ", Thread Start Time : " + starttime);
		
		processDaemon.start();
		
		while(true) {
			currentTime = System.currentTimeMillis();
			//System.out.println("ThreadName : " + getName() + ", Thread Current Time : " + currentTime);
			try {
				Thread.sleep(1000);
				if(currentTime - this.starttime > timeOut) {
					writeLog("ThreadName : " + getName() + ", Thread Start Time : " + starttime);
					processDaemon.interrupt();
					successYN = "N";
					this.setReturnObject(null);
					break;
				}
				//System.out.println("ThreadName : " + getName() + ", Thread Current Time : " + currentTime);
			} catch (InterruptedException e) {
				this.returnObject[0] = "N";
				//this.setReturnObject(null);
				processDaemon.interrupt();
				break;
			} catch (Exception e) {
				this.returnObject[0] = "N";
				//this.setReturnObject(null);
				processDaemon.interrupt();
				break;
			}
		}
		
		System.out.println("ThreadName : " + getName() + ", Thread End Time : " + currentTime);
	}
	
	private void writeLog(String msg) throws Exception {
		try {
			// time out �߻��� �α� ��� ����
			// ......
			System.out.println("�ð� �ʰ� �Ǿ����ϴ�. �����մϴ�.");
			
		} catch (Exception e) {
			throw new Exception("[TimeoutInterrupt.java], exception : " + e.getMessage());
		} finally {
		}
	}
	
	public synchronized void setReturnObject(Object returnObject) {
		this.returnObject[0] = successYN;
		this.returnObject[1] = returnObject;
	}
	
	public synchronized Object[] getReturnObject() {
		return returnObject;
	}
	
	public Object[] checkReturnValue(TimeoutInterrupt t) throws Exception {
		Object returnObj[] = null;
		if(t != null) {
			//Object returnObj[] = null;
			/*
			 * returnObj �迭
			 * [0] = ������ ���� ���� Y:��������, N:Ÿ�� �ƿ����� ���� ����
			 * [1] = TimeoutInterrupt ���� ���� �޼ҵ��� ����� 
			 */
			
			returnObj = t.getReturnObject();
			
			// �����尡 ���� ���� �ɶ� ���� �ݺ� �� ó�� ��������� ó���Ѵ�.
			while(!"N".equals(returnObj[0]) && !"Y".equals(returnObj[0])) {
				Thread.sleep(100);
				returnObj = t.getReturnObject();
			}
		}
		return returnObj;
	}
	
	// Time Out Interrupt �� �׽�Ʈ �޼ҵ�
	public String sleep() throws Exception{
		Thread.sleep(10000);
		return "Success";
	}
}
