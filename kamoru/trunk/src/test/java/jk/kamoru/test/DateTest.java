package jk.kamoru.test;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DateTest {

	private static final Logger logger = LoggerFactory.getLogger(DateTest.class);

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		long first = 0;

		if (logger.isInfoEnabled()) first = System.currentTimeMillis();

		System.out.println(first);
	 	System.out.println(new Date(1369222653873l));
	 	logger.error("" + first, new Exception("aaa"));

	}

}
