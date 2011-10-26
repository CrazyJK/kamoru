
public class Calc {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String c = "123";
		String a = "23";
		String b = "123";
		
		int calcInt = (int)Math.round(Double.parseDouble(c) / (Double.parseDouble(a) + Double.parseDouble(b)) * 100);

		double calcDouble = (Integer.parseInt(c) / (double)(Integer.parseInt(a) + Integer.parseInt(b)) * 100);
		
		System.out.println(calcInt);
		System.out.println(calcDouble);
		System.out.println(123d/(23d+123d)*100d);
		
		System.out.println("".split(",").length);
	}

}
