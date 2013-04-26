package kamoru.test;

public class FormatTest {

	/**
	 * String %s
	 * int    %n
	 * float  %f
	 * @param args
	 */
	public static void main(String[] args) {
		System.err.format("이름 [%-5s]%n", "홍길동");     
		System.out.format("이름 [%5s]%n", "박진");        
		System.out.format("이름 [%5s]%n", "난홍길동"); 
		System.out.format("안녕하세요 내 이름은 %-20s 입니다%n.", "팩회장");
		System.out.format("나이는 %10d 입니다.%n", 14);
		System.out.format("왼쪽눈 시력 : %.2f ,%n 오른쪽 눈 시력 : %.1f %n", 1.5, 2.0);
	}

}
