package jk.springframework.security.web.authentication;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

public class KamoruAuthenticationSuccessHandler extends
		SavedRequestAwareAuthenticationSuccessHandler {

	private static final Logger logger = LoggerFactory.getLogger(KamoruAuthenticationSuccessHandler.class);

    @Override
    public void onAuthenticationSuccess(
    		HttpServletRequest request, 
    		HttpServletResponse response,
            Authentication authentication) throws ServletException, IOException {

    	logger.info("log in {}. role={}", authentication.getName(), authentication.getAuthorities());
    	
    	logger.debug("call super.onAuthenticationSuccess");
        super.onAuthenticationSuccess(request, response, authentication);
    }

}
