package jk.kamoru.util;

import java.io.IOException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class RuntimeUtils {

	public static void exec(String command) {
		try {
			log.trace("exec {}", command);
			Runtime.getRuntime().exec(command);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	public static void exec(String[] command) {
		try {
			log.trace("exec {}", ArrayUtils.toString(command));
			Runtime.getRuntime().exec(command);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}
