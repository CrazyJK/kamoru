package jk.kamoru.test;

/* * final 키워드를 사용하여 이 클래스로부터 상속이 불가능하도록 하였다. */ 
public final class Singleton { 
	// static inner class (여기도 final 키워드 사용) 를 사용하여 Singleton 클래스의 
	// 객체를 생성함 
	/**
	 * @author  kamoru
	 */
	private static final class SingletonHolder { 
		// 역시 이 내부에서도 static final 키워드 사용 
		/**
		 * @uml.property  name="singleton"
		 * @uml.associationEnd  
		 */
		static final Singleton singleton = new Singleton(); 
	} 
	private Singleton() {
		
	} 
	public static Singleton getInstance() { 
		return SingletonHolder.singleton; 
	} 
} 

