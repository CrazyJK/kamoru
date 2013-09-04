package com.hs.alice.web.security;

import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

import com.hs.alice.auth.domain.AuthUser;
import com.hs.alice.auth.service.AuthService;

public class AliceAuthenticationSuccessHandler extends
		SavedRequestAwareAuthenticationSuccessHandler implements
		AuthenticationSuccessHandler {

	@Autowired AuthService authService;
	
	/**
	 * 로그인 성공후 작업 수행<br/>
	 * 계정 관련 flag 초기화
	 */
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request,
			HttpServletResponse response, Authentication authentication)
			throws ServletException, IOException {
		
		AliceUserDetails user = (AliceUserDetails)authentication.getPrincipal();
		AuthUser authUser = user.getAuthUser();
		authUser.initializeFlag();
		authUser.setLastlogindate(new Date());
		
		this.authService.updateUser(authUser);
		
		super.onAuthenticationSuccess(request, response, authentication);
	}

	
}
