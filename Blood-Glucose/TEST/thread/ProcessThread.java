/*
 * 작성자: 황룡
 * 작성일: 2008.07.31
 * 이름: ProcessThread.java
 * 설명: 실제 애플리케이션 처리를 위한 쓰레드
 * 수정이력 : 2008.07.31 [황룡] [최초생성]
 */
package thread;

import java.lang.reflect.*;

public class ProcessThread extends Thread {

	/**
	 * @param args
	 */
	private Object obj = null;
	private String method = null;
	private Object[] argument = null; 
	private TimeoutInterrupt timeoutInterrupt = null;
	
	public ProcessThread() {
		
	}
	
	public ProcessThread(TimeoutInterrupt timeoutInterrupt, Object obj, String method, Object[] argument) {
		this.obj = obj;
		this.method = method;
		this.argument = argument;
		this.timeoutInterrupt = timeoutInterrupt;
	}
	
	public void run() {
		try {
			process();
		} catch (Exception e) {
			timeoutInterrupt.setReturnObject(new Exception(e));
			timeoutInterrupt.interrupt();
		}
	}
	
	private synchronized void process() throws Exception {
		Class c = obj.getClass();
		
		Object returnObject = null;
		
		int classLength = 0;
		int methodcnt = 0;
		Class classList[] = null;
		boolean equalsmethod = false;
		boolean equalsargumentlength = false;
		
		Method[] aMethod = c.getMethods();
		for(int i = 0; i < aMethod.length; i++) {
			if(method.equals(aMethod[i].getName())) {
				classLength = aMethod[i].getParameterTypes().length;
				if(classLength == argument.length) {
					classList = aMethod[i].getParameterTypes();
					if(classLength == 0) {
						equalsmethod = true;
						equalsargumentlength = true;
					} else {
						for(int j = 0; j < classLength; j++) {
							if(!classList[j].equals(argument[j].getClass())) {
								equalsmethod = false;
								break;
							}
							equalsmethod = true;
							equalsargumentlength = true;
							methodcnt++;
						}
					}
				} else {
					equalsargumentlength = false;
				}
				
				if(equalsmethod && equalsargumentlength) {
					returnObject = aMethod[i].invoke(obj, argument);
					break;
				} else {
					throw new Exception("[ProcessThread.java] message : 호출 메소드이 없거나 파라메타 정보가 다릅니다.");
				}
			}
		}
		
		System.out.println(timeoutInterrupt.getName() + " Process End...");
		
		timeoutInterrupt.setReturnObject(returnObject);
		timeoutInterrupt.stop();
	}
	
	public static void main(String[] args) throws Exception {
		TimeoutInterrupt testObj = new TimeoutInterrupt();
		TimeoutInterrupt threadObj1 = new TimeoutInterrupt(System.currentTimeMillis(), testObj, "sleep", new Object[]{"", "", ""}, "5000");
		TimeoutInterrupt threadObj2 = new TimeoutInterrupt(System.currentTimeMillis(), testObj, "sleep", new Object[0], "5000");
		TimeoutInterrupt threadObj3 = new TimeoutInterrupt(System.currentTimeMillis(), testObj, "sleep", new Object[0], "15000");
		threadObj1.start();
		threadObj2.start();
		threadObj3.start();
		
		// 결과 테스트 두 쓰래드 종료시까지 대기
		Object[] retobj1 = testObj.checkReturnValue(threadObj1);
		Object[] retobj2 = testObj.checkReturnValue(threadObj2);
		Object[] retobj3 = testObj.checkReturnValue(threadObj3);
		
		System.out.println("retobj1[0] : " + retobj1[0]);
		System.out.println("retobj1[1] : " + retobj1[1]);
		System.out.println("retobj2[0] : " + retobj2[0]);
		System.out.println("retobj2[1] : " + retobj2[1]);
		System.out.println("retobj3[0] : " + retobj3[0]);
		System.out.println("retobj3[1] : " + retobj3[1]);
	}
}
