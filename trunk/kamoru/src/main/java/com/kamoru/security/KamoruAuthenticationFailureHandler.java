package com.kamoru.security;

import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

public class KamoruAuthenticationFailureHandler extends
		SimpleUrlAuthenticationFailureHandler implements
		AuthenticationFailureHandler {

}
