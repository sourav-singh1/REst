package com.yash.demo.aop;

import java.util.logging.Logger;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
public class LogService {

	private static final Logger logger = Logger.getLogger(LogService.class.getName());

	@Pointcut(value = "execution(* com.yash.demo.restController.EmployeeRestController.*(..))||"
			+ "execution(* com.yash.demo.serviceImpl.EmployeeServiceImpl.*(..))||"
			+ "execution(* com.yash.demo.dao.EmployeeDAO.*(..))")
	public void logMethod() {
	}

	@Before(value = "logMethod()", argNames = "joinPoint")
	public void logPrint(JoinPoint joinPoint) {
		logger.info("=============Method Executing===========");
		logger.info("Method name:" + joinPoint.getSignature().toString());
		Object[] signatureArgs = joinPoint.getArgs();
		   for (Object signatureArg: signatureArgs) {
		      logger.info("method arguments: " + signatureArg);
		   }
	}

	@AfterThrowing(value = "logMethod()", argNames = "joinPoint")
	public void logThrowing(JoinPoint joinPoint) {
		logger.info("===============Exception Thrown at:==========="+joinPoint.getSignature().getName()+"==============");

	}

	@After(value = "logMethod()",  argNames = "joinPoint")
	public void logOk(JoinPoint joinPoint) {
		logger.info("================Method Executed----"+joinPoint.getSignature().toString()+"================");
	}
}
