package jk.springframework.web.servlet.handler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

/**{@link SimpleMappingExceptionResolver} 소스 베이스에 slf4j 로그 추가
 * @author kamoru
 */
public class DebugMappingExceptionResolver extends SimpleMappingExceptionResolver {
	protected static final Logger logger = LoggerFactory.getLogger(DebugMappingExceptionResolver.class);

	@Override
	protected ModelAndView doResolveException(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex) {
		logger.error("Error - ", ex);
		return super.doResolveException(request, response, handler, ex);
	}

	
}
