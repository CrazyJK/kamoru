package jk.kamoru.web.servlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.resource.ResourceHttpRequestHandler;

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
			logger.info("{}.{} Elapsed time {} ms - RemoteAddr [{}]",
						method.getBean().getClass().getSimpleName(), 
						method.getMethod().getName(), 
						elapsedtime,
						request.getRemoteAddr()
			);
		}
		else if (handler instanceof org.springframework.web.servlet.resource.ResourceHttpRequestHandler) {
			ResourceHttpRequestHandler requestHandler = (ResourceHttpRequestHandler)handler;
			logger.info("{} elapsedtime={}", requestHandler, elapsedtime);
		}
	}

}
