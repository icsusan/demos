package com.aop.employee.aspect;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

//@Aspect
//@Component
public class ExceptionLoggerPointCut {
	
	private static final Logger LOGGER = Logger.getLogger(ExceptionLoggerPointCut.class.getName());
	
	@AfterThrowing(pointcut = "execution(* com.aop.employee.service.EmployeeManager.*(..))", throwing = "ex")
	public void logError(JoinPoint joinPoint, Throwable ex) {
		
		Signature signature = joinPoint.getSignature();
		String methodName = signature.getName();
		String clazz = signature.getDeclaringTypeName();
		
		//Se forma el mensaje de error generado por el método.
		StringBuilder mensaje = new StringBuilder();
		mensaje.append("El método '").append(methodName).append("' de la clase '").append(clazz);
		mensaje.append("' generó incidencias que se detallan a continuación. ");
		
		
		mensaje.append("\n\t\t Clase: '").append(clazz).append("'");
		mensaje.append("\n\t\t Método: '").append(methodName).append("'");
		mensaje.append("\n\t\t Mensaje de excepción: '");
		if (ex.getCause() == null) {
			
			mensaje.append(ex.getLocalizedMessage()).append("").append("' ");
			
		} else {
			
			mensaje.append(ex.getCause()).append("").append("' ");
			mensaje.append(ex.getLocalizedMessage()).append(" || ");
			
		}
		
		mensaje.append("\n\t\t Excepción técnica: '").append(ex).append("'");
			
		//Si las hay, se agrega todas las variables con sus valores, para que se imprima en el log.
		Object value = null;
		Object [] values = joinPoint.getArgs();
		if (values != null && values.length > 0) {
			
			mensaje.append("\n\t\t Argumentos:La invocación fue realizada con los siguientes argumentos: \n");
			
			for(int i = 0; i < values.length; i++){
			
				value = values[i];
				
				mensaje.append("\t\t Argumento #").append(i).append(" con valor ");
				mensaje.append(" con valor '");
				mensaje.append((value == null ? value : value.toString()));
				mensaje.append("' \n");
		
			}
			
		} else {
			
			mensaje.append(". \n");
			
		}
		
		String stackTrace = ExceptionUtils.getStackTrace(ex);
		
		mensaje.append("\t\t Stack Trace: ").append(stackTrace);
		
		LOGGER.log(Level.SEVERE, mensaje.toString());
		
	}
	
}
