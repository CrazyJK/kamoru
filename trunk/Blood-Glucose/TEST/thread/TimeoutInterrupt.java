/*
 * 작성자: 황룡
 * 작성일: 2008.07.31
 * 이름: TimeoutInterrupt.java
 * 설명: TimeOut 처리 쓰레드
 * 수정이력 : 2008.07.31 [황룡] [최초생성]
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
		
		String sTimeOut = timeout; // 기본 60초 
			
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
			// time out 발생시 로그 기록 로직
			// ......
			System.out.println("시간 초과 되었습니다. 종료합니다.");
			
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
			 * returnObj 배열
			 * [0] = 스레드 종료 여부 Y:정상종료, N:타임 아웃으로 인한 종료
			 * [1] = TimeoutInterrupt 에서 보낸 메소드의 결과값 
			 */
			
			returnObj = t.getReturnObject();
			
			// 쓰레드가 완전 종료 될때 까지 반복 후 처리 결과값으로 처리한다.
			while(!"N".equals(returnObj[0]) && !"Y".equals(returnObj[0])) {
				Thread.sleep(100);
				returnObj = t.getReturnObject();
			}
		}
		return returnObj;
	}
	
	// Time Out Interrupt 용 테스트 메소드
	public String sleep() throws Exception{
		Thread.sleep(10000);
		return "Success";
	}
}
