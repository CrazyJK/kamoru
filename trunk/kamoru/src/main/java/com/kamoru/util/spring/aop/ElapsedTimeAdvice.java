package com.kamoru.util.spring.aop;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ElapsedTimeAdvice implements MethodInterceptor {

	protected static final Log logger = LogFactory.getLog(ElapsedTimeAdvice.class);
	
	@Override
	public Object invoke(MethodInvocation invocation) throws Throwable {
		long t1 = System.currentTimeMillis();
		invocation.proceed();
		long elapsedTime = System.currentTimeMillis() - t1;
		String className = invocation.getThis().getClass().getSimpleName();
		String methodName = invocation.getMethod().getName();
		logger.info(className + "." + methodName + " elasped time : " + elapsedTime + " ms");
		return null;
	}

}
