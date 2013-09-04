package jk.kamoru.test.enumtest;

public class Test_enum {

	public static void printGender(Gender d) {
		System.out.println(d);
	}
	public static void print(Gender d) {
		switch (d) {
		case MALE: System.out.println("싫어"); break;
		default: System.out.println("좋아");break;
		}
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		printGender(Gender.MALE);
		print(Gender.FEMAL);
		print(Gender.MALE);
		for(Gender g : Gender.values()) {
			System.out.println(g);
		}
	}

}
