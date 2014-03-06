package jk.kamoru.test;

public class test2 {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// thread 10, user 5
		int threadSize = 2;
		int userSize = 4;
		int index = 0;
		for (int i=0; i<threadSize; i++) { 

			System.out.format("%2s : %2s,  %2s,  %2s,  %2s%n", 
					i,
					index++,
					threadSize % userSize,
					i % threadSize,
					i % userSize
					);
		}
	}

}
