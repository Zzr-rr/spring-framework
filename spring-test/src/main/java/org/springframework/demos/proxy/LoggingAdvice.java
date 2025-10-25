package org.springframework.demos.proxy;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.jspecify.annotations.Nullable;

public class LoggingAdvice implements MethodInterceptor {

	@Override
	public @Nullable Object invoke(MethodInvocation invocation) throws Throwable {
		System.out.println("Before" + invocation.getMethod().getName());
		Object result = invocation.proceed();
		System.out.println("After" + invocation.getMethod().getName());
		return result;
	}
}
