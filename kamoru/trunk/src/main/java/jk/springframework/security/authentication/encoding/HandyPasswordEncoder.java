package jk.springframework.security.authentication.encoding;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.encoding.PasswordEncoder;

public class HandyPasswordEncoder implements PasswordEncoder {

	private static final Logger logger = LoggerFactory.getLogger(HandyPasswordEncoder.class);

	@Override
	public String encodePassword(String rawPass, Object salt) {
		logger.info(rawPass);
		return rawPass;
	}

	@Override
	public boolean isPasswordValid(String encPass, String rawPass, Object salt) {
		logger.info("{} - {}", encPass, rawPass);
		if (encPass == null)
			return false;
		return encPass.equals(encodePassword(rawPass, null));
	}

}
