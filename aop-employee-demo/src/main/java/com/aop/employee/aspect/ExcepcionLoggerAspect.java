package com.aop.employee.aspect;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import com.aop.employee.util.MensajeLogUtil;
import com.aop.employee.util.model.Parametros;

@Aspect
@Component
public class ExcepcionLoggerAspect {
	
	private static final Logger LOGGER = Logger.getLogger(ExcepcionLoggerAspect.class.getName());
	
	@AfterThrowing(pointcut = "execution(* com.aop.employee.service.EmployeeManager.*(..))", throwing = "ex")
	public void logError(JoinPoint joinPoint, Throwable ex) {
		
		Signature signature = joinPoint.getSignature();
		String nombreMetodo = signature.getName();
		String nombreClase = signature.getDeclaringTypeName();
		
		//Se forma el mensaje de error generado por el método.
		StringBuffer mensaje = new StringBuffer();
		mensaje.append("Se ejecutó el método '").append(nombreMetodo).append("' y generó las incidencias que se detallan a continuación. ");
		
		MensajeLogUtil.armarDatosMensajeLog(nombreMetodo, nombreClase, null, null, null, mensaje);
		 
		mensaje.append("\n\t\t Mensaje de excepción: '");
		if (ex.getCause() == null) {
			
			mensaje.append(ex.getLocalizedMessage()).append("").append("' ");
			
		} else {
			
			mensaje.append(ex.getCause()).append("").append("' ");
			mensaje.append(ex.getLocalizedMessage()).append(" || ");
			
		}
		
		mensaje.append("\n\t\t Excepción técnica: '").append(ex).append("'");
		
		Parametros parametros = MensajeLogUtil.obtenerParametrosParaExcepcionesLog(joinPoint);
		mensaje.append(parametros.obtenerDescripcionLog());
		
		String stackTrace = ExceptionUtils.getStackTrace(ex);
		mensaje.append("\t\t Stack Trace: \t").append(stackTrace);
		
		LOGGER.log(Level.SEVERE, mensaje.toString());
		
	}
	
}
