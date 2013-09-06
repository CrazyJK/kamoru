package jk.kamoru.util;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RuntimeUtils {
	protected static final Logger logger = LoggerFactory.getLogger(RuntimeUtils.class);

	public static void exec(String command) {
		try {
			logger.trace("exec {}", command);
			Runtime.getRuntime().exec(command);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	public static void exec(String[] command) {
		try {
			logger.trace("exec {}", ArrayUtils.toString(command));
			Runtime.getRuntime().exec(command);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}
