package com.aop.employee.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {

	@Around("execution(* com.aop.employee.service.EmployeeManager.*(..))")
	public void logAroundAllMethods(ProceedingJoinPoint joinPoint) throws Throwable {
		System.out.println("****LoggingAspect.logAroundAllMethods() : " + joinPoint.getSignature().getName()
				+ ": Before Method Execution");
		try {
			joinPoint.proceed();
		} finally {
			// Do Something useful, If you have
		}
		System.out.println("****LoggingAspect.logAroundAllMethods() : " + joinPoint.getSignature().getName()
				+ ": After Method Execution");
	}

//	@Around("execution(* com.howtodoinjava.app.service.EmployeeManager.getEmployeeById(..))")
//	public void logAroundGetEmployee(ProceedingJoinPoint joinPoint) throws Throwable {
//		System.out.println("****LoggingAspect.logAroundGetEmployee() : " + joinPoint.getSignature().getName()
//				+ ": Before Method Execution");
//		try {
//			joinPoint.proceed();
//		} finally {
//			// Do Something useful, If you have
//		}
//		System.out.println("****LoggingAspect.logAroundGetEmployee() : " + joinPoint.getSignature().getName()
//				+ ": After Method Execution");
//	}

//	@Around("execution(* com.howtodoinjava.app.service.EmployeeManager.createEmployee(..))")
//	public void logAroundCreateEmployee(ProceedingJoinPoint joinPoint) throws Throwable {
//		System.out.println("****LoggingAspect.logAroundCreateEmployee() : " + joinPoint.getSignature().getName()
//				+ ": Before Method Execution");
//		try {
//			joinPoint.proceed();
//		} finally {
//			// Do Something useful, If you have
//		}
//		System.out.println("****LoggingAspect.logAroundCreateEmployee() : " + joinPoint.getSignature().getName()
//				+ ": After Method Execution");
//	}

}
