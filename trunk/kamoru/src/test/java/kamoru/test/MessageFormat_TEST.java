package kamoru.test;

import java.text.MessageFormat;
import java.util.Date;

public class MessageFormat_TEST {

	public static void main(String[] s) {
		String history = MessageFormat.format("{0,date} {0,time} [{1}] {2}", new Date(), "getOpus()", "msgsfshfkjsdhfks");
		System.out.println(history);
		
		 int planet = 7;
		 String event = "a disturbance in the Force";

		 String result = MessageFormat.format(
		     "At {1,time} on {1,date}, there was {2} on planet {0,number,integer}.",
		     planet, new Date(), event);
		 System.out.println(result);
	}
}
