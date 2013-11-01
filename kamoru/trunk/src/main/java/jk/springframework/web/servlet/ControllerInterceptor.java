package jk.springframework.web.servlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jk.kamoru.util.StringUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

public class ControllerInterceptor implements HandlerInterceptor {

	private static final Logger logger = LoggerFactory.getLogger(ControllerInterceptor.class);

	private long startTime;
	
	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		startTime = System.currentTimeMillis();
//		logger.info(handler.getClass().getName());
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
//		logger.info(handler.getClass().getName());

	}

	@Override
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		long elapsedtime = System.currentTimeMillis() - startTime;
		if ( handler instanceof org.springframework.web.method.HandlerMethod) {
			HandlerMethod method = (HandlerMethod)handler;
			logger.debug("{}.{} - {} - {} - {} - {} - {}",
					String.format( "%15s", method.getBean().getClass().getSimpleName()), 
					String.format("%-18s", method.getMethod().getName()), 
					String.format(  "%4s", elapsedtime),
					String.format("%-10s", request.getRemoteAddr()),
					String.format( "%-6s", request.getMethod()),
					String.format( "%25s", StringUtils.trimToEmpty(response.getContentType())),
					request.getRequestURI()
			);
			
		}
		/* static resources
		else if (handler instanceof org.springframework.web.servlet.resource.ResourceHttpRequestHandler) {
			ResourceHttpRequestHandler requestHandler = (ResourceHttpRequestHandler)handler;
			logger.info("{} elapsedtime={}", requestHandler, elapsedtime);
		}
		*/
	}

}
