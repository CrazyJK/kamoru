package com.hs.alice.web.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.authentication.BadCredentialsException;

import com.hs.alice.auth.domain.AuthUser;
import com.hs.alice.auth.service.AuthService;
import com.hs.alice.util.web.WebUtils;

public class AliceAuthenticationFailureHandler extends
		SimpleUrlAuthenticationFailureHandler implements
		AuthenticationFailureHandler {

	private static final Log logger = LogFactory.getLog(AliceAuthenticationFailureHandler.class);

	@Autowired AuthService authService;
	@Value("#{security[UsePasswordFailure]}") boolean usePasswordFailure;
	@Value("#{security[MaxPasswordFailureCount]}") int maxPasswordFailureCount;
	
	/** 로그인 실패시 처리<br>
	 *  패스워드 틀리면 failcount 올림
	 */
	@Override
	public void onAuthenticationFailure(HttpServletRequest request,
			HttpServletResponse response, AuthenticationException exception)
			throws IOException, ServletException {
		
		String username = WebUtils.getUTF8EncodingString(request.getParameter("j_username"));
		
		logger.info(username + " 로그인 실패 : " + exception);

		if(exception instanceof BadCredentialsException) {
			logger.info("usePasswordFailure=" + usePasswordFailure);
			if(usePasswordFailure) {
				AuthUser authUser = authService.getUserByUsername(username);
				authUser.drivePasswordFailure(maxPasswordFailureCount);
				authService.updateUser(authUser);
				
				logger.info("패스워스 실패 처리");
			}
		}
		
		super.onAuthenticationFailure(request, response, exception);
	}

}
