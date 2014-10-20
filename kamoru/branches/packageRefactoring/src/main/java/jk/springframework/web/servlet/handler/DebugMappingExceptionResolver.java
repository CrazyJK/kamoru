package jk.springframework.web.servlet.handler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lombok.extern.slf4j.Slf4j;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

/**
 * {@link SimpleMappingExceptionResolver} 소스 베이스에 slf4j 로그 추가
 * 
 * @author kamoru
 */
@Slf4j
public class DebugMappingExceptionResolver extends SimpleMappingExceptionResolver {

	@Override
	protected ModelAndView doResolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
		log.error("Error - ", ex);
		return super.doResolveException(request, response, handler, ex);
	}

}
