package jk.springframework.security.authentication.encoding;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.encoding.PasswordEncoder;

/**사용자 패스워드 비교<br>
 * 암호화 적용하지 않고, plain text로 비교
 * @author kamoru
 *
 */
public class HandyPasswordEncoder implements PasswordEncoder {

	private static final Logger logger = LoggerFactory.getLogger(HandyPasswordEncoder.class);

	@Override
	public String encodePassword(String rawPass, Object salt) {
		logger.trace(rawPass);
		return rawPass;
	}

	@Override
	public boolean isPasswordValid(String encPass, String rawPass, Object salt) {
		logger.trace("{} - {}", encPass, rawPass);
		if (encPass == null)
			return false;
		return encPass.equals(encodePassword(rawPass, null));
	}

}
