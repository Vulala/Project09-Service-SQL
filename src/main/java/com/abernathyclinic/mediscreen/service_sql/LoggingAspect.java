package com.abernathyclinic.mediscreen.service_sql;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * LoggingAspect is a class which use Spring Boot AOP dependency. <br>
 * AOP is used here to handle all the logging for the controller package. <br>
 */
@Component
@Aspect
public class LoggingAspect {

	private static final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

	@Around("execution(* com.abernathyclinic.mediscreen.service_sql.controller.*.*(..))")
	public Object logAllControllers(ProceedingJoinPoint proceedingJoinPoint) {
		MethodSignature methodSignature = (MethodSignature) proceedingJoinPoint.getSignature();

		// Retrieve the details of the intercepted method
		String className = methodSignature.getDeclaringType().getSimpleName();
		String methodName = methodSignature.getName();

		Object result = null;
		try {
			result = proceedingJoinPoint.proceed();
		} catch (Throwable e) {
			logger.info("The execution of {} in the {} class have failed.", methodName, className);
			e.printStackTrace();
		}

		logger.info("Execution of {} in the {} class.", methodName, className);

		return result;
	}

}
