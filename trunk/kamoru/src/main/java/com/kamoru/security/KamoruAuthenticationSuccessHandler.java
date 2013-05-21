package com.kamoru.security;

import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

public class KamoruAuthenticationSuccessHandler extends
		SavedRequestAwareAuthenticationSuccessHandler implements
		AuthenticationSuccessHandler {

}
