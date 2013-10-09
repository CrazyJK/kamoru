package jk.aopalliance.intercept;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ElapsedTimeAdvice implements MethodInterceptor {

	private static final Logger logger = LoggerFactory.getLogger(ElapsedTimeAdvice.class);
	
	@Override
	public Object invoke(MethodInvocation invocation) throws Throwable {
		long t1 = System.currentTimeMillis();
		invocation.proceed();
		long elapsedTime = System.currentTimeMillis() - t1;
		String className = invocation.getThis().getClass().getSimpleName();
		String methodName = invocation.getMethod().getName();
		logger.debug("{}.{} Elapsed time : {} ms", className, methodName, elapsedTime);
		return null;
	}

}
