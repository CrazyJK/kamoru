package jk.kamoru.test;

import java.text.MessageFormat;
import java.util.Date;

import static org.junit.Assert.*;
import org.junit.Test;

public class MessageFormatTest {

	
	@Test
	public void testDateTime() {
		String history = MessageFormat.format("{0,date} - {0,time}", new Date());
		System.out.println(history);
		assertTrue(history instanceof String);
	}
	
	@Test 
	public void testNumber() {
		 int planet = 7;
		 String result = MessageFormat.format("{0,number,integer}", planet);
		 assertEquals("7", result);
	}
	
}
