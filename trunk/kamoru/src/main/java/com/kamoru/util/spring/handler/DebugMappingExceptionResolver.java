package com.kamoru.util.spring.handler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

public class DebugMappingExceptionResolver extends SimpleMappingExceptionResolver {
	protected static final Log logger = LogFactory.getLog(DebugMappingExceptionResolver.class);

	@Override
	protected ModelAndView doResolveException(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex) {
		logger.error("", ex);
		return super.doResolveException(request, response, handler, ex);
	}

	
}
