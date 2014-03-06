package jk.springframework.web.servlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jk.kamoru.util.StringUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.resource.ResourceHttpRequestHandler;

/**Controller 호출 전, 후에 추가적인 처리를 한다.<br>
 * 호출 전 시간을 기억해 완료 후에 access log형식으로 로그에 기록한다.
 * @author kamoru
 *
 */
public class ControllerInterceptor implements HandlerInterceptor {

	private static final Logger logger = LoggerFactory.getLogger(ControllerInterceptor.class);

	private long startTime;
	
	/* (non-Javadoc)
	 * set start time millis
	 * @see org.springframework.web.servlet.HandlerInterceptor#preHandle(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, java.lang.Object)
	 */
	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		startTime = System.currentTimeMillis();
//		logger.info(handler.getClass().getName());
		return true;
	}

	/* (non-Javadoc)
	 * do nothing
	 * @see org.springframework.web.servlet.HandlerInterceptor#postHandle(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, java.lang.Object, org.springframework.web.servlet.ModelAndView)
	 */
	@Override
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
//		logger.info(handler.getClass().getName());
	}

	/* (non-Javadoc)
	 * print access log
	 * @see org.springframework.web.servlet.HandlerInterceptor#afterCompletion(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, java.lang.Object, java.lang.Exception)
	 */
	@Override
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		long elapsedtime = System.currentTimeMillis() - startTime;
		String accessLog = String.format("[%s] %s %s %s %sms", 
				request.getRemoteAddr(),
				request.getMethod(),
				request.getRequestURI(),
				StringUtils.trimToEmpty(response.getContentType()),
				elapsedtime);
		String optionalInfo = "";

		if ( handler instanceof org.springframework.web.method.HandlerMethod) {
			HandlerMethod method = (HandlerMethod)handler;
			optionalInfo = String.format("[%s.%s]", method.getBean().getClass().getSimpleName(), method.getMethod().getName());
		}
		else if (handler instanceof ResourceHttpRequestHandler) {
			/* static resources 
			ResourceHttpRequestHandler requestHandler = (ResourceHttpRequestHandler)handler;
			optionalInfo = String.format("[%s]", requestHandler);
			*/
		}
		else {
			optionalInfo = String.format("[%s]", handler);
		}
		logger.info("{} {}", accessLog, optionalInfo);
	}

}
