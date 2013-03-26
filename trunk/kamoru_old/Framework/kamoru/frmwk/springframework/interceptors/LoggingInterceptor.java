package kamoru.frmwk.springframework.interceptors;

import java.lang.reflect.Method;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.aop.MethodBeforeAdvice;

public class LoggingInterceptor implements MethodBeforeAdvice {

	Log logger = LogFactory.getLog(this.getClass());
	@Override
	public void before(Method method, Object[] args, Object target) throws Throwable {
		logger.info("Start method:" + method.getName() + ", arguments:" + ArrayUtils.toString(args) + ", target:" + target);

	}

}
