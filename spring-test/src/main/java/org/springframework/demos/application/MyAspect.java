package org.springframework.demos.application;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class MyAspect {
	@Pointcut("@annotation(org.springframework.demos.application.Loggable)")
	public void LoggableMethodPointCut() {
	}

	@Pointcut("execution(* org.springframework.demos.application.User.*(..))")
	public void logBeforeMethodExecutionPointCut() {
	}

	@Before("execution(* org.springframework.demos.application.User.*(..))")
	public void logBeforeMethodExecution() {
		System.out.println("[Aspect]log with method execution");
	}

	@Before("LoggableMethodPointCut()")
	public void logWithAnnotation() {
		System.out.println("[Aspect]log with annotation");
	}
}
