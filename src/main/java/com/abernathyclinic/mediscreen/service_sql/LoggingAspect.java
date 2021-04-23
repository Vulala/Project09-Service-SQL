package com.abernathyclinic.mediscreen.service_sql;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.abernathyclinic.mediscreen.service_sql.exception.PatientNotFoundException;

/**
 * LoggingAspect is a class which use Spring Boot AOP dependency. <br>
 * AOP is used here to handle all the logging for the controller package. <br>
 */
@Component
@Aspect
public class LoggingAspect {

	private static final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

	@Pointcut("execution(* com.abernathyclinic.mediscreen.service_sql.controller.*.*(..))")
	protected void controller() {
		// Allow us to write once and for all the package and/or class targeted with the
		// differents advices, making the path clearer in the advices definition.
	}

	@Before("controller()")
	public void logBeforeTheMethod(JoinPoint joinPoint) {
		Signature signature = joinPoint.getSignature();
		String className = signature.getDeclaringType().getSimpleName();
		String methodName = signature.getName();

		logger.info("Execution of {} in the {} class", methodName, className);
	}

	@AfterReturning("controller()")
	public void logAfterTheMethodEndWithASuccess(JoinPoint joinPoint) {
		logger.info("The execution has ended successfully");
	}

	@AfterThrowing(pointcut = "controller()", throwing = "patientNotFoundException")
	public void logAfterTheMethodEndWithAnError(JoinPoint joinPoint,
			PatientNotFoundException patientNotFoundException) {
		Signature signature = joinPoint.getSignature();
		String className = signature.getDeclaringType().getSimpleName();
		String methodName = signature.getName();

		logger.info("The execution of {} in the {} class has failed", methodName, className, patientNotFoundException);
	}
}
