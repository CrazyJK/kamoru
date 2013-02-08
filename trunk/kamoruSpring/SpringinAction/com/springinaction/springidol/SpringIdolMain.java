package com.springinaction.springidol;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SpringIdolMain {

	/**
	 * @param args
	 * @throws PerformanceException 
	 */
	public static void main(String[] args) throws PerformanceException {
		ApplicationContext ctx = new ClassPathXmlApplicationContext("com/springinaction/springidol/spring-idol.xml");

		Auditorium auditorium = (Auditorium) ctx.getBean("auditorium");
		
		Performer duke = (Performer) ctx.getBean("duke");
		duke.perform();

//		Performer poeticDuke = (Performer) ctx.getBean("poeticDuke");
//		poeticDuke.perform();
//		
//		Performer kenny = (Performer) ctx.getBean("kenny");
//		kenny.perform();
		
		
		Thinker volunteer = (Thinker) ctx.getBean("volunteer");
		volunteer.thinkOfSomething("Thinker say : ajdweihoijfoisjfosd");
	}

}
