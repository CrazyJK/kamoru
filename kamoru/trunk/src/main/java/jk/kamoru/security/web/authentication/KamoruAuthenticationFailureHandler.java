package jk.kamoru.security.web.authentication;

import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

public class KamoruAuthenticationFailureHandler extends
		SimpleUrlAuthenticationFailureHandler implements
		AuthenticationFailureHandler {

}
